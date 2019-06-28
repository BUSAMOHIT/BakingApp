package com.oganbelema.bakingapp.ui;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.material.snackbar.Snackbar;
import com.oganbelema.bakingapp.R;
import com.oganbelema.bakingapp.databinding.FragmentMediaBinding;
import com.oganbelema.network.data.Step;

/**
 * A simple {@link Fragment} subclass.
 */
public class MediaFragment extends Fragment {

    private SimpleExoPlayer mPlayer;

    private String mVideoUrl;

    private int currentWindow = 0;

    private long playbackPosition = 0;

    private boolean playWhenReady = true;

    private FragmentMediaBinding mFragmentMediaBinding;

    private Step step;


    public MediaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentMediaBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_media, container,
                false);
        return mFragmentMediaBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null){
            MediaFragmentArgs args = MediaFragmentArgs.fromBundle(getArguments());

            String recipeName = args.getRecipeName();

            step = args.getStep();

            getActivity().setTitle(recipeName);

            mVideoUrl = step.getVideoURL();
        }
    }

    private void initializePlayer() {
        mPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        mFragmentMediaBinding.fullScreenVideo.setPlayer(mPlayer);

        mPlayer.setPlayWhenReady(playWhenReady);
        mPlayer.seekTo(currentWindow, playbackPosition);

        if (mVideoUrl != null){
            Uri uri = Uri.parse(mVideoUrl);
            MediaSource mediaSource = buildMediaSource(uri);
            mPlayer.prepare(mediaSource, true, false);
        } else {
            Snackbar.make(mFragmentMediaBinding.getRoot(),
                    getContext().getString(R.string.no_video_url),
                    Snackbar.LENGTH_LONG).show();
        }
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            playbackPosition = mPlayer.getCurrentPosition();
            currentWindow = mPlayer.getCurrentWindowIndex();
            playWhenReady = mPlayer.getPlayWhenReady();
            mPlayer.release();
            mPlayer = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("bakingApp")).
                createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            initializePlayer();
        }

    }

    @Override
    public void onPause() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            releasePlayer();
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            releasePlayer();
        }
        super.onStop();
    }
}
