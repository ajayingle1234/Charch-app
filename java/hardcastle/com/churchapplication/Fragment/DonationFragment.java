package hardcastle.com.churchapplication.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hardcastle.com.churchapplication.Adapter.NotificationAdapter;
import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.Utils.APIUtils;
import hardcastle.com.churchapplication.Utils.CommonUtils;
import hardcastle.com.churchapplication.Utils.NetworkCalls;
import hardcastle.com.churchapplication.model.GivingTypeBean;
import hardcastle.com.churchapplication.model.NotificationResponseBean;

public class DonationFragment extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.btnNext)
    Button buttonNext;
    @BindView(R.id.type)
    TextView txtType;
    @BindView(R.id.input_amount)
    EditText input_amount;
    @BindView(R.id.lable_date)
    TextView lable_date;
    @BindView(R.id.btn_oneTime)
    Button btn_oneTime;
    @BindView(R.id.btn_reusing)
    Button btn_reusing;

    @BindView(R.id.btn_every_week)
    Button btn_every_week;
    @BindView(R.id.btn_half_month)
    Button btn_half_month;
    @BindView(R.id.btn_month)
    Button btn_month;
    @BindView(R.id.btn_year)
    Button btn_year;


    @BindView(R.id.layout_frequency)
    LinearLayout layout_frequency;
    private Toolbar toolbar;
    private Calendar myCalendar;
    private Context context;
    private Calendar cal;
    private List<GivingTypeBean.TypeOptions> typeOptionsList;

    public DonationFragment() {
        // Required empty public constructor
    }

    public static DonationFragment newInstance() {
        DonationFragment fragment = new DonationFragment();
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
        View view = inflater.inflate(R.layout.fragment_donation, container, false);
        unbinder = ButterKnife.bind(this, view);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        getActivity().setTitle("Giving");
        getActivity().setActionBar(toolbar);
        cal = Calendar.getInstance();
        lable_date.setText(CommonUtils.formatDateForDisplay(cal.getTime(), "dd-MMM-yyyy"));

        try {
            new GetGivingType().execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @OnClick(R.id.btnNext)
    public void payDonation() {
        getFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new PaymentMethodFragment()).addToBackStack("PayMethodFragment")
                .commit();
    }

    @OnClick(R.id.type)
    public void selectType() {
        if (typeOptionsList.size() == 0) {
        } else {
            customAlert(typeOptionsList);
        }
    }

    @OnClick(R.id.btn_oneTime)
    public void selectOneTime() {
        btn_oneTime.setBackgroundColor(getResources().getColor(R.color.colorPrimaryButton));
        btn_oneTime.setTextColor(getResources().getColor(R.color.white));

        btn_reusing.setBackground(getResources().getDrawable(R.drawable.white_button));
        btn_reusing.setTextColor(getResources().getColor(R.color.colorPrimaryButton));

        layout_frequency.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_reusing)
    public void selectReusing() {

        btn_reusing.setBackgroundColor(getResources().getColor(R.color.colorPrimaryButton));
        btn_reusing.setTextColor(getResources().getColor(R.color.white));

        btn_oneTime.setBackground(getResources().getDrawable(R.drawable.white_button));
        btn_oneTime.setTextColor(getResources().getColor(R.color.colorPrimaryButton));
        layout_frequency.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_every_week)
    public void btnEveryWeek() {

        btn_every_week.setBackgroundColor(getResources().getColor(R.color.colorPrimaryButton));
        btn_every_week.setTextColor(getResources().getColor(R.color.white));

        btn_half_month.setBackground(getResources().getDrawable(R.drawable.white_button));
        btn_half_month.setTextColor(getResources().getColor(R.color.colorPrimaryButton));

        btn_month.setBackground(getResources().getDrawable(R.drawable.white_button));
        btn_month.setTextColor(getResources().getColor(R.color.colorPrimaryButton));

        btn_year.setBackground(getResources().getDrawable(R.drawable.white_button));
        btn_year.setTextColor(getResources().getColor(R.color.colorPrimaryButton));
    }

    @OnClick(R.id.btn_half_month)
    public void btnHalfyWeek() {
        btn_half_month.setBackgroundColor(getResources().getColor(R.color.colorPrimaryButton));
        btn_half_month.setTextColor(getResources().getColor(R.color.white));

        btn_every_week.setBackground(getResources().getDrawable(R.drawable.white_button));
        btn_every_week.setTextColor(getResources().getColor(R.color.colorPrimaryButton));

        btn_month.setBackground(getResources().getDrawable(R.drawable.white_button));
        btn_month.setTextColor(getResources().getColor(R.color.colorPrimaryButton));

        btn_year.setBackground(getResources().getDrawable(R.drawable.white_button));
        btn_year.setTextColor(getResources().getColor(R.color.colorPrimaryButton));

    }

    @OnClick(R.id.btn_month)
    public void btnMonth() {
        btn_month.setBackgroundColor(getResources().getColor(R.color.colorPrimaryButton));
        btn_month.setTextColor(getResources().getColor(R.color.white));

        btn_half_month.setBackground(getResources().getDrawable(R.drawable.white_button));
        btn_half_month.setTextColor(getResources().getColor(R.color.colorPrimaryButton));

        btn_every_week.setBackground(getResources().getDrawable(R.drawable.white_button));
        btn_every_week.setTextColor(getResources().getColor(R.color.colorPrimaryButton));

        btn_year.setBackground(getResources().getDrawable(R.drawable.white_button));
        btn_year.setTextColor(getResources().getColor(R.color.colorPrimaryButton));

    }

    @OnClick(R.id.btn_year)
    public void btnYear() {
        btn_year.setBackgroundColor(getResources().getColor(R.color.colorPrimaryButton));
        btn_year.setTextColor(getResources().getColor(R.color.white));

        btn_half_month.setBackground(getResources().getDrawable(R.drawable.white_button));
        btn_half_month.setTextColor(getResources().getColor(R.color.colorPrimaryButton));

        btn_month.setBackground(getResources().getDrawable(R.drawable.white_button));
        btn_month.setTextColor(getResources().getColor(R.color.colorPrimaryButton));

        btn_every_week.setBackground(getResources().getDrawable(R.drawable.white_button));
        btn_every_week.setTextColor(getResources().getColor(R.color.colorPrimaryButton));

    }


    @OnClick(R.id.lable_date)
    public void selectDate() {
        selectDatePicker(context);
    }

    private void selectDatePicker(Context context) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dlg = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                lable_date.setText(CommonUtils.formatDateForDisplay(cal.getTime(), "dd-MMM-yyyy"));
                //customerBean.setCust_dob(CommonUtils.formatDateForDisplay(cal.getTime(), "yyyy-MM-dd"));

            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dlg.setTitle("Select From Date");
        Calendar cal = Calendar.getInstance();
        // cal.add(Calendar.YEAR, -18);

        dlg.getDatePicker().setMaxDate(cal.getTime().getTime());
        dlg.show();
    }


    public void customAlert(List<GivingTypeBean.TypeOptions> typeOptionsList) {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Type:");
        final String[] typelist = new String[typeOptionsList.size()];
        // add a list
        for (int i = 0; i < typeOptionsList.size(); i++) {
            GivingTypeBean.TypeOptions typeOptions = typeOptionsList.get(i);
            typelist[i] = typeOptions.getOption();
        }
        builder.setItems(typelist, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        txtType.setText(typelist[which]);
                        break;
                    case 1:
                        txtType.setText(typelist[which]);
                        break;

                }
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public class GetGivingType extends AsyncTask<Void, Void, Void> {

        String response;
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* pDialog = new ProgressDialog(getContext());
            pDialog.setMessage(getResources().getString(R.string.get_event_msg));
            pDialog.setCancelable(false);
            pDialog.show();*/
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                NetworkCalls networkCalls = new NetworkCalls();
                response = networkCalls.sendGet(APIUtils.BASE_URL + "get_giving.php");
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
                    GivingTypeBean givingTypeBean = new Gson().fromJson(responseObject.toString(), GivingTypeBean.class);

                    typeOptionsList = givingTypeBean.getTypeOptionsList();

                } else if (responseObject.get("MESSAGE").toString().trim().equalsIgnoreCase("FAILURE")) {
                    // Toast.makeText(getContext(), "No Notifications Available", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
         /*   if (pDialog.isShowing()) {
                pDialog.dismiss();
            }*/
        }
    }

}
