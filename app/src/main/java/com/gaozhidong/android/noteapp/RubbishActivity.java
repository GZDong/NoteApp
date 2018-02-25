package com.gaozhidong.android.noteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.gaozhidong.android.noteapp.Adapter.NoteAdaper;
import com.gaozhidong.android.noteapp.Adapter.RubListAdapter;
import com.gaozhidong.android.noteapp.Model.NoteBody;
import com.gaozhidong.android.noteapp.Model.NotesLab;

import java.util.ArrayList;
import java.util.List;

public class RubbishActivity extends AppCompatActivity {

    private List<NoteBody> mList;
    private RecyclerView mRecyclerView;
    private RubListAdapter mAdaper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rubbish);
        mList = NotesLab.get(this).getRcycleList();
        mAdaper = new RubListAdapter(this,mList);
        mRecyclerView = (RecyclerView) findViewById(R.id.r_list);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(mAdaper);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rubbish_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.recover_item:
                List<NoteBody> list = new ArrayList<>();
                list.addAll(mList);
                for (NoteBody body2 : list){
                    int noteId = body2.getNoteId();
                    NoteBody body = NotesLab.get(RubbishActivity.this).getNoteFromRList(noteId);
                    NotesLab.get(RubbishActivity.this).addNote(body);
                }
                mAdaper.notifyDataSetChanged();
                break;
            case R.id.delete_item:
                List<NoteBody> list2 = new ArrayList<>();
                list2.addAll(mList);
                for (NoteBody body2 : list2){
                    int noteId = body2.getNoteId();
                    NotesLab.get(RubbishActivity.this).deleteRList(noteId);
                }
                mAdaper.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(1,intent);
        finish();
    }
}
