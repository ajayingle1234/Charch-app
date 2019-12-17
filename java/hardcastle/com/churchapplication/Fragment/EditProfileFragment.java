package hardcastle.com.churchapplication.Fragment;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import hardcastle.com.churchapplication.LoginActivity;
import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.UserRegistrationActivity;
import hardcastle.com.churchapplication.Utils.APIUtils;
import hardcastle.com.churchapplication.Utils.NetworkCalls;
import hardcastle.com.churchapplication.model.LoginResponceBean;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static final int REQUEST_IMAGE_CAPTURE = 2;
    static final int CHOOSE_FROM_GALLERY = 1;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.input_user_firstname)
    EditText _userFirstName;
    @BindView(R.id.input_user_lastname)
    EditText _userLastName;
    @BindView(R.id.input_mobile)
    EditText _userMobileNo;

    @BindView(R.id.country_code)
    EditText _countryCode;
    @BindView(R.id.input_email)
    EditText _userEmail;
    @BindView(R.id.input_perm_address)
    EditText _userPermanantAddress;

    @BindView(R.id.input_state)
    TextView _userState;
    @BindView(R.id.input_city)
    TextView _userCity;
    @BindView(R.id.input_country)
    TextView _userCountry;
    @BindView(R.id.input_zipcode)
    EditText _userZipCode;

    @BindView(R.id.layout_not_signIn)
    LinearLayout layout_not_signIn;
    @BindView(R.id.btnLogIn)
    Button btn_not_signIn;
    @BindView(R.id.scroll_edit_profile)
    ScrollView scroll_edit_profile;

    @BindView(R.id.profile_img)
    CircleImageView _circleImageProfile;

    @BindView(R.id.btnSubmit)
    Button _userSubmit;
    private SharedPreferences sharedPreferences;

    private Unbinder unbinder;
    private Toolbar toolbar;
    private Integer user_id;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        getActivity().setTitle("Edit Profile");
        getActivity().setActionBar(toolbar);

        sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        if (sharedPreferences.getBoolean(getResources().getString(R.string.preference_is_login), false)) {
            layout_not_signIn.setVisibility(View.GONE);
            scroll_edit_profile.setVisibility(View.VISIBLE);
            setUserProfile();
        } else {
            scroll_edit_profile.setVisibility(View.GONE);
            layout_not_signIn.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @OnClick(R.id.profile_img)
    public void takePicture() {
        selectImage(getActivity());
    }

    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);

                } else if (options[item].equals("Choose from Gallery")) {
                    /*Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, CHOOSE_FROM_GALLERY);*/
                    initializeControlsGallery();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        _circleImageProfile.setImageBitmap(selectedImage);
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

                        Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();

                        _circleImageProfile.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    }
                    break;
            }
        }
    }

    private void initializeControlsGallery() {

// TODO Auto-generated method stub
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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


    private void setUserProfile() {
        String login_Type = sharedPreferences.getString(getResources().getString(R.string.preference_login_type), "");
        if (login_Type.equals("G")) {
            _userFirstName.setText(mParam1);
            _userLastName.setText(mParam2);
        } else if (login_Type.equals("R")) {
            Gson gson = new Gson();
            String userDetails = sharedPreferences.getString(getResources().getString(R.string.preference_login_user_info), "");

            LoginResponceBean.LoginResponse loginResponse = gson.fromJson(userDetails, LoginResponceBean.LoginResponse.class);
            user_id = loginResponse.getUserId();
            _userFirstName.setText(loginResponse.getFirstname());
            _userLastName.setText(loginResponse.getLastname());
            _userEmail.setText(loginResponse.getEmailId());

            String mobileString = loginResponse.getMobileNo();

           if(mobileString.contains(" ")) {
               String[] numbers = mobileString.split("\\s");

               _userMobileNo.setText(numbers[1]);
               _countryCode.setText(numbers[0].replace("+", ""));
           }else {
               _userMobileNo.setText(mobileString);
           }
            _userPermanantAddress.setText(loginResponse.getAddress());
            _userCity.setText(loginResponse.getCity());
            _userState.setText(loginResponse.getState());
            _userCountry.setText(loginResponse.getCountry());
            _userZipCode.setText(loginResponse.getZipcode());
        }
    }


    @OnClick(R.id.btnSubmit)
    public void updateUser() {
        if (checkValidate()) {

            if (isValidEmail(_userEmail.getText().toString().trim())) {
                // do whatever you want to do if email is valid i.e in form of something@somedomain.someextension
                //registerUser();
                new UpdateUserTask().execute();
            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.verify_email_msg), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean checkValidate() {

        if (_userFirstName.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), getResources().getString(R.string.enter_name_msg), Toast.LENGTH_SHORT).show();
            //TSnackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.enter_name_msg), TSnackbar.LENGTH_LONG).show();
            return false;
        }
        if (_userLastName.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), getResources().getString(R.string.enter_last_name_msg), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_countryCode.getText().toString().isEmpty()) {

            Toast.makeText(getContext(), getResources().getString(R.string.enter_country_code), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (_userMobileNo.getText().toString().isEmpty()) {

            Toast.makeText(getContext(), getResources().getString(R.string.enter_mobile_msg), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_userEmail.getText().toString().isEmpty()) {

            Toast.makeText(getContext(), getResources().getString(R.string.enter_email_msg), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isValidEmail(_userEmail.getText().toString().trim())) {
            Toast.makeText(getContext(), getResources().getString(R.string.verify_email_msg), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_userPermanantAddress.getText().toString().isEmpty()) {

            Toast.makeText(getContext(), getResources().getString(R.string.enter_address_msg), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_userCity.getText().toString().isEmpty()) {

            Toast.makeText(getContext(), getResources().getString(R.string.enter_city_msg), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_userState.getText().toString().isEmpty()) {

            Toast.makeText(getContext(), getResources().getString(R.string.enter_state_msg), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_userCountry.getText().toString().isEmpty()) {

            Toast.makeText(getContext(), getResources().getString(R.string.enter_contry_msg), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_userZipCode.getText().toString().isEmpty()) {

            Toast.makeText(getContext(), getResources().getString(R.string.enter_zip_code_msg), Toast.LENGTH_SHORT).show();
            return false;
        }







      /*  if (txt_hint.getText().toString().equalsIgnoreCase("VERIFY")) {
            txt_hint.setTextColor(Color.RED);
            txt_hint.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake));
            Toast.makeText(getContext(), getResources().getString(R.string.verify_number_msg), Toast.LENGTH_SHORT).show();
            return false;

        }*/
        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @OnClick(R.id.btnLogIn)
    public void login() {
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(loginIntent);
        getActivity().finish();
    }

    @OnClick(R.id.input_city)
    public void selectCity() {

        final String[] cityList = {"City of London"
                , "Oxfordshire"
                , "Somerset"
                , "Hampshire"
                , "Kent"
                , "Derbyshire"};
        customAlert(cityList, _userCity);
    }

    @OnClick(R.id.input_state)
    public void selectState() {

        final String[] stateList = {
                "Bristol"
                , "Buckinghamshire"
                , "Cambridgeshire"
                , "Cambridgeshire"
                , "Cambridgeshire"
                , "Cheshire"
                , "Cornwall"
                , "Cumberland"
                , "Gloucestershire"
                , "Derbyshire"};
        customAlert(stateList, _userState);

    }

    @OnClick(R.id.input_country)
    public void selectContry() {

        final String[] contryList = {
                "US"
                , "UK"
                , "Cambodia"
                , "Brazil"
                , "Canada"
                , "Colombia"
                , "Cameroon"
                , "Chile"
                , "Denmark"};

        customAlert(contryList, _userCountry);

    }

    public void customAlert(String[] stringList, final TextView textView) {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select");

// add a list
        final String[] list = stringList;
        builder.setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                textView.setText(list[which]);
               /* switch (which) {
                    case 0:
                        txtType.setText(animals[which]);
                        break;
                    case 1:
                        //txtType.setText(animals[which]);
                        break;

                }*/
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public class UpdateUserTask extends AsyncTask<Void, Void, Void> {

        String response;
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getResources().getString(R.string.send_user_info_msg));
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            NetworkCalls networkCalls = new NetworkCalls();
            JSONObject paramObject = new JSONObject();

            //Log.e(TAG, "doLogin: DEVICE TOKEN : " + sharedPreferences.getString(getResources().getString(R.string.preference_device_token), ""));
            try {

                paramObject.put("user_id", user_id);
                paramObject.put("TYPE", "R");
                paramObject.put("firstname", _userFirstName.getText().toString().trim());
                paramObject.put("lastname", _userLastName.getText().toString().trim());
                paramObject.put("email_id", _userEmail.getText().toString().trim());
                paramObject.put("mobile_no", "+" + _countryCode.getText().toString().trim() + " " + _userMobileNo.getText().toString().trim());
                paramObject.put("device_id", "");
                paramObject.put("country", _userCountry.getText().toString().trim());
                paramObject.put("state", _userState.getText().toString().trim());
                paramObject.put("city", _userCity.getText().toString().trim());
                paramObject.put("address", _userPermanantAddress.getText().toString().trim());
                paramObject.put("zipcode", _userZipCode.getText().toString().trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            response = networkCalls.sendJson(APIUtils.BASE_URL + "update_info.php", paramObject);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }

            Log.e("Response", response);

            try {

                JSONObject responseObject = new JSONObject(response);

                JSONObject jsonArray = responseObject.getJSONObject("DATA");

                if (responseObject.get("MESSAGE").toString().trim().equalsIgnoreCase("SUCCESS")) {
                    Toast.makeText(getActivity(), "User Updated Succesfully", Toast.LENGTH_LONG).show();

                    Gson gson = new Gson();
                    LoginResponceBean.LoginResponse loginResponse = new Gson().fromJson(jsonArray.toString(), LoginResponceBean.LoginResponse.class);

                    String userDetail = gson.toJson(loginResponse);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(getResources().getString(R.string.preference_login_user_info), userDetail);
                    editor.putString(getResources().getString(R.string.preference_login_type), "R");
                    editor.putBoolean(getResources().getString(R.string.preference_is_login), true);
                    editor.apply();
                    editor.commit();
                    setUserProfile();
                } else if (responseObject.get("MESSAGE").toString().trim().equalsIgnoreCase("FAILURE")) {
                    Toast.makeText(getActivity(), "UserName and password do not match. Kindly try again", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
}
