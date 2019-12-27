package com.example.jszx.mytime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jszx.mytime.data.model.Timer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Timer> theTimers;//list
    private ListView listViewSuper;//list
    private TimersArrayAdapter theAdaper;//list
    public static final int REQUEST_CODE_NEW_TIMER = 901;
    public static final int REQUEST_CODE_UPDATE_TIMER = 902;
    public static final int REQUEST_CODE_TIMERDETAIL = 903;
    public static final int TIMERDETAIL_UPDATE = 904;
    public static final int TIMERDETAIL_DELETE = 905;


    public List<Timer> getListTimers() {
        List<Timer> Books;
        Books=new ArrayList<Timer>();
        Books.add(new Timer("信息安全数学基础（第2版）",R.drawable.timer_1));
        Books.add(new Timer("创新工程实践",R.drawable.timer_2));
        Books.add(new Timer("软件项目管理案例教程（第4版）",R.drawable.timer_3));
        return Books;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //右下角fab点击事件:新增Timer
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent intent = new Intent(MainActivity.this,TimerEditActivity.class);
                startActivity(intent);
                */
                //新建timer
                Intent intent = new Intent(MainActivity.this, TimerEditActivity.class);
                startActivityForResult(intent, REQUEST_CODE_NEW_TIMER);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //List
        theTimers = getListTimers();
        listViewSuper= (ListView) this.findViewById(R.id.list_view_timers);
        theAdaper=new TimersArrayAdapter(this,R.layout.list_item_timer,theTimers);
        listViewSuper.setAdapter(theAdaper);

        //list item点击事件:编辑
        listViewSuper.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // TODO Auto-generated method stub
                /*
                Intent intent = new Intent(MainActivity.this, TimerEditActivity.class);
                Timer timer=theTimers.get(position);
                intent.putExtra("timer", timer);
                intent.putExtra("edit_position",position);
                startActivityForResult(intent, REQUEST_CODE_UPDATE_TIMER);
                */
                Intent intent = new Intent(MainActivity.this, TimerDetailActivity.class);
                Timer timer=theTimers.get(position);
                intent.putExtra("timer", timer);
                intent.putExtra("edit_position",position);
                startActivityForResult(intent, REQUEST_CODE_TIMERDETAIL);

            }

        });
    }

    protected class TimersArrayAdapter extends ArrayAdapter<Timer>
    {
        private  int resourceId;
        public TimersArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Timer> objects) {
            super(context, resource, objects);
            resourceId=resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater mInflater= LayoutInflater.from(this.getContext());
            View item = mInflater.inflate(this.resourceId,null);

            ImageView img = (ImageView)item.findViewById(R.id.image_view_book_cover);
            TextView name = (TextView)item.findViewById(R.id.text_view_book_title);

            Timer good_item= this.getItem(position);
            img.setImageResource(good_item.getCoverResourceId());
            name.setText(good_item.getTitle());

            return item;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case REQUEST_CODE_NEW_TIMER:
                if(resultCode==RESULT_OK)
                {
                    //在最上面新增
                    int position=0;
                    Timer new_timer = (Timer)data.getSerializableExtra("timer");
                    theTimers.add(position, new_timer);
                    theAdaper.notifyDataSetChanged();

                    Toast.makeText(this, "新建成功", Toast.LENGTH_SHORT).show();
                }
                break;
            /*
            case REQUEST_CODE_UPDATE_TIMER:
                if(resultCode==RESULT_OK)
                {
                    Timer new_timer = (Timer)data.getSerializableExtra("timer");
                    int position=data.getIntExtra("edit_position",0);
                    Timer timer=theTimers.get(position);

                    timer.setTitle(new_timer.getTitle());

                    theAdaper.notifyDataSetChanged();
                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                }
                break;
            */
            case REQUEST_CODE_TIMERDETAIL:
                if (resultCode==RESULT_OK)
                {
                    int action = data.getIntExtra("action",0);
                    int position;
                    switch (action)
                    {
                        //编辑timer
                        case TIMERDETAIL_UPDATE:
                            Timer new_timer = (Timer)data.getSerializableExtra("timer");
                            position=data.getIntExtra("edit_position",0);
                            Timer timer=theTimers.get(position);

                            timer.setTitle(new_timer.getTitle());//名字
                            timer.setDate(new_timer.getDate());//时间

                            theAdaper.notifyDataSetChanged();
                            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                            break;
                        //删除timer
                        case TIMERDETAIL_DELETE:
                            position=data.getIntExtra("edit_position",0);
                            theTimers.remove(position);
                            theAdaper.notifyDataSetChanged();
                            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                            break;

                    }
                }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
