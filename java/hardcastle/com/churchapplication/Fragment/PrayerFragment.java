package hardcastle.com.churchapplication.Fragment;


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hardcastle.com.churchapplication.Adapter.EventAdapter;
import hardcastle.com.churchapplication.Adapter.PrayerAdapter;
import hardcastle.com.churchapplication.Adapter.PrayerListViewAdapter;
import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.Utils.APIUtils;
import hardcastle.com.churchapplication.Utils.NetworkCalls;
import hardcastle.com.churchapplication.model.EventsResponseBean;
import hardcastle.com.churchapplication.model.LoginResponceBean;
import hardcastle.com.churchapplication.model.PrayerResponseBean;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static hardcastle.com.churchapplication.Fragment.EditProfileFragment.CHOOSE_FROM_GALLERY;
import static hardcastle.com.churchapplication.Fragment.EditProfileFragment.REQUEST_IMAGE_CAPTURE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrayerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Context activityContext;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SharedPreferences sharedPreferences;
    private Unbinder unbinder;

    @BindView(R.id.edt_prayer)
    EditText _edt_prayer;
    @BindView(R.id.btn_upload_video)
    ImageView _btn_uploadVideo;
    @BindView(R.id.btn_upload_image)
    ImageView _btn_uploadImage;
    @BindView(R.id.btn_capture_image)
    ImageView _btn_captureImage;
    @BindView(R.id.btn_add_prayer)
    Button _btn_addPrayer;

    @BindView(R.id.txt_msg)
    TextView txt_msg;

    @BindView(R.id.txt_user_lastname)
    TextView txt_user_lastname;

    @BindView(R.id.lvPrayer)
    ListView prayer_listView;

    @BindView(R.id.RgVisibility)
    RadioGroup rgVisibility;
    @BindView(R.id.RbCommunal)
    RadioButton rbCommunal;
    @BindView(R.id.RbPastoral)
    RadioButton rbPastoral;

    private static final int SELECT_VIDEO = 3;

    private String selectedPath;
    private PrayerListViewAdapter prayerAdapter;

    private List<PrayerResponseBean.PrayerResponse> prayerResponseList = new ArrayList<>();
    private Toolbar toolbar;

    public PrayerFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PrayerFragment newInstance() {
        PrayerFragment fragment = new PrayerFragment();
        Bundle args = new Bundle();
     /*   args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);*/
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prayer, container, false);

        unbinder = ButterKnife.bind(this, view);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        getActivity().setTitle("Prayer Wall");
        getActivity().setActionBar(toolbar);

        sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        if (sharedPreferences.getBoolean(getResources().getString(R.string.preference_is_login), false)) {

            String login_Type = sharedPreferences.getString(getResources().getString(R.string.preference_login_type), "");

            if (login_Type.equals("R")) {
                Gson gson = new Gson();
                String userDetails = sharedPreferences.getString(getResources().getString(R.string.preference_login_user_info), "");

                LoginResponceBean.LoginResponse loginResponse = gson.fromJson(userDetails, LoginResponceBean.LoginResponse.class);

                txt_user_lastname.setText(loginResponse.getFirstname() + " " + loginResponse.getLastname());
            }

        }
        rgVisibility.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.RbCommunal:
                        rbCommunal.setChecked(true);
                        rbPastoral.setChecked(false);
                        break;
                    case R.id.RbPastoral:
                        rbCommunal.setChecked(false);
                        rbPastoral.setChecked(true);
                        break;
                }
            }
        });
        getPrayerlist();
        return view;
    }

    private void getPrayerlist() {
        new GetPrayerDetails().execute();

    }

    @OnClick(R.id.btn_add_prayer)
    void addPrayer() {
        if (!_edt_prayer.getText().toString().trim().equalsIgnoreCase("")) {
            new AddPrayer().execute();
            _edt_prayer.setText("");
            getPrayerlist();
        } else {
            Toast.makeText(activityContext, "Please enter the prayer. ", Toast.LENGTH_SHORT).show();
        }


        //uploadVideo();
    }

    @OnClick(R.id.btn_upload_video)
    void chooseVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);
    }


    @OnClick(R.id.btn_upload_image)
    void uploadImage() {

        initializeControlsGallery();
    }

    @OnClick(R.id.btn_capture_image)
    void captureImage() {

        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
    }


    private void initializeControlsGallery() {

// TODO Auto-generated method stub
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// call android default gallery
        intent.setType("image/*");
        // intent.setAction(Intent.ACTION_PICK);
        // ******** code for crop image
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 150);

        try {

            intent.putExtra("return-data", true);
            startActivityForResult(Intent.createChooser(intent,
                    "Complete action using"), CHOOSE_FROM_GALLERY);

        } catch (ActivityNotFoundException e) {
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        //_circleImageProfile.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (requestCode == CHOOSE_FROM_GALLERY) {
                        /*try {
                            Bundle extras2 = data.getExtras();
                            if (extras2 != null) {
                                Bitmap photo = extras2.getParcelable("data");
                                _circleImageProfile.setImageBitmap(photo);
                                Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                                        photo, 500, 500, false);
                                image = getStringImage(resizedBitmap);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContext().getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();

                        //_circleImageProfile.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    }
                    break;
                case 3:
                    System.out.println("SELECT_VIDEO");
                    Uri selectedImageUri = data.getData();
                    selectedPath = getPath(selectedImageUri);
                    break;
                default:
                    break;
            }
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = activityContext.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = activityContext.getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();

        return path;
    }

    private void uploadVideo() {
        class UploadVideo extends AsyncTask<Void, Void, String> {

            ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uploading = ProgressDialog.show(activityContext, "Uploading File", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploading.dismiss();
                Toast.makeText(activityContext, "" + s, Toast.LENGTH_SHORT).show();
                //textViewResponse.setText(Html.fromHtml("<b>Uploaded at <a href='" + s + "'>" + s + "</a></b>"));
                //textViewResponse.setMovementMethod(LinkMovementMethod.getInstance());
            }

            @Override
            protected String doInBackground(Void... params) {
                Upload u = new Upload();
                String msg = u.uploadVideo(selectedPath);
                return msg;
            }
        }
        UploadVideo uv = new UploadVideo();
        uv.execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activityContext = context;
    }

    public class GetPrayerDetails extends AsyncTask<Void, Void, Void> {

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
                response = networkCalls.sendGet(APIUtils.BASE_URL + "get_prayer.php");
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
                    PrayerResponseBean prayerResponseBean = new Gson().fromJson(responseObject.toString(), PrayerResponseBean.class);

                    prayerResponseList = prayerResponseBean.getPrayerResponseList();
                    sortListAccordingToID(prayerResponseList);

                    if (prayerResponseList.size() == 0) {
                        txt_msg.setVisibility(View.VISIBLE);
                        txt_msg.setText("No Prayers Available....");
                    } else {
                        // prayerAdapter = new PrayerAdapter(getActivity(), prayerResponseList);
                        prayerAdapter = new PrayerListViewAdapter(getActivity(), prayerResponseList);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        //prayer_recyclerView.setLayoutManager(layoutManager);
                        prayer_listView.setAdapter(prayerAdapter);
                    }


                } else if (responseObject.get("MESSAGE").toString().trim().equalsIgnoreCase("FAILURE")) {
                    Toast.makeText(getContext(), "No Prayers", Toast.LENGTH_LONG).show();
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

    private void sortListAccordingToID(final List<PrayerResponseBean.PrayerResponse> prayerResponseList) {

        Collections.sort(prayerResponseList);
    }

    public class AddPrayer extends AsyncTask<Void, Void, Void> {

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
                JSONObject paramObject = new JSONObject();

                paramObject.put("prayer_video", "XYZ");
                paramObject.put("description", _edt_prayer.getText().toString().trim());
                paramObject.put("prayer_photo", "XYZ");
                response = networkCalls.sendJson(APIUtils.BASE_URL + "add_prayer.php", paramObject);
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
                    Toast.makeText(getContext(), "Prayer Added Successfully", Toast.LENGTH_LONG).show();
                } else if (responseObject.get("MESSAGE").toString().trim().equalsIgnoreCase("FAILURE")) {
                    Toast.makeText(getContext(), "Something Wrong", Toast.LENGTH_LONG).show();
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