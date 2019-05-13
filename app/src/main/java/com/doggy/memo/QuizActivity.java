package com.doggy.memo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import android.view.WindowManager;

import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

public class QuizActivity extends AppCompatActivity{

    public Realm realm;

    public EditText answerEditText;
    public ListView listView;
    Memo memo;
    MemoAdapter2 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        realm = Realm.getDefaultInstance();

        listView = (ListView) findViewById(R.id.listView);
        answerEditText = (EditText) findViewById(R.id.answerEditText);

    }

    public void setMemoList2() {

        RealmResults<Memo> results = realm.where(Memo.class).findAll();
        List<Memo> items = realm.copyFromRealm(results);

        adapter = new MemoAdapter2(this, R.layout.layout_item_memo2, items);

        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setMemoList2();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        startService(new Intent(this, PlaySoundService.class));
    }


    public void answer(View view) {
        memo = adapter.getItem(0);
        if(answerEditText.getText().toString().equals(memo.content)){
            adapter.remove(memo);
            Toast.makeText(this, "おやすみ！", Toast.LENGTH_SHORT).show();
            answerEditText.setText("");

            if (listView.getCount() == 0) {
                stopService(new Intent(QuizActivity.this, PlaySoundService.class));
                PreferenceUtil pref = new PreferenceUtil(QuizActivity.this);
                pref.delete(AlarmSetActivity.ALARM_TIME);
                Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "二度寝しよう！", Toast.LENGTH_SHORT).show();

            } else {}

        }else {
            Toast.makeText(this, "おはよう！", Toast.LENGTH_SHORT).show();
            answerEditText.setText("");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        realm.close();
    }
}
