package hardcastle.com.churchapplication;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import hardcastle.com.churchapplication.Utils.APIUtils;
import hardcastle.com.churchapplication.Utils.GetDataService;
import hardcastle.com.churchapplication.Utils.MyApplication;
import hardcastle.com.churchapplication.Utils.NetworkCalls;
import hardcastle.com.churchapplication.model.LoginResponceBean;


public class LoginActivity extends AppCompatActivity {

    private static final int RC_GSIGN_IN = 1;
    private static final String EMAIL = "email";
    private static final int PERMISSIONS_REQUEST = 1234;
    @BindView(R.id.input_user_firstname)
    EditText _userFirstName;
    @BindView(R.id.input_password)
    EditText _userpassword;
    @BindView(R.id.txtForgetPassword)
    TextView _forgotPassword;
    @BindView(R.id.txtLoginWith)
    TextView _loginWith;
    @BindView(R.id.btnLogIn)
    Button _loginButton;
    @BindView(R.id.btnCreateAccount)
    Button _createAccountButton;
    @BindView(R.id.skip_login)
    Button _skipLoginButton;
    @BindView(R.id.img_gmail)
    ImageView img_GmailLogin;
    @BindView(R.id.img_facebook)
    ImageView img_FBLogin;
    @BindView(R.id.login_buttonfb)
    LoginButton btn_FBLogin;
    //G+ Login
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private String TAG = "LoginActivity";
    private MyApplication myApplication;
    private GetDataService mApiInterface;
    //FB Login
    private CallbackManager callbackManager;
    private SharedPreferences sharedPreferences;

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions();
        }

        mApiInterface = APIUtils.getAPIInterface();
        myApplication = MyApplication.getInstance();
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        loginwithFBConfig();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermissions() {
        String[] ungrantedPermissions = requiredPermissionsStillNeeded();
        if (ungrantedPermissions.length == 0) {
            // Toast.makeText(DashboardActivity.this, "Permissions already granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissions(ungrantedPermissions, PERMISSIONS_REQUEST);
        }
    }

    public String[] getRequiredPermissions() {
        String[] permissions = null;
        try {
            permissions = getPackageManager().getPackageInfo(getPackageName(),
                    PackageManager.GET_PERMISSIONS).requestedPermissions;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (permissions == null) {
            return new String[0];
        } else {
            return permissions.clone();
        }
    }

    @TargetApi(23)
    private String[] requiredPermissionsStillNeeded() {

        Set<String> permissions = new HashSet<String>();
        for (String permission : getRequiredPermissions()) {
            permissions.add(permission);
        }
        for (Iterator<String> i = permissions.iterator(); i.hasNext(); ) {
            String permission = i.next();
            if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                Log.d(LoginActivity.class.getSimpleName(),
                        "Permission: " + permission + " already granted.");
                i.remove();
            } else {
                Log.d(LoginActivity.class.getSimpleName(),
                        "Permission: " + permission + " not yet granted.");
            }
        }
        return permissions.toArray(new String[permissions.size()]);
    }

    private void loginwithFBConfig() {

        //FB Login
        callbackManager = CallbackManager.Factory.create();

        List<String> permissionNeeds = Arrays.asList("email",
                /*"user_birthday",*/ "public_profile");
        btn_FBLogin.setReadPermissions(permissionNeeds);
        btn_FBLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                if (accessToken != null && !accessToken.isExpired()) {
                    Set<String> profile = loginResult.getRecentlyGrantedPermissions();

                    //String p = profile.toString();

                    //================================
                    // Log.i("accessToken", accessToken);

                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object,
                                                        GraphResponse response) {

                                    Log.i("LoginActivity",
                                            response.toString());
                                    try {
                                        String id = object.getString("id");
                                        try {
                                            URL profile_pic = new URL(
                                                    "http://graph.facebook.com/" + id + "/picture?type=large");
                                            Log.i("profile_pic",
                                                    profile_pic + "");

                                        } catch (MalformedURLException e) {
                                            e.printStackTrace();
                                        }
                                      /*  String name = object.getString("name");
                                        String email = object.getString("email");
                                        String gender = object.getString("gender");
                                        String birthday = object.getString("birthday");*/
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields",
                            "id,name,email,gender, birthday");


                    //=================================


                    Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(LoginActivity.this, DashBoardActivity.class);
                    startActivity(loginIntent);
                }
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(LoginActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

   /* @OnClick(R.id.btnLogIn)
    public void login() {

        Intent loginIntent = new Intent(LoginActivity.this, DashBoardActivity.class);
        startActivity(loginIntent);
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account == null) {
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        } else
            updateUI(account);
    }

    @OnClick(R.id.skip_login)
    public void skipLogin() {
        Intent loginIntent = new Intent(LoginActivity.this, DashBoardActivity.class);
        startActivity(loginIntent);
        finish();
        //overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

    }

    @OnClick(R.id.txtForgetPassword)
    public void forgotPassword() {
        Intent loginIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(loginIntent);

    }

    @OnClick(R.id.btnCreateAccount)
    public void createAccount() {
        Intent loginIntent = new Intent(LoginActivity.this, UserRegistrationActivity.class);
        startActivity(loginIntent);
    }

    @OnClick(R.id.img_gmail)
    public void loginGmail() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GSIGN_IN);
    }

    @OnClick(R.id.img_facebook)
    public void loginFB() {
        btn_FBLogin.performClick();
    }

    @OnTextChanged(value = R.id.input_user_firstname, callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    void beforeTextChangedPhoneNo(CharSequence s, int start, int count, int after) {

    }

    @OnTextChanged(value = R.id.input_password, callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    void beforeTextChangedPassword(CharSequence s, int start, int count, int after) {

    }

    @OnTextChanged(value = R.id.input_user_firstname, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChangedPhoneNo(CharSequence s, int start, int before, int count) {
        try {
            if (_userFirstName.getText().length() == 10 || isValidEmail(_userFirstName.getText().toString())) {
                // _loginButton.setBackgroundResource(R.drawable.blue_button);
                //_loginButton.setEnabled(true);
            } else if (_userFirstName.getText().length() < 10) {
                //_loginButton.setBackgroundResource(R.drawable.blue_button_light);
            } else {
            }//_loginButton.setBackgroundResource(R.drawable.blue_button_light);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnTextChanged(value = R.id.input_password, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChangedPassword(CharSequence s, int start, int before, int count) {

        try {
            if (_userpassword.getText().toString().trim().length() > 0) {
                // _loginButton.setBackgroundResource(R.drawable.blue_button);
                //_loginButton.setEnabled(true);
            } else if (TextUtils.isEmpty(_userpassword.getText().toString().trim())) {
                // _loginButton.setBackgroundResource(R.drawable.blue_button_light);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnTextChanged(value = R.id.input_user_firstname, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChangedPhoneNo(Editable editable) {

    }

    @OnTextChanged(value = R.id.input_password, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChangedPassword(Editable editable) {
    }

    @OnClick(R.id.btnLogIn)
    void checkLogin() {

        try {
           /* if (!isValidEmail(_userFirstName.getText().toString())) {
                Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();
            } else*/
            if (_userFirstName.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter UserName", Toast.LENGTH_SHORT).show();
            } else if (_userpassword.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter the password", Toast.LENGTH_SHORT).show();
            } else {
                //doLogin();
                new UserLoginTask().execute();
              /*  Intent intentDashboard = new Intent(LoginActivity.this, DashBoardActivity.class);
                startActivity(intentDashboard);
                finish();*/
            }

            /*} else {
                MyApplication.showMessage(this, getResources().getString(R.string.check_internet_connection));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_GSIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount user) {
        if (user == null) {
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(getResources().getString(R.string.preference_is_login), true);
            editor.putString(getResources().getString(R.string.preference_login_type), "G");
            editor.apply();
            Intent loginIntent = new Intent(LoginActivity.this, DashBoardActivity.class);
            startActivity(loginIntent);
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Void> {
        String response;
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage(getResources().getString(R.string.send_user_login_msg));
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
                paramObject.put("username", _userFirstName.getText().toString().trim());
                paramObject.put("password", _userpassword.getText().toString().trim());
                response = networkCalls.sendJson(APIUtils.BASE_URL + "login_user.php", paramObject);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
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

                    Gson gson = new Gson();
                    LoginResponceBean.LoginResponse loginResponse = new Gson().fromJson(jsonArray.toString(), LoginResponceBean.LoginResponse.class);

                    String userDetail = gson.toJson(loginResponse);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(getResources().getString(R.string.preference_login_user_info), userDetail);
                    editor.putString(getResources().getString(R.string.preference_login_type), "R");
                    editor.putBoolean(getResources().getString(R.string.preference_is_login), true);
                    editor.apply();
                    editor.commit();

                    Intent intentDashboard = new Intent(LoginActivity.this, DashBoardActivity.class);
                    startActivity(intentDashboard);
                    finish();

                } else if (responseObject.get("MESSAGE").toString().trim().equalsIgnoreCase("FAILURE")) {
                    Toast.makeText(LoginActivity.this, "UserName and password do not match. Kindly try again", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
