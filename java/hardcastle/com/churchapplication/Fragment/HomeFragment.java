package hardcastle.com.churchapplication.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hardcastle.com.churchapplication.R;

public class HomeFragment extends Fragment implements TabLayout.BaseOnTabSelectedListener {
    /*
        @BindView(R.id.videoView)
        VideoView videoView;*/
    private Unbinder unbinder;


    @BindView(R.id.imagePlay)
    ImageView imagePlay;
    @BindView(R.id.backdrop)
    ImageView imageView;
    private ViewPager viewPager;
    private TabLayout tabs;

    public HomeFragment() {
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
        getActivity().setTitle("Church");
        unbinder = ButterKnife.bind(this, view);

        // Setting ViewPager for each Tabs
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabs = (TabLayout) view.findViewById(R.id.result_tabs);
        setupViewPager(viewPager);

        // Set Tabs inside Toolbar
        tabs.setupWithViewPager(viewPager);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.addOnTabSelectedListener(this);
        tabs.setBackgroundColor(getResources().getColor(R.color.tab_background));
        tabs.setSelectedTabIndicatorColor(Color.parseColor("#71CDF5"));


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {

        TodaysEventFragment.Adapter adapter = new TodaysEventFragment.Adapter(getFragmentManager());
        adapter.addFragment(new DailyActivitiesFragment(), "Daily Activities");
        adapter.addFragment(new SermonListFragment(), "Sermons");
        adapter.addFragment(new GalleryFragment(), "Gallery");
        adapter.addFragment(new AboutUsFragment(), "About Us");
        viewPager.setAdapter(adapter);
    }

   /* @OnClick(R.id.imagePlay)
    public void imgClick() {
        getFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, SermonsFragment.newInstance(1)).addToBackStack(null*//*"SermonsFragment"*//*)
                .commit();

    }*/

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                //imageView.setBackground(getResources().getDrawable(R.drawable.biblecross));
                imageView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.biblecross, null));
                imagePlay.setVisibility(View.INVISIBLE);
                break;
            case 1:
                //imageView.setBackground(getResources().getDrawable(R.drawable.church));
                imageView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.church, null));
                imagePlay.setVisibility(View.INVISIBLE);
                break;
            case 2:
                //imageView.setBackground(getResources().getDrawable(R.drawable.mormon_church));
                imageView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.mormon_church, null));
                imagePlay.setVisibility(View.INVISIBLE);
                break;
            case 3:
                //imageView.setBackground(getResources().getDrawable(R.drawable.open_bible));
                imageView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.dashboard_church1, null));
                imagePlay.setVisibility(View.INVISIBLE);
                break;
            default:
                imageView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.dashboard_church, null));
                imagePlay.setVisibility(View.INVISIBLE);
                break;
        }

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
