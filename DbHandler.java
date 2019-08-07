

package kupers800.trainingslog.mainlogcode;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kupers800 helped by code of tutlane on 05-01-2018.
 */

public class DbHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "usersdb";
    private static final String TABLE_Users = "userdetails";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LOC = "location";
    private static final String KEY_DESG = "designation";
    private static final String KEY_DATE = "date";
    private static final String KEY_LABEL_ONE = "labelOne";
    private static final String KEY_LABEL_TWO = "labelTwo";
    private static final String KEY_LABEL_THREE = "labelThree";
    public DbHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_LOC + " TEXT,"
                + KEY_DESG + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_LABEL_ONE + " TEXT,"
                + KEY_LABEL_TWO + " TEXT,"
                + KEY_LABEL_THREE + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        // Create tables again
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new User Details
    void insertUserDetails(String name, String location, String designation, String date, String labelOne, String labelTwo, String labelThree){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put(KEY_LOC, location);
        cValues.put(KEY_DESG, designation);
        cValues.put(KEY_DATE, date);
        cValues.put(KEY_LABEL_ONE, labelOne);
        cValues.put(KEY_LABEL_TWO, labelTwo);
        cValues.put(KEY_LABEL_THREE, labelThree);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Users,null, cValues);
        db.close();
    }
    // Get User Details
    public ArrayList<HashMap<String, String>> GetUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT id, name, location, designation, date, labelOne, labelTwo, labelThree FROM "+ TABLE_Users;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("id",cursor.getString(cursor.getColumnIndex(KEY_ID)));
            user.put("name",cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put("designation",cursor.getString(cursor.getColumnIndex(KEY_DESG)));
            user.put("location",cursor.getString(cursor.getColumnIndex(KEY_LOC)));
            user.put("date",cursor.getString(cursor.getColumnIndex(KEY_DATE)));
            user.put("labelOne",cursor.getString(cursor.getColumnIndex(KEY_LABEL_ONE)));
            user.put("labelTwo",cursor.getString(cursor.getColumnIndex(KEY_LABEL_TWO)));
            user.put("labelThree",cursor.getString(cursor.getColumnIndex(KEY_LABEL_THREE)));
            userList.add(user);
        }
        return  userList;
    }
    // Get User Details based on userid
    public ArrayList<HashMap<String, String>> GetUserByUserId(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT name, location, designation FROM "+ TABLE_Users;
        Cursor cursor = db.query(TABLE_Users,
                new String[]{KEY_NAME, KEY_LOC, KEY_DESG, KEY_DATE,
                        KEY_LABEL_ONE,KEY_LABEL_TWO,KEY_LABEL_THREE}
                        , KEY_ID+ "=?",new String[]{String.valueOf(userid)},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();

            user.put("name",cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put("designation",cursor.getString(cursor.getColumnIndex(KEY_DESG)));
            user.put("location",cursor.getString(cursor.getColumnIndex(KEY_LOC)));
            user.put("date",cursor.getString(cursor.getColumnIndex(KEY_DATE)));
            user.put("labelOne",cursor.getString(cursor.getColumnIndex(KEY_LABEL_ONE)));
            user.put("labelTwo",cursor.getString(cursor.getColumnIndex(KEY_LABEL_TWO)));
            user.put("labelThree",cursor.getString(cursor.getColumnIndex(KEY_LABEL_THREE)));
            userList.add(user);
        }
        return  userList;
    }
    // Delete User Details
    public void DeleteUser(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Users, KEY_ID+" = ?",new String[]{String.valueOf(userid)});
        db.close();
    }
    // Update User Details
    public int UpdateUserDetails(String location, String designation, String date, String labelOne, String labelTwo, String labelThree, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_LOC, location);
        cVals.put(KEY_DESG, designation);
        cVals.put(KEY_DATE, date);
        cVals.put(KEY_LABEL_ONE, labelOne);
        cVals.put(KEY_LABEL_TWO, labelTwo);
        cVals.put(KEY_LABEL_THREE, labelThree);
        int count = db.update(TABLE_Users, cVals, KEY_ID+" = ?",new String[]{String.valueOf(id)});
        return  count;
    }

}
