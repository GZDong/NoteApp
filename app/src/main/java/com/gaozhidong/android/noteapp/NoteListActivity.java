package com.gaozhidong.android.noteapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gaozhidong.android.noteapp.Adapter.NoteAdaper;
import com.gaozhidong.android.noteapp.Listener.ItemTouchCallback;
import com.gaozhidong.android.noteapp.Listener.OnRecyclerItemClickListener;
import com.gaozhidong.android.noteapp.Model.NoteBody;
import com.gaozhidong.android.noteapp.Model.NotesLab;
import com.gaozhidong.android.noteapp.Util.DateUtils;
import com.gaozhidong.android.noteapp.Util.LogUtil;

import java.util.Calendar;
import java.util.List;

public class NoteListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView mRecyclerView;
    private NoteAdaper mNoteAdaper;
    private List<NoteBody> mBodyList;

    public static Intent newInstance(Context context){
        return new Intent(context,NoteListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.gaozhidong.android.Color");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                NotesLab.get(context).updateFlag(intent.getIntExtra("noteId",1),1);
                mNoteAdaper.notifyDataSetChanged();
            }
        };
        registerReceiver(receiver,intentFilter);

        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction("com.gaozhidong.android.NoColor");
        BroadcastReceiver receiver1 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LogUtil.e("test","接收到清空图标的广播了" + intent.getIntExtra("noteId",1));
                NotesLab.get(context).updateFlag(intent.getIntExtra("noteId",1),0);
                mNoteAdaper.updateList(NotesLab.get(context).getBodyList());
                mNoteAdaper.notifyDataSetChanged();
            }
        };
        registerReceiver(receiver1,intentFilter1);

        mBodyList = NotesLab.get(this).getBodyList();
        mRecyclerView = (RecyclerView) findViewById(R.id.note_recycler_view);
        mNoteAdaper = new NoteAdaper(this,mBodyList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(mNoteAdaper);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        /*mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {

            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder) {

            }
        });*/
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchCallback(mNoteAdaper));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int noteid = mBodyList.size() + 1;
                NoteBody noteBody = NoteBody.Builder()
                        .setNoteId(noteid)
                        .setText("")
                        .setTime(DateUtils.getNowStrDate())
                        .setAccount("gaozhidong")
                        .setCalendar(Calendar.getInstance())
                        .create();
                NotesLab.get(NoteListActivity.this).addNote(noteBody);

                mNoteAdaper.notifyDataSetChanged();

                Intent intent = ContentActivity.newInstance(NoteListActivity.this);
                intent.putExtra("noteId",noteid);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (NoteBody noteBody : mBodyList){
            LogUtil.e("test",noteBody.getNoteId() + " " + noteBody.getFlag());
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
        getMenuInflater().inflate(R.menu.note_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.item_sort) {
            return true;
        }
        if (id == R.id.item_search){
            Intent intent = new Intent(NoteListActivity.this,SortActivity.class);
            startActivity(intent);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == 1){
                    mNoteAdaper.notifyDataSetChanged();
                }
                break;
            default:
        }
    }
}
