/*
package hardcastle.com.churchapplication;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import hardcastle.com.churchapplication.Adapter.SermonsAdapter;
import hardcastle.com.churchapplication.Fragment.SermonsFragment;
import hardcastle.com.churchapplication.Interface.OnRecyclerViewItemClickListener;
import hardcastle.com.churchapplication.VideoPlayer.ResizeSurfaceView;
import hardcastle.com.churchapplication.VideoPlayer.VideoControllerView;

public class PlaySermonActivity extends AppCompatActivity implements OnRecyclerViewItemClickListener, SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, VideoControllerView.MediaPlayerControlListener, MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnCompletionListener, View.OnTouchListener {

    ResizeSurfaceView mVideoSurface;
    MediaPlayer mMediaPlayer;
    VideoControllerView controller;
    private int mVideoWidth;
    private int mVideoHeight;
    private View mContentView;
    private View mLoadingView;
    private boolean mIsComplete;
    private RecyclerView mRecyclerVideoList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private SermonsAdapter sermonsAdapter;
    private Context context;

    private Unbinder unbinder;
    private int pos;

    public static SermonsFragment newInstance(int position) {
        SermonsFragment fragment = new SermonsFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pos = getArguments().getInt("position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sermons, container, false);
        getActivity().setTitle("Sermons");
        unbinder = ButterKnife.bind(this, view);
        //Recyclerview for show list
        mRecyclerVideoList = view.findViewById(R.id.recycler_video_list);
        sermonsAdapter = new SermonsAdapter(context, this);
        // set a GridLayoutManager with default vertical orientation and 3 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerVideoList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        mRecyclerVideoList.setAdapter(sermonsAdapter);

        mVideoSurface = (ResizeSurfaceView) view.findViewById(R.id.videoSurface);
        mContentView = view.findViewById(R.id.video_container);
        mLoadingView = view.findViewById(R.id.loading);
        SurfaceHolder videoHolder = mVideoSurface.getHolder();
        videoHolder.addCallback(this);

        //(FrameLayout) findViewById(R.id.videoSurfaceContainer)
        controller = new VideoControllerView.Builder(getActivity(), this)
                .withVideoTitle("Buck Bunny")
                .withVideoSurfaceView(mVideoSurface)//to enable toggle display controller view
                .canControlBrightness(true)
                .canControlVolume(true)
                .canSeekVideo(true)
                .exitIcon(R.drawable.video_top_back)
                .pauseIcon(R.drawable.ic_media_pause)
                .playIcon(R.drawable.ic_media_play)
                .shrinkIcon(R.drawable.ic_media_fullscreen_shrink)
                .stretchIcon(R.drawable.ic_media_fullscreen_stretch)
                .build((FrameLayout) view.findViewById(R.id.videoSurfaceContainer));//layout container that hold video play view

        if (pos == 1 || pos == 5) {
            playVideo("android.resource://" + getActivity().getPackageName() + "/" + R.raw.videoplayfirst);
        } else if (pos == 3 || pos == 6) {
            playVideo("android.resource://" + getActivity().getPackageName() + "/" + R.raw.videoplaysecond);
        } else if (pos == 2 || pos == 4) {
            playVideo("android.resource://" + getActivity().getPackageName() + "/" + R.raw.videoplaythird);
        } else {
            playVideo("android.resource://" + getActivity().getPackageName() + "/" + R.raw.videoplay);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void playVideo(String path) {
        mLoadingView.setVisibility(View.VISIBLE);
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnVideoSizeChangedListener(this);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(this, Uri.parse(path));
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            //mMediaPlayer.seekTo(1);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        controller.show();
        return false;
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        mVideoHeight = mp.getVideoHeight();
        mVideoWidth = mp.getVideoWidth();
        if (mVideoHeight > 0 && mVideoWidth > 0)
            mVideoSurface.adjustSize(mContentView.getWidth(), mContentView.getHeight(), mMediaPlayer.getVideoWidth(), mMediaPlayer.getVideoHeight());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mVideoWidth > 0 && mVideoHeight > 0)
            mVideoSurface.adjustSize(getDeviceWidth(this), getDeviceHeight(this), mVideoSurface.getWidth(), mVideoSurface.getHeight());
    }

    private void resetPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public static int getDeviceWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(mDisplayMetrics);
        return mDisplayMetrics.widthPixels;
    }

    public static int getDeviceHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(mDisplayMetrics);
        return mDisplayMetrics.heightPixels;
    }


    // Implement SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mMediaPlayer.setDisplay(holder);
            mMediaPlayer.prepareAsync();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        resetPlayer();
    }
// End SurfaceHolder.Callback


    // Implement MediaPlayer.OnPreparedListener
    @Override
    public void onPrepared(MediaPlayer mp) {
        //setup video controller view
        mLoadingView.setVisibility(View.GONE);
        mVideoSurface.setVisibility(View.VISIBLE);
        mMediaPlayer.start();
        mIsComplete = false;
    }
// End MediaPlayer.OnPreparedListener

    */
/**
     * Implement VideoMediaController.MediaPlayerControl
     *//*


    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        if (null != mMediaPlayer)
            return mMediaPlayer.getCurrentPosition();
        else
            return 0;
    }

    @Override
    public int getDuration() {
        if (null != mMediaPlayer)
            return mMediaPlayer.getDuration();
        else
            return 0;
    }

    @Override
    public boolean isPlaying() {
        if (null != mMediaPlayer)
            return mMediaPlayer.isPlaying();
        else
            return false;
    }

    @Override
    public boolean isComplete() {
        return mIsComplete;
    }

    @Override
    public void pause() {
        if (null != mMediaPlayer) {
            mMediaPlayer.pause();
        }

    }

    @Override
    public void seekTo(int i) {
        if (null != mMediaPlayer) {
            mMediaPlayer.seekTo(i);
        }
    }

    @Override
    public void start() {
        if (null != mMediaPlayer) {
            mMediaPlayer.start();
            mIsComplete = false;
        }
    }

    @Override
    public boolean isFullScreen() {
        return getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ? true : false;
    }

    @Override
    public void toggleFullScreen() {
        if (isFullScreen()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void exit() {
        resetPlayer();
        finish();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mIsComplete = true;
    }

    @Override
    public void onClick(int position) {
        */
/*mIsComplete = true;
        resetPlayer();
        playVideo("android.resource://" + getActivity().getPackageName() + "/" + R.raw.videoplay);*//*


        getFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, SermonsFragment.newInstance(position)).addToBackStack(*/
/*"SermonsFragment"*//*
null)
                .commit();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        controller.toggleControllerView();
        return false;
    }

// End VideoMediaController.MediaPlayerControl


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_sermon);
        setTitle("Sermons");
        unbinder = ButterKnife.bind(this, view);
        //Recyclerview for show list
        mRecyclerVideoList = view.findViewById(R.id.recycler_video_list);
        sermonsAdapter = new SermonsAdapter(context, this);
        // set a GridLayoutManager with default vertical orientation and 3 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerVideoList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        mRecyclerVideoList.setAdapter(sermonsAdapter);

        mVideoSurface = (ResizeSurfaceView) view.findViewById(R.id.videoSurface);
        mContentView = view.findViewById(R.id.video_container);
        mLoadingView = view.findViewById(R.id.loading);
        SurfaceHolder videoHolder = mVideoSurface.getHolder();
        videoHolder.addCallback(this);

        //(FrameLayout) findViewById(R.id.videoSurfaceContainer)
        controller = new VideoControllerView.Builder(this, this)
                .withVideoTitle("Buck Bunny")
                .withVideoSurfaceView(mVideoSurface)//to enable toggle display controller view
                .canControlBrightness(true)
                .canControlVolume(true)
                .canSeekVideo(true)
                .exitIcon(R.drawable.video_top_back)
                .pauseIcon(R.drawable.ic_media_pause)
                .playIcon(R.drawable.ic_media_play)
                .shrinkIcon(R.drawable.ic_media_fullscreen_shrink)
                .stretchIcon(R.drawable.ic_media_fullscreen_stretch)
                .build((FrameLayout) view.findViewById(R.id.videoSurfaceContainer));//layout container that hold video play view

        if (pos == 1 || pos == 5) {
            playVideo("android.resource://" + getPackageName() + "/" + R.raw.videoplayfirst);
        } else if (pos == 3 || pos == 6) {
            playVideo("android.resource://" + getPackageName() + "/" + R.raw.videoplaysecond);
        } else if (pos == 2 || pos == 4) {
            playVideo("android.resource://" + getPackageName() + "/" + R.raw.videoplaythird);
        } else {
            playVideo("android.resource://" + getPackageName() + "/" + R.raw.videoplay);
        }
    }
}
*/
