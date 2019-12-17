package hardcastle.com.churchapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import hardcastle.com.churchapplication.Fragment.GalleryViewFragment;
import hardcastle.com.churchapplication.GallerySliderActivity;
import hardcastle.com.churchapplication.Interface.OnGalleryClickListener;
import hardcastle.com.churchapplication.Interface.OnRecyclerViewItemClickListener;
import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.model.GalleryBean;

import static com.facebook.FacebookSdk.getApplicationContext;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.CustomViewHolder> {

    private ArrayList<GalleryBean> dataList;
    private Context context;


    public GalleryAdapter(Context context, ArrayList<GalleryBean> dataList) {
        this.context = context;
        this.dataList = dataList;
        /*for (int i = 0; i < 10; i++)
            dataList.add(i);*/
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        private ImageView coverImage, imgPlay;

        private CardView cardView;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            //txtTitle = mView.findViewById(R.id.name);
            coverImage = mView.findViewById(R.id.image);
            imgPlay = mView.findViewById(R.id.imagePlay);
            cardView = mView.findViewById(R.id.card_view_video);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.video_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        // holder.coverImage.setBackground(context.getResources().getDrawable(dataList.get(position)));


        if (dataList.get(position).getImg() != null) {
            holder.imgPlay.setVisibility(View.GONE);
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(dataList.get(position).getImg())
                    .placeholder((R.drawable.app_background))
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.coverImage);

        } else {
            holder.imgPlay.setVisibility(View.VISIBLE);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //context.startActivity(new Intent(context, GallerySliderActivity.class));
                Intent intent = new Intent(getApplicationContext(), GallerySliderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("List", dataList);

                intent.putExtras(bundle);
                intent.putExtra("Position", position);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}