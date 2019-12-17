package hardcastle.com.churchapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.Unbinder;
import hardcastle.com.churchapplication.Adapter.SermonsAdapter;
import hardcastle.com.churchapplication.Fragment.HomeFragment;
import hardcastle.com.churchapplication.Fragment.SermonsFragment;
import hardcastle.com.churchapplication.Interface.OnSermonVideoItemClickListener;
import hardcastle.com.churchapplication.Utils.APIUtils;
import hardcastle.com.churchapplication.Utils.NetworkCalls;
import hardcastle.com.churchapplication.VideoPlayer.ResizeSurfaceView;
import hardcastle.com.churchapplication.VideoPlayer.VideoControllerView;
import hardcastle.com.churchapplication.model.SermonsResponseBean;

import static butterknife.ButterKnife.bind;

public class SermonActivity extends AppCompatActivity implements OnSermonVideoItemClickListener {

    ResizeSurfaceView mVideoSurface;
    MediaPlayer mMediaPlayer;
    VideoControllerView controller;
    private int mVideoWidth;
    private int mVideoHeight;
    private View mContentView;
    private View mLoadingView;
    private boolean mIsComplete;
    private RecyclerView mRecyclerVideoList;
    private Fragment selectedFragment = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private SermonsAdapter sermonsAdapter;
    private Context context;

    private Unbinder unbinder;
    private String video_url;
    private List<SermonsResponseBean.SermonList> sermonList;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sermon);

        setTitle("Sermons");
        bind(this);
        //Recyclerview for show list
        mRecyclerVideoList = findViewById(R.id.recycler_video_list);
        getVideoList();

        Intent intent = getIntent();
        video_url = intent.getStringExtra("url");

        selectedFragment = SermonsFragment.newInstance(video_url);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.sermon_frame_layout, selectedFragment);
        //transaction.addToBackStack("HomeFragment");
        transaction.commit();

        //  playVideo(video_url);
    }

    @Override
    protected void onStart() {
        super.onStart();
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

                    sermonsAdapter = new SermonsAdapter(context, SermonActivity.this, sermonList);
                    // set a GridLayoutManager with default vertical orientation and 3 number of columns
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SermonActivity.this);
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


    @Override
    public void onVideoClick(SermonsResponseBean.SermonList sermonVideoDetailsBean) {
        //resetPlayer();
        //playVideo(sermonVideoDetailsBean.getSermonsPhoto());

        selectedFragment = SermonsFragment.newInstance(sermonVideoDetailsBean.getSermonsPhoto());
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.sermon_frame_layout, selectedFragment);
        //transaction.addToBackStack("HomeFragment");
        transaction.commit();



      /*  getFragmentManager().beginTransaction()
                .replace(R.id.sermon_frame_layout, SermonsFragment.newInstance(sermonVideoDetailsBean.getSermonsPhoto())).addToBackStack("SermonsFragment")
                .commit();*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
