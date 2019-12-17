package hardcastle.com.churchapplication.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import hardcastle.com.churchapplication.Adapter.SermonsAdapter;
import hardcastle.com.churchapplication.Interface.OnSermonVideoItemClickListener;
import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.Utils.APIUtils;
import hardcastle.com.churchapplication.Utils.NetworkCalls;
import hardcastle.com.churchapplication.VideoPlayer.ResizeSurfaceView;
import hardcastle.com.churchapplication.VideoPlayer.VideoControllerView;
import hardcastle.com.churchapplication.model.SermonsResponseBean;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutUsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SermonsFragment extends Fragment implements OnSermonVideoItemClickListener, SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, VideoControllerView.MediaPlayerControlListener, MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnCompletionListener, View.OnTouchListener {

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
    private String video_url;
    private List<SermonsResponseBean.SermonList> sermonList;


    public SermonsFragment() {
        // Required empty public constructor
    }

    public static SermonsFragment newInstance(String video_url) {
        SermonsFragment fragment = new SermonsFragment();
        Bundle args = new Bundle();
        args.putString("video_url", video_url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            video_url = getArguments().getString("video_url");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sermons, container, false);
        getActivity().setTitle("Sermons");
        unbinder = ButterKnife.bind(this, view);
        //Recyclerview for show list

       // mRecyclerVideoList = view.findViewById(R.id.recycler_video_list);
        //getVideoList();

        mVideoSurface = (ResizeSurfaceView) view.findViewById(R.id.videoSurface);
        mContentView = view.findViewById(R.id.video_container);
        mLoadingView = view.findViewById(R.id.loading);
        SurfaceHolder videoHolder = mVideoSurface.getHolder();
        videoHolder.addCallback(this);

        //(FrameLayout) findViewById(R.id.videoSurfaceContainer)
        controller = new VideoControllerView.Builder(getActivity(), this)
                .withVideoTitle("")
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

    /*    if (pos == 1 || pos == 5) {
            playVideo("android.resource://" + getActivity().getPackageName() + "/" + R.raw.videoplayfirst);
        } else if (pos == 3 || pos == 6) {
            playVideo("android.resource://" + getActivity().getPackageName() + "/" + R.raw.videoplaysecond);
        } else if (pos == 2 || pos == 4) {
            playVideo("android.resource://" + getActivity().getPackageName() + "/" + R.raw.videoplaythird);
        } else {*/
        playVideo(video_url);
        //}
        return view;
    }


    public void getVideoList() {

        try {
            new GetSermonList().execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class GetSermonList extends AsyncTask<Void, Void, Void> {

        String response;
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  pDialog = new ProgressDialog(getContext());
            pDialog.setMessage(getResources().getString(R.string.get_event_msg));
            pDialog.setCancelable(false);
            pDialog.show();*/
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                NetworkCalls networkCalls = new NetworkCalls();
                response = networkCalls.sendGet(APIUtils.BASE_URL + "get_sermons_videos.php");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.e("Response", response);
            try {
                JSONObject responseObject = new JSONObject(response);

                if (responseObject.get("MESSAGE").toString().trim().equalsIgnoreCase("SUCCESS")) {

                    Gson gson = new Gson();
                    SermonsResponseBean sermonsResponseBean = new Gson().fromJson(responseObject.toString(), SermonsResponseBean.class);

                    sermonList = sermonsResponseBean.getSermonLists();

                    sermonsAdapter = new SermonsAdapter(context, SermonsFragment.this, sermonList);
                    // set a GridLayoutManager with default vertical orientation and 3 number of columns
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    mRecyclerVideoList.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
                    mRecyclerVideoList.setAdapter(sermonsAdapter);

                } else if (responseObject.get("MESSAGE").toString().trim().equalsIgnoreCase("FAILURE")) {
                    //Toast.makeText(getContext(), "No Notifications Available", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            /*if (pDialog.isShowing()) {
                pDialog.dismiss();
            }*/
        }
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
            mMediaPlayer.setDataSource(getActivity(), Uri.parse(path));
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
            mVideoSurface.adjustSize(getDeviceWidth(getContext()), getDeviceHeight(getContext()), mVideoSurface.getWidth(), mVideoSurface.getHeight());
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

    /**
     * Implement VideoMediaController.MediaPlayerControl
     */

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
        return getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ? true : false;
    }

    @Override
    public void toggleFullScreen() {
        if (isFullScreen()) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void exit() {
        resetPlayer();
        getActivity().finish();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mIsComplete = true;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        controller.toggleControllerView();
        return false;
    }

    @Override
    public void onVideoClick(SermonsResponseBean.SermonList sermonVideoDetailsBean) {
        getFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, SermonsFragment.newInstance(sermonVideoDetailsBean.getSermonsPhoto().toString())).addToBackStack("SermonsFragment")
                .commit();
    }

// End VideoMediaController.MediaPlayerControl
}