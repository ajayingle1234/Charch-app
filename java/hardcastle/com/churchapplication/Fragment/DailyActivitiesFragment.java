package hardcastle.com.churchapplication.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hardcastle.com.churchapplication.Adapter.DailyActivitesDetailsAdapter;
import hardcastle.com.churchapplication.Adapter.DailyActivityAdapter;
import hardcastle.com.churchapplication.R;

public class DailyActivitiesFragment extends Fragment implements View.OnClickListener {
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private DailyActivityAdapter adapter;
    private DailyActivitesDetailsAdapter dailyActivitesDetailsAdapter;

    LinearLayout l1;
    LinearLayout l2;
    LinearLayout l3;
    LinearLayout l4;
    LinearLayout l5;
    LinearLayout l6;
    LinearLayout l7;
    TextView t11, t12, t13, t14, t15, t16, t17;

    public DailyActivitiesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DailyActivitiesFragment newInstance() {
        DailyActivitiesFragment fragment = new DailyActivitiesFragment();

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
        View view = inflater.inflate(R.layout.fragment_daily_activities, container, false);

        l1 = view.findViewById(R.id.monday);
        l2 = view.findViewById(R.id.tusday);
        l3 = view.findViewById(R.id.wednesday);
        l4 = view.findViewById(R.id.thursday);
        l5 = view.findViewById(R.id.friday);
        l6 = view.findViewById(R.id.saturday);
        l7 = view.findViewById(R.id.sunday);

        t11 = view.findViewById(R.id.txt_11);
        t12 = view.findViewById(R.id.txt_12);
        t13 = view.findViewById(R.id.txt_13);
        t14 = view.findViewById(R.id.txt_14);
        t15 = view.findViewById(R.id.txt_15);
        t16 = view.findViewById(R.id.txt_16);
        t17 = view.findViewById(R.id.txt_17);

        l1.setOnClickListener(this);
        l2.setOnClickListener(this);
        l3.setOnClickListener(this);
        l4.setOnClickListener(this);
        l5.setOnClickListener(this);
        l6.setOnClickListener(this);
        l7.setOnClickListener(this);


        Calendar cal = Calendar.getInstance();
        int currentday = cal.get(Calendar.DAY_OF_MONTH);

        List<Integer> daylist = new ArrayList<>();
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

        t11.setText(dayofWeek[0].toString());
        t12.setText(dayofWeek[1].toString());
        t13.setText(dayofWeek[2].toString());
        t14.setText(dayofWeek[3].toString());
        t15.setText(dayofWeek[4].toString());
        t16.setText(dayofWeek[5].toString());
        t17.setText(dayofWeek[6].toString());

        for (int i = 0; i < dayofWeek.length; i++) {
            if (currentday == dayofWeek[i]) {
                if (i == 0) {
                    l1.setBackground(getResources().getDrawable(R.drawable.button_dark));
                } else if (i == 1) {
                    l2.setBackground(getResources().getDrawable(R.drawable.button_dark));
                } else if (i == 2) {
                    l3.setBackground(getResources().getDrawable(R.drawable.button_dark));
                } else if (i == 3) {
                    l4.setBackground(getResources().getDrawable(R.drawable.button_dark));
                } else if (i == 4) {
                    l5.setBackground(getResources().getDrawable(R.drawable.button_dark));
                } else if (i == 5) {
                    l6.setBackground(getResources().getDrawable(R.drawable.button_dark));
                } else if (i == 6) {
                    l7.setBackground(getResources().getDrawable(R.drawable.button_dark));
                }
            }
        }

        recyclerView = view.findViewById(R.id.customRecyclerView);
        recyclerView1 = view.findViewById(R.id.customRecyclerView1);

        adapter = new DailyActivityAdapter(getActivity(), daylist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //Toast.makeText(getActivity(), "" + daylist, Toast.LENGTH_SHORT).show();

     /*   dailyActivitesDetailsAdapter = new DailyActivitesDetailsAdapter(getActivity());
        // set a GridLayoutManager with default vertical orientation and 1 number of columns
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        recyclerView1.setLayoutManager(layoutManager1); // set LayoutManager to RecyclerView
        recyclerView1.setAdapter(dailyActivitesDetailsAdapter);*/

        Format dayformat = new SimpleDateFormat("E, MMM d, yyyy");
        String day = dayformat.format(Calendar.getInstance().getTimeInMillis()).substring(0,3).toUpperCase();
        setDateAdapter(String.valueOf("" + day + "\n" + currentday));
        return view;
    }

    public void setDateAdapter(String date) {
        dailyActivitesDetailsAdapter = new DailyActivitesDetailsAdapter(getActivity(), date);
        // set a GridLayoutManager with default vertical orientation and 1 number of columns
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
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

            case R.id.monday:

                l1.setBackground(getResources().getDrawable(R.drawable.button_dark));

                l2.setBackground(getResources().getDrawable(R.drawable.white_button));
                l3.setBackground(getResources().getDrawable(R.drawable.white_button));
                l4.setBackground(getResources().getDrawable(R.drawable.white_button));
                l5.setBackground(getResources().getDrawable(R.drawable.white_button));
                l6.setBackground(getResources().getDrawable(R.drawable.white_button));
                l7.setBackground(getResources().getDrawable(R.drawable.white_button));
                setDateAdapter("MON\n" + t11.getText().toString());
                break;

            case R.id.tusday:

                l2.setBackground(getResources().getDrawable(R.drawable.button_dark));

                l1.setBackground(getResources().getDrawable(R.drawable.white_button));
                l3.setBackground(getResources().getDrawable(R.drawable.white_button));
                l4.setBackground(getResources().getDrawable(R.drawable.white_button));
                l5.setBackground(getResources().getDrawable(R.drawable.white_button));
                l6.setBackground(getResources().getDrawable(R.drawable.white_button));
                l7.setBackground(getResources().getDrawable(R.drawable.white_button));
                setDateAdapter("TUE\n" + t12.getText().toString());
                break;
            case R.id.wednesday:
                l3.setBackground(getResources().getDrawable(R.drawable.button_dark));

                l2.setBackground(getResources().getDrawable(R.drawable.white_button));
                l1.setBackground(getResources().getDrawable(R.drawable.white_button));
                l4.setBackground(getResources().getDrawable(R.drawable.white_button));
                l5.setBackground(getResources().getDrawable(R.drawable.white_button));
                l6.setBackground(getResources().getDrawable(R.drawable.white_button));
                l7.setBackground(getResources().getDrawable(R.drawable.white_button));
                setDateAdapter("WED\n" + t13.getText().toString());
                break;

            case R.id.thursday:

                l4.setBackground(getResources().getDrawable(R.drawable.button_dark));

                l2.setBackground(getResources().getDrawable(R.drawable.white_button));
                l3.setBackground(getResources().getDrawable(R.drawable.white_button));
                l1.setBackground(getResources().getDrawable(R.drawable.white_button));
                l5.setBackground(getResources().getDrawable(R.drawable.white_button));
                l6.setBackground(getResources().getDrawable(R.drawable.white_button));
                l7.setBackground(getResources().getDrawable(R.drawable.white_button));
                setDateAdapter("THU\n" + t14.getText().toString());
                break;
            case R.id.friday:
                l5.setBackground(getResources().getDrawable(R.drawable.button_dark));

                l2.setBackground(getResources().getDrawable(R.drawable.white_button));
                l3.setBackground(getResources().getDrawable(R.drawable.white_button));
                l4.setBackground(getResources().getDrawable(R.drawable.white_button));
                l1.setBackground(getResources().getDrawable(R.drawable.white_button));
                l6.setBackground(getResources().getDrawable(R.drawable.white_button));
                l7.setBackground(getResources().getDrawable(R.drawable.white_button));
                setDateAdapter("FRI\n" + t15.getText().toString());
                break;

            case R.id.saturday:
                l6.setBackground(getResources().getDrawable(R.drawable.button_dark));

                l2.setBackground(getResources().getDrawable(R.drawable.white_button));
                l3.setBackground(getResources().getDrawable(R.drawable.white_button));
                l4.setBackground(getResources().getDrawable(R.drawable.white_button));
                l5.setBackground(getResources().getDrawable(R.drawable.white_button));
                l1.setBackground(getResources().getDrawable(R.drawable.white_button));
                l7.setBackground(getResources().getDrawable(R.drawable.white_button));
                setDateAdapter("SAT\n" + t16.getText().toString());
                break;
            case R.id.sunday:
                l7.setBackground(getResources().getDrawable(R.drawable.button_dark));

                l2.setBackground(getResources().getDrawable(R.drawable.white_button));
                l3.setBackground(getResources().getDrawable(R.drawable.white_button));
                l4.setBackground(getResources().getDrawable(R.drawable.white_button));
                l5.setBackground(getResources().getDrawable(R.drawable.white_button));
                l6.setBackground(getResources().getDrawable(R.drawable.white_button));
                l1.setBackground(getResources().getDrawable(R.drawable.white_button));
                setDateAdapter("SUN\n" + t17.getText().toString());
                break;
        }
    }
}
