package hardcastle.com.churchapplication.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hardcastle.com.churchapplication.Adapter.GalleryAdapter;
import hardcastle.com.churchapplication.Adapter.SermonsAdapter;
import hardcastle.com.churchapplication.Interface.OnRecyclerViewItemClickListener;
import hardcastle.com.churchapplication.Interface.OnSermonVideoItemClickListener;
import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.SermonActivity;
import hardcastle.com.churchapplication.Utils.APIUtils;
import hardcastle.com.churchapplication.Utils.NetworkCalls;
import hardcastle.com.churchapplication.model.AboutUsResponseBean;
import hardcastle.com.churchapplication.model.SermonVideoDetailsBean;
import hardcastle.com.churchapplication.model.SermonsResponseBean;

/**
 * A simple {@link Fragment} subclass.
 */
public class SermonListFragment extends Fragment implements OnSermonVideoItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private SermonsAdapter sermonsAdapter;
    private Context context;
    private List<SermonsResponseBean.SermonList> sermonList;

    public SermonListFragment() {
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
        getVideoList();
        return view;
    }
    public void getVideoList() {

        try {
            new GetSermonList().execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    /*    sermonsAdapter = new SermonsAdapter(context, this, setVideoList());
        // set a GridLayoutManager with default vertical orientation and 3 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        recyclerView.setAdapter(sermonsAdapter);*/
    }

    public List<SermonVideoDetailsBean> setVideoList() {
        List<SermonVideoDetailsBean> sermonVideoDetailsBeanList = new ArrayList<>();

        String url = "https://r1---sn-cvh7kney.googlevideo.com/videoplayback?gir=yes&dur=124.784&expire=1552672133&pl=24&c=WEB&ratebypass=yes&itag=18&ipbits=0&signature=09BCF9CB5914F266C24FD2F5215183517E67FF70.5A21DA1A44A9FDAA32F5FE2EC034D3652F48DA5F&source=youtube&clen=8364452&requiressl=yes&mime=video%2Fmp4&ei=JZGLXKTLC4LHyAWRr4DABA&id=o-AANa2zzUCdXNsZnhueIjkBUiDws-yzvnH6ED9qQ2B0_t&fvip=9&lmt=1447642017943032&ip=95.161.9.41&key=cms1&sparams=clen,dur,ei,expire,gir,id,ip,ipbits,ipbypass,itag,lmt,mime,mip,mm,mn,ms,mv,pl,ratebypass,requiressl,source&video_id=7JxVkqRytJU&title=Bishop+TD+Jakes+-+Don%27t+Be+Blindsided&rm=sn-axqs7y&req_id=ba80316ffa3a3ee&ipbypass=yes&mip=1.22.183.122&redirect_counter=2&cm2rm=sn-up3vo5-2o9l7e&cms_redirect=yes&mm=29&mn=sn-cvh7kney&ms=rdu&mt=1552650410&mv=m";
        SermonVideoDetailsBean sermonVideoDetailsBean = new SermonVideoDetailsBean(url, "Bishop TD Jakes - Don't Be Blindsided");
        sermonVideoDetailsBeanList.add(sermonVideoDetailsBean);

        url = "https://r3---sn-cvh7kney.googlevideo.com/videoplayback?key=cms1&mime=video%2Fmp4&c=WEB&expire=1552658561&ei=IVyLXOniB8vFyAWg2Y64Aw&signature=21A021E520D60A8FE8890CFA16E91A225FEE6699.58595A014922FACF2D459781F7405C07AD3406E2&clen=17575146&gir=yes&ratebypass=yes&lmt=1512628958413036&dur=204.451&source=youtube&sparams=clen,dur,ei,expire,gir,id,ip,ipbits,ipbypass,itag,lmt,mime,mip,mm,mn,ms,mv,pl,ratebypass,requiressl,source&requiressl=yes&fvip=11&itag=18&ip=91.224.156.197&ipbits=0&pl=24&id=o-AJghpkAviN1SfYsk0QZnpos7FiAjb49Q8uTJ8eX3kHw-&video_id=LgXkxr5Sdco&title=What+Are+You+Running+From+-+Pastor+Steven+Furtick&rm=sn-cpboxuaaj03g-px8e7e,sn-3c2edee&req_id=4d81f797ff6ba3ee&redirect_counter=2&cms_redirect=yes&ipbypass=yes&mip=1.22.183.122&mm=29&mn=sn-cvh7kney&ms=rdu&mt=1552650324&mv=m";
        SermonVideoDetailsBean sermonVideoDetailsBean1 = new SermonVideoDetailsBean(url, "What Are You Running From? | Pastor Steven Furtick\n" + "\n");
        sermonVideoDetailsBeanList.add(sermonVideoDetailsBean1);

        url = "https://r3---sn-npoeener.googlevideo.com/videoplayback?id=o-AH3M87pR-xXOP56SQdU9ONiWr8Vbd-LmLJ-rVnMM5URF&pl=24&ipbits=0&ip=202.169.239.66&fvip=3&sparams=clen,dur,ei,expire,gir,id,ip,ipbits,ipbypass,itag,lmt,mime,mip,mm,mn,ms,mv,pl,ratebypass,requiressl,source&requiressl=yes&itag=18&source=youtube&dur=410.203&lmt=1331076560644526&ratebypass=yes&clen=33104478&gir=yes&signature=1A7F636619487045254FD9F6ADDEAE11FF265DC2.6179ED0D7CCC5BE3C0467A1FA176660619F8545B&ei=pJKLXIG0OMqQowOyn42YBQ&c=WEB&expire=1552672517&mime=video%2Fmp4&key=cms1&video_id=qdiCqP2Bp3g&title=Christine+Caine-Fools+for+Christ&rm=sn-2uuxa3vh-n0cz7s,sn-npols7s&req_id=813be39c5364a3ee&ipbypass=yes&mip=1.22.183.137&redirect_counter=3&cm2rm=sn-cvhlk7s&cms_redirect=yes&mm=34&mn=sn-npoeener&ms=ltu&mt=1552650787&mv=m";
        SermonVideoDetailsBean sermonVideoDetailsBean2 = new SermonVideoDetailsBean(url, "Christine Caine-Fools for Christ\n" + "\n");
        sermonVideoDetailsBeanList.add(sermonVideoDetailsBean2);

        url = "https://r2---sn-cvh76nez.googlevideo.com/videoplayback?expire=1552672654&ei=LpOLXPyzEJq8oQOJuq6oBQ&itag=18&signature=0FF39BA4332AC316D8359866FC9EF23718702BA6.4F752C2EFC499B13DCDD21D9B70EAED9DB70BF65&requiressl=yes&source=youtube&dur=272.300&clen=17323780&ipbits=0&c=WEB&ratebypass=yes&lmt=1393488535098431&fvip=2&sparams=clen,dur,ei,expire,gir,id,ip,ipbits,ipbypass,itag,lmt,mime,mip,mm,mn,ms,mv,pl,ratebypass,requiressl,source&mime=video%2Fmp4&id=o-AFQIFmiGuTjP8TQUzrN6Ltw5H0Q4qB8_yLD3QJVAim13&pl=24&gir=yes&key=cms1&ip=180.180.218.226&video_id=3GW6tpdx2n0&title=Joel+Osteen+-+How+to+keep+moving+forward&rm=sn-uvu-c3367r,sn-30als7l&req_id=406d6aa871b3a3ee&redirect_counter=2&cms_redirect=yes&ipbypass=yes&mip=1.22.183.42&mm=30&mn=sn-cvh76nez&ms=nxu&mt=1552649953&mv=u";
        SermonVideoDetailsBean sermonVideoDetailsBean3 = new SermonVideoDetailsBean(url, "Joel Osteen - How to keep moving forward");
        sermonVideoDetailsBeanList.add(sermonVideoDetailsBean3);
        return sermonVideoDetailsBeanList;
    }

    @Override
    public void onVideoClick(SermonsResponseBean.SermonList sermonList) {
        Intent intent=new Intent(getActivity(), SermonActivity.class);
        intent.putExtra("url",sermonList.getSermonsPhoto());
        startActivity(intent);
/*        getFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, SermonsFragment.newInstance(sermonList.getSermonsPhoto())).addToBackStack("SermonsFragment")
                .commit();*/
    }

    public class GetSermonList extends AsyncTask<Void, Void, Void> {

        String response;
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  pDialog = new ProgressDialog(getContext());
            pDialog.setMessage(getResources().getString(R.string.get_event_msg));
            pDialog.setCancelable(false);
            pDialog.show();*/
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                NetworkCalls networkCalls = new NetworkCalls();
                response = networkCalls.sendGet(APIUtils.BASE_URL + "get_sermons_videos.php");
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

                if (responseObject.get("MESSAGE").toString().trim().equalsIgnoreCase("SUCCESS")) {

                    Gson gson = new Gson();
                    SermonsResponseBean sermonsResponseBean = new Gson().fromJson(responseObject.toString(), SermonsResponseBean.class);

                    sermonList = sermonsResponseBean.getSermonLists();

                    sermonsAdapter = new SermonsAdapter(context,  SermonListFragment.this, sermonList);
                    // set a GridLayoutManager with default vertical orientation and 3 number of columns
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
                    recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                    recyclerView.setAdapter(sermonsAdapter);

                } else if (responseObject.get("MESSAGE").toString().trim().equalsIgnoreCase("FAILURE")) {
                    //Toast.makeText(getContext(), "No Notifications Available", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            /*if (pDialog.isShowing()) {
                pDialog.dismiss();
            }*/
        }
    }

}
