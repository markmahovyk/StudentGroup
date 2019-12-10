package mark.com.studentgroup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding3.widget.RxTextView;

import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import mark.com.studentgroup.data.AudioDao;
import mark.com.studentgroup.data.Player;

public class PlayerActivity extends AppCompatActivity {
    private static String ARG_ID = "arg_id";
    private EditText inputCode;
    private EditText inputName;
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
        setContentView(R.layout.activity_player);


        inputCode = findViewById(R.id.input_code);
        inputName = findViewById(R.id.input_name);

        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(v -> addPlayer());

        setInputListener();
    }

    private void addPlayer() {
        Player player = new Player(UUID.randomUUID().toString(),
                Integer.parseInt(inputCode.getText().toString()),
                inputName.getText().toString());

        disposable.add(Observable.just(AudioDao.get(getApplicationContext()).addPlayer(player))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> finish()));
    }

    private void setInputListener() {
        disposable.add(Observable.combineLatest(RxTextView.textChanges(inputName),
                RxTextView.textChanges(inputCode), (fistName, lastName) -> !fistName.toString().trim().isEmpty() && !lastName.toString().trim().isEmpty()).subscribe(isVisible -> btnSave.setEnabled(isVisible),
                throwable -> throwable.printStackTrace()));
    }
}
