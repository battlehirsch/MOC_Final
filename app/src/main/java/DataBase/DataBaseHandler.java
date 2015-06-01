package DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import dataClasses.Address;
import dataClasses.Course;
import dataClasses.University;

/**
 * Created by Norbert on 22.05.2015.
 * ANLEITUNG http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 * To do - Update UniBookmark & Coursebookmark
 */
public class DataBaseHandler extends SQLiteOpenHelper{

    //Logcat tag
    private static final String LOG = "DatabaseHandler";
    //Database Version
    private static final int DATABASE_VERSION = 1;
    //Database Name
    private static final String DATABASE_NAME = "YoUniDB";


    //Table Names
    private static final String TABLE_COURSE = "courses";
    private static final String TABLE_UNIVERSITY = "universities";
    private static final String TABLE_ADRESS = "adresses";
    private static final String TABLE_COURSE_UNIVERSITY = "course_university";
    private static final String TABLE_FILE_VERSION = "file_version";

    //Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_FLAG = "flag";

    //Course column names
    private static final String KEY_TYPE = "type";

    //University column names
    private static final String KEY_ADDRESSID = "addressid";
    private static final String KEY_WEBSITE = "website";

    //Address column names
    private static final String KEY_STREET = "street";
    private static final String KEY_HOUSENUMBER = "housenumber";
    private static final String KEY_ZIP = "zip";
    private static final String KEY_REGION = "region";
    private static final String KEY_COUNTRY = "country";

    //Course_University column names
    private  static final String KEY_COURSEID = "courseid";
    private  static final String KEY_UNIID = "uniid";

    //file_version colum name
    private static final String KEY_FILE_NAME = "filetype";
    private static final String KEY_FILE_VERSION = "fileversion";

    //CREATE TABLE STATEMENTS
    //CREATE TABLE COURSE
    private static final String CREATE_TABLE_COURSE = "CREATE TABLE " + TABLE_COURSE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_TYPE + " TEXT," + KEY_FLAG + " INTEGER)";
    //CREATE TABLE UNIVERSITY
    private static final String CREATE_TABLE_UNIVERSITY = "CREATE TABLE " + TABLE_UNIVERSITY + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                                                            + KEY_ADDRESSID + " INTEGER," + KEY_WEBSITE + " TEXT," + KEY_FLAG + " INTEGER)";
    //CREATE TABLE ADRESS
    private static final String CREATE_TABLE_ADDRESS = "CREATE TABLE " + TABLE_ADRESS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_STREET + " TEXT," + KEY_HOUSENUMBER + " INTEGER,"
                                                        + KEY_ZIP + " INTEGER," + KEY_REGION + " TEXT," + KEY_COUNTRY + " TEXT)";
    //CREATE TABLE COURSE_UNIVERSITY
    private static final String CREATE_TABLE_COURSE_UNIVERSITY = "CREATE TABLE " + TABLE_COURSE_UNIVERSITY + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_COURSEID + " INTEGER," + KEY_UNIID + " INTEGER)";

    //CREATE TABLE FILE_VERSION
    private static final String CREATE_TABLE_FILE_VERSION = String.format("CREATE TABLE %s (%s TEXT, %s REAL)", TABLE_FILE_VERSION, KEY_FILE_NAME, KEY_FILE_VERSION);

    private static DataBaseHandler dbHandler;

    public static DataBaseHandler getInstance(Context con){
        if(dbHandler == null) dbHandler = new DataBaseHandler(con);
        return  dbHandler;
    }

