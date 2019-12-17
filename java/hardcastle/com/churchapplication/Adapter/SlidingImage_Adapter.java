package hardcastle.com.churchapplication.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;


import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.VideoPlayer.ResizeSurfaceView;
import hardcastle.com.churchapplication.VideoPlayer.VideoControllerView;
import hardcastle.com.churchapplication.model.GalleryBean;


public class SlidingImage_Adapter extends PagerAdapter  {


    private ArrayList<GalleryBean> galleryBeanArrayList;
    private LayoutInflater inflater;
    private Context context;



    public SlidingImage_Adapter(Context context, ArrayList<GalleryBean> galleryBeanArrayList) {
        this.context = context;
        this.galleryBeanArrayList=galleryBeanArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return galleryBeanArrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {

        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
        final ProgressBar progressVideo = (ProgressBar) imageLayout.findViewById(R.id.progressVideo);

        final ImageView ivPlay = (ImageView) imageLayout
                .findViewById(R.id.IvPlay);
        final VideoView videoView =(VideoView)imageLayout.findViewById(R.id.videoView);



        if (galleryBeanArrayList.get(position).getType()==0){
            imageView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.GONE);
            ivPlay.setVisibility(View.GONE);
            progressVideo.setVisibility(View.GONE);
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(galleryBeanArrayList.get(position).getImg())
                    .error(R.drawable.app_background)
                    .into(imageView);

        }
        else {
            final ProgressDialog pDialog = new ProgressDialog(context);
            imageView.setVisibility(View.GONE);
            progressVideo.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoPath(galleryBeanArrayList.get(position).getVideo());
            ivPlay.setVisibility(View.GONE );
            videoView.start();
            videoView.setMediaController(new MediaController(context));



            videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                        ivPlay.setVisibility(View.GONE);
                        progressVideo.setVisibility(View.GONE);
                        return true;
                    }
                    return false;
                }
            });


        }




        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}