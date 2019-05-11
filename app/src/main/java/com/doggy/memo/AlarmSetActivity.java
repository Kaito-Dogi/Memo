package com.doggy.memo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class AlarmSetActivity extends AppCompatActivity {

TextView setTime;
EditText hourMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmset);

        setTime = (TextView) findViewById(R.id.setTime);
        hourMinute = (EditText) findViewById(R.id.hourMinute);
    }

    public void set(View view) {
        setTime.setText(hourMinute.getText().toString());
        Toast.makeText(this, hourMinute.getText().toString()
                + "にアラームをセットしました。", Toast.LENGTH_SHORT).show();
        hourMinute.setText("");
    }
}
