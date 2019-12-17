package hardcastle.com.churchapplication.Fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import hardcastle.com.churchapplication.DashBoardActivity;
import hardcastle.com.churchapplication.LoginActivity;
import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.Utils.APIUtils;
import hardcastle.com.churchapplication.Utils.NetworkCalls;
import hardcastle.com.churchapplication.model.LoginResponceBean;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Unbinder unbinder;
    @BindView(R.id.btnChangePassword)
    Button btnChangePassword;
    @BindView(R.id.changePasswordLayout)
    LinearLayout changePasswordLayout;
    @BindView(R.id.layout_not_signIn)
    LinearLayout layout_not_signIn;
    @BindView(R.id.btnLogIn)
    Button btn_not_signIn;
    @BindView(R.id.input_current_password)
    EditText input_current_password;
    @BindView(R.id.input_password)
    EditText input_password;
    @BindView(R.id.input_confirmpassword)
    EditText input_confirmpassword;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;
    private Integer user_id;
    private View view;


    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    public static ChangePasswordFragment newInstance() {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
        view = inflater.inflate(R.layout.fragment_change_password, container, false);

        unbinder = ButterKnife.bind(this, view);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        getActivity().setTitle("Change Password");
        getActivity().setActionBar(toolbar);
        sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(getResources().getString(R.string.preference_is_login), false)) {

            String login_Type = sharedPreferences.getString(getResources().getString(R.string.preference_login_type), "");
            if (login_Type.equals("R")) {
                Gson gson = new Gson();
                String userDetails = sharedPreferences.getString(getResources().getString(R.string.preference_login_user_info), "");

                LoginResponceBean.LoginResponse loginResponse = gson.fromJson(userDetails, LoginResponceBean.LoginResponse.class);
                user_id = loginResponse.getUserId();

            }
        }
        else {
            changePasswordLayout.setVisibility(View.GONE);
            layout_not_signIn.setVisibility(View.VISIBLE);
        }
        return view;
    }

    public boolean validate() {
        try {
            if (input_current_password.getText().toString().trim().length() == 0) {
                Snackbar.make(view, getResources().getString(R.string.current_password_msg), Snackbar.LENGTH_LONG).show();
                return false;
            } else if (input_password.getText().toString().trim().length() == 0) {
                Snackbar.make(view, getResources().getString(R.string.new_password_msg), Snackbar.LENGTH_LONG).show();
                return false;
            } else if (input_confirmpassword.getText().toString().trim().length() == 0) {
                Snackbar.make(view, getResources().getString(R.string.confirm_password_msg), Snackbar.LENGTH_LONG).show();
                return false;
            } else if (!input_confirmpassword.getText().toString().equals(input_password.getText().toString())) {
                Snackbar.make(view, "Confirm Password Not Match", Snackbar.LENGTH_LONG).show();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    @OnClick(R.id.btnChangePassword)
    public void changePassword() {
        if (validate())
            new UpdatePassword().execute();

    }

    public class UpdatePassword extends AsyncTask<Void, Void, Void> {
        String response;
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getResources().getString(R.string.please_wait));
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... voids) {
            NetworkCalls networkCalls = new NetworkCalls();
            JSONObject paramObject = new JSONObject();

            try {

                paramObject.put("user_id", String.valueOf(user_id));
                paramObject.put("current_pwd", input_current_password.getText().toString().trim());
                paramObject.put("new_pwd", input_password.getText().toString().trim());

                response = networkCalls.sendJson(APIUtils.BASE_URL + "change_pwd.php", paramObject);
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
                JSONObject jsonObject = responseObject.getJSONObject("DATA");
                if (responseObject.get("MESSAGE").toString().trim().equalsIgnoreCase("SUCCESS")) {

                    Snackbar.make(view, "Password Updated Succesfully", Snackbar.LENGTH_LONG).show();

                } else if (responseObject.get("MESSAGE").toString().trim().equalsIgnoreCase("FAILURE")) {
                    Snackbar.make(view, "Password does not match. Kindly try again", Snackbar.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.btnLogIn)
    public void login() {
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(loginIntent);
        getActivity().finish();
    }
}