    private DataBaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_COURSE);
        db.execSQL(CREATE_TABLE_UNIVERSITY);
        db.execSQL(CREATE_TABLE_ADDRESS);
        db.execSQL(CREATE_TABLE_COURSE_UNIVERSITY);
        db.execSQL(CREATE_TABLE_FILE_VERSION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNIVERSITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADRESS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE_UNIVERSITY);
        onCreate(db);
    }

    public float getFileVersion(String file){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT MAX(%s) as %s FROM %s WHERE %s = '%s'",KEY_FILE_VERSION,KEY_FILE_VERSION,TABLE_FILE_VERSION, KEY_FILE_NAME, file );
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()) {
            return c.getFloat(c.getColumnIndex(KEY_FILE_VERSION));
        }
        else
            return Float.NaN;

    }

    public void insertFileVersion(String file, float fileVersion){
        SQLiteDatabase db = getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(KEY_FILE_NAME, file);
        values.put(KEY_FILE_VERSION, fileVersion);
        db.insert(TABLE_FILE_VERSION,null, values);

    }
    public boolean createCourse(Course c){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, c.getName());
        values.put(KEY_TYPE, c.getType());

        int flag = 0;
        if(c.isFlag()) flag = 1;

        values.put(KEY_FLAG, flag);

        db.insert(TABLE_COURSE, null, values);

        return  true;
    }

    public ArrayList<Address> queryAllAddresses(){
        ArrayList<Address> adrList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ADRESS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){
            do{
                boolean flag = false;
                Address adr = new Address(c.getInt(c.getColumnIndex(KEY_ID)),
                        c.getString(c.getColumnIndex(KEY_STREET)),
                        c.getInt(c.getColumnIndex(KEY_HOUSENUMBER)),
                        c.getInt(c.getColumnIndex(KEY_ZIP)),
                        c.getString(c.getColumnIndex(KEY_REGION)),
                        c.getString(c.getColumnIndex(KEY_COUNTRY)));
                adrList.add(adr);
            }while (c.moveToNext());
        }
        c.close();
        return adrList;
    }

    public boolean createUniversity(University u){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME,u.getName());
        values.put(KEY_ADDRESSID,u.getAddressId());
        values.put(KEY_WEBSITE,u.getWebsite());

        int flag = 0;
        if(u.isFlag()) flag = 1;

        values.put(KEY_FLAG, flag);

        db.insert(TABLE_UNIVERSITY, null, values);

        return  true;
    }

    public boolean createUniversityFromXml(University u){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME,u.getName());
        values.put(KEY_WEBSITE,u.getWebsite());

        //Lookup if adress already exists
        ArrayList<Address> adr = queryAllAddresses();
        Address toStore = null;
        for (Address a : adr){
            if(a.toString().equals(u.getAddress().toString())) {
                toStore = a;
                break;
            }
        }
        //IF yes store it
        if(toStore != null){
            values.put(KEY_ADDRESSID, toStore.getId());
        }

        //IF not store the adress and geht the id
        else{
            createAddress(u.getAddress());

            adr = queryAllAddresses();
            for (Address a : adr){
                if(a.toString().equals(u.getAddress().toString())) {
                    toStore = a;
                    break;
                }
            }
            values.put(KEY_ADDRESSID, toStore.getId());
        }


        values.put(KEY_FLAG, 0);

        db.insert(TABLE_UNIVERSITY, null, values);

        return  true;
    }

    public boolean createAddress(Address a){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_STREET,a.getStreet());
        values.put(KEY_ZIP,a.getZip());
        values.put(KEY_HOUSENUMBER,a.getHouseNumber());
        values.put(KEY_COUNTRY,a.getCountry());
        values.put(KEY_REGION, a.getRegion());

        db.insert(TABLE_ADRESS, null, values);

        return true;
    }

    public boolean linkCourseToUni(int cid, int uid){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_COURSEID,cid);
        values.put(KEY_UNIID, uid);

        db.insert(TABLE_COURSE_UNIVERSITY,null,values);

        return  true;
    }

    public boolean linkAddressToUni(int aid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ADDRESSID, aid);
        db.insert(TABLE_UNIVERSITY, null, values);
        return false;
    }

    public ArrayList<Course> queryAllCourses(){
        ArrayList<Course> courseList = new ArrayList<Course>();
        String selectQuery = "SELECT * FROM " + TABLE_COURSE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);

        if(c.moveToFirst()){
            do{
                boolean flag = false;
                if(c.getInt(c.getColumnIndex(KEY_FLAG))==1) flag = true;
                Course course = new Course(c.getInt(c.getColumnIndex(KEY_ID)),c.getString(c.getColumnIndex(KEY_NAME)),flag);
                courseList.add(course);
            } while (c.moveToNext());
        }
        c.close();
        return courseList;
    }

    public ArrayList<University> queryAllUniversities(){
        ArrayList<University> uniList = new ArrayList<University>();
        String selectQuery = "SELECT * FROM " + TABLE_UNIVERSITY;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);

        if(c.moveToFirst()){
            do{
                boolean flag = false;
                if(c.getInt(c.getColumnIndex(KEY_FLAG))==1) flag = true;
                University uni = new University(c.getInt(c.getColumnIndex(KEY_ID)),c.getString(c.getColumnIndex(KEY_NAME)),
                        c.getInt(c.getColumnIndex(KEY_ADDRESSID)),c.getString(c.getColumnIndex(KEY_WEBSITE)),flag);
                uni.setAddress(queryAddressById(uni.getAddressId()));
                uniList.add(uni);
            }while (c.moveToNext());
        }
        c.close();
        return  uniList;
    }

    public Address queryAddressById(int aid){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_ADRESS + " WHERE " + KEY_ID + " = " + aid;
        Cursor c = db.rawQuery(selectQuery,null);
        if(c!=null){
            c.moveToFirst();
            Address a = new Address(c.getInt(c.getColumnIndex(KEY_ID)),c.getString(c.getColumnIndex(KEY_STREET)),c.getInt(c.getColumnIndex(KEY_HOUSENUMBER)),c.getInt(c.getColumnIndex(KEY_ZIP)),c.getString(c.getColumnIndex(KEY_REGION)),c.getString(c.getColumnIndex(KEY_COUNTRY)));
            c.close();
            return  a;
        }
        return  null;
    }

    public int updateUniBookmark(University uni, boolean bookmark){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        int flag = 0;

        if(uni.isFlag()) flag = 1;

        values.put(KEY_FLAG, flag);

        return db.update(TABLE_UNIVERSITY, values, KEY_ID + " = ?", new String[] {String.valueOf(uni.getId())});
    }

    public int updateStudiBookmark(Course course, boolean bookmark){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        int flag = 0;

        if(course.isFlag()) flag = 1;

        values.put(KEY_FLAG, flag);

        return db.update(TABLE_COURSE, values, KEY_ID + " = ?", new String[] {String.valueOf(course.getId())});
    }

    public void insertInitialData(){
        createCourse(new Course(1,"Wirtschaftsinformatik","Bachelor",false));
        createCourse(new Course(2,"Informatik","Master",false));
        createCourse(new Course(3,"Biologie","Bachelor",false));

        createAddress(new Address(1, "Welthandelsplatz", 1, 1020, "Wien", "Austria"));

        createUniversity(new University(1, "Wirtschaftsuniversitaet Wien", 1,"http://www.wu.ac.at/",false));

        linkCourseToUni(1, 1);
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    public ArrayList<University> getUniversitiesByName(ArrayList<String> uniNames) {

        ArrayList<University> unis = queryAllUniversities();
        ArrayList<University> returnUni = new ArrayList<>();

       for(String s : uniNames){
           for(University u : unis){
               if(s.equals(u.getName())){
                   returnUni.add(u);
               }
           }
       }

        return returnUni;


    }

    public int getCourseIdByObject(Course course) {
        String query = String.format("SELECT id from %s where %s = '%s' and %s = '%s' ",TABLE_COURSE , KEY_TYPE, course.getType(), KEY_NAME, course.getName());
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return cursor.getInt(cursor.getColumnIndex(KEY_ID));
        }

        else
            return -1;
    }
}
