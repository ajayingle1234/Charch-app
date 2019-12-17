package hardcastle.com.churchapplication.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import hardcastle.com.churchapplication.Fragment.SermonListFragment;
import hardcastle.com.churchapplication.Interface.OnRecyclerViewItemClickListener;
import hardcastle.com.churchapplication.Interface.OnSermonVideoItemClickListener;
import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.model.SermonVideoDetailsBean;
import hardcastle.com.churchapplication.model.SermonsResponseBean;

public class SermonsAdapter extends RecyclerView.Adapter<SermonsAdapter.CustomViewHolder> {

    private List<Integer> dataList;
    private Context context;
    private List<SermonsResponseBean.SermonList> videoList;
    private OnSermonVideoItemClickListener mClickListener;

    public SermonsAdapter(Context context, OnSermonVideoItemClickListener mClickListener, List<SermonsResponseBean.SermonList> videoList) {
        this.context = context;
        this.mClickListener = mClickListener;
        this.videoList = videoList;
       /* this.dataList = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++)
            dataList.add(i);*/
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        private TextView txtTitle;
        private ImageView coverImage;
        private CardView cardView;
        private ImageView videoView;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            //txtTitle = mView.findViewById(R.id.name);
            coverImage = mView.findViewById(R.id.image);
            cardView = mView.findViewById(R.id.card_view_video);
            txtTitle = mView.findViewById(R.id.video_name);
            videoView = mView.findViewById(R.id.video_view);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_sermon, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        // holder.txtTitle.setText(dataList.get(position).getTitle());
        SermonsResponseBean.SermonList sermonList = videoList.get(position);
        Uri videoUri = Uri.parse(sermonList.getSermonsPhoto());
       /* holder.videoView.setVideoURI(videoUri);
        holder.videoView.seekTo(1);*/
        holder.txtTitle.setText(sermonList.getName());
/*
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(position).getThumbnailUrl())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.coverImage);*/

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    Log.d("myLog", "mClickListener != null");
                    mClickListener.onVideoClick(videoList.get(position));
                } else {
                    Log.d("myLog", "mClickListener = null");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

}