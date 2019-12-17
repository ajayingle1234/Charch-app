package hardcastle.com.churchapplication.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hardcastle.com.churchapplication.Interface.SetTabInterface;
import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.Utils.APIUtils;
import hardcastle.com.churchapplication.Utils.GetDataService;
import hardcastle.com.churchapplication.Utils.NetworkCalls;
import hardcastle.com.churchapplication.model.EventsResponseBean;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TodaysEventFragment extends Fragment implements SetTabInterface {

   static int TabSelected;
    private GetDataService mApiInterface;

    //DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy");
    static int tab;

    List<EventsResponseBean.DATum> eventsResponseBeanList = null;

    List<EventsResponseBean.DATum> todayEventsResponseBeanList;
    List<EventsResponseBean.DATum> thisWeekEventsResponseBeanList;
    List<EventsResponseBean.DATum> thisMonthEventsResponseBeanList;

    List<EventsResponseBean.DATum> datesInInterval = new ArrayList<>();
    private ViewPager viewPager;

    public static TodaysEventFragment todaysEventFragment;

    public TodaysEventFragment() {
        // Required empty public constructor
    }

    public static TodaysEventFragment newInstance() {
        TodaysEventFragment fragment = new TodaysEventFragment();
        todaysEventFragment = fragment;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //tab=0;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todays_event, container, false);
        getActivity().setTitle("Events");


        //getEventDetails();
        // Setting ViewPager for each Tabs
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        //setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) view.findViewById(R.id.result_tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TabSelected=tab.getPosition();
                Log.i("Tabselected-  onTabSe",TabSelected+"");

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        try {
            new GetEventDetails().execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getFragmentManager());

        adapter.addFragment(new EventFragment(), "Today");
        adapter.addFragment(new EventFragment(), "Week");
        adapter.addFragment(new EventFragment(), "Month");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void setTab(int position) {
        tab = position;
    }


    static class Adapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public class GetEventDetails extends AsyncTask<Void, Void, Void> {

        String response;
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage(getResources().getString(R.string.get_event_msg));
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                NetworkCalls networkCalls = new NetworkCalls();
                response = networkCalls.sendGet(APIUtils.BASE_URL + "get_event_details.php");
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

                JSONArray jsonArray = responseObject.getJSONArray("DATA");

                if (responseObject.get("MESSAGE").toString().trim().equalsIgnoreCase("SUCCESS")) {

                    Gson gson = new Gson();
                    EventsResponseBean eventsResponseBean = new Gson().fromJson(responseObject.toString(), EventsResponseBean.class);

                    eventsResponseBeanList = eventsResponseBean.getDATA();

                    todayEventsResponseBeanList = getToday(eventsResponseBeanList);
                    thisWeekEventsResponseBeanList = getDatesThisWeek(eventsResponseBeanList);
                    thisMonthEventsResponseBeanList = getDatesThisMonth(eventsResponseBeanList);

                    // Add Fragments to Tabs
                    Adapter adapter = new Adapter(getFragmentManager());
                    adapter.addFragment(EventFragment.newInstance(todayEventsResponseBeanList), "Today");
                    adapter.addFragment(EventFragment.newInstance(thisWeekEventsResponseBeanList), "Week");
                    adapter.addFragment(EventFragment.newInstance(thisMonthEventsResponseBeanList), "Month");

                    viewPager.setAdapter(adapter);
                    viewPager.setCurrentItem(tab);

                    //setupViewPager(viewPager, todayEventsResponseBeanList, thisWeekEventsResponseBeanList, thisMonthEventsResponseBeanList);

                } else if (responseObject.get("MESSAGE").toString().trim().equalsIgnoreCase("FAILURE")) {
                    Toast.makeText(getContext(), "No Events", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }
    }

    public Date getCurentDate() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        Date currentDate = today.getTime();
        return currentDate;
    }

    public Date getDate(Date date) {
        Calendar today = Calendar.getInstance();
        today.setTime(date);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        Date getDate = today.getTime();
        return getDate;
    }

    @Nullable
    private List<EventsResponseBean.DATum> getToday(List<EventsResponseBean.DATum> dates) {
        //Date d=getCurentDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final Date today = getCurentDate();
        for (EventsResponseBean.DATum dateDaTum : dates) {
            Date localDate;
            localDate = null;
            try {
                Date d = dateFormat.parse(dateDaTum.getEventDate());
                localDate = getDate(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (today.compareTo(localDate) == 0) {
                datesInInterval.add(dateDaTum);
            }
        }
        return datesInInterval;
    }

    private List<EventsResponseBean.DATum> getDatesThisWeek(List<EventsResponseBean.DATum> dates) throws ParseException {
        //final TemporalField dayOfWeek = WeekFields.of(Locale.getDefault()).dayOfWeek();

        Calendar mCalendar = Calendar.getInstance();
        Date date = new Date();


        mCalendar.setTime(date);
        mCalendar.set(Calendar.HOUR_OF_DAY, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MILLISECOND, 0);

        // 1 = Sunday, 2 = Monday, etc.
        int day_of_week = mCalendar.get(Calendar.DAY_OF_WEEK);

        int monday_offset;
        if (day_of_week == 1) {
            monday_offset = -6;
        } else
            monday_offset = (2 - day_of_week); // need to minus back
        mCalendar.add(Calendar.DAY_OF_YEAR, monday_offset);

        Date start = mCalendar.getTime();
        //final Date start = mCalendar.getInstance().getTime();
        final Date end = new Date(start.getTime() + 604800000L); // 7 * 24 * 60 * 60 * 1000

        return getDatesBetween(dates, start, end);
    }

    private List<EventsResponseBean.DATum> getDatesThisMonth(List<EventsResponseBean.DATum> dates) throws ParseException {
        Calendar mCalendar = Calendar.getInstance();
        Date date = new Date();
        mCalendar.setTime(date);
        mCalendar.set(Calendar.HOUR_OF_DAY, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MILLISECOND, 0);
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        final Date start = mCalendar.getTime();


        int monthMaxDays = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        Long total = (86400000L * monthMaxDays);
        final Date end = new Date(start.getTime() + (86400000L * monthMaxDays)-1); // 7 * 24 * 60 * 60 * 1000


        return getDatesBetween(dates, start, end);
    }

    private List<EventsResponseBean.DATum> getDatesBetween(List<EventsResponseBean.DATum> dates, Date start, Date end) throws ParseException {
        final List<EventsResponseBean.DATum> datesInInterval = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (EventsResponseBean.DATum date : dates) {
            Date localDate = dateFormat.parse(date.getEventDate());
            if (start.compareTo(localDate) == 0 || end.compareTo(localDate) == 0 || (localDate.equals(end)) || (localDate.equals(start)) || ((localDate.after(start) && localDate.before(end)))) {
                datesInInterval.add(date);
            }
        }


        return datesInInterval;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
