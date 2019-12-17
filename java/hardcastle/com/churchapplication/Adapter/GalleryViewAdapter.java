package hardcastle.com.churchapplication.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import hardcastle.com.churchapplication.R;

public class GalleryViewAdapter extends PagerAdapter {

    private static final String TAG = "ImageViewPage";
    Context mContext;
    LayoutInflater mLayoutInflater;
    List<Integer> mResources;

    public GalleryViewAdapter(Context context, List<Integer> resources) {
        mContext = context;
        mResources = resources;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d(TAG,
                "instantiateItem() called with: " + "container = [" + container + "], position = [" + position + "]");

        View itemView = mLayoutInflater.inflate(R.layout.item_gallery_pager, container, false);

        Log.d(TAG, "load in gallery:" + mResources.get(position) + "#end");
        final ImageView ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);

        if (!mResources.get(position).equals("")) {
            Glide.with(mContext)
                    .load(mResources.get(position))
                    //.crossFade()
                    .into(ivPhoto);
        }

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d(TAG, "destroyItem() called with: " + "container = [" + container + "], position = [" + position
                + "], object = [" + object + "]");
        container.removeView((LinearLayout) object);
    }
}