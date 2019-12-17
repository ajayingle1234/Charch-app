package hardcastle.com.churchapplication;

import android.annotation.SuppressLint;
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
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import hardcastle.com.churchapplication.Utils.APIUtils;
import hardcastle.com.churchapplication.Utils.GetDataService;
import hardcastle.com.churchapplication.Utils.MyApplication;
import hardcastle.com.churchapplication.Utils.NetworkCalls;
import hardcastle.com.churchapplication.model.LoginResponceBean;
import hardcastle.com.churchapplication.model.ResponseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegistrationActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 0;
    static final int CHOOSE_FROM_GALLERY = 1;
    private static final String TAG = "UserRegistration";

    @BindView(R.id.layout_user_registration)
    RelativeLayout layoutUserRegistration;

    @BindView(R.id.input_user_firstname)
    EditText _userFirstName;
    @BindView(R.id.input_user_lastname)
    EditText _userLastName;
    @BindView(R.id.input_mobile)
    EditText _userMobileNo;
    @BindView(R.id.country_code)
    EditText _user_country_code;
    @BindView(R.id.input_email)
    EditText _userEmail;
    @BindView(R.id.input_perm_address)
    EditText _userPermanantAddress;
    @BindView(R.id.input_password)
    EditText _userPassword;
    @BindView(R.id.input_confirm_password)
    EditText _userConfirmPassword;

    @BindView(R.id.input_state)
    TextView _userState;
    @BindView(R.id.input_city)
    TextView _userCity;
    @BindView(R.id.input_country)
    TextView _userCountry;
    @BindView(R.id.chk_terms)
    CheckBox chk_terms;

    @BindView(R.id.input_zipcode)
    EditText _userZipCode;

    @BindView(R.id.btnUserRegister)
    Button _userRegisterButton;
    @BindView(R.id.profile_img)
    CircleImageView _circleImageProfile;
    private MyApplication myApplication;
    private GetDataService mApiInterface;
    private View parentLayout;
    private String image = "";
    private boolean checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        ButterKnife.bind(this);
        parentLayout = findViewById(R.id.layout_user_registration);
        mApiInterface = APIUtils.getAPIInterface();
        myApplication = MyApplication.getInstance();
    }

    @OnClick(R.id.profile_img)
    public void takePicture() {
        selectImage(this);
    }

    @OnCheckedChanged(R.id.chk_terms)
    public void checkTerms() {
        checked = chk_terms.isChecked();
        if (!checked) {
            Toast.makeText(this, "Please Agree Terms & Conditions", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btnUserRegister)
    public void btn_registerUser() {
        if (checkValidate()) {

            if (isValidEmail(_userEmail.getText().toString().trim())) {
                // do whatever you want to do if email is valid i.e in form of something@somedomain.someextension
                //registerUser();
                if (!checked) {
                    Toast.makeText(this, "Please Agree Terms & Conditions", Toast.LENGTH_SHORT).show();
                } else {
                    new RegisterUserTask().execute();
                }
            } else {
                Toast.makeText(UserRegistrationActivity.this, getResources().getString(R.string.verify_email_msg), Toast.LENGTH_SHORT).show();
            }
        }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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


    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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


    private void initializeControls() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "img.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);


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


    public class RegisterUserTask extends AsyncTask<Void, Void, Void> {

        String response;
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UserRegistrationActivity.this);
            pDialog.setMessage(getResources().getString(R.string.send_user_info_msg));
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... voids) {

            NetworkCalls networkCalls = new NetworkCalls();
            JSONObject paramObject = new JSONObject();

            //Log.e(TAG, "doLogin: DEVICE TOKEN : " + sharedPreferences.getString(getResources().getString(R.string.preference_device_token), ""));
            try {
                paramObject.put("TYPE", "R");
                paramObject.put("firstname", _userFirstName.getText().toString().trim());
                paramObject.put("lastname", _userLastName.getText().toString().trim());
                paramObject.put("email_id", _userEmail.getText().toString().trim());
                paramObject.put("mobile_no","+"+_user_country_code.getText().toString().trim()+" "+ _userMobileNo.getText().toString().trim());
                paramObject.put("password", _userPassword.getText().toString().trim());
                paramObject.put("device_id", "");
                paramObject.put("country", _userCountry.getText().toString().trim());
                paramObject.put("state", _userState.getText().toString().trim());
                paramObject.put("city", _userCity.getText().toString().trim());
                paramObject.put("address", _userPermanantAddress.getText().toString().trim());
                paramObject.put("zipcode", _userZipCode.getText().toString().trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            response = networkCalls.sendJson(APIUtils.BASE_URL + "register.php", paramObject);
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
                    Toast.makeText(UserRegistrationActivity.this, "Registration Succesfully", Toast.LENGTH_LONG).show();
                    clearForm((ViewGroup) layoutUserRegistration);

                } else if (responseObject.get("MESSAGE").toString().trim().equalsIgnoreCase("FAILURE")) {
                    Toast.makeText(UserRegistrationActivity.this, "UserName and password do not match. Kindly try again", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }

            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

                        Cursor cursor = getContentResolver().query(selectedImage,
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

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean checkValidate() {

        if (_userFirstName.getText().toString().isEmpty()) {
            Toast.makeText(UserRegistrationActivity.this, getResources().getString(R.string.enter_name_msg), Toast.LENGTH_SHORT).show();
            //TSnackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.enter_name_msg), TSnackbar.LENGTH_LONG).show();
            return false;
        }
        if (_userLastName.getText().toString().isEmpty()) {
            Toast.makeText(UserRegistrationActivity.this, getResources().getString(R.string.enter_last_name_msg), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (_user_country_code.getText().toString().isEmpty()) {

            Toast.makeText(UserRegistrationActivity.this, getResources().getString(R.string.enter_country_code), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (_userMobileNo.getText().toString().isEmpty()) {

            Toast.makeText(UserRegistrationActivity.this, getResources().getString(R.string.enter_mobile_msg), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_userEmail.getText().toString().isEmpty()) {

            Toast.makeText(UserRegistrationActivity.this, getResources().getString(R.string.enter_email_msg), Toast.LENGTH_SHORT).show();
            return false;
        }else if (!isValidEmail(_userEmail.getText().toString().trim())) {
            Toast.makeText(UserRegistrationActivity.this, getResources().getString(R.string.verify_email_msg), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_userPermanantAddress.getText().toString().isEmpty()) {

            Toast.makeText(UserRegistrationActivity.this, getResources().getString(R.string.enter_address_msg), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_userCity.getText().toString().isEmpty()) {

            Toast.makeText(UserRegistrationActivity.this, getResources().getString(R.string.enter_city_msg), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_userState.getText().toString().isEmpty()) {

            Toast.makeText(UserRegistrationActivity.this, getResources().getString(R.string.enter_state_msg), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_userCountry.getText().toString().isEmpty()) {

            Toast.makeText(UserRegistrationActivity.this, getResources().getString(R.string.enter_contry_msg), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_userZipCode.getText().toString().isEmpty()) {
            Toast.makeText(UserRegistrationActivity.this, getResources().getString(R.string.enter_zip_code_msg), Toast.LENGTH_SHORT).show();
            return false;
        }else if (_userZipCode.getText().toString().trim().length()!=5){
            Toast.makeText(UserRegistrationActivity.this, getResources().getString(R.string.enter_valid_zip_code_msg), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (_userPassword.getText().toString().isEmpty()) {

            Toast.makeText(UserRegistrationActivity.this, getResources().getString(R.string.enter_password_msg), Toast.LENGTH_SHORT).show();
            return false;
        }else if (_userPassword.getText().toString().length() < 6) {

            Toast.makeText(UserRegistrationActivity.this, getResources().getString(R.string.password_chk_msg), Toast.LENGTH_SHORT).show();
            return false;
        }




        if (!_userPassword.getText().toString().equalsIgnoreCase(_userConfirmPassword.getText().toString())) {

            Toast.makeText(UserRegistrationActivity.this, getResources().getString(R.string.password_match_msg), Toast.LENGTH_SHORT).show();
            return false;
        }

      /*  if (txt_hint.getText().toString().equalsIgnoreCase("VERIFY")) {
            txt_hint.setTextColor(Color.RED);
            txt_hint.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake));
            Toast.makeText(UserRegistrationActivity.this, getResources().getString(R.string.verify_number_msg), Toast.LENGTH_SHORT).show();
            return false;

        }*/
        return true;
    }
}
