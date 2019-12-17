package hardcastle.com.churchapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nex3z.notificationbadge.NotificationBadge;

import java.util.List;

import hardcastle.com.churchapplication.CommentsActivity;
import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.model.PrayerResponseBean;

public class PrayerAdapter extends RecyclerView.Adapter<PrayerAdapter.CustomViewHolder> implements View.OnClickListener {

    private Context context;
    List<PrayerResponseBean.PrayerResponse> prayerResponseList;
    private int adapter_position;

    private TextView txt_prayer_details;
    private ImageView btn_like, btn_call, btn_comment, btn_share;
    private TextView notification_details;
    private ImageView coverImage;
    RelativeLayout rlLike;
    private NotificationBadge badgeLike, badgeComment;

    public PrayerAdapter(Context context, List<PrayerResponseBean.PrayerResponse> prayerResponseList) {
        this.context = context;
        this.prayerResponseList = prayerResponseList;

    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            txt_prayer_details = mView.findViewById(R.id.txt_prayer_details);

            btn_like = mView.findViewById(R.id.btn_like);
            btn_call = mView.findViewById(R.id.btn_call);
            btn_comment = mView.findViewById(R.id.btn_comment);
            btn_share = mView.findViewById(R.id.btn_share);
            badgeLike = mView.findViewById(R.id.badgeLike);
            badgeComment = mView.findViewById(R.id.badgeComment);
            rlLike = mView.findViewById(R.id.rlLike);
/*
            btn_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    int likeCount = prayerResponseList.get(position).getLikeCount();
                    if (prayerResponseList.get(position).isLiked()) {
                        // prayerResponseList.get(position).setLiked(false);
                        //btn_like.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_thumb));
                        likeCount--;
                        prayerResponseList.get(position).setLikeCount(likeCount);
                        // badgeLike.setNumber(likeCount);
                        prayerResponseList.get(position).setLiked(false);
                    } else {
                        // prayerResponseList.get(position).setLiked(true);

                        //btn_like.setImageDrawable(context.getResources().getDrawable(R.drawable.blue_thumb));
                        likeCount++;
                        prayerResponseList.get(position).setLikeCount(likeCount);
                        //badgeLike.setNumber(likeCount);
                        prayerResponseList.get(position).setLiked(true);

                    }

                    notifyItemChanged(position, prayerResponseList.get(position));
                    Log.i("Position", " --" + position);
                }
            });
*/

        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_prayer, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {

        //PrayerResponseBean.PrayerResponse prayerResponse = prayerResponseList.get(position);
        adapter_position = holder.getAdapterPosition();
        Log.i("onBind Position", "--" + adapter_position);

        txt_prayer_details.setText(prayerResponseList.get(position).getDescription());


        badgeLike.setNumber(prayerResponseList.get(position).getLikeCount());

        //commentCount=prayerResponse.getCommentCount();
        //liked=prayerResponse.isLiked();

        badgeComment.setNumber(prayerResponseList.get(position).getCommentCount());

        btn_call.setOnClickListener(this);
        btn_share.setOnClickListener(this);
        btn_comment.setOnClickListener(this);


        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int position = getAdapterPosition();
                int likeCount = prayerResponseList.get(position).getLikeCount();
                if (prayerResponseList.get(position).isLiked()) {
                    // prayerResponseList.get(position).setLiked(false);
                    //btn_like.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_thumb));
                    likeCount--;
                    prayerResponseList.get(position).setLikeCount(likeCount);
                    // badgeLike.setNumber(likeCount);
                    prayerResponseList.get(position).setLiked(false);
                } else {
                    // prayerResponseList.get(position).setLiked(true);

                    //btn_like.setImageDrawable(context.getResources().getDrawable(R.drawable.blue_thumb));
                    likeCount++;
                    prayerResponseList.get(position).setLikeCount(likeCount);
                    //badgeLike.setNumber(likeCount);
                    prayerResponseList.get(position).setLiked(true);

                }

                notifyItemChanged(position, prayerResponseList.get(position));
                Log.i("Position", " --" + position);
            }
        });


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            /*case R.id.btn_like:

                if (liked) {
                   btn_like.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_thumb));
                   likeCount--;
                    badgeLike.setNumber(likeCount);
                    liked=false;
                }else {
                    btn_like.setImageDrawable(context.getResources().getDrawable(R.drawable.blue_thumb));
                    likeCount++;
                    badgeLike.setNumber(likeCount);
                    liked=true;

                }


                break;*/

            case R.id.btn_comment:
                context.startActivity(new Intent(context, CommentsActivity.class));

                break;
            case R.id.btn_call:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "9130208292"));
                context.startActivity(intent);
                break;
            case R.id.btn_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, prayerResponseList.get(adapter_position).getDescription());
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return prayerResponseList.size();
    }
}