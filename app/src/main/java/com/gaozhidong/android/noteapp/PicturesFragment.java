package com.gaozhidong.android.noteapp;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gaozhidong.android.noteapp.Model.Pictures;

import java.io.File;

/**
 * Created by zhidong on 2018/2/19.
 */

public class PicturesFragment extends Fragment {
    private static final String ARG_PARAM1 = "pictures";
    public static final String TAG = "Pictures Fragment";

    private int mParam1;
    private ImageView mImageView;
    private Pictures mPictures;

    public PicturesFragment() {
    }

    public static PicturesFragment newInstance(Pictures pictures) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1,pictures);
        PicturesFragment fragment = new PicturesFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPictures = (Pictures) getArguments().getSerializable("pictures");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pictures,container,false);
        mImageView = (ImageView) view.findViewById(R.id.pic_view);

        String path = mPictures.getPath();
        Glide.with(getActivity()).load(path).asBitmap().centerCrop().into(mImageView);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;
    }
}
