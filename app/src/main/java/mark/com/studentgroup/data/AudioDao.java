package mark.com.studentgroup.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static mark.com.studentgroup.data.StudentDbSchema.StudentTable.NAME;

public class AudioDao {

    private static AudioDao studentDAO;
    private Context mContext;
    private SQLiteDatabase database;

    public static AudioDao get(Context context) {
        if (studentDAO == null) {
            studentDAO = new AudioDao(context);
        }
        return studentDAO;
    }

    private AudioDao(Context context) {
        mContext = context.getApplicationContext();
        database = new AudioBaseHelper(mContext).getWritableDatabase();
    }


    public long addStudent(Magazine student) {
        ContentValues values = getContentValuesMagazine(student);
        return database.insert(NAME, null, values);
    }

    public int updateStudent(Magazine student) {
        ContentValues values = getContentValuesMagazine(student);
        return database.update(NAME, values,
                StudentDbSchema.StudentTable.Cols.ID + " = ?",
                new String[]{student.getId()});
    }

    public List<Magazine> getStudents() {
        List<Magazine> students = new ArrayList<>();
        AudioCursorWrapper cursor = queryStudent(NAME, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                students.add(cursor.getMagazine());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return students;
    }

    public Magazine getStudent(String id) {
        AudioCursorWrapper cursor = queryStudent(NAME,
                StudentDbSchema.StudentTable.Cols.ID + " = ?",
                new String[]{id}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getMagazine();
        } finally {
            cursor.close();
        }
    }

    public Object deleteAll() {
        return database.delete(NAME, null, null);
    }

    public int deleteStudent(String id) {
        return database.delete(NAME, StudentDbSchema.StudentTable.Cols.ID + " = ?", new String[]{id});
    }
    public long addPlayer(Player student) {
        ContentValues values = getContentValuesPlayer(student);
        return database.insert(StudentDbSchema.PlayerTable.NAME, null, values);
    }

    public int updatePlayer(Player student) {
        ContentValues values = getContentValuesPlayer(student);
        return database.update(StudentDbSchema.PlayerTable.NAME, values,
                StudentDbSchema.PlayerTable.Cols.ID + " = ?",
                new String[]{student.getId()});
    }

    public List<Player> getPlayer() {
        List<Player> players = new ArrayList<>();
        AudioCursorWrapper cursor = queryStudent(StudentDbSchema.PlayerTable.NAME,null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                players.add(cursor.getPlayer());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return players;
    }

    private AudioCursorWrapper queryStudent(String table, String whereClause, String[] whereArgs) {
        Cursor cursor  = database.query(
                table,
                null,
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new AudioCursorWrapper(cursor);
    }

    private static ContentValues getContentValuesMagazine(Magazine student) {
        ContentValues values = new ContentValues();
        values.put(StudentDbSchema.StudentTable.Cols.ID, student.getId());
        values.put(StudentDbSchema.StudentTable.Cols.NUMBER, student.getNumber());
        values.put(StudentDbSchema.StudentTable.Cols.DATE_RECORDING, student.getDateRecording());
        values.put(StudentDbSchema.StudentTable.Cols.CONTENT, student.getContent());
        values.put(StudentDbSchema.StudentTable.Cols.ID_PLAYER, student.getIdPlayer());
        values.put(StudentDbSchema.StudentTable.Cols.NUMBER_SHELF, student.getNumberShelf());
        values.put(StudentDbSchema.StudentTable.Cols.TYPE_STORAGE, student.getTypeStorage());
        values.put(StudentDbSchema.StudentTable.Cols.FULL_NAME, student.getFullName());
        values.put(StudentDbSchema.StudentTable.Cols.PASSPORT_DATA, student.getPassportData());
        values.put(StudentDbSchema.StudentTable.Cols.AMOUNT_RENTAL_FOR_ONE_DAY, student.getAmountRentalForOneDay());
        values.put(StudentDbSchema.StudentTable.Cols.COUNT_DAY_OF_RETAIN, student.getCountDayOfRetain());
        return values;
    }

    private static ContentValues getContentValuesPlayer(Player student) {
        ContentValues values = new ContentValues();
        values.put(StudentDbSchema.PlayerTable.Cols.ID, student.getId());
        values.put(StudentDbSchema.PlayerTable.Cols.CODE, student.getCode());
        values.put(StudentDbSchema.PlayerTable.Cols.Name, student.getName());
        return values;
    }
}
