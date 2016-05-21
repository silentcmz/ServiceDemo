package com.minzou.servicedemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

public class StarLockView extends RelativeLayout implements OnClickListener
{

	private static final boolean DBG = true;
	private Context mContext;
	private Handler mainHandler = null;
	private float mx;
	private float my;
	private int count = 0;
	private long firstClick = 0;
	private long secondClick = 0;
	
	private int mWidth, mHight;
	private int mScreenHalfWidth;
	private int mAlphaViewWidth, mAlphaViewHeight;

	private int imgViewWidth, imgViewHeight;

	private int mCenterViewWidth, mCenterViewHeight;
	private int mCenterViewTop, mCenterViewBottom;
	private int mAlphaViewTop, mAlphaViewBottom;

	private int imgViewTop, imgViewBottom;

	private int mSmsViewHalfWidth, mSmsViewHalfHeight;
	private int mDialViewHalfWidth, mDialViewHalfHeight;
	private int mCameraViewHalfWidth, mHalfCameraViewHeight;
	private int mUnlockViewHalfWidth, mUnlockViewHalfHeight;	
	private int mLightViewHalfWidth, mLightViewHalfHeight;
	private int mMusicViewHalfWidth, mMusicViewHalfHeight;

	private ImageView mSmsView, mDialView, mCameraView, mUnLockView;
	private ImageView mCenterView, mAlphaView,imgView;
	private ImageView mSmsLightView, mUnLockLightView,
	                  mCameraLightView, mDialLightView;


	private Rect smsRect, dialRect, cameraRect, unlockRect;
	private Rect mCenterViewRect;
	
	private AlphaAnimation alpha;
	private boolean mTracking = false;
	
	private static final String TAG = "FxLockView";
	public static final String SHOW_MUSIC = "com.phicomm.hu.action.music";
	private static final String SERVICECMD = "com.android.music.musicservicecommand";
	private static final String CMDNAME = "command";
	private static final String CMDSTOP = "stop";
	private static final String CMDPAUSE = "pause";
	private static final String CMDPLAY = "play";
	private static final String CMDPREV = "previous";
	private static final String CMDNEXT = "next";
	
