package com.gaozhidong.android.noteapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.gaozhidong.android.noteapp.Adapter.PicListAdapter;
import com.gaozhidong.android.noteapp.Model.NoteBody;
import com.gaozhidong.android.noteapp.Model.NotesLab;
import com.gaozhidong.android.noteapp.Model.Pictures;
import com.gaozhidong.android.noteapp.Util.DateUtils;
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

    public static final String TAG = "contentActivity";

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

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        //这个for指定当前显示的页面
        for (int i = 0; i < mBodyList.size();i++){
            if (mBodyList.get(i).getNoteId() == noteId){
                mViewPager.setCurrentItem(i);
                break;
            }
        }

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
       // private File mPhotoFile;
        private FloatingActionButton fab;
        //private ImageView mImageView;
        private TextView textTime;
        private EditText text;
        private String oldString = "";
        private String newString = "";
        private RecyclerView mRecyclerView;
        private PicListAdapter mPicListAdapter;
        private List<String> mPath;
        private int noteid;
        private boolean fflag = false;

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
            setHasOptionsMenu(true);
            mNoteBody = (NoteBody)getArguments().getSerializable("noteBody");
            noteid = mNoteBody.getNoteId();
           // mPhotoFile = NotesLab.get(getActivity()).getPhotoFile(mNoteBody);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.fragment_content, container, false);
            textTime = (TextView) rootView.findViewById(R.id.text_title);
            text = (EditText) rootView.findViewById(R.id.input_text);
           // mImageView = (ImageView ) rootView.findViewById(R.id.img_view);
            textTime.setText(mNoteBody.getTime());
            text.setText(mNoteBody.getText());
            text.setSelection(text.getText().length());
            oldString = mNoteBody.getText();
            newString = mNoteBody.getText();
            fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
            mPath = NotesLab.get(getActivity()).getPicPaths(noteid);
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.picture_list);
            mPicListAdapter = new PicListAdapter(getActivity(),mPath);
            mPicListAdapter.setNodeid(noteid);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setAdapter(mPicListAdapter);
            if (mPath!=null && mPath.size() > 1){
                mRecyclerView.scrollToPosition(mPath.size()-1);
            }


            /*PackageManager pm = getActivity().getPackageManager();
            final Intent imgIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            boolean canTakePhoto = mPhotoFile!=null && imgIntent.resolveActivity(pm)!=null;
            if (canTakePhoto){
                Uri uri = Uri.fromFile(mPhotoFile);
                imgIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
            }*/


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    File externalFilesDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    String picName;
                    if (mPath == null){
                        picName = mNoteBody.getNoteId() + "_" + "1";
                    }else {
                        int count = mPath.size() + 1;
                        picName = mNoteBody.getNoteId() + "_" + count;
                    }

                    String path = externalFilesDir + File.separator + picName;

                    Pictures pictures = new Pictures();
                    pictures.setNoteid(mNoteBody.getNoteId());
                    pictures.setName(picName);
                    pictures.setPath(path);
                    pictures.save();

                    mPath.add(path);
                    Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri uri = Uri.fromFile(new File(path));
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);

                    startActivityForResult(intent,REQUEST_PHOTO);
                }
            });

            text.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!charSequence.toString().equals(oldString)){
                        newString = charSequence.toString();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            updatePhotoView();
            return rootView;
        }

        private void updatePhotoView(){

               // Bitmap bitmap = ImgUtils.getScaleBitmap(mPhotoFile.getPath(),getActivity());
               // mImageView.setVisibility(View.VISIBLE);
              //  mImageView.setImageBitmap(bitmap);
                mPicListAdapter.notifyDataSetChanged();
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == REQUEST_PHOTO){
                updatePhotoView();
            }
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            super.onCreateOptionsMenu(menu, inflater);
            inflater.inflate(R.menu.menu_content,menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.set_time) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                AlarmDialogFragment fragment = AlarmDialogFragment.newInstance(mNoteBody.getNoteId());
                fragment.show(fm,"chooseAlarm");
                return true;
            }
            if (id == android.R.id.home){
                fflag = true;
                if (!newString.equals(oldString)) {
                    NotesLab.get(getActivity()).updateNote(mNoteBody.getNoteId(),newString, DateUtils.getNowStrDate());
                }
                Intent intent = new Intent();
                getActivity().setResult(1,intent);
                getActivity().finish();
            }

            if (id == R.id.item_share){
                String report = getString(R.string.send_note,mNoteBody.getAccount(),mNoteBody.getTime(),mNoteBody.getText());
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT,report);
                i.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.send_note_subject));
                i = Intent.createChooser(i,getString(R.string.send_note_choose));
                startActivity(i);
                LogUtil.e("test",report);
            }
            return super.onOptionsItemSelected(item);
        }


        @Override
        public void onPause() {
            super.onPause();
            if (fflag == false &&newString!=null &&oldString!=null&& !newString.equals(oldString)) {
                Log.e(TAG, "onPause: " );
                NotesLab.get(getActivity()).updateNote(mNoteBody.getNoteId(),newString, DateUtils.getNowStrDate());
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(1,intent);
        finish();
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
