package com.example.jszx.mytime;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.jszx.mytime.data.model.Timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jszx on 2019/12/3.
 */

public class TimerEditActivity extends AppCompatActivity {

    private Button buttonOk,buttonReturn;
    private EditText editTextTimerName;
    private int editPosition;
    private Timer timer;
    private TextView editTextTimerDate;//get date
    private TimePickerView pvTime;//get date

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
        editTextTimerDate = (TextView)findViewById(R.id.edit_text_timer_date);//get date

        editTextTimerName.setText(timer.getTitle());
        editTextTimerDate.setText(getTimes(timer.getDate()));//date

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


        //get date
        editTextTimerDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击组件的点击事件
                pvTime.show(editTextTimerDate);
            }
        });


        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2019, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, 12, 31);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                TextView btn = (TextView) v;
                btn.setText(getTimes(date));
                timer.setDate(date);
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                //.setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(true)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setDecorView(null)
                .build();

    }

    private String getTimes(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH时mm分ss秒");
        return format.format(date);
    }


}
