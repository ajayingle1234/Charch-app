package hardcastle.com.churchapplication.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hardcastle.com.churchapplication.Adapter.DailyActivitesDetailsAdapter;
import hardcastle.com.churchapplication.Adapter.DailyActivityAdapter;
import hardcastle.com.churchapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private DailyActivityAdapter adapter;
    private DailyActivitesDetailsAdapter dailyActivitesDetailsAdapter;
    private Context context;
    private Integer[] monthofyear;


    public DailyFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DailyFragment newInstance() {
        DailyFragment fragment = new DailyFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily, container, false);

        Calendar cal = Calendar.getInstance();
        int currentday = cal.get(Calendar.DAY_OF_MONTH);

        List<Integer> daylist = new ArrayList<>();
        daylist.add(10);
        daylist.add(11);
        daylist.add(12);
        daylist.add(10);
        daylist.add(11);
        daylist.add(12);
        daylist.add(12);

        Date date = Calendar.getInstance().getTime();

        // if (currentday==Integer.parseInt(tx))

        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        String[] days = new String[7];
        Integer dayofWeek[] = new Integer[7];
        for (int i = 0; i < 7; i++) {
            days[i] = format.format(calendar.getTime());
            dayofWeek[i] = calendar.get(Calendar.DAY_OF_MONTH);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        int daysofmonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.WEEK_OF_MONTH, Calendar.MONTH);
        monthofyear = new Integer[daysofmonth];
        for (int i = 0; i < daysofmonth; i++) {
            //   days[i] = format.format(calendar.getTime());
            //dayofWeek[i] = calendar.get(Calendar.DAY_OF_MONTH);

            monthofyear[i] = calendar.get(Calendar.DAY_OF_MONTH);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        recyclerView = view.findViewById(R.id.customRecyclerView);
        recyclerView1 = view.findViewById(R.id.customRecyclerView1);

        adapter = new DailyActivityAdapter(getActivity(), Arrays.asList(monthofyear));
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setAdapter(adapter);
        //Toast.makeText(getActivity(), "" + daylist, Toast.LENGTH_SHORT).show();

        //setDateAdapter(String.valueOf("WED\n" + currentday));
        return view;
    }

    public void setDateAdapter(String date) {
        dailyActivitesDetailsAdapter = new DailyActivitesDetailsAdapter(getActivity(), date);
        // set a GridLayoutManager with default vertical orientation and 1 number of columns
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(context);
        recyclerView1.setLayoutManager(layoutManager1); // set LayoutManager to RecyclerView
        recyclerView1.setAdapter(dailyActivitesDetailsAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


        }
    }
}