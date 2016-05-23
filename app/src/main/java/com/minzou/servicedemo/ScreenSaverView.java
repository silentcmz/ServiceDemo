package com.minzou.servicedemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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


public class ScreenSaverView extends RelativeLayout
{
	private Context mContext;
	private View rootView;
	private DirectionalViewPager mViewPager;
	private int[] ids = new int[]{R.drawable.biz_ad_new_version1_img0, R.drawable.biz_ad_new_version1_img1, R.drawable.biz_ad_new_version1_img2, R.drawable.biz_ad_new_version1_img3};
	private ArrayList<View> mList;
	private IntentFilter fliter;
	private BroadCastReceiver receiver;

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}

	public ScreenSaverView(Context context)
	{
		super(context);
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rootView = inflater.inflate(R.layout.screen_saver, this);
		mViewPager = (DirectionalViewPager) rootView.findViewById(R.id.viewpager);
		mList = new ArrayList<>();
		for (int i = 0; i < ids.length; i++) {
			ImageView img = new ImageView(mContext);
			img.setImageResource(ids[i]);
			mList.add(img);
		}
		mViewPager.setAdapter(new MyPagerAdapter(mList));
		mViewPager.setOrientation(DirectionalViewPager.VERTICAL);
		mViewPager.setCurrentItem(ids.length * 100);
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
//				btn.setVisibility(position % listViews.size() == listViews.size() - 1 ? View.VISIBLE : View.GONE);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		receiver = new BroadCastReceiver();
		fliter = new IntentFilter();
		fliter.addAction("wo.shi.jie.suo");
		mContext.registerReceiver(receiver,fliter);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	public class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}


		@Override
		public void destroyItem(ViewGroup container, int arg1, Object arg2) {
			container.removeView(mListViews.get(arg1 % mListViews.size()));
		}

//        @Override
//        public void finishUpdate(ViewGroup container) {
//            super.finishUpdate(container);
//        }

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
//			View v = mListViews.get(position % mListViews.size());
//			ImageView iv = (ImageView) v.findViewById(R.id.iv);
//			iv.setImageResource(ids[position % mListViews.size()]);
			container.addView(mListViews.get(position % mListViews.size()));

			return mListViews.get(position % mListViews.size());
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}


	}

	private class BroadCastReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction() != null){

				Log.i("这是解锁",intent.getAction());
				switch (intent.getAction()){

					case "wo.shi.jie.suo":
						if (intent.getStringExtra("type").equals("1")){
							Log.i("这是解锁","1111111111");
						}else{

							Log.i("这是解锁","22222222222");
						}
						break;

				}

			}
		}
	}
}
