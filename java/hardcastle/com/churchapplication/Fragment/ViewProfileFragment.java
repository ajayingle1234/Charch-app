package hardcastle.com.churchapplication.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hardcastle.com.churchapplication.LoginActivity;
import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.model.LoginResponceBean;

public class ViewProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SharedPreferences sharedPreferences;
    private Unbinder unbinder;

    @BindView(R.id.txt_user_firstname)
    TextView _userFirstName;
    @BindView(R.id.txt_user_lastname)
    TextView _userLastName;
    @BindView(R.id.txt_mobile)
    TextView _userMobileNo;
    @BindView(R.id.txt_email)
    TextView _userEmail;
    @BindView(R.id.txt_perm_address)
    TextView _userPermanantAddress;
    private Toolbar toolbar;

    @BindView(R.id.layout_not_signIn)
    LinearLayout layout_not_signIn;
    @BindView(R.id.btnLogIn)
    Button btn_not_signIn;
    @BindView(R.id.scroll_view_profile)
    ScrollView scroll_view_profile;

    public ViewProfileFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ViewProfileFragment newInstance(String param1, String param2) {
        ViewProfileFragment fragment = new ViewProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_view_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        getActivity().setTitle("Profile");
        getActivity().setActionBar(toolbar);

        sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        if (sharedPreferences.getBoolean(getResources().getString(R.string.preference_is_login), false)) {
            layout_not_signIn.setVisibility(View.GONE);
            scroll_view_profile.setVisibility(View.VISIBLE);
            setUserProfile();
        } else {
            scroll_view_profile.setVisibility(View.GONE);
            layout_not_signIn.setVisibility(View.VISIBLE);
        }
        return view;
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

            _userFirstName.setText(loginResponse.getFirstname());
            _userLastName.setText(loginResponse.getLastname());
            _userEmail.setText(loginResponse.getEmailId());
            _userMobileNo.setText(loginResponse.getMobileNo());
            _userPermanantAddress.setText(loginResponse.getAddress());
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @OnClick(R.id.btnLogIn)
    public void login() {
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(loginIntent);
        getActivity().finish();
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
