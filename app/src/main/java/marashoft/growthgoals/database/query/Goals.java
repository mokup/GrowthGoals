package marashoft.growthgoals.database.query;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;

import marashoft.growthgoals.database.DBHandler;
import marashoft.growthgoals.database.beans.Goal;

/**
 * Created by Alessandro on 23/10/2017.
 */

public class Goals {
    public static Cursor getData(DBHandler myhelper)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = new String[0];
        Goal.getFields().keySet().toArray(columns);
        Cursor cursor =db.query(Goal.getTableName(),columns,null,null,null,null,null);

        return cursor;
    }

    public static boolean insertDailyGoal(DBHandler myhelper,int id,String goal,String data,boolean archived)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        ContentValues insertValues = new ContentValues();
        insertValues.put("_name", goal);
        insertValues.put("_start_date", data);
        insertValues.put("_end_date", data);
        insertValues.put("_type", 1);
        insertValues.put("_archived", archived?1:0);

        long esito=-1;
        if(id==0) esito = db.insert(Goal.getTableName(), null, insertValues);
        else esito = db.update(Goal.getTableName(), insertValues, "_id=?", new String[]{id + ""});

        return esito>0;
    }

    public static boolean archive(DBHandler myhelper,int id,boolean archive)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        ContentValues insertValues = new ContentValues();
        insertValues.put("_archived", archive?1:0);

        Log.d("DATABASE","update _archived="+insertValues.getAsString("_archived")+ " where id="+id);
        long esito = db.update(Goal.getTableName(), insertValues, "_id=?", new String[]{id + ""});
        Log.d("DATABASE","esito="+esito);
        return esito>0;
    }

    public static Cursor getData4Day(DBHandler myhelper,String data)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = new String[0];
        Goal.getFields().keySet().toArray(columns);
        Cursor cursor =db.query(Goal.getTableName(),columns,"_start_date=?",new String[] {data},null,null,"_id");

        return cursor;
    }

    public static String getAllDays(DBHandler myhelper)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT min(_start_date) _start_date FROM "+Goal.getTableName()+" WHERE _type=1",null);
        cursor.moveToFirst();
        String data=null;
        while (!cursor.isAfterLast())
        {
            data=cursor.getString(cursor.getColumnIndex("_start_date"));
            cursor.moveToNext();
        }
        cursor.close();
        return data;
    }

    public static boolean delete(DBHandler myhelper, int id) {
        SQLiteDatabase db = myhelper.getWritableDatabase();



        Log.d("DATABASE","delete where id="+id);

        long esito = db.delete(Goal.getTableName(),  "_id=?", new String[]{id + ""});
        Log.d("DATABASE","esito="+esito);
        return esito>0;
    }
}
