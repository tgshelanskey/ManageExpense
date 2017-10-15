package com.example.jasper.manageexpense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.com.example.utilities.CurrencyHandler;
import com.example.com.example.utilities.DateUtil;
import com.example.dataObject.Setting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jasper on 2/24/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ExpenseManager.db";
    public static final String CATEGORY_TABLE_NAME = "Category";
    public static final String CATEGORY_COLUMN_ID = "id";
    public static final String CATEGORY_COLUMN_CATEGORY_NAME = "category_name";

    //shelanskey US1 - Add max budget columm
    public static final String CATEGORY_COLUMN_BUDGET_NAME = "budget";

    //Removed because makes no sense
    //public static final String EXPENSE_COLUMN_CATEGORY_IDENTIFIER = "identifier";

    public static final String EXPENSE_TABLE_ADD = "Add_Expense";
    public static final String EXPENSE_ADD_COLUMN_ID = "add_id";
    public static final String EXPENSE_ADD_COLUMN_CATEGORY_ADD = "category_add";
    public static final String EXPENSE_ADD_COLUMN_AMOUNT = "amount";
    public static final String EXPENSE_ADD_COLUMN_DATE = "date";
    public static final String EXPENSE_ADD_COLUMN_NOTE = "note";



    public static final String EXPENSE_ADD_COLUMN_CURRENCY = "currency"; //PGhale US7: Added column for currency  Add_expense table
    public static final String Expense_ADD_COLUMN_PAYMENT = "payment"; // PGhale :Added column for payment in Add_expense table
    public static final String EXPENSE_ADD_COLUMN_LOCATION = "location"; //PGhale: Added column for location in Add_expense table

    // Shelanskey US4 - add settings table
    public static final String SETTINGS_TABLE_NAME = "settings";
    public static final String SETTINGS_COLUMN_ID = "id";
    public static final String SETTINGS_COLUMN_KEY = "key";
    public static final String SETTINGS_COLUMN_VALUE = "value";


    private HashMap hp;
    private Context con;

    public DBHelper(Context context) {
        super(context, "ExpenseManager", null, 1);
        con = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        //Shelanskey US1 - added budget column to store max budget value
        String createTable = "create table Category (id integer primary key AUTOINCREMENT, category_name, budget);";
        db.execSQL(createTable);

        //pghale US7: Added currency, payment and location columns in Expense table
        String createTableAdd = "create table Add_Expense (add_id integer primary key AUTOINCREMENT, category_add, amount double, date long, note, currency, payment, location);";
        db.execSQL(createTableAdd);


        //Shelanskey US4 - adding table creates for new settings table
        String createTableSettings = "create table " + SETTINGS_TABLE_NAME + " (" + SETTINGS_COLUMN_ID + " integer primary key AUTOINCREMENT, " +
                SETTINGS_COLUMN_KEY + ", " +  SETTINGS_COLUMN_VALUE + " );";
        db.execSQL(createTableSettings);

        //Shelanksey US4 - ensuring that the application starts with a default setting for CURRENCY
        String insertDefaultSettings = "INSERT INTO " + SETTINGS_TABLE_NAME + "(" +SETTINGS_COLUMN_KEY + ", " + SETTINGS_COLUMN_VALUE + ") VALUES ('CURRENCY', 'Dollar')";
        db.execSQL(insertDefaultSettings);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS!");
        onCreate(db);
    }

    public void insertCategory(String category_name, Integer budgetAmount) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CATEGORY_COLUMN_CATEGORY_NAME, category_name);//column name, column value

        //Shelanskey US1 - added budget column need to store new value
        values.put(CATEGORY_COLUMN_BUDGET_NAME, budgetAmount);

        // Inserting Row
        db.insert(CATEGORY_TABLE_NAME, null, values);//tableName, nullColumnHack, ContentValues
        db.close(); // Closing database connection

    }

    public double insertAdd_Expense(String category_add, Double amount, Date date, String note, String currency, String payment, String location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EXPENSE_ADD_COLUMN_CATEGORY_ADD, category_add);
        values.put(EXPENSE_ADD_COLUMN_AMOUNT, CurrencyHandler.convertToDollars(currency ,amount));
        values.put(EXPENSE_ADD_COLUMN_DATE, date.getTime());
        values.put(EXPENSE_ADD_COLUMN_NOTE, note);
        values.put(Expense_ADD_COLUMN_PAYMENT, payment);//pGhale :  inserts payment data in payment column
        values.put(EXPENSE_ADD_COLUMN_CURRENCY, currency); //pGhale US7: It inserts currency data in currency column
        values.put(EXPENSE_ADD_COLUMN_LOCATION, location); //pGhale: It inserts location data in currency column
        db.insert(EXPENSE_TABLE_ADD, null, values);
        db.close();
        double chkValue = checkThreshold(category_add);
        return chkValue;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Category where id=" + id + "", null);
        return res;
    }

    public Cursor getDataAdd(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor_add = db.rawQuery("select * from Category where id_add=" + id + "", null);
        return cursor_add;
    }

    public Cursor getsumExpenseAmount(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor_EA = db.rawQuery("select sum(amount) from Add_Expense where category_add = '$category_add' ", null);
        return cursor_EA;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CATEGORY_TABLE_NAME);
        return numRows;
    }

    public int numberOfRowsAdd() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRowsAdd = (int) DatabaseUtils.queryNumEntries(db, EXPENSE_TABLE_ADD);
        return numRowsAdd;
    }

    //Shelanskey US1 - New method to handle budget amount
    public boolean updateCategory(Integer id, String name, Integer budget) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_COLUMN_ID, id);
        contentValues.put(CATEGORY_COLUMN_CATEGORY_NAME, name);

        //Shelanskey US1 - added new column
        contentValues.put(CATEGORY_COLUMN_BUDGET_NAME, budget);

        db.update(CATEGORY_TABLE_NAME, contentValues, CATEGORY_COLUMN_ID + "=" +id, null);
        return true;
    }

    //Keeping original method signature for default category creation with a 0 budget
    //TODO change flow on category add
    public boolean updateCategory(Integer id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_COLUMN_ID, id);
        contentValues.put(CATEGORY_COLUMN_CATEGORY_NAME, name);


        db.update(CATEGORY_TABLE_NAME, contentValues, CATEGORY_COLUMN_ID + "=" +id, null);
        return true;
    }

    public boolean updateAddExpense(Integer id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(EXPENSE_ADD_COLUMN_ID, id);
        contentValues.put(EXPENSE_ADD_COLUMN_CATEGORY_ADD, name);

        db.update(EXPENSE_TABLE_ADD, contentValues, CATEGORY_COLUMN_ID + "=" +id , new String[]{name});
        return true;
    }

    public boolean updateCategoryAdd(Integer id, String category_add, Double amount, Date date, String note, String currency) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXPENSE_ADD_COLUMN_ID, id);
        contentValues.put(EXPENSE_ADD_COLUMN_CATEGORY_ADD, category_add);

        //Shelanskey, Ghale US4, US6 - All expenses get saved in Dollars to normalize sum, history, and budget calculations
        contentValues.put(EXPENSE_ADD_COLUMN_AMOUNT, CurrencyHandler.convertToDollars(currency ,amount));

        contentValues.put(EXPENSE_ADD_COLUMN_DATE, date.getTime());
        contentValues.put(EXPENSE_ADD_COLUMN_NOTE, note);


        db.update(EXPENSE_TABLE_ADD, contentValues,EXPENSE_ADD_COLUMN_ID+ "=" +id , null );
        return true;
    }

    public boolean deleteCategory(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CATEGORY_TABLE_NAME, CATEGORY_COLUMN_CATEGORY_NAME + "='" + value +"' ;", null) > 0;
    }

    public boolean deleteAddCategory(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(EXPENSE_TABLE_ADD, EXPENSE_ADD_COLUMN_CATEGORY_ADD + "='" + value +"' ;", null) > 0;
    }

    public int deleteAddExpense(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(EXPENSE_TABLE_ADD, EXPENSE_ADD_COLUMN_ID +"=" + id , null);

    }

    public ArrayList<String> getAllCategory() {
        ArrayList<String> array_list = new ArrayList<String>();
        hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Category", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(CATEGORY_COLUMN_CATEGORY_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
    public List<Overview_ListView> getOverviewList() {
        Overview_ListView overList = null;

        //Shelanskey, Ghale US4, US6 - All expenses get saved in Dollars to normalize sum, history, and budget calculations
        String currencyType = getSetting("CURRENCY");

        List<Overview_ListView> listOverview = new ArrayList<>();
        hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT category_add, date, note,  SUM(amount) AS total  FROM Add_Expense  Group by category_add ORDER by date desc ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Date readDate = new Date(cursor.getLong(cursor.getColumnIndexOrThrow("date")));
            overList = new Overview_ListView(cursor.getInt(0), cursor.getString(cursor.getColumnIndexOrThrow("category_add")), CurrencyHandler.convertToNative(currencyType, cursor.getDouble(cursor.getColumnIndexOrThrow("total"))),
                     readDate, cursor.getString(cursor.getColumnIndexOrThrow("note")));
            listOverview.add(overList);
            cursor.moveToNext();

        }
        cursor.close();
        db.close();
        return listOverview;
    }

    public List<TabHistory_Week_List> getHistoryWeek() {

        //Shelanskey, Ghale US4, US6 - All expenses get saved in Dollars to normalize sum, history, and budget calculations
        String currencyType = getSetting("CURRENCY");

        TabHistory_Week_List sample = null;

        List<TabHistory_Week_List> sampleList = new ArrayList<>();
        hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Add_Expense  ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Date readDate = new Date(cursor.getLong(cursor.getColumnIndexOrThrow("date")));
            sample = new TabHistory_Week_List(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), readDate, cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
            //pGhale : getting currency value from the add_expense table
            sample.setAmount(CurrencyHandler.convertToNative(currencyType, sample.getAmount()));
            sampleList.add(sample);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return sampleList;
    }

    public List<Graph_all_List> getPieGrapgListView() {
        Graph_all_List overList = null;

        List<Graph_all_List> listOverview = new ArrayList<>();
        hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT category_add, SUM(amount) AS total  FROM Add_Expense GROUP BY category_add", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            overList = new Graph_all_List(cursor.getInt(0), cursor.getString(cursor.getColumnIndexOrThrow("category_add")), cursor.getInt(cursor.getColumnIndexOrThrow("total")));
            listOverview.add(overList);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return listOverview;
    }

    public List<Tab1_ListView> getCategoryName() {
        Tab1_ListView overList = null;

        List<Tab1_ListView> listOverview = new ArrayList<>();
        hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Category", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            overList = new Tab1_ListView(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
            listOverview.add(overList);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return listOverview;
    }

    public List<TabHistory_Week_List> getHistoryList(){
        TabHistory_Week_List tab = null;

        List<TabHistory_Week_List> listArrayList = new ArrayList<>();
        hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT *  FROM Add_Expense ", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            //pGhale US6: Retrieving value of currency for History page
            Date readDate = new Date(cursor.getLong(cursor.getColumnIndexOrThrow("date")));
            tab = new TabHistory_Week_List(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), readDate, cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));

            listArrayList.add(tab);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return listArrayList;
    }

    public List<Edit_expense_List> getAllExpenses() {
        Edit_expense_List list = null;

        List<Edit_expense_List> listArray = new ArrayList<>();
        hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT *  FROM Add_Expense ", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Date readDate = new Date(cursor.getLong(cursor.getColumnIndexOrThrow("date")));
            list = new Edit_expense_List(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), readDate, cursor.getString(4),cursor.getString(5));
            listArray.add(list);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return listArray;
    }

    public List<List_All> getAllList() {
        List_All overList = null;

        List<List_All> listOverview = new ArrayList<>();
        hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT category_name, SUM(amount) AS total, date FROM Add_Expense " , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Date readDate = new Date(cursor.getLong(cursor.getColumnIndexOrThrow("date")));
            overList = new List_All(cursor.getInt(0), cursor.getString(cursor.getColumnIndexOrThrow("category_name")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("total")), readDate );
            listOverview.add(overList);
            cursor.moveToNext();

        }
        cursor.close();
        db.close();
        return listOverview;
    }

    //shelanskey US4 - method for inserting new values into the settings table
    public void insertSetting(String setting_name, String setting_value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SETTINGS_COLUMN_KEY, setting_name);//column name, column value
        values.put(SETTINGS_COLUMN_VALUE, setting_value);

        // Inserting Row
        db.insert(SETTINGS_TABLE_NAME, null, values);//tableName, nullColumnHack, ContentValues
        db.close(); // Closing database connection
    }

    //shelanskey US4 - method for updating settings value
    public boolean updateSetting(String setting_name, String setting_value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(SETTINGS_COLUMN_VALUE, setting_value);

        db.update(SETTINGS_TABLE_NAME, contentValues, SETTINGS_COLUMN_KEY + "=?", new String[] {setting_name});
        return true;
    }

    //shelanskey US4 - method for getting all settings
    public List<com.example.dataObject.Setting> getAllSettings() {
        List<Setting> listArray = new ArrayList<>();
        Setting setting;
        hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT *  FROM " + SETTINGS_TABLE_NAME, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            setting = new Setting(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            listArray.add(setting);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        return listArray;
    }

    //shelanskey US4 - method for getting a specific settings
    public String getSetting(String setting_name) {
        Setting setting;
        hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT *  FROM " + SETTINGS_TABLE_NAME + " where " + SETTINGS_COLUMN_KEY + "=?", new String[] {setting_name});
        cursor.moveToFirst();
        setting = new Setting(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        cursor.close();
        db.close();

        return setting.getSetting_value();
    }

    private double getBudgetAmount(String category){
        double budgetAmount;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + CATEGORY_COLUMN_BUDGET_NAME + "  from " + CATEGORY_TABLE_NAME + " where " +
                CATEGORY_COLUMN_CATEGORY_NAME + " = \"" + category +"\"", null);
        cursor.moveToFirst();
        budgetAmount = cursor.getDouble(0);
        cursor.close();
        db.close();
        return budgetAmount;
    }

    private double checkThreshold(String category){
        double overageAmount = 0.0;
        double expenseAmount, budgetAmount;
        long firstOfMonth = DateUtil.getFirstDayOfMonth().getTime();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select sum(amount) from Add_Expense where category_add = \"" + category + "\" and date > " + firstOfMonth, null);
        cursor.moveToFirst();
        expenseAmount = cursor.getDouble(0);
        cursor.close();
        db.close();
        budgetAmount = getBudgetAmount(category);
        overageAmount = budgetAmount - expenseAmount;
        return overageAmount;
    }
}
