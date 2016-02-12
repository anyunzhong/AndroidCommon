package net.datafans.android.common.widget.photobrowser;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import net.datafans.android.common.R;
import net.datafans.android.common.helper.LogHelper;
import net.datafans.android.common.widget.imageview.CommonImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhonganyun on 16/2/12.
 */
public class PhotoBrowser {
    private Context context;

    private View rootView;

    private PopupWindow popupWindow;

    private ViewPager viewPager;
    private PhotoPagerAdapter adapter;

    private List<Photo> photos = new ArrayList<>();

    private List<View> views = new ArrayList<>();


    public PhotoBrowser(Context context, View rootView) {
        this.context = context;
        this.rootView = rootView;
        init();
    }

    private void init() {
        View popView = LayoutInflater.from(context).inflate(R.layout.photo_browser, null);

        popupWindow = new PopupWindow(popView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        popupWindow.setAnimationStyle(R.style.pop_anim_style);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.argb(0, 0, 0, 0)));


        viewPager = (ViewPager) popView.findViewById(R.id.viewpager);

        adapter = new PhotoPagerAdapter();
        viewPager.setAdapter(adapter);

    }

    public void show() {
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
    }

    public void setCurrentPage(int index) {
        viewPager.setCurrentItem(index, false);
    }

    public void addPhoto(List<Photo> photos) {
        this.photos.addAll(photos);
        adapter.notifyDataSetChanged();
    }


    private class PhotoPagerAdapter extends PagerAdapter {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

            return arg0 == arg1;
        }

        @Override
        public int getCount() {

            return photos.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(views.get(position));

        }

        @Override
        public int getItemPosition(Object object) {

            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.photo_browser_item, null);
            container.addView(view);

            views.add(view);

            CommonImageView imageView = (CommonImageView) view.findViewById(R.id.image);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });


            imageView.getImageView().setScaleType(ImageView.ScaleType.FIT_CENTER);
            Photo photo = photos.get(position);
            if (photo.getBitmap() != null) {
                imageView.getImageView().setImageBitmap(photo.getBitmap());
            } else {
                imageView.loadImage(photo.getUrl());
            }
            return view;
        }

    }

}
