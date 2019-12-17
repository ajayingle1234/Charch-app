package hardcastle.com.churchapplication.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import hardcastle.com.churchapplication.Adapter.NotificationAdapter;
import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.Utils.APIUtils;
import hardcastle.com.churchapplication.Utils.NetworkCalls;
import hardcastle.com.churchapplication.model.NotificationResponseBean;

public class NotificationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private List<NotificationResponseBean.NotificationBeanList> notificationBeanList;
    private NotificationAdapter adapter;

    public NotificationFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();

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
        View view = inflater.inflate(R.layout.fragment_notification, container, false);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            getActivity().setTitle("Notifications");
            getActivity().setActionBar(toolbar);
        }


   /*     notificationBeanList = new ArrayList<>();
        NotificationBean NotificationBean1 = new NotificationBean("We,ve Updated our app!", getResources().getString(R.string.text_notification));
        NotificationBean NotificationBean2 = new NotificationBean("New Sermon Available Now!", getResources().getString(R.string.text_notification2));
        NotificationBean NotificationBean3 = new NotificationBean("Are you ready for church?", getResources().getString(R.string.text_notification0));
        NotificationBean NotificationBean4 = new NotificationBean("Download the Latest Update!", getResources().getString(R.string.text_notification3));
        NotificationBean NotificationBean5 = new NotificationBean("New Sermon Available Now!", getResources().getString(R.string.text_notification1));
        NotificationBean NotificationBean6 = new NotificationBean("We,ve Updated our app!", getResources().getString(R.string.text_notification));
        NotificationBean NotificationBean7 = new NotificationBean("Are you ready for church?", getResources().getString(R.string.text_notification0));
        notificationBeanList.add(NotificationBean1);
        notificationBeanList.add(NotificationBean2);
        notificationBeanList.add(NotificationBean3);
        notificationBeanList.add(NotificationBean4);
        notificationBeanList.add(NotificationBean5);
        notificationBeanList.add(NotificationBean6);
        notificationBeanList.add(NotificationBean7);*/

        recyclerView = view.findViewById(R.id.notification_recyclerView);

        try {
            new GetNotificationDetails().execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public class GetNotificationDetails extends AsyncTask<Void, Void, Void> {

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
                response = networkCalls.sendGet(APIUtils.BASE_URL + "get_notification.php");
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
                    NotificationResponseBean notificationResponseBean = new Gson().fromJson(responseObject.toString(), NotificationResponseBean.class);

                    notificationBeanList = notificationResponseBean.getNotificationBeanList();

                    adapter = new NotificationAdapter(getActivity(), notificationBeanList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                } else if (responseObject.get("MESSAGE").toString().trim().equalsIgnoreCase("FAILURE")) {
                    Toast.makeText(getContext(), "No Notifications Available", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }
    }

}
