package mark.com.studentgroup.data;

public class StudentDbSchema {
    public static final class StudentTable {
        public static final String NAME = "student";

        public static final class Cols {
            public static final String ID = "uuid";
            public static final String NUMBER = "number";
            public static final String DATE_RECORDING = "dateRecording";
            public static final String CONTENT = "content";
            public static final String ID_PLAYER = "idPlayer";
            public static final String NUMBER_SHELF = "numberShelf";
            public static final String TYPE_STORAGE = "typeStorage";
            public static final String FULL_NAME = "fullName";
            public static final String PASSPORT_DATA = "passportData";
            public static final String AMOUNT_RENTAL_FOR_ONE_DAY = "amountRentalForOneDay";
            public static final String COUNT_DAY_OF_RETAIN = "countDayOfRetain";
        }
    }

    public static final class PlayerTable {
        public static final String NAME = "playerTable";

        public static final class Cols {
            public static final String ID = "uuid";
            public static final String CODE = "code";
            public static final String Name = "name";

        }
    }
}