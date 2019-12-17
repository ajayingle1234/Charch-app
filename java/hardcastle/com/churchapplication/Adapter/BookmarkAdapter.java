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

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.CustomViewHolder> {

    private Context context;
    List<BookmarkPojo> bookmarkPojoList;

    public BookmarkAdapter(Context context, List<BookmarkPojo> bookmarkPojoList) {
        this.context = context;
        this.bookmarkPojoList = bookmarkPojoList;

    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        private TextView bookmark_title;
        private CardView cardView;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            bookmark_title = mView.findViewById(R.id.bookmark_title);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_bookmark, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {

        BookmarkPojo bookmarkPojo = bookmarkPojoList.get(position);

        holder.bookmark_title.setText(bookmarkPojo.getBookmarkTitle());


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
        return bookmarkPojoList.size();
    }
}