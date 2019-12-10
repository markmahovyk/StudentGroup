package mark.com.studentgroup.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import mark.com.studentgroup.data.StudentDbSchema.StudentTable.Cols;


public class AudioBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "studentBase.db";

    public AudioBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + StudentDbSchema.StudentTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                Cols.ID + ", " +
                Cols.NUMBER + ", " +
                Cols.DATE_RECORDING + ", " +
                Cols.CONTENT + ", " +
                Cols.ID_PLAYER + ", " +
                Cols.NUMBER_SHELF + ", " +
                Cols.TYPE_STORAGE + ", " +
                Cols.FULL_NAME + ", " +
                Cols.PASSPORT_DATA + ", " +
                Cols.AMOUNT_RENTAL_FOR_ONE_DAY + ", " +
                Cols.COUNT_DAY_OF_RETAIN +  ")"
        );

        db.execSQL("create table " + StudentDbSchema.PlayerTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                StudentDbSchema.PlayerTable.Cols.ID + ", " +
                StudentDbSchema.PlayerTable.Cols.CODE + ", " +
                StudentDbSchema.PlayerTable.Cols.Name +  ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}