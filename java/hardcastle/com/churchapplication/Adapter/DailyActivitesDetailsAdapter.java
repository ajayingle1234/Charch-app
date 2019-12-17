package hardcastle.com.churchapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hardcastle.com.churchapplication.R;

public class DailyActivitesDetailsAdapter extends RecyclerView.Adapter<DailyActivitesDetailsAdapter.CustomViewHolder> {

    private List<Integer> dataList;
    private Context context;
    private String date;

    public DailyActivitesDetailsAdapter(Context context, String date/*List<RetroPhoto> dataList*/) {
        this.context = context;
        this.dataList = new ArrayList<Integer>();
        this.date = date;
        for (int i = 0; i < 10; i++)
            dataList.add(i);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        private TextView txtTitle;
        private TextView coverImage;
        private CardView cardView;
        private TextView event_desc;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            //txtTitle = mView.findViewById(R.id.name);
            coverImage = mView.findViewById(R.id.coverImage);
            event_desc = mView.findViewById(R.id.event_desc);
            //cardView = mView.findViewById(R.id.card_view_video);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_daily_details, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        // holder.txtTitle.setText(dataList.get(position).getTitle());

        holder.coverImage.setText(date);
        holder.event_desc.setText(date);

    /*    Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(position).getThumbnailUrl())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.coverImage);*/

       /* holder.cardView.setOnClickListener(new View.OnClickListener() {
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
        return dataList.size();
    }


}
