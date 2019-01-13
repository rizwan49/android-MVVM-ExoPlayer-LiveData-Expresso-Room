package com.ar.bakingapp.fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.Guideline;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ar.bakingapp.R;
import com.ar.bakingapp.activities.home.HomeActivity;
import com.ar.bakingapp.network.model.StepsItem;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class PlayerFragment extends Fragment implements ExoPlayer.EventListener {
    private static final String TAG = PlayerFragment.class.getName();
    private static final String SELECTED_STEP_ID = "step_id";
    private static final String PLAYBACK_STATE = "playback_state";
    private static final String MEDIA_POSITION = "media_position";
    private static MediaSessionCompat mMediaSession;
    View rootView;
    @BindView(R.id.playerView)
    SimpleExoPlayerView mPlayerView;
    @BindView(R.id.tvStepsValue)
    TextView tvStepValue;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    Guideline guideline;

    private int selectedStepId;
    private boolean playBackState;
    private long mediaPosition;

    private SimpleExoPlayer mExoPlayer;
    private PlaybackStateCompat.Builder mStateBuilder;
    private StepsItem selectedObj;


    public PlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (selectedObj != null) {
            selectedStepId = selectedObj.getId();
            outState.putInt(SELECTED_STEP_ID, selectedStepId);
        }
        if (mExoPlayer != null) {
            outState.putBoolean(PLAYBACK_STATE, mExoPlayer.getPlayWhenReady());
            outState.putLong(MEDIA_POSITION, mExoPlayer.getCurrentPosition());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle state) {
        // Inflate the layout for this fragment
        if (state != null) {
            selectedStepId = state.getInt(SELECTED_STEP_ID);
            playBackState = state.getBoolean(PLAYBACK_STATE, true);
            mediaPosition = state.getLong(MEDIA_POSITION);
            Log.d(TAG, "mediaPos:" + mediaPosition + " SelectedId:" + selectedStepId + " MediaState:" + playBackState);
        }
        rootView = inflater.inflate(R.layout.fragment_player, container, false);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mExoPlayer != null && !mExoPlayer.getPlayWhenReady())
            mExoPlayer.setPlayWhenReady(true);
    }

    private void init() {
        guideline = rootView.findViewById(R.id.horizontalHalf);
        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.question_mark));
        initializeMediaSession();
    }

    private void initializeMediaSession() {
        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(

                        PlaybackStateCompat.ACTION_SEEK_TO |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
        if (mMediaSession != null)
            mMediaSession.setActive(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        releasePlayer();
        super.onDestroy();
    }

    public void setStepInfo(StepsItem step) {
        if (step == null) return;
        selectedObj = step;

        if (!TextUtils.isEmpty(step.getVideoURL())) {
            initializePlayer(Uri.parse(step.getVideoURL()));
        } else if (step.getThumbnailURL() != null) {
            initializePlayer(Uri.parse(step.getThumbnailURL()));
        }
        if (!TextUtils.isEmpty(step.getDescription())) {
            tvStepValue.setText(step.getDescription());
        }
    }

    /**
     * Initialize ExoPlayer.
     *
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);
            // Prepare the MediaSource.
        }
        setupVideo(mediaUri);
    }

    private void setupVideo(Uri mediaUri) {
        if (mediaUri != null && !TextUtils.isEmpty(mediaUri.toString()))
            mPlayerView.setUseController(true);
        else
            mPlayerView.setUseController(false);
        String userAgent = Util.getUserAgent(getContext(), selectedObj.getShortDescription());
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
        mExoPlayer.prepare(mediaSource);
        if (selectedObj.getId() == selectedStepId) {
            mExoPlayer.seekTo(mediaPosition);
            mExoPlayer.setPlayWhenReady(playBackState);
        } else
            mExoPlayer.setPlayWhenReady(true);
        HomeActivity.mIdlingResource.setIdleState(true);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null && mExoPlayer.getPlayWhenReady())
            mExoPlayer.setPlayWhenReady(false);
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    public void hideView() {
        guideline.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);
        guideline.setGuidelinePercent(1);
    }

    public void showView() {
        guideline.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.VISIBLE);
        guideline.setGuidelinePercent(0.4f);
    }

    public void setMediaViewPortion(float value) {
        guideline.setGuidelinePercent(value);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }

        @Override
        public void onSkipToNext() {
            mExoPlayer.seekTo(0, 1);
        }

    }
}
