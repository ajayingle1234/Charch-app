package hardcastle.com.churchapplication.Adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import hardcastle.com.churchapplication.CommentsActivity;
import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.model.PrayerResponseBean;

public class PrayerListViewAdapter extends ArrayAdapter<PrayerResponseBean.PrayerResponse> implements View.OnClickListener {

    private List<PrayerResponseBean.PrayerResponse> prayerResponseArrayList;
    Context context;

    // View lookup cache
    private static class ViewHolder {
        private TextView txt_prayer_details;
        private ImageView btn_like, btn_call, btn_comment, btn_share;
        private TextView notification_details;
        private ImageView coverImage;
        RelativeLayout rlLike;
        private NotificationBadge badgeLike, badgeComment;
    }

    public PrayerListViewAdapter(Context context, List<PrayerResponseBean.PrayerResponse> prayerResponseArrayList) {
        super(context, R.layout.item_prayer, prayerResponseArrayList);
        this.prayerResponseArrayList = prayerResponseArrayList;
        this.context = context;

    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();

        switch (v.getId()) {
            case R.id.badgeLike:
                int likeCount=prayerResponseArrayList.get(position).getLikeCount();
                if (prayerResponseArrayList.get(position).isLiked()) {
                    // prayerResponseList.get(position).setLiked(false);
                    //btn_like.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_thumb));
                    likeCount--;
                    prayerResponseArrayList.get(position).setLikeCount(likeCount);
                    // badgeLike.setNumber(likeCount);
                    prayerResponseArrayList.get(position).setLiked(false);
                }else {
                    // prayerResponseList.get(position).setLiked(true);

                    //btn_like.setImageDrawable(context.getResources().getDrawable(R.drawable.blue_thumb));
                    likeCount++;
                    prayerResponseArrayList.get(position).setLikeCount(likeCount);
                    //badgeLike.setNumber(likeCount);
                    prayerResponseArrayList.get(position).setLiked(true);

                }

                notifyDataSetChanged();
                Log.i("Position"," --"+position);
                break;

            case R.id.btn_comment:
                context.startActivity(new Intent(context, CommentsActivity.class));

                break;
            case R.id.btn_call:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "8275484196"));
                context.startActivity(intent);
                break;

            case R.id.btn_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, prayerResponseArrayList.get(position).getDescription());
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        PrayerResponseBean.PrayerResponse prayerResponse = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_prayer, parent, false);
            viewHolder.txt_prayer_details = convertView.findViewById(R.id.txt_prayer_details);

            viewHolder.btn_like = convertView.findViewById(R.id.btn_like);
            viewHolder.btn_call = convertView.findViewById(R.id.btn_call);
            viewHolder.btn_comment = convertView.findViewById(R.id.btn_comment);
            viewHolder.btn_share = convertView.findViewById(R.id.btn_share);
            viewHolder.badgeLike = convertView.findViewById(R.id.badgeLike);
            viewHolder.badgeComment = convertView.findViewById(R.id.badgeComment);
            viewHolder.rlLike = convertView.findViewById(R.id.rlLike);
            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

       /* Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);*/
        lastPosition = position;

        viewHolder.txt_prayer_details.setText(prayerResponseArrayList.get(position).getDescription());


        viewHolder.badgeLike.setNumber(prayerResponseArrayList.get(position).getLikeCount());

        //commentCount=prayerResponse.getCommentCount();
        //liked=prayerResponse.isLiked();

        viewHolder.badgeComment.setNumber(prayerResponseArrayList.get(position).getCommentCount());
        viewHolder.badgeLike.setOnClickListener(this);
        viewHolder.badgeLike.setTag(position);
        viewHolder.btn_call.setOnClickListener(this);
        viewHolder.btn_call.setTag(position);
        viewHolder.btn_share.setOnClickListener(this);
        viewHolder.btn_share.setTag(position);
        viewHolder.btn_comment.setOnClickListener(this);
        viewHolder.btn_comment.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
