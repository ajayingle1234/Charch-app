package hardcastle.com.churchapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import hardcastle.com.churchapplication.Interface.OnRecyclerViewItemClickListener;
import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.model.EventsResponseBean;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.CustomViewHolder> {

    private Context context;
    private OnRecyclerViewItemClickListener mClickListener;
    List<EventsResponseBean.DATum> todayEventsResponseBeanList;

    public EventAdapter(Context context, OnRecyclerViewItemClickListener mClickListener, List<EventsResponseBean.DATum> todayEventsResponseBeanList) {
        this.context = context;
        this.mClickListener = mClickListener;
        this.todayEventsResponseBeanList = todayEventsResponseBeanList;

    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        private TextView txtTitle;
        private TextView txtEventDate;
        private ImageView coverImage;
        private CardView cardView;
        private TextView txtEventDetails;
        private TextView txtEventTime;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.title);
            txtEventDate = mView.findViewById(R.id.event_date);
            //txtEventDetails = mView.findViewById(R.id.event_desc);
            txtEventTime = mView.findViewById(R.id.event_time);
            coverImage = mView.findViewById(R.id.coverImage);
            cardView = mView.findViewById(R.id.card_view_event);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.event_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {

        EventsResponseBean.DATum daTum = todayEventsResponseBeanList.get(position);
        holder.txtTitle.setText(daTum.getEventName());
        holder.txtEventDate.setText(daTum.getEventDate());
        holder.txtEventTime.setText(daTum.getEventTime());
        //holder.txtEventDetails.setText(daTum.getDescription());

        Picasso.get().load(daTum.getEventPhoto()).resize(100, 130)
                .placeholder(R.drawable.church)
                .error(R.drawable.church).into(holder.coverImage);

     /*   Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(position).getThumbnailUrl())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.coverImage);*/

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventsResponseBean.DATum daTum = todayEventsResponseBeanList.get(position);
                if (mClickListener != null) {
                    Log.d("myLog", "mClickListener != null");
                    mClickListener.onClick(position);
                } else {
                    Log.d("myLog", "mClickListener = null");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return todayEventsResponseBeanList.size();
    }
}