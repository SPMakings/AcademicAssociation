package app.tutor.com.tutorapps.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import app.tutor.com.tutorapps.R;
import app.tutor.com.tutorapps.customview.TouchImageView;

/**
 * Created by Saikat's Mac on 15/05/16.
 */

public class GalleryPagerFragment extends Fragment {

    String IMAGE_ID = "";


    public static GalleryPagerFragment getInstance(final String IMG_) {
        GalleryPagerFragment frag_ = new GalleryPagerFragment();
        frag_.IMAGE_ID = IMG_;
        return frag_;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.items_gallery_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Picasso.with(getActivity()).load(IMAGE_ID).placeholder(R.drawable.place_cam).into((TouchImageView) view.findViewById(R.id.t_image_view));

    }
}
