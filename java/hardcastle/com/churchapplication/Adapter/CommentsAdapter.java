package hardcastle.com.churchapplication.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.model.CommentsBean;

public class CommentsAdapter extends
        RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {

    private List<CommentsBean> commentsList;

    /**
     * View holder class
     * */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvComment;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvComment = (TextView) view.findViewById(R.id.tvComment);
        }
    }

    public CommentsAdapter(List<CommentsBean> countryList) {
        this.commentsList = countryList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CommentsBean c = commentsList.get(position);
        holder.tvName.setText(c.getUserName());
        holder.tvComment.setText(String.valueOf(c.getComment()));
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item,parent, false);
        return new MyViewHolder(v);
    }
}