package com.gaozhidong.android.noteapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.gaozhidong.android.noteapp.Model.NoteBody;
import com.gaozhidong.android.noteapp.Model.NotesLab;
import com.gaozhidong.android.noteapp.Util.ImgUtils;
import com.gaozhidong.android.noteapp.Util.LogUtil;

import java.io.File;
import java.util.Calendar;
import java.util.List;

public class ContentActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private List<NoteBody> mBodyList;
    private static final int REQUEST_PHOTO = 1;
    private int noteId;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    public static Intent newInstance(Context context){
        return new Intent(context,ContentActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);

        mBodyList = NotesLab.get(this).getBodyList();

        noteId = getIntent().getIntExtra("noteId",0);
        LogUtil.e("test",""+ noteId);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        for (int i = 0; i < mBodyList.size();i++){
            if (mBodyList.get(i).getNoteId() == noteId){
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.set_time) {

            FragmentManager fm = getSupportFragmentManager();
            AlarmDialogFragment fragment = new AlarmDialogFragment();
            fragment.show(fm,"chooseAlarm");
            return true;
        }
        if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private NoteBody mNoteBody;
        private File mPhotoFile;
        private FloatingActionButton fab;
        private ImageView mImageView;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(NoteBody noteBody) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putSerializable("noteBody",noteBody);
            fragment.setArguments(args);
            return fragment;   //这里返回的是一个fragment
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNoteBody = (NoteBody)getArguments().getSerializable("noteBody");
            mPhotoFile = NotesLab.get(getActivity()).getPhotoFile(mNoteBody);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_content, container, false);
            TextView textTime = (TextView) rootView.findViewById(R.id.text_title);
            EditText text = (EditText) rootView.findViewById(R.id.input_text);
            mImageView = (ImageView ) rootView.findViewById(R.id.img_view);
            textTime.setText(mNoteBody.getTime());
            text.setText(mNoteBody.getText());
            text.setSelection(text.getText().length());

            fab = (FloatingActionButton) rootView.findViewById(R.id.fab);


            PackageManager pm = getActivity().getPackageManager();
            final Intent imgIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            boolean canTakePhoto = mPhotoFile!=null && imgIntent.resolveActivity(pm)!=null;
            if (canTakePhoto){
                Uri uri = Uri.fromFile(mPhotoFile);
                imgIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
            }

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityForResult(imgIntent,REQUEST_PHOTO);
                }
            });

            updatePhotoView();
            return rootView;
        }

        private void updatePhotoView(){
            if (mPhotoFile == null || !mPhotoFile.exists()){
                mImageView.setVisibility(View.GONE);
            }else {
                Bitmap bitmap = ImgUtils.getScaleBitmap(mPhotoFile.getPath(),getActivity());
                mImageView.setVisibility(View.VISIBLE);
                mImageView.setImageBitmap(bitmap);
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == REQUEST_PHOTO){
                updatePhotoView();
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            NoteBody noteBody = mBodyList.get(position);
            return PlaceholderFragment.newInstance(noteBody);
        }

        @Override
        public int getCount() {
            return mBodyList.size();
        }

    }

}
