package hardcastle.com.churchapplication.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hardcastle.com.churchapplication.R;

public class PlayVideoFragment extends Fragment {
    /*
        @BindView(R.id.videoView)
        VideoView videoView;*/
    @BindView(R.id.backdrop)
    ImageView imageView;

    private MediaController mediaController;
    private String path;

    private Unbinder unbinder;

    public PlayVideoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PlayVideoFragment newInstance() {
        PlayVideoFragment fragment = new PlayVideoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_play_video, container, false);
        getActivity().setTitle("Sermons");
        unbinder = ButterKnife.bind(this, view);

    /*    //Set MediaController  to enable play, pause, forward, etc options.
        mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(videoView);
        path = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.videoplayback;

        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Starting VideView By Setting MediaController and URI

                videoView.setMediaController(mediaController);
                videoView.setVideoURI(Uri.parse(path));
                videoView.requestFocus();
                videoView.start();
            }
        });*/

        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) view.findViewById(R.id.result_tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);


        return view;
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {

        TodaysEventFragment.Adapter adapter = new TodaysEventFragment.Adapter(getFragmentManager());
        adapter.addFragment(new AboutUsFragment(), "About Us");
        adapter.addFragment(new SermonsFragment(), "Sermons");
        adapter.addFragment(new DailyActivitiesFragment(), "Daily Activities");
        adapter.addFragment(new GalleryFragment(), "Gallery");

        viewPager.setAdapter(adapter);

    }

/*      vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            if(!(currentPosition < videosList.size())){
                return;
            }
            Uri nextUri =
                    Uri.parse(videosList.get(currentPosition++));
            vv.setVideoURI(nextUri);
            vv.start();
        }
    });*/
}
