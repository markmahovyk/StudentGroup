package mark.com.studentgroup.data;

import android.database.Cursor;
import android.database.CursorWrapper;

public class AudioCursorWrapper extends CursorWrapper {

    public AudioCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Magazine getMagazine() {
        String id = getString(getColumnIndex(StudentDbSchema.StudentTable.Cols.ID));
        int number = getInt(getColumnIndex(StudentDbSchema.StudentTable.Cols.NUMBER));
        long dateRecording = getLong(getColumnIndex(StudentDbSchema.StudentTable.Cols.DATE_RECORDING));
        String content = getString(getColumnIndex(StudentDbSchema.StudentTable.Cols.CONTENT));
        int idPlayer = getInt(getColumnIndex(StudentDbSchema.StudentTable.Cols.ID_PLAYER));
        int numberShelf = getInt(getColumnIndex(StudentDbSchema.StudentTable.Cols.NUMBER_SHELF));
        int typeStorage = getInt(getColumnIndex(StudentDbSchema.StudentTable.Cols.TYPE_STORAGE));
        String fullName = getString(getColumnIndex(StudentDbSchema.StudentTable.Cols.FULL_NAME));
        String passportData = getString(getColumnIndex(StudentDbSchema.StudentTable.Cols.PASSPORT_DATA));
        double amount = getDouble(getColumnIndex(StudentDbSchema.StudentTable.Cols.AMOUNT_RENTAL_FOR_ONE_DAY));
        int countDays = getInt(getColumnIndex(StudentDbSchema.StudentTable.Cols.COUNT_DAY_OF_RETAIN));

        return new Magazine(id, number, dateRecording, content, idPlayer, numberShelf, typeStorage, fullName, passportData, amount, countDays);
    }

    public Player getPlayer() {
        String id = getString(getColumnIndex(StudentDbSchema.StudentTable.Cols.ID));
        int code = getInt(getColumnIndex(StudentDbSchema.PlayerTable.Cols.CODE));
        String name = getString(getColumnIndex(StudentDbSchema.PlayerTable.Cols.Name));

        return new Player(id, code, name);
    }
}
