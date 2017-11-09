package marashoft.growthgoals.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import marashoft.growthgoals.database.beans.Goal;

/**
 * Created by Alessandro on 23/10/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "goals_end_dreams";
    // Contacts table name

    public DBHandler(Context context) {
        //super(context,  DATABASE_NAME, null, DATABASE_VERSION);
        super(context,  Environment.getExternalStorageDirectory()
                + File.separator+DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("DATABASE",context.getDatabasePath(DATABASE_NAME).getAbsolutePath());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String q="CREATE TABLE "+Goal.getTableName()+"(#)";
        String fields="";
        for (String key:Goal.getFields().keySet()
             ) {
            if(!fields.equals("")) fields+=",";
            fields+=key+" "+Goal.getFields().get(key);
        }
        db.execSQL(q.replace("#",fields));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>=2 && oldVersion==1){
            db.execSQL("ALTER TABLE " + Goal.getTableName()+" ADD COLUMN _archived integer default 0");
            db.execSQL("ALTER TABLE " + Goal.getTableName()+" ADD COLUMN _complete real default 0");
            db.execSQL("ALTER TABLE " + Goal.getTableName()+" ADD COLUMN _complete_date text");
            db.execSQL("UPDATE " + Goal.getTableName()+" set _archived = 0");
            db.execSQL("UPDATE " + Goal.getTableName()+" set _complete = 0");
        }
//        db.execSQL("DROP TABLE IF EXISTS " + Goal.getTableName());
//// Creating tables again
//        onCreate(db);
    }
}
