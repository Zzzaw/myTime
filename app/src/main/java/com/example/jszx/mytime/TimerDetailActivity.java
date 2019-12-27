package com.example.jszx.mytime;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.example.jszx.mytime.data.model.Timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    private TextView timerName;
    private int editPosition;
    private Timer timer;

    private static final int msgKey1 = 1;//countdown
    private TextView tv_time;//countdown
    private TextView date;//get date
    private LinearLayout mainLayout;//用于设置背景图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timerdetail);

        editPosition=getIntent().getIntExtra("edit_position",0);//要编辑的item的位置
        timer = (Timer) getIntent().getSerializableExtra("timer");

        buttonReturn=(Button)findViewById(R.id.button_return);
        buttonEdit=(Button)findViewById(R.id.button_edit);
        buttonDelete=(Button)findViewById(R.id.button_delete);
        timerName = (TextView)findViewById(R.id.name);
        tv_time = (TextView) findViewById(R.id.mytime);//countdown
        date = (TextView)findViewById(R.id.date);//get date
        mainLayout = (LinearLayout)findViewById(R.id.layout_timer_detail);

        timerName.setText(timer.getTitle());
        date.setText(getTimes(timer.getDate()));//date
        mainLayout.setBackgroundResource(timer.getCoverResourceId());

        new TimeThread().start();//countdown



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

    private String getTimes(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH时mm分ss秒");
        return format.format(date);
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

                    timer.setTitle(new_timer.getTitle());//名字
                    timer.setDate(new_timer.getDate());//时间
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

    public class TimeThread extends  Thread{
        @Override
        public void run() {
            super.run();
            do{
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = msgKey1;
                    mHandler.sendMessage(msg);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (true);
        }
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case msgKey1:
                    long time = System.currentTimeMillis();
                    Date date_now = new Date(time);
                    //Date date = new Date(time);
                    Date date = timer.getDate();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 EEE");

                    String remain = dateDiff(date_now, date, format);
                    tv_time.setText(remain);
                    //tv_time.setText(format.format(date_now));

                    break;
                default:
                    break;
            }
        }
    };

    public String dateDiff(Date startDate, Date endDate, SimpleDateFormat format){
        String startTime = format.format(startDate);
        String endTime = format.format(endDate);

        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;

        try {
            // 获得两个时间的毫秒时间差异
            diff = format.parse(endTime).getTime()
                    - format.parse(startTime).getTime();
            day = diff / nd;// 计算差多少天
            long hour = diff % nd / nh;// 计算差多少小时
            long min = diff % nd % nh / nm;// 计算差多少分钟
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            return day + "天" + hour + "小时" + min
                    + "分钟" + sec + "秒";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


}
