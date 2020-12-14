package com.allentownblower.common;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.allentownblower.application.AllentownBlowerApplication;
import com.allentownblower.database.DatabaseTable;
import com.allentownblower.database.SqliteHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class JSONObjectHandler {

    private static SqliteHelper myDb;
    private AllentownBlowerApplication allentownBlowerApplication;

    public JSONObjectHandler(Activity act) {

        allentownBlowerApplication = (AllentownBlowerApplication) act.getApplication();

        if (myDb == null)
            myDb = new SqliteHelper(act);
    }

    public static JSONObject getJSONObject(JSONObject jObj, String strKey) {
        try {
            return (jObj.has(strKey) && !jObj.isNull(strKey)) ? jObj.getJSONObject(strKey) : new JSONObject();
        } catch (JSONException e) {
            return new JSONObject();
        }
    }

    public static String getString(JSONObject jObj, String strKey) {
        try {
            return (jObj.has(strKey) && !jObj.isNull(strKey)) ? jObj.getString(strKey) : "";
        } catch (JSONException e) {
            return "";
        }
    }

    public static int getInt(JSONObject jObj, String strKey) {
        try {
            return (jObj.has(strKey) && !jObj.isNull(strKey)) ? jObj.getInt(strKey) : 0;
        } catch (JSONException e) {
            return 0;
        }
    }

    public static boolean getBool(JSONObject jObj, String strKey) {
        try {
            return (jObj.has(strKey) && !jObj.isNull(strKey)) && jObj.getBoolean(strKey);
        } catch (JSONException e) {
            return false;
        }
    }

    public void saveNewIDForRackBlowerInDataBase(JSONObject jsonObject) {
        if (jsonObject.length() > 0){
            myDb.db.beginTransaction();
            try{
//                for (int i = 0; i < arrList.length(); i++) {
//                    JSONObject jObj = arrList.getJSONObject(i);

                ContentValues cv = new ContentValues();
                cv.put(DatabaseTable.COL_RACKBLOWERDETAILS_ID, getInt(jsonObject, ApiHandler.strRackSerialNumberId));
                cv.put(DatabaseTable.COL_RACKBLOWERDETAILS_RACKBLOWERCUSTOMER_ID, getInt(jsonObject, ApiHandler.strRackBlowerCustomerID));
                cv.put(DatabaseTable.COL_RACKBLOWERDETAILS_ABLOWER_SERIAL, getInt(jsonObject, ApiHandler.strRackBlowerABlowerSerial));

                if (myDb.getQueryResultCount("select * from " + DatabaseTable.TBL_RACKBLOWERDETAILS) <= 0){
                    myDb.insert(DatabaseTable.TBL_RACKBLOWERDETAILS, null, cv);
                    cv.clear();
                } else {
                    myDb.update(DatabaseTable.TBL_RACKBLOWERDETAILS, cv, DatabaseTable.COL_RACKBLOWERDETAILS_ID + " = " + getInt(jsonObject, ApiHandler.strRackSerialNumberId));
                    cv.clear();
                }
//                }
                myDb.db.setTransactionSuccessful();
            }catch (Exception e){
                Log.e("jsonError", ""+ e.getMessage());
            } finally {
                myDb.db.endTransaction();
            }
        }
    }
}
