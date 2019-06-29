package com.oganbelema.bakingapp.ui;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.oganbelema.bakingapp.R;
import com.oganbelema.bakingapp.databinding.FragmentMediaBinding;
import com.oganbelema.network.data.Step;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class MediaFragment extends Fragment implements Player.EventListener {

    private static final String TAG = MediaFragment.class.getName();

    private SimpleExoPlayer mPlayer;

    private String mVideoUrl;

    private String mImageUrl;

    private int currentWindow = 0;

    private long playbackPosition = 0;

    private boolean playWhenReady = true;

    private FragmentMediaBinding mFragmentMediaBinding;


    public MediaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentMediaBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_media, container,
                false);
        return mFragmentMediaBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            MediaFragmentArgs args = MediaFragmentArgs.fromBundle(getArguments());

            String recipeName = args.getRecipeName();

            Step step = args.getStep();

            if (getActivity() != null)
                getActivity().setTitle(recipeName);

            mVideoUrl = step.getVideoURL();

            mImageUrl = step.getThumbnailURL();

            if (!mVideoUrl.isEmpty()){
                mFragmentMediaBinding.fullScreenVideo.setVisibility(View.VISIBLE);
            } else if (!mImageUrl.isEmpty()){
                Picasso.get().load(mImageUrl).into(mFragmentMediaBinding.stepImage);
                mFragmentMediaBinding.stepImage.setVisibility(View.VISIBLE);
            } else {
                mFragmentMediaBinding.noMediaTextView.setVisibility(View.VISIBLE);
            }


        }
    }

    private void initializePlayer() {
        mPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        mFragmentMediaBinding.fullScreenVideo.setPlayer(mPlayer);

        mPlayer.setPlayWhenReady(playWhenReady);
        mPlayer.seekTo(currentWindow, playbackPosition);


        Uri uri = Uri.parse(mVideoUrl);
        MediaSource mediaSource = buildMediaSource(uri);
        mPlayer.prepare(mediaSource, true, false);

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
    public void onPlayerError(ExoPlaybackException error) {
        switch (error.type) {
            case ExoPlaybackException.TYPE_SOURCE:
                if (getContext() != null)
                    Toast.makeText(getContext(), getContext().getString(R.string.no_video_url),
                        Toast.LENGTH_LONG).show();
                Log.e(TAG, "TYPE_SOURCE: " + error.getSourceException().getMessage());
                break;

            case ExoPlaybackException.TYPE_RENDERER:
                Log.e(TAG, "TYPE_RENDERER: " + error.getRendererException().getMessage());
                break;

            case ExoPlaybackException.TYPE_UNEXPECTED:
                Log.e(TAG, "TYPE_UNEXPECTED: " + error.getUnexpectedException().getMessage());
                break;
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mVideoUrl.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                initializePlayer();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mVideoUrl.isEmpty()) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                initializePlayer();
            }
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            releasePlayer();
        }
        super.onStop();
    }
}
