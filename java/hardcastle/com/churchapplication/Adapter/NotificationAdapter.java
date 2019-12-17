package hardcastle.com.churchapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.model.NotificationBean;
import hardcastle.com.churchapplication.model.NotificationResponseBean;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.CustomViewHolder> {

    private Context context;
    List<NotificationResponseBean.NotificationBeanList> notificationBeanList;

    public NotificationAdapter(Context context, List<NotificationResponseBean.NotificationBeanList> notificationBeanList) {
        this.context = context;
        this.notificationBeanList = notificationBeanList;

    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        private TextView notification_title;
        private TextView notification_details;
        private TextView notification_time;
        private ImageView coverImage;
        private CardView cardView;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            notification_title = mView.findViewById(R.id.notification_title);
            notification_details = mView.findViewById(R.id.notification_details);
            notification_time = mView.findViewById(R.id.notification_time);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_notification, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {

        NotificationResponseBean.NotificationBeanList notificationBean = notificationBeanList.get(position);
        String format = null;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(notificationBean.getNotificationDate());
            format = new SimpleDateFormat("dd-MMM-yyyy").format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.notification_title.setText(notificationBean.getNotification());
        holder.notification_details.setText(format);
        holder.notification_time.setText(notificationBean.getNotificationTime());


    /*    Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(position).getThumbnailUrl())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.coverImage);*/

        /*holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    Log.d("myLog", "mClickListener != null");
                    mClickListener.onClick(position);
                } else {
                    Log.d("myLog", "mClickListener = null");
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return notificationBeanList.size();
    }
}