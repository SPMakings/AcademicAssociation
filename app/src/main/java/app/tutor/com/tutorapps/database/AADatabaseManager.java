package app.tutor.com.tutorapps.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;

import app.tutor.com.tutorapps.helper.Logger;
import app.tutor.com.tutorapps.pojo.ReportDataModel;

/**
 * Created by Saikat's Mac on 08/05/16.
 */

public class AADatabaseManager extends SQLiteOpenHelper {


    Context mContext = null;


    private final String TAG = "AADatabaseManager";
    //----------Table description

    protected final String TABLE_NAME = "ExamReport";

    protected final String EXAM_DATE = "EXAM_DATE";
    protected final String EXAM_SUBJECT = "EXAM_SUBJECT";
    protected final String EXAM_QUN_SET = "EXAM_QUN_SET";
    protected final String EXAM_TOTAL = "EXAM_TOTAL";
    protected final String EXAM_CORRECT = "EXAM_CORRECT";
    protected final String EXAM_WRONG = "EXAM_WRONG";



    public AADatabaseManager(Context context) {
        super(context, "androidsqlite.db", null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlQueryReport = "CREATE TABLE " + TABLE_NAME
                + " (id INTEGER PRIMARY KEY,"
                + EXAM_DATE + " TEXT,"
                + EXAM_SUBJECT + " TEXT,"
                + EXAM_QUN_SET + " TEXT,"
                + EXAM_TOTAL + " INTEGER,"
                + EXAM_CORRECT + " INTEGER,"
                + EXAM_WRONG + " INTEGER)";

        try {
            db.execSQL(sqlQueryReport);
        } catch (Exception e) {
            Logger.showMessage(TAG, e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void inserReportData(final ReportDataModel reportData_) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(EXAM_DATE, reportData_.getExamDate());
            values.put(EXAM_SUBJECT, reportData_.getExamSubject());
            values.put(EXAM_QUN_SET, reportData_.getQuestionSet());
            values.put(EXAM_TOTAL, reportData_.getTotalQuestion());
            values.put(EXAM_CORRECT, reportData_.getCorrectAns());
            values.put(EXAM_WRONG, reportData_.getWrongAns());

            db.insert(TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }


    public LinkedList<ReportDataModel> getAllData(final String matcher) {
        LinkedList<ReportDataModel> temp_ = new LinkedList<ReportDataModel>();
        SQLiteDatabase db = null;
        try {
            db = this.getReadableDatabase();
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + EXAM_SUBJECT + "='" + matcher + "'";
            SQLiteCursor cursor = (SQLiteCursor) db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    ReportDataModel tm_ = new ReportDataModel();
                    tm_.setCorrectAns(cursor.getInt(cursor.getColumnIndex(EXAM_CORRECT)));
                    tm_.setTotalQuestion(cursor.getInt(cursor.getColumnIndex(EXAM_TOTAL)));
                    tm_.setWrongAns(cursor.getInt(cursor.getColumnIndex(EXAM_WRONG)));

                    tm_.setExamDate(cursor.getString(cursor.getColumnIndex(EXAM_DATE)));
                    tm_.setExamSubject(cursor.getString(cursor.getColumnIndex(EXAM_SUBJECT)));
                    tm_.setQuestionSet(cursor.getString(cursor.getColumnIndex(EXAM_QUN_SET)));

                    temp_.add(tm_);
                }
            } else {
                Logger.showMessage(TAG, "Exist..insertDatatoSmartGalleryTable()");
            }
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return temp_;
    }

}
