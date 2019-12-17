package hardcastle.com.churchapplication.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hardcastle.com.churchapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentMethodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentMethodFragment extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.btnCardPay)
    Button buttonPay;

    public PaymentMethodFragment() {
        // Required empty public constructor
    }

    public static PaymentMethodFragment newInstance() {
        PaymentMethodFragment fragment = new PaymentMethodFragment();
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
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnCardPay)
    public void payCard() {
        getFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new PaymentFragment()).addToBackStack("PaymentFragment")
                .commit();
    }
}
