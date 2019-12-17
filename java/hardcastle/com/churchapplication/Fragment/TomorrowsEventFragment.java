package hardcastle.com.churchapplication.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hardcastle.com.churchapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TomorrowsEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TomorrowsEventFragment extends Fragment {


    public TomorrowsEventFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TomorrowsEventFragment newInstance() {
        TomorrowsEventFragment fragment = new TomorrowsEventFragment();

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tomorrows_event, container, false);
    }
}
