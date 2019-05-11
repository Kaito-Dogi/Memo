package com.doggy.memo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
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
    }

    public  void showData() {
            memo = realm.where(Memo.class).equalTo("updateDate",
                    getIntent().getStringExtra("updateDate")).findFirst();
    }

    public void answer(View view) {
        showData();
        if(answerEditText.getText().toString().equals(memo.content)){
            adapter.remove(memo);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        realm.close();
    }
}

