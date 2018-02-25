package com.gaozhidong.android.noteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.SearchView;

import com.gaozhidong.android.noteapp.Adapter.NoteAdaper;
import com.gaozhidong.android.noteapp.Model.NoteBody;
import com.gaozhidong.android.noteapp.Model.NotesLab;

import java.util.ArrayList;
import java.util.List;

public class SortActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private List<NoteBody> mList;
    private NoteAdaper mAdaper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        mRecyclerView = (RecyclerView) findViewById(R.id.search_list);
        mList = new ArrayList<>();
        mAdaper = new NoteAdaper(this,mList);
        mAdaper.setActivityTag(2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdaper);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!TextUtils.isEmpty(s)){
                    mList.addAll(NotesLab.get(SortActivity.this).searchNotes(s));
                    mAdaper.notifyDataSetChanged();
                }else {
                    mList.clear();
                    mAdaper.notifyDataSetChanged();
                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == 1){
                    mAdaper.notifyDataSetChanged();
                }
                break;
            default:
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(1,intent);
        finish();
    }
}
