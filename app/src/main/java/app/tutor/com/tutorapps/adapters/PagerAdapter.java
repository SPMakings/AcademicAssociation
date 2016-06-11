package app.tutor.com.tutorapps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;

import app.tutor.com.tutorapps.R;
import app.tutor.com.tutorapps.customview.RobotoBold;

/**
 * Created by apple on 05/05/16.
 */
public class PagerAdapter extends android.support.v4.view.PagerAdapter {


    LinkedList<String> subName;
    int imageList[];
    Context mContext = null;

    public PagerAdapter(Context mContext, LinkedList<String> subName, int imageList[]) {
        super();
        this.subName = subName;
        this.imageList = imageList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return subName.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.fragments_pager, null);
        ((RobotoBold) view.findViewById(R.id.subject_name)).setText(subName.get(position));
        Picasso.with(mContext).load(imageList[position]).fit().centerCrop().placeholder(R.drawable.place_cam).into((ImageView) view.findViewById(R.id.header_image));
        // Glide.with(mContext).load("http://images.designtrends.com/wp-content/uploads/2016/03/16033524/cityscapes-cities-architecture-buildings-skyscrapers-night-lights-hdr-wallpaper-1.jpg").centerCrop().into((ImageView) view.findViewById(R.id.header_image));
//        Picasso.with(mContext).load(imageList[position]).fit().centerCrop().into((ImageView) view.findViewById(R.id.header_image));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ViewGroup) object);
    }


}