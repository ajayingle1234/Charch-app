package hardcastle.com.churchapplication.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hardcastle.com.churchapplication.Adapter.EventAdapter;
import hardcastle.com.churchapplication.EventDetailsActivity;
import hardcastle.com.churchapplication.Interface.OnRecyclerViewItemClickListener;
import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.model.EventsResponseBean;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventFragment extends Fragment implements OnRecyclerViewItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_EVENT_LIST = "event_list";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<EventsResponseBean.DATum> todayEventsResponseBeanList;


    private EventAdapter adapter;
    private RecyclerView recyclerView;
    private TextView textView_msg;

    private OnFragmentInteractionListener mListener;

    public EventFragment() {
        // Required empty public constructor
    }


    public static EventFragment newInstance(List<EventsResponseBean.DATum> todayEventsResponseBeanList) {
        EventFragment fragment = new EventFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_EVENT_LIST, (ArrayList<? extends Parcelable>) todayEventsResponseBeanList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            todayEventsResponseBeanList = getArguments().getParcelableArrayList(ARG_EVENT_LIST);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_event, container, false);
        recyclerView = view.findViewById(R.id.customRecyclerView);
        textView_msg = view.findViewById(R.id.txt_msg_label);
        if (todayEventsResponseBeanList.size() == 0) {
            textView_msg.setVisibility(View.VISIBLE);
            textView_msg.setText("No Events Available....");
        } else {
            adapter = new EventAdapter(getActivity(), this, todayEventsResponseBeanList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(int position) {
        EventsResponseBean.DATum daTum = todayEventsResponseBeanList.get(position);
       /* getFragmentManager().beginTransaction()
                .add(R.id.frame_layout, EventDetailsFragment.newInstance(daTum),"EventDetails")
                .commit();*/



        Intent in=new Intent(getContext(), EventDetailsActivity.class);
                in.putExtra("EventDetails",daTum);
                in.putExtra("TabSelected",TodaysEventFragment.TabSelected);
                startActivity(in);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