	public StarLockView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		mContext = context;
		if(DBG) Log.d(TAG, "FxLockView2");
		//connectMediaService();
//		if(DBG) Log.d(TAG, "FxLockView-->isMusic--->" + MusicInfo.isMusic());
//		MusicInfo.setMusic(false);
		initViews(context);
		setViewId();
		//setViewOnClick();
		onAnimationStart();
	}

	public StarLockView(Context context) {
		super(context);
		mContext = context;
		if(DBG) Log.d(TAG, "FxLockView2");
//		View view = LayoutInflater.from(context).inflate(R.layout.activity_main,null);
//		addView(view);
		//connectMediaService();
//		if(DBG) Log.d(TAG, "FxLockView-->isMusic--->" + MusicInfo.isMusic());
//		MusicInfo.setMusic(false);
		initViews(context);
		setViewId();
		//setViewOnClick();
		onAnimationStart();
	}


	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		if (changed) {
			mWidth = r;
			mHight = b;
			////mHalfWidth >> 1为向右位移1位，相当于mHalfWidth / 2。采用位移的原因是计算效率比较高。
			mScreenHalfWidth = mWidth >> 1;
			
			getViewMeasure();
			mCenterViewTop = 4 * mHight / 7 - (mCenterViewHeight >> 1);
			mCenterViewBottom = 4 * mHight / 7 + (mCenterViewHeight >> 1);
			mAlphaViewTop = 4 * mHight / 7 - (mAlphaViewHeight >> 1);
			mAlphaViewBottom = 4 * mHight / 7 + (mAlphaViewHeight >> 1);
			
			setChildViewLayout();
			setActivatedViewLayout();
			getChildViewRect();

			mCenterViewRect = new Rect(mWidth / 2 - mAlphaViewWidth / 2, mAlphaViewTop,
					mWidth / 2 + mAlphaViewWidth / 2, mAlphaViewBottom);

		}

		if(DBG) Log.d(TAG, "l-->" + l);
		if(DBG) Log.d(TAG, "t-->" + t);
		if(DBG) Log.d(TAG, "r-->" + r);
		if(DBG) Log.d(TAG, "b-->" + b);
	}

	//获取各个图标的宽、高
	private void getViewMeasure()
	{
		mAlphaView.measure(MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED), MeasureSpec
				.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		mAlphaViewWidth = mAlphaView.getMeasuredWidth();
		mAlphaViewHeight = mAlphaView.getMeasuredHeight();

		mCenterView.measure(MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED), MeasureSpec
				.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		mCenterViewWidth = mCenterView.getMeasuredWidth();
		mCenterViewHeight = mCenterView.getMeasuredHeight();
		
		mSmsView.measure(MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED), MeasureSpec
				.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		mSmsViewHalfWidth = (mSmsView.getMeasuredWidth()) >> 1;
		mSmsViewHalfHeight = (mSmsView.getMeasuredHeight()) >> 1;
		
		mDialView.measure(MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED), MeasureSpec
				.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		mDialViewHalfWidth = (mDialView.getMeasuredWidth()) >> 1;
		mDialViewHalfHeight = (mDialView.getMeasuredHeight()) >> 1;
		
		mCameraView.measure(MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED), MeasureSpec
				.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		mCameraViewHalfWidth = (mCameraView.getMeasuredWidth()) >> 1;
		mHalfCameraViewHeight = (mCameraView.getMeasuredHeight()) >> 1;
		
		mUnLockView.measure(MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED), MeasureSpec
				.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		mUnlockViewHalfWidth = (mUnLockView.getMeasuredWidth()) >> 1;
		mUnlockViewHalfHeight = (mUnLockView.getMeasuredHeight()) >> 1;
		
		mSmsLightView.measure(MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED), MeasureSpec
				.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		mLightViewHalfWidth = (mSmsLightView.getMeasuredWidth()) >> 1;
		mLightViewHalfHeight = (mSmsLightView.getMeasuredHeight()) >> 1;

	}
	
	//设置高亮图标的布局
	private void setActivatedViewLayout()
	{
		mUnLockLightView.layout(mScreenHalfWidth - mLightViewHalfWidth - 5, 
				(mCenterViewTop + 2 * mCenterViewHeight) - mLightViewHalfHeight, 
				mScreenHalfWidth + mLightViewHalfWidth - 5,
				(mCenterViewBottom + mCenterViewHeight) + mLightViewHalfHeight);
		mSmsLightView.layout((mScreenHalfWidth + 3 * mCenterViewWidth / 2) - 2 * mLightViewHalfWidth,
				(mCenterViewTop + mCenterViewHeight / 2) - mLightViewHalfHeight, 
				(mScreenHalfWidth + 3 * mCenterViewWidth / 2) + 2 * mLightViewHalfWidth,
				(mAlphaViewBottom - mCenterViewHeight / 2) + mLightViewHalfHeight);
		mDialLightView.layout((mScreenHalfWidth - 3 * mCenterViewWidth / 2) - mLightViewHalfWidth, 
				(mCenterViewTop + mCenterViewHeight / 2) - mLightViewHalfHeight, 
				(mScreenHalfWidth - 3 * mCenterViewWidth / 2) + mLightViewHalfWidth, 
				(mAlphaViewBottom - mCenterViewHeight / 2) + mLightViewHalfHeight);
		mCameraLightView.layout(mScreenHalfWidth - mLightViewHalfWidth, 
				(mCenterViewTop - mCenterViewHeight) - mLightViewHalfHeight,
				mScreenHalfWidth + mLightViewHalfWidth,
				(mCenterViewBottom - 2 * mCenterViewHeight) + mLightViewHalfHeight);
	}
	
	//设置各图标在FxLockView中的布局
	private void setChildViewLayout()
	{
		mAlphaView.layout(mScreenHalfWidth - mAlphaViewWidth / 2, mAlphaViewTop,
				mScreenHalfWidth + mAlphaViewWidth / 2, mAlphaViewBottom);
		
		mCenterView.layout(mScreenHalfWidth - mCenterViewWidth / 2, mCenterViewTop,
				mScreenHalfWidth + mCenterViewWidth / 2, mCenterViewBottom);
		
		mSmsView.layout((mScreenHalfWidth + 3 * mCenterViewWidth / 2) - 2 * mSmsViewHalfWidth,
				(mCenterViewTop + mCenterViewHeight / 2) - mSmsViewHalfHeight, 
				(mScreenHalfWidth + 3 * mCenterViewWidth / 2) + 2 * mSmsViewHalfWidth,
				(mAlphaViewBottom - mCenterViewHeight / 2) + mSmsViewHalfHeight);
		
		mDialView.layout((mScreenHalfWidth - 3 * mCenterViewWidth / 2) - mDialViewHalfWidth, 
				(mCenterViewTop + mCenterViewHeight / 2) - mDialViewHalfHeight, 
				(mScreenHalfWidth - 3 * mCenterViewWidth / 2) + mDialViewHalfWidth, 
				(mAlphaViewBottom - mCenterViewHeight / 2) + mDialViewHalfHeight);
		
		mCameraView.layout(mScreenHalfWidth - mCameraViewHalfWidth, 
				(mCenterViewTop - mCenterViewHeight) - mHalfCameraViewHeight,
				mScreenHalfWidth + mCameraViewHalfWidth,
				(mCenterViewBottom - 2 * mCenterViewHeight) + mHalfCameraViewHeight);
		
		mUnLockView.layout(mScreenHalfWidth - mUnlockViewHalfWidth, 
				(mCenterViewTop + 2 * mCenterViewHeight) - mUnlockViewHalfHeight,
				mScreenHalfWidth + mUnlockViewHalfWidth,
				(mCenterViewBottom + mCenterViewHeight) + mUnlockViewHalfHeight);
		
	}
	
	//创建各图标位置对应的Rect
	private void getChildViewRect()
	{
		smsRect = new Rect((mScreenHalfWidth + 3 * mCenterViewWidth / 2) - 2 * mSmsViewHalfWidth,
				(mCenterViewTop + mCenterViewHeight / 2) - mSmsViewHalfHeight, 
				(mScreenHalfWidth + 3 * mCenterViewWidth / 2) + 2 * mSmsViewHalfWidth,
				(mAlphaViewBottom - mCenterViewHeight / 2) + mSmsViewHalfHeight);
			
		dialRect = new Rect((mScreenHalfWidth - 3 * mCenterViewWidth / 2) - mDialViewHalfWidth, 
				(mCenterViewTop + mCenterViewHeight / 2) - mDialViewHalfHeight, 
				(mScreenHalfWidth - 3 * mCenterViewWidth / 2) + mDialViewHalfWidth, 
				(mAlphaViewBottom - mCenterViewHeight / 2) + mDialViewHalfHeight);
		
		cameraRect = new Rect(mScreenHalfWidth - mCameraViewHalfWidth, 
				(mCenterViewTop - mCenterViewHeight) - mHalfCameraViewHeight,
				mScreenHalfWidth + mCameraViewHalfWidth,
				(mCenterViewBottom - 2 * mCenterViewHeight) + mHalfCameraViewHeight);
			
		unlockRect = new Rect(mScreenHalfWidth - mUnlockViewHalfWidth, 
				(mCenterViewTop + 2 * mCenterViewHeight) - mUnlockViewHalfHeight,
				mScreenHalfWidth + mUnlockViewHalfWidth,
				(mCenterViewBottom + mCenterViewHeight) + mUnlockViewHalfHeight);
	}
	
	//获取图标，将获取的图标添加入FxLockView，设置图标的可见性
	private void initViews(Context context) {
//		setBackgroundResource(R.mipmap.iphone5s);
		imgView = new ImageView(context);
		imgView.setImageResource(R.mipmap.iphone5s);
		setViewsLayout1(imgView);
		imgView.setVisibility(View.VISIBLE);

//				View view = LayoutInflater.from(context).inflate(R.layout.activity_main,this);
//		addView(view);
//		ImageView backGround = new ImageView(context);
//		backGround.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
//				LayoutParams.MATCH_PARENT));
//		backGround.setImageResource(R.mipmap.iphone5s);
//		addView(backGround);
//		backGround.setVisibility(View.VISIBLE);
//		StarLockView view = new StarLockView(mContext);
//		view.setBackgroundResource(R.mipmap.iphone5s);


		mAlphaView = new ImageView(context);
		mAlphaView.setImageResource(R.drawable.centure2);
		setViewsLayout(mAlphaView);
		mAlphaView.setVisibility(View.INVISIBLE);

		mCenterView = new ImageView(context);
		mCenterView.setImageResource(R.drawable.centure1);
		setViewsLayout(mCenterView);
		mCenterView.setVisibility(View.VISIBLE);
		
		mSmsView = new ImageView(context);
		mSmsView.setImageResource(R.drawable.sms);
		setViewsLayout(mSmsView);
		mSmsView.setVisibility(View.VISIBLE);
		
		mDialView = new ImageView(context);
		mDialView.setImageResource(R.drawable.dial);
		setViewsLayout(mDialView);
		mDialView.setVisibility(View.VISIBLE);

		mCameraView = new ImageView(context);
		mCameraView.setImageResource(R.drawable.camera);
		setViewsLayout(mCameraView);
		mCameraView.setVisibility(View.VISIBLE);
	
		mUnLockView = new ImageView(context);
		mUnLockView.setImageResource(R.drawable.home);
		setViewsLayout(mUnLockView);
		mUnLockView.setVisibility(View.VISIBLE);

		
		mSmsLightView= new ImageView(context);
		setLightDrawable(mSmsLightView);
		setViewsLayout(mSmsLightView);
		mSmsLightView.setVisibility(INVISIBLE);
		
		mUnLockLightView = new ImageView(context);
		setLightDrawable(mUnLockLightView);
		setViewsLayout(mUnLockLightView);
		mUnLockLightView.setVisibility(INVISIBLE);
		
		mCameraLightView = new ImageView(context);
		setLightDrawable(mCameraLightView);
		setViewsLayout(mCameraLightView);
		mCameraLightView.setVisibility(INVISIBLE);
		
		mDialLightView = new ImageView(context);
		setLightDrawable(mDialLightView);
		setViewsLayout(mDialLightView);
		mDialLightView.setVisibility(INVISIBLE);
	}

	private void setLightDrawable(ImageView img)
	{
		img.setImageResource(R.drawable.light);
	}
	
	//设置获取图标的参数，并添加到FxLockView
	private void setViewsLayout(ImageView image) {
		image.setScaleType(ScaleType.CENTER);
		image.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		addView(image);
	}

	private void setViewsLayout1(ImageView image) {
		image.setScaleType(ScaleType.CENTER);
		image.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		addView(image);
	}

	//设置音乐播放控制按钮的点击触摸反馈背景
	private void setMusicButtonBackground(ImageView musicIcon)
	{
		musicIcon.setBackgroundResource(R.drawable.music_button_bg);
		musicIcon.setVisibility(View.INVISIBLE);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		final int action = ev.getAction();
		final float x = ev.getX();
		final float y = ev.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			//手指点在中心图标范围区域内
			if (mCenterViewRect.contains((int) x, (int) y)) 
			{
				mTracking = true;
				//stopViewAnimation();
				onAnimationEnd();
				mAlphaView.setVisibility(View.INVISIBLE);
				return true;
			} 
//			else if(MusicInfo.isMusic())
//			{
//				//为音乐控制按钮绑定点击监听
//				setMusicViewsOnClick();
//			}
			break;

		default:
			break;
		}
		if(DBG) Log.d(TAG, "onInterceptTouchEvent()");
		//此处返回false，onClick事件才能监听的到
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/*mTracking为true时，说明中心图标被点击移动
		 * 即只有在中心图标被点击移动的情况下，onTouchEvent
		 * 事件才会触发。
		 */
		if (mTracking)
		{
			final int action = event.getAction();
			final float nx = event.getX();
			final float ny = event.getY();

			switch (action) {
			case MotionEvent.ACTION_DOWN:
				showMusicButtons();
				break;
			case MotionEvent.ACTION_MOVE:
			     setTargetViewVisible(nx, ny);
				 //中心图标移动
				 handleMoveView(nx, ny);
				break;
			case MotionEvent.ACTION_UP:
				 mTracking = false;
				 doTriggerEvent(mx, my);
				 resetMoveView();
				break;
			case MotionEvent.ACTION_CANCEL:
				 mTracking = false;
				 doTriggerEvent(mx, my);
				 resetMoveView();
				break;
			}
		}
		if(DBG) Log.d(TAG, "onTouchEvent()");
		return mTracking || super.onTouchEvent(event);
	}

	//双击中心图标显示音乐控制按钮
	private void showMusicButtons()
	{
		Intent intent = new Intent(SHOW_MUSIC);
		count ++;
		if(count == 1)
		{
			firstClick = System.currentTimeMillis();
		}
		else if (count == 2)
		{
			secondClick = System.currentTimeMillis();
			if (secondClick - firstClick < 500)
			{
				//双击监听事件
				//Toast.makeText(mContext, "click twice",  Toast.LENGTH_LONG).show();
				//setTargetViewInvisible();
				if(DBG) Log.d(TAG, "count--->" + count);
//				if(DBG) Log.d(TAG, "isMusic--->" + MusicInfo.isMusic());
//				if (MusicInfo.isMusic())
//				{
//					setMusicButtonVisibility(false);
//					setTargetViewVisible();
//				}
//				else
//				{
//					setMusicButtonVisibility(true);
//				}
//				intent.putExtra("music_show", MusicInfo.isMusic());
//				mContext.sendBroadcast(intent);
			}				
			count = 0;
			firstClick = 0;
			secondClick = 0;
		}
	}
	
	//平方和计算
	private float dist2(float dx, float dy)
	{
		return dx * dx + dy * dy;
	}
	
	private void handleMoveView(float x, float y)
	{
		
		int mHalfCenterViewWidth = mCenterViewWidth >> 1;
			
		//Radius为中心图标移动的限定的圆范围区域半径
		int Radius = mCenterViewWidth + mHalfCenterViewWidth;
		
		/*若用户手指移动的点与中心点的距离长度大于Radius，则中心图标坐标位置限定在移动区域范围圆弧上。
		 * 一般是用户拖动中心图标，手指移动到限定圆范围区域外。
		 */
		if (Math.sqrt(dist2(x - mScreenHalfWidth, y - (mCenterView.getTop() + mCenterViewWidth / 2)
				)) > Radius)		
		{
			//原理为x1 / x = r1 / r
			x = (float) ((Radius / (Math.sqrt(dist2(x - mScreenHalfWidth, y - (mCenterView.getTop() + mHalfCenterViewWidth)
			)))) * (x - mScreenHalfWidth) + mScreenHalfWidth);
			
			y = (float) ((Radius / (Math.sqrt(dist2(x - mScreenHalfWidth, y - (mCenterView.getTop() + mHalfCenterViewWidth)
			)))) * (y - (mCenterView.getTop() + mHalfCenterViewWidth)) + mCenterView.getTop() + mHalfCenterViewWidth);
		}
		
		mx = x;
		my = y;
		/*图形的坐标是以左上角为基准的，
		 * 所以，为了使手指所在的坐标和图标的中心位置一致，
		 * 中心坐标要减去宽度和高度的一半。
		 */
		mCenterView.setX((int)x - mCenterView.getWidth()/2);
		mCenterView.setY((int)y - mCenterView.getHeight()/2);
		ShowLightView(x, y);
	    invalidate();
	}
	
	//监听解锁、启动拨号、相机、短信应用
	private void doTriggerEvent(float a, float b)
	{
		if (smsRect.contains((int)a, (int) b))
		{
			//stopViewAnimation();
			onAnimationEnd();
//			setTargetViewInvisible(mSmsView);
			virbate();
			//发送消息到MainActivity类中的mHandler出来
//			mainHandler.obtainMessage(MainActivity.MSG_LAUNCH_SMS).sendToTarget();
		}
		else if (dialRect.contains((int)a , (int)b))
		{
			onAnimationEnd();
//			setTargetViewInvisible(mDialView);
			virbate();
//			mainHandler.obtainMessage(MainActivity.MSG_LAUNCH_DIAL).sendToTarget();
		}
		else if (cameraRect.contains((int)a, (int)b))
		{
			onAnimationEnd();
//			setTargetViewInvisible(mCameraView);
			virbate();
//			mainHandler.obtainMessage(MainActivity.MSG_LAUNCH_CAMERA).sendToTarget();
		}
		else if (unlockRect.contains((int)a, (int)b))
		{
			onAnimationEnd();
//			setTargetViewInvisible(mUnLockView);
			virbate();
//			mainHandler.obtainMessage(MainActivity.MSG_LAUNCH_HOME).sendToTarget();
		}
	}
	
	//中心图标拖动到指定区域时显示高亮图标
	private void ShowLightView(float a, float b)
	{
		if (unlockRect.contains((int)a, (int)b))
		{
			setLightVisible(mUnLockLightView);
		}
		else if (smsRect.contains((int)a, (int) b))
		{
			setLightVisible(mSmsLightView);
		}
		else if (dialRect.contains((int)a , (int)b))
		{
			setLightVisible(mDialLightView);
		}
		else if (cameraRect.contains((int)a, (int)b))
		{
			setLightVisible(mCameraLightView);
		}
		else
		{
			setLightInvisible();
		}
	}
	
	private void setLightVisible(ImageView view)
	{
		view.setVisibility(View.VISIBLE);
        mCenterView.setVisibility(View.INVISIBLE);
	}
	
	//隐藏高亮图标
	private void setLightInvisible()
	{
		final View mActivatedViews[] = {mUnLockLightView, mSmsLightView, 
				mDialLightView, mCameraLightView};
		for (View view : mActivatedViews)
		{
			view.setVisibility(View.INVISIBLE);
		}

        mCenterView.setVisibility(View.VISIBLE);
	}
	
	private void setTargetViewInvisible(ImageView img)
	{
		img.setVisibility(View.INVISIBLE);	
	}
	
	private void setTargetViewVisible(float x, float y)
	{
		if(Math.sqrt(dist2(x - mScreenHalfWidth, y - (mCenterView.getTop() + mCenterViewWidth / 2)
		)) > mAlphaViewHeight / 4)
		{
//		    if (MusicInfo.isMusic())
//		    {
//		        setMusicButtonVisibility(false);
//		    }
//		    else
//		    {
//		        return;
//		    }
		}
	}

	//重置中心图标，回到原位置
	private void resetMoveView()
	{
		mCenterView.setX(mWidth / 2 - mCenterViewWidth /2);
		mCenterView.setY((mCenterView.getTop() + mCenterViewHeight / 2) - mCenterViewHeight / 2);
		onAnimationStart();
//		if (MusicInfo.isMusic())
//		{
//			setMusicButtonVisibility(true);
//		}
		invalidate();
	}
	
	public void setMainHandler(Handler handler)
	{
		mainHandler = handler;
	}
	
	//解锁时震动
	private void virbate()
	{
		Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(200);
		Intent i = new Intent(mContext, Service1.class);
		i.setAction(Service1.UNLOCK_ACTION);
		mContext.startService(i);
	}
	
	private void setViewId()
	{
//		if(DBG) Log.d(TAG, "setViewId()");
//		mPlayView.setId(0);
//		mNextView.setId(1);
//		mPrevView.setId(2);
//		mStopView.setId(3);
	}
	
	//音乐播放控制按钮点击事件监听
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		
		Intent intent = new Intent();
		intent.setAction(SERVICECMD);
	
		

	}

	//停止中心图标动画
	@Override
	protected void onAnimationEnd() {
		// TODO Auto-generated method stub
		super.onAnimationEnd();
		if (alpha != null)
		{
			alpha = null;
		}
		mAlphaView.setAnimation(null);
	}

	//显示中心图标动画
	@Override
	protected void onAnimationStart() {
		// TODO Auto-generated method stub
		super.onAnimationStart();
		mAlphaView.setVisibility(View.VISIBLE);

		if (alpha == null) {
			alpha = new AlphaAnimation(0.0f, 1.0f);
			alpha.setDuration(1000);
		}
		alpha.setRepeatCount(Animation.INFINITE);
		mAlphaView.startAnimation(alpha);
	}
	
}