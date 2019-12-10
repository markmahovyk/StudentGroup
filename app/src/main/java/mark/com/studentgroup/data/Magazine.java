package mark.com.studentgroup.data;

import java.util.Date;

public class Magazine {
    private String id;
    private int number;
    private long dateRecording;
    private String content;
    private int idPlayer;
    private int numberShelf;
    private int typeStorage; // 0 в прокате, 1 в  аудиотеке
    private String fullName;
    private String passportData;
    private double amountRentalForOneDay;
    private int countDayOfRetain;

    final static int IN_RETAIN = 0;
    final static int IN_AUDIO_LIBRARY = 1;

    public Magazine(String id, int number, long dateRecording, String content, int idPlayer,
                    int numberShelf, int typeStorage, String fullName, String passportData,
                    double amountRentalForOneDay, int countDayOfRetain) {
        this.id = id;
        this.number = number;
        this.dateRecording = dateRecording;
        this.content = content;
        this.idPlayer = idPlayer;
        this.numberShelf = numberShelf;
        this.typeStorage = typeStorage;
        this.fullName = fullName;
        this.passportData = passportData;
        this.amountRentalForOneDay = amountRentalForOneDay;
        this.countDayOfRetain = countDayOfRetain;
    }

    public String  getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getDateRecording() {
        return dateRecording;
    }

    public void setDateRecording(long dateRecording) {
        this.dateRecording = dateRecording;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int getNumberShelf() {
        return numberShelf;
    }

    public void setNumberShelf(int numberShelf) {
        this.numberShelf = numberShelf;
    }

    public int getTypeStorage() {
        return typeStorage;
    }

    public void setTypeStorage(int typeStorage) {
        this.typeStorage = typeStorage;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassportData() {
        return passportData;
    }

    public void setPassportData(String passportData) {
        this.passportData = passportData;
    }

    public double getAmountRentalForOneDay() {
        return amountRentalForOneDay;
    }

    public void setAmountRentalForOneDay(double amountRentalForOneDay) {
        this.amountRentalForOneDay = amountRentalForOneDay;
    }

    public int getCountDayOfRetain() {
        return countDayOfRetain;
    }

    public void setCountDayOfRetain(int countDayOfRetain) {
        this.countDayOfRetain = countDayOfRetain;
    }
}
