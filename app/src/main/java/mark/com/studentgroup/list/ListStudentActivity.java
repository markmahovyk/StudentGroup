package mark.com.studentgroup.list;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import mark.com.studentgroup.PlayerActivity;
import mark.com.studentgroup.R;
import mark.com.studentgroup.StudentDetailsActivity;
import mark.com.studentgroup.data.AudioDao;
import mark.com.studentgroup.data.Player;

public class ListStudentActivity extends AppCompatActivity implements OnItemClick {
    RecyclerView recyclerView;
    StudentAdapter adapter;
    Disposable disposable;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.remove_all) {
            removeAllStudent();
        }
        return super.onOptionsItemSelected(item);
    }

    private void removeAllStudent() {
        disposable = Observable.just(AudioDao.get(this).deleteAll())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> updateStudents());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new StudentAdapter();
        adapter.setOnItemClick(this);
        recyclerView.setAdapter(adapter);
        findViewById(R.id.btn_add).setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), StudentDetailsActivity.class)));
        findViewById(R.id.btn_add_player).setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), PlayerActivity.class)));

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateStudents();
    }

    private void updateStudents() {
        disposable = Observable.just(AudioDao.get(getApplicationContext()).getStudents())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(students -> {
                    showEmptyList(students.isEmpty());
                    adapter.setStudents(students);
                });
    }

    private void showEmptyList(boolean empty) {
        findViewById(R.id.text_list_empty).setVisibility(empty ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public void onItemClick(String id) {
        startActivity(StudentDetailsActivity.newInstance(this, id));
    }

    @Override
    public void onDeleteClick(String id) {
        new AlertDialog.Builder(this)
                .setTitle("Delete student")
                .setMessage("You really want delete student")
                .setPositiveButton("Yes", (dialogInterface, i) -> deleteStudent(id))
                .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss())
                .create().show();
    }

    private void deleteStudent(String id) {
        disposable = Observable.just(AudioDao.get(this).deleteStudent(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> updateStudents());
    }
}
