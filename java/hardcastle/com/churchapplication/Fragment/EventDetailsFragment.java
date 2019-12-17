package hardcastle.com.churchapplication.Fragment;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.model.EventsResponseBean;

public class EventDetailsFragment extends Fragment {

    private Unbinder unbinder;
    private static final String ARG_EVENT_DETAILS = "event_details";
    private EventsResponseBean.DATum eventDetails;

    @BindView(R.id.btnbook_event)
    Button _book_event;
    @BindView(R.id.imageView_event)
    ImageView _image_eventDetais;
    @BindView(R.id.txt_event_location)
    TextView _eventLocation;
    @BindView(R.id.txt_event_date)
    TextView _eventDate;
    @BindView(R.id.txt_event_ticket_type)
    TextView _eventTicketType;
    @BindView(R.id.txt_event_ticket_slot)
    TextView _eventTicketSlot;
    @BindView(R.id.txt_start_time)
    TextView _eventStartTime;
    /*   @BindView(R.id.txt_end_time)
       TextView _eventEndTime;*/
    @BindView(R.id.txt_event_description)
    TextView _eventDescription;
    private Toolbar toolbar;


    public EventDetailsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static EventDetailsFragment newInstance() {
        EventDetailsFragment fragment = new EventDetailsFragment();

        return fragment;
    }

    public static EventDetailsFragment newInstance(EventsResponseBean.DATum daTum) {
        EventDetailsFragment fragment = new EventDetailsFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_EVENT_DETAILS, daTum);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventDetails = getArguments().getParcelable(ARG_EVENT_DETAILS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);


  /*      RequestOptions requestOption = new RequestOptions()
                .placeholder(R.drawable.event_img);
        Glide.with(this).load(eventDetails.getEventPhoto())
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(requestOption)
                .into(_image_eventDetais);*/

        if (getArguments() != null) {
            eventDetails = getArguments().getParcelable(ARG_EVENT_DETAILS);
        }
        setEventDetails();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    private void setEventDetails() {


        if (eventDetails != null) {
            getActivity().setTitle(eventDetails.getEventName());


            getActivity().setActionBar(toolbar);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = dateFormat.parse(eventDetails.getEventDate());
                if (date.before(Calendar.getInstance().getTime())){
                    disableBook();
                }

                _eventDate.setText(new SimpleDateFormat("dd-MMM-yyyy").format(date).toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            _eventLocation.setText(eventDetails.getEventPlace());
            _eventDate.setText(eventDetails.getEventDate());
            _eventTicketType.setText(eventDetails.getEventFreePaid());
            _eventStartTime.setText(eventDetails.getEventTime());
            _eventDescription.setText(eventDetails.getDescription());
            _eventTicketSlot.setText(eventDetails.getEventTicketSlot());

            Picasso.get().load(eventDetails.getEventPhoto())
                    .placeholder(R.drawable.church).fit()
                    .error(R.drawable.church).into(_image_eventDetais);
        }
    }

    private void disableBook() {
        _book_event.setClickable(false);
        _book_event.setBackgroundColor(getActivity().getResources().getColor(R.color.colorBackgroundCard));
    }

    @OnClick(R.id.btnbook_event)
    public void bookEvent() {

        getFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new EventPaymentFragment()).addToBackStack("EventPaymentFragment")
                .commit();

    }
}
