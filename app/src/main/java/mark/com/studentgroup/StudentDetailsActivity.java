package mark.com.studentgroup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import mark.com.studentgroup.data.Magazine;
import mark.com.studentgroup.data.AudioDao;
import mark.com.studentgroup.data.Player;
import mark.com.studentgroup.data.TypeStorage;

public class StudentDetailsActivity extends AppCompatActivity {
    private static String ARG_ID = "arg_id";
    private EditText number;
    private EditText dateRecording;
    private EditText content;
    private AppCompatSpinner idPlayer;
    private EditText numberShelf;
    private AppCompatSpinner typeStorageSpinner; // 0 в прокате, 1 в  аудиотеке
    private EditText fullName;
    private EditText passportData;
    private EditText amountRentalForOneDay;
    private EditText countDayOfRetain;
    private Magazine magazine;
    private List<Player> players;
    Button btnSave;
    CompositeDisposable disposable = new CompositeDisposable();


    public static Intent newInstance(Context context, String id) {
        Intent intent = new Intent(context, StudentDetailsActivity.class);
        intent.putExtra(ARG_ID, id);
        return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        number = findViewById(R.id.input_number);
        dateRecording = findViewById(R.id.input_dateRecording);
        content = findViewById(R.id.input_content);
        idPlayer = findViewById(R.id.input_code);
        numberShelf = findViewById(R.id.input_number_shelf);
        typeStorageSpinner = findViewById(R.id.input_type_storage);
        fullName = findViewById(R.id.input_full_name);
        passportData = findViewById(R.id.input_passport_data);
        amountRentalForOneDay = findViewById(R.id.input_amount);
        countDayOfRetain = findViewById(R.id.input_count_day);

        btnSave = findViewById(R.id.btn_save);
        btnSave.setSelected(true);
        btnSave.setOnClickListener(v -> saveUser());

        setAdapterTypeStorage();

        getPlayer();

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(ARG_ID)) {
            getStudentLocal(getIntent().getExtras().getString(ARG_ID));
        }
    }

    private void setAdapterTypeStorage() {
        ArrayAdapter<Player> adapter =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item, TypeStorage.getListType());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeStorageSpinner.setAdapter(adapter);
    }

    private void getPlayer() {
        disposable.add(Observable.just(AudioDao.get(this).getPlayer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(players -> {
                    this.players = players;
                    ArrayAdapter<Player> adapter =
                            new ArrayAdapter(this, android.R.layout.simple_spinner_item, players);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    idPlayer.setAdapter(adapter);
                    if (magazine != null) {
                        int code = magazine.getIdPlayer();
                        for (Player player : players) {
                            if (player.getCode() == code) {
                                idPlayer.setSelection(players.indexOf(player));
                            }
                        }
                    }
                }, throwable ->throwable.printStackTrace()));
    }

    private void getStudentLocal(String id) {
               disposable.add(Observable.just(AudioDao.get(this).getStudent(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(student -> {
                    this.magazine = student;
                    showStudent(student);
                }, throwable ->throwable.printStackTrace()));

    }


    private void showStudent(Magazine student) {
        if (student == null) return;
        number.setText(String.valueOf(student.getNumber()));
        dateRecording.setText(String.valueOf(student.getDateRecording()));
        content.setText(student.getContent());
        if (players != null && players.size() > 0) {
            int code = magazine.getIdPlayer();
            for (Player player : players) {
                if (player.getCode() == code) {
                    idPlayer.setSelection(players.indexOf(player));
                }
            }
        }
        numberShelf.setText(String.valueOf(student.getNumberShelf()));
        selectTypeStorage(student.getTypeStorage());
        fullName.setText(student.getFullName());
        passportData.setText(student.getPassportData());
        amountRentalForOneDay.setText(String.valueOf(student.getAmountRentalForOneDay()));
        countDayOfRetain.setText(String.valueOf(student.getCountDayOfRetain()));
    }

    private void selectTypeStorage(int typeStorage) {
        List<TypeStorage> list = TypeStorage.getListType();
        for (TypeStorage type : list) {
            if (type.getId() == typeStorage) {
                typeStorageSpinner.setSelection(list.indexOf(type));
            }
        }
    }

    private void saveUser() {
        if (magazine != null && magazine.getId() != null) {
            updateStudent();
        } else {
            addStudent();
        }
    }


    private void addStudent() {
        try {
            magazine = new Magazine(UUID.randomUUID().toString(),
                    Integer.parseInt(number.getText().toString()),
                    Integer.parseInt(dateRecording.getText().toString()),
                    content.getText().toString(),
                    ((Player)idPlayer.getSelectedItem()).getCode(),
                    Integer.parseInt(numberShelf.getText().toString()),
                    ((TypeStorage) typeStorageSpinner.getSelectedItem()).getId(),
                    fullName.getText().toString(),
                    passportData.getText().toString(),
                    Integer.parseInt(amountRentalForOneDay.getText().toString()),
                    Integer.parseInt(countDayOfRetain.getText().toString())
            );

            disposable.add(Observable.just(AudioDao.get(getApplicationContext()).addStudent(magazine))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> finish()));
        } catch (Exception e) {
            Toast.makeText(this, "Неправельные данные", Toast.LENGTH_LONG).show();
        }
    }

    private void updateStudent() {
        try {
            magazine.setNumber(Integer.parseInt(number.getText().toString()));
            magazine.setDateRecording(Integer.parseInt(dateRecording.getText().toString()));
            magazine.setContent(content.getText().toString());
            magazine.setIdPlayer(((Player) idPlayer.getSelectedItem()).getCode());
            magazine.setNumberShelf(Integer.parseInt(numberShelf.getText().toString()));
            magazine.setTypeStorage(((TypeStorage) typeStorageSpinner.getSelectedItem()).getId());
            magazine.setFullName(fullName.getText().toString());
            magazine.setPassportData(passportData.getText().toString());
            magazine.setAmountRentalForOneDay(Double.parseDouble(amountRentalForOneDay.getText().toString()));
            magazine.setCountDayOfRetain(Integer.parseInt(countDayOfRetain.getText().toString()));
            disposable.add(Observable.just(AudioDao.get(getApplicationContext()).updateStudent(magazine))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> finish()));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Неправельные данные", Toast.LENGTH_LONG).show();
        }
    }
}
