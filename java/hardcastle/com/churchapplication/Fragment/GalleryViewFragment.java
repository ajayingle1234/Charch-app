package hardcastle.com.churchapplication.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hardcastle.com.churchapplication.Adapter.GalleryViewAdapter;
import hardcastle.com.churchapplication.Interface.OnGalleryClickListener;
import hardcastle.com.churchapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GalleryViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GalleryViewFragment extends Fragment implements OnGalleryClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ViewPager viewPager;
    private Context context;
    private List<Integer> gallery_imageList;

    public GalleryViewFragment() {
        // Required empty public constructor
    }

    public static GalleryViewFragment newInstance(List<Integer> list_gallery_images) {
        GalleryViewFragment fragment = new GalleryViewFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList("gallery_images", (ArrayList<Integer>) list_gallery_images);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            gallery_imageList = getArguments().getIntegerArrayList("gallery_images");
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
        View view = inflater.inflate(R.layout.fragment_gallery_view, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.vp_photogallery);

        if (viewPager != null) {
            viewPager.setAdapter(new GalleryViewAdapter(context, gallery_imageList));
        }

        return view;
    }

    @Override
    public void onGalleryItemClick(List<Integer> list_gallery_images) {

    }
}
