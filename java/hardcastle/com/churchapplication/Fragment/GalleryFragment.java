package hardcastle.com.churchapplication.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hardcastle.com.churchapplication.Adapter.GalleryAdapter;
import hardcastle.com.churchapplication.Adapter.PrayerAdapter;
import hardcastle.com.churchapplication.Interface.OnGalleryClickListener;
import hardcastle.com.churchapplication.Interface.OnRecyclerViewItemClickListener;
import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.Utils.APIUtils;
import hardcastle.com.churchapplication.Utils.NetworkCalls;
import hardcastle.com.churchapplication.model.GalleryBean;
import hardcastle.com.churchapplication.model.PrayerResponseBean;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GalleryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GalleryFragment extends Fragment implements OnGalleryClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    TextView txtEmpty;
    private GalleryAdapter galleryAdapter;
    private Context context;
    private ArrayList<GalleryBean> galleryResponseList = new ArrayList<>();

    public GalleryFragment() {
        // Required empty public constructor
    }

    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView = view.findViewById(R.id.customRecyclerView);
        txtEmpty=view.findViewById(R.id.txt_msg);

        List<Integer> listofImages = new ArrayList<>();
        listofImages.add(R.drawable.sermons);
        listofImages.add(R.drawable.church);
        listofImages.add(R.drawable.biblecross);
        listofImages.add(R.drawable.sermons);
        listofImages.add(R.drawable.mormon_church);
        listofImages.add(R.drawable.sermons);
        listofImages.add(R.drawable.event_img);
        listofImages.add(R.drawable.sermons);


        listofImages.add(R.drawable.sermons);

        //galleryAdapter = new GalleryAdapter(context, this, listofImages);
        // set a GridLayoutManager with default vertical orientation and 1 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        recyclerView.setAdapter(galleryAdapter);

        getGalleryList();
        return view;
    }

    private void getGalleryList() {
        new GetGalleryDetails().execute();

    }
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onGalleryItemClick(List<Integer> list_gallery_images) {

        getFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, GalleryViewFragment.newInstance(list_gallery_images)).addToBackStack("SermonsFragment")
                .commit();

    }


    public class GetGalleryDetails extends AsyncTask<Void, Void, Void> {

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
                response = networkCalls.sendGet(APIUtils.BASE_URL + "get_gallery.php");
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
                    GalleryBean galleryBean = new Gson().fromJson(responseObject.toString(), GalleryBean.class);

                     galleryResponseList= galleryBean.getGalleryResponseList();
                    //sortListAccordingToID(prayerResponseList);

                    if (galleryResponseList.size() == 0) {
                        txtEmpty.setVisibility(View.VISIBLE);
                        txtEmpty.setText("No content Available....");
                    } else {
                        galleryAdapter = new GalleryAdapter(getActivity(), galleryResponseList);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

                        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                        recyclerView.setAdapter(galleryAdapter);
                    }


                } else if (responseObject.get("MESSAGE").toString().trim().equalsIgnoreCase("FAILURE")) {
                    Toast.makeText(getContext(), "No Gallery content", Toast.LENGTH_LONG).show();
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
