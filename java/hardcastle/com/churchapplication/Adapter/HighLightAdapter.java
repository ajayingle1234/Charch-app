package hardcastle.com.churchapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.model.BookmarkPojo;
import hardcastle.com.churchapplication.model.HighLightPojo;

public class HighLightAdapter extends RecyclerView.Adapter<HighLightAdapter.CustomViewHolder> {
    private static OnItemClickListener mOnItemClickLister;
    private static OnItemLongClickListener mOnItemLongClickListener;

    public interface OnItemClickListener {
        void onItemClicked(View view, int pos);
    }

    public interface OnItemLongClickListener {
        void onItemLongClicked(View view, int pos);
    }


    private Context context;
    List<HighLightPojo> highLightPojos;

    public HighLightAdapter(Context context, List<HighLightPojo> highLightPojos, OnItemClickListener listener,OnItemLongClickListener longClickListener) {
        this.context = context;
        this.highLightPojos = highLightPojos;
        mOnItemClickLister = listener;
        mOnItemLongClickListener=longClickListener;

    }

    class CustomViewHolder extends RecyclerView.ViewHolder {//implements View.OnClickListener {

        public final View mView;
        private TextView highlight_title;
        private CardView cardView;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            highlight_title = mView.findViewById(R.id.bookmark_title);
            cardView=mView.findViewById(R.id.card_view_event);
        }

      /*  @Override
        public void onClick(View v) {
            int position = v.getLayoutDirection();
            mOnItemClickLister.onItemClicked(v, position);
        }*/
    }

    @Override
    public HighLightAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_bookmark, parent, false);

        return new HighLightAdapter.CustomViewHolder(view);
    }


    @Override
    public void onBindViewHolder(HighLightAdapter.CustomViewHolder holder, final int position) {

        HighLightPojo highLightPojo = highLightPojos.get(position);

        holder.highlight_title.setText(highLightPojo.getTitle());


    /*    Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(position).getThumbnailUrl())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.coverImage);*/

    holder.cardView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mOnItemClickLister.onItemClicked(v, position);
        }
    });

    holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
           mOnItemLongClickListener.onItemLongClicked(v,position);
           return true;
        }
    });


    }

    @Override
    public int getItemCount() {
        return highLightPojos.size();
    }
}

