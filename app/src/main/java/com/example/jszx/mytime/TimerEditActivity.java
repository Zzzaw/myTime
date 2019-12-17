package com.example.jszx.mytime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jszx.mytime.data.model.Timer;

/**
 * Created by jszx on 2019/12/3.
 */

public class TimerEditActivity extends AppCompatActivity {

    private Button buttonOk,buttonReturn;
    private EditText editTextTimerName;
    private int editPosition;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeredit);

        editPosition=getIntent().getIntExtra("edit_position",0);//要编辑的item的位置
        timer = (Timer) getIntent().getSerializableExtra("timer");
        if(timer == null){
            timer = new Timer("new",R.drawable.timer_1);
        }

        buttonReturn=(Button)findViewById(R.id.button_return);
        buttonOk=(Button)findViewById(R.id.button_ok);
        editTextTimerName = (EditText)findViewById(R.id.edit_text_timer_name);

        //确定
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("edit_position", editPosition);
                timer.setTitle(editTextTimerName.getText().toString().trim());
                intent.putExtra("timer", timer);
                //intent.putExtra("timer_name", editTextTimerName.getText().toString().trim());
                setResult(RESULT_OK, intent);
                TimerEditActivity.this.finish();
            }
        });


        //返回
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimerEditActivity.this.finish();
            }
        });
    }


}
