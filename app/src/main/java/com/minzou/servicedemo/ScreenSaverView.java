package com.minzou.servicedemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.List;


public class ScreenSaverView extends RelativeLayout {
    private Context mContext;
    private View rootView;
    private DirectionalViewPager mViewPager;
//    private int[] ids = new int[]{R.drawable.biz_ad_new_version1_img2,R.drawable.biz_ad_new_version1_img3};
    private List<String> listImg;
    private ArrayList<ImageView> mList;
    private IntentFilter fliter;
    private BroadCastReceiver receiver;
    private int mPosition;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public ScreenSaverView(Context context) {
        super(context);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.screen_saver, this);
        mViewPager = (DirectionalViewPager) rootView.findViewById(R.id.viewpager);
        listImg = Utils.getPictures(Environment.getExternalStorageDirectory().getAbsolutePath() + "/demo_img");
        mList = new ArrayList<ImageView>();
        // 添加viewpager多出的两个view
        int length = listImg.size() + 2;
        for (int i = 0; i < length; i++) {
            ImageView imageView = new ImageView(context);
            ViewGroup.LayoutParams viewPagerImageViewParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(viewPagerImageViewParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mList.add(imageView);
        }
        mViewPager.setAdapter(new MyPagerAdapter(mList));
        mViewPager.setOrientation(DirectionalViewPager.VERTICAL);
//		mViewPager.setCurrentItem(ids.length * 100);

        mViewPager.setCurrentItem(1);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                mPosition = position;

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
                Log.i("onPageSelected", mPosition+"");
                int pageIndex = mPosition;
                if (mPosition == 0) {
                    // 当视图在第一个时，将页面号设置为图片的最后一张。
                    pageIndex = listImg.size();
                } else if (mPosition == listImg.size() + 1) {
                    // 当视图在最后一个是,将页面号设置为图片的第一张。
                    pageIndex = 1;
                }
                if (mPosition != pageIndex) {
                    mViewPager.setCurrentItem(pageIndex,true);
//                    return;
                }

            }



            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
        receiver = new BroadCastReceiver();
        fliter = new IntentFilter();
        fliter.addAction("wo.shi.jie.suo");
        mContext.registerReceiver(receiver, fliter);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public class MyPagerAdapter extends PagerAdapter {
        public List<ImageView> mListViews;

        public MyPagerAdapter(List<ImageView> mListViews) {
            this.mListViews = mListViews;
        }


        @Override
        public void destroyItem(ViewGroup container, int arg1, Object arg2) {
            ImageView view = mListViews.get(arg1);
            container.removeView(view);
            view.setImageBitmap(null);

//			container.removeView(mListViews.get(arg1 % mListViews.size()));

        }

        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (position == 0) {
                mListViews.get(position).setImageBitmap(BitmapFactory.decodeFile(listImg.get(listImg.size()-1)));
            } else if (position == (mListViews.size() - 1)) {
                mListViews.get(position).setImageBitmap(BitmapFactory.decodeFile(listImg.get(0)));
            } else {
                mListViews.get(position).setImageBitmap(BitmapFactory.decodeFile(listImg.get(position-1)));
            }
            container.addView(mListViews.get(position));
            return mListViews.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }


    }

    private class BroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null) {

                Log.i("这是解锁", intent.getAction());
                switch (intent.getAction()) {

                    case "wo.shi.jie.suo":
                        if (intent.getStringExtra("type").equals("1")) {
                            Log.i("这是解锁", "1111111111");
                        } else {

                            Log.i("这是解锁", "22222222222");
                        }
                        break;

                }

            }
        }
    }
}
