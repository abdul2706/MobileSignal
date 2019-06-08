package com.example.abdul.mobilesignal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.LinkedList;

public class DatabaseHandler {

    private static final String TAG = "DatabaseHandler";
    private static SQLiteDatabase sqLiteDatabase;
    private static String DATABASE_NAME = "SignalData";
    private static String TABLE_NAME = "signalTable";

    public static void initialize(Context context) {
        Log.d(TAG, "initialize: starts");
        try {
            sqLiteDatabase = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
//            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INTEGER PRIMARY KEY, signalData DOUBLE, longitude DOUBLE, latitude DOUBLE)");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        Log.d(TAG, "initialize: ends");
    }

    public static void deleteTable(Context context) {
        Log.d(TAG, "deleteTable: starts");
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME);
        Log.d(TAG, "deleteTable: ends");
    }

    public static void insertData(Context context, SignalData signalData) {
        Log.d(TAG, "insertData: starts");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME + " (signalData, longitude, latitude) VALUES (" + SignalData.convertToQuery(signalData) + ")");
        Log.d(TAG, "insertPerson: ends");
    }

//    public static void deletePerson(Context context, int index) {
//        SignalData signalData = MainActivity.getContext().getPersonArrayList().get(index);
//        sqLiteDatabase.execSQL("DELETE FROM personTable WHERE officeLocation = '" + signalData.getPersonData("officeLocation") + "'");
//        Log.d(TAG, "deletePerson: Person Deleted");
//    }

//    public static void updatePerson(Context context, SignalData signalData, String key, String newValue) {
//        sqLiteDatabase.execSQL("UPDATE " + TABLE_NAME + " SET " + key + " = '" + newValue + "' WHERE officeLocation = '" + signalData.getPersonData("regNo") + "'");
//        Log.d(TAG, "updatePerson: Person Updated");
//    }

    public static LinkedList<SignalData> selectSignalData(Context context, String query) {
        Log.d(TAG, "selectSignalData: start");
        LinkedList<SignalData> matchedPeople = new LinkedList<>();
        String[] values = new String[3];
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + query, null);
        Log.d(TAG, "selectSignalData: cursor.length -> " + cursor.getCount());
        String[] cols = cursor.getColumnNames();
        for (String col : cols) {
            Log.d(TAG, "selectSignalData: col -> " + col);
        }
        try {
            cursor.moveToFirst();
            if (cursor.getCount() != 0) {
                do {
                    for (int i = 1; i < cursor.getColumnNames().length; i++) {
                        values[i - 1] = cursor.getString(i);
                    }
                    matchedPeople.add(new SignalData(values));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Toast.makeText(context, "No Signal Data in Database", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        cursor.close();
        Log.d(TAG, "selectSignalData: end");
        return matchedPeople;
    }

//    public static void loadSignalDataToDatabase(Context context) {
//        Log.d(TAG, "loadPersonsToDatabase: starts");
//        ArrayList<SignalData> persons = new ArrayList<>();
//        InputStream inputStream = context.getResources().openRawResource(R.raw.person_data);
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
//        String line;
//        try {
//            bufferedReader.readLine();
//            while ((line = bufferedReader.readLine()) != null) {
//                Log.d(TAG, "loadPersonsToDatabase: line -> " + line);
//                String[] values = line.split(",");
//                persons.add(new Person(values));
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "loadPersonsToDatabase: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        for (Person person : persons) {
//            insertPerson(context, person);
//        }
//        Log.d(TAG, "loadPersonsToDatabase: ends");
//    }

}
