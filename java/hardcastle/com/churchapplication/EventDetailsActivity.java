package hardcastle.com.churchapplication;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import hardcastle.com.churchapplication.Fragment.TodaysEventFragment;
import hardcastle.com.churchapplication.Interface.SetTabInterface;
import hardcastle.com.churchapplication.model.EventsResponseBean;

public class EventDetailsActivity extends AppCompatActivity{

    private static final String ARG_EVENT_DETAILS = "EventDetails";
    private EventsResponseBean.DATum eventDetails;
    Button _book_event;
    ImageView _image_eventDetais;
    TextView _eventLocation;
    TextView _eventDate;
    TextView _eventTicketType;
    TextView _eventTicketSlot;
    TextView _eventStartTime;
    TextView _eventDescription;
    private Toolbar toolbar;

    int Tab;

    SetTabInterface setTabInterface;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        _book_event=findViewById(R.id.btnbook_event);
         _image_eventDetais=findViewById(R.id.imageView_event);
         _eventLocation=findViewById(R.id.txt_event_location);
         _eventDate=findViewById(R.id.txt_event_date);
         _eventTicketType=findViewById(R.id.txt_event_ticket_type);
         _eventTicketSlot=findViewById(R.id.txt_event_ticket_slot);
         _eventStartTime=findViewById(R.id.txt_start_time);
         _eventDescription=findViewById(R.id.txt_event_description);
        if (getIntent() != null) {
            eventDetails = getIntent().getParcelableExtra(ARG_EVENT_DETAILS);
            Tab=getIntent().getIntExtra("TabSelected",0);
        }
        setEventDetails();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setEventDetails() {


        if (eventDetails != null) {
            this.setTitle(eventDetails.getEventName());


            this.setActionBar(toolbar);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


            try {
                Date date = dateFormat.parse(eventDetails.getEventDate());
                Date newDate=new Date(date.getTime()+86400000L);
                if (newDate.before(Calendar.getInstance().getTime())){
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
        _book_event.setBackgroundColor(this.getResources().getColor(R.color.colorBackgroundCard));
    }


    public void bookEvent(View view) {

        startActivity(new Intent(this,EventPaymentActivity.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setTabInterface= TodaysEventFragment.todaysEventFragment;
        setTabInterface.setTab(Tab);
    }


}
