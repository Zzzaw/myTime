package com.example.jszx.mytime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jszx.mytime.data.model.Timer;

/**
 * Created by jszx on 2019/12/16.
 */

public class TimerDetailActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_NEW_TIMER = 901;
    public static final int REQUEST_CODE_UPDATE_TIMER = 902;
    public static final int REQUEST_CODE_TIMERDETAIL = 903;
    public static final int TIMERDETAIL_UPDATE = 904;
    public static final int TIMERDETAIL_DELETE = 905;

    private Button buttonReturn;
    private Button buttonEdit;
    private Button buttonDelete;
    private EditText editTextTimerName;
    private int editPosition;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timerdetail);

        editPosition=getIntent().getIntExtra("edit_position",0);//要编辑的item的位置
        timer = (Timer) getIntent().getSerializableExtra("timer");

        buttonReturn=(Button)findViewById(R.id.button_return);
        buttonEdit=(Button)findViewById(R.id.button_edit);
        buttonDelete=(Button)findViewById(R.id.button_delete);
        editTextTimerName = (EditText)findViewById(R.id.edit_text_timer_name);

        editTextTimerName.setText(timer.getTitle());

        //返回
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimerDetailActivity.this.finish();
            }
        });
        //删除
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //回应主页
                Intent intent = new Intent();
                intent.putExtra("edit_position", editPosition);
                intent.putExtra("action", TIMERDETAIL_DELETE);
                setResult(RESULT_OK, intent);
                TimerDetailActivity.this.finish();
            }
        });

        //编辑
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimerDetailActivity.this, TimerEditActivity.class);
                intent.putExtra("timer", timer);
                intent.putExtra("edit_position",editPosition);
                startActivityForResult(intent, REQUEST_CODE_UPDATE_TIMER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {

            case REQUEST_CODE_UPDATE_TIMER:
                if(resultCode==RESULT_OK)
                {
                    Timer new_timer = (Timer)data.getSerializableExtra("timer");
                    int position=data.getIntExtra("edit_position",0);
                    timer.setTitle(new_timer.getTitle());//title
                    //Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();

                    //回应主页
                    Intent intent = new Intent();
                    intent.putExtra("edit_position", editPosition);
                    intent.putExtra("action", TIMERDETAIL_UPDATE);
                    intent.putExtra("timer", timer);
                    setResult(RESULT_OK, intent);
                    TimerDetailActivity.this.finish();
                }
                break;

        }
    }

}
