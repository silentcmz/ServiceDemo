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

public class StarLockView extends RelativeLayout{

    private static final boolean DBG = true;
    private Context mContext;
    private float mx;
    private float my;

    private int mWidth, mHight;
    private int mScreenHalfWidth;
//    private int mAlphaViewWidth, mAlphaViewHeight;

    private int mCenterViewWidth, mCenterViewHeight;
    private int mCenterViewTop, mCenterViewBottom;
//    private int mAlphaViewTop, mAlphaViewBottom;

    private int mSmsViewHalfWidth, mSmsViewHalfHeight;
    private int mDialViewHalfWidth, mDialViewHalfHeight;
    private int mLeftViewHalfWidth, mRightViewHalfHeight;
//    private int mLightViewHalfWidth, mLightViewHalfHeight;

    private ImageView mSmsView, mDialView;
    private ImageView mLeftView, mRightView;
    private ImageView mCenterView;
//    private ImageView mSmsLightView,
//            mDialLightView;


    private Rect smsRect, dialRect,rightRect,leftRect;
    private Rect mCenterViewRect;

    private AlphaAnimation alpha;
    private boolean mTracking = false;

    private static final String TAG = "FxLockView";

    public StarLockView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        mContext = context;
        if (DBG) Log.d(TAG, "FxLockView2");
        initViews(context);
//        onAnimationStart();
    }

    public StarLockView(Context context) {
        super(context);
        mContext = context;
        if (DBG) Log.d(TAG, "FxLockView2");
        initViews(context);
//        onAnimationStart();
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
            mCenterViewTop = 9 * mHight / 10 - (mCenterViewHeight >> 1);
            mCenterViewBottom = 9 * mHight / 10 + (mCenterViewHeight >> 1);
//            mAlphaViewTop = 4 * mHight / 7 - (mAlphaViewHeight >> 1);
//            mAlphaViewBottom = 4 * mHight / 7 + (mAlphaViewHeight >> 1);

            setChildViewLayout();
//            setActivatedViewLayout();
            getChildViewRect();

            mCenterViewRect = new Rect(mWidth / 2 - mCenterViewWidth / 2, mCenterViewTop,
                    mWidth / 2 + mCenterViewWidth / 2, mCenterViewBottom);

        }

        if (DBG) Log.d(TAG, "l-->" + l);
        if (DBG) Log.d(TAG, "t-->" + t);
        if (DBG) Log.d(TAG, "r-->" + r);
        if (DBG) Log.d(TAG, "b-->" + b);
    }

    //获取各个图标的宽、高
    private void getViewMeasure() {
//        mAlphaView.measure(MeasureSpec.makeMeasureSpec(0,
//                MeasureSpec.UNSPECIFIED), MeasureSpec
//                .makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//        mAlphaViewWidth = mAlphaView.getMeasuredWidth();
//        mAlphaViewHeight = mAlphaView.getMeasuredHeight();

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

        mRightView.measure(MeasureSpec.makeMeasureSpec(0,
                MeasureSpec.UNSPECIFIED), MeasureSpec
                .makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        mSmsViewHalfWidth = (mRightView.getMeasuredWidth()) >> 1;
        mSmsViewHalfHeight = (mRightView.getMeasuredHeight()) >> 1;

        mLeftView.measure(MeasureSpec.makeMeasureSpec(0,
                MeasureSpec.UNSPECIFIED), MeasureSpec
                .makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        mDialViewHalfWidth = (mLeftView.getMeasuredWidth()) >> 1;
        mDialViewHalfHeight = (mLeftView.getMeasuredHeight()) >> 1;

//        mSmsLightView.measure(MeasureSpec.makeMeasureSpec(0,
//                MeasureSpec.UNSPECIFIED), MeasureSpec
//                .makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//        mLightViewHalfWidth = (mSmsLightView.getMeasuredWidth()) >> 1;
//        mLightViewHalfHeight = (mSmsLightView.getMeasuredHeight()) >> 1;

    }

    //设置各图标在FxLockView中的布局
    private void setChildViewLayout() {

        mCenterView.layout(mScreenHalfWidth - mCenterViewWidth / 2, mCenterViewTop,
                mScreenHalfWidth + mCenterViewWidth / 2, mCenterViewBottom);

        mSmsView.layout((mScreenHalfWidth + 4 * mCenterViewWidth / 2) - 2 * mSmsViewHalfWidth,
                (mCenterViewTop + mCenterViewHeight / 2) - mSmsViewHalfHeight,
                (mScreenHalfWidth + 4 * mCenterViewWidth / 2) + 2 * mSmsViewHalfWidth,
                (mCenterViewBottom - mCenterViewHeight / 2) + mSmsViewHalfHeight);

        mDialView.layout((mScreenHalfWidth - 4 * mCenterViewWidth / 2) - mDialViewHalfWidth,
                (mCenterViewTop + mCenterViewHeight / 2) - mDialViewHalfHeight,
                (mScreenHalfWidth - 4 * mCenterViewWidth / 2) + mDialViewHalfWidth,
                (mCenterViewBottom - mCenterViewHeight / 2) + mDialViewHalfHeight);

        mRightView.layout((mScreenHalfWidth + 2 * mCenterViewWidth / 2) - 2 * mSmsViewHalfWidth,
                (mCenterViewTop + mCenterViewHeight / 2) - mSmsViewHalfHeight,
                (mScreenHalfWidth + 2 * mCenterViewWidth / 2) + 2 * mSmsViewHalfWidth,
                (mCenterViewBottom - mCenterViewHeight / 2) + mSmsViewHalfHeight);

        mLeftView.layout((mScreenHalfWidth - 2 * mCenterViewWidth / 2) - mDialViewHalfWidth,
                (mCenterViewTop + mCenterViewHeight / 2) - mDialViewHalfHeight,
                (mScreenHalfWidth - 2 * mCenterViewWidth / 2) + mDialViewHalfWidth,
                (mCenterViewBottom - mCenterViewHeight / 2) + mDialViewHalfHeight);

    }

    //创建各图标位置对应的Rect
    private void getChildViewRect() {
        smsRect = new Rect((mScreenHalfWidth + 4 * mCenterViewWidth / 2) - 2 * mSmsViewHalfWidth,
                (mCenterViewTop + mCenterViewHeight / 2) - mSmsViewHalfHeight,
                (mScreenHalfWidth + 4 * mCenterViewWidth / 2) + 2 * mSmsViewHalfWidth,
                (mCenterViewBottom - mCenterViewHeight / 2) + mSmsViewHalfHeight);

        dialRect = new Rect((mScreenHalfWidth - 4 * mCenterViewWidth / 2) - mDialViewHalfWidth,
                (mCenterViewTop + mCenterViewHeight / 2) - mDialViewHalfHeight,
                (mScreenHalfWidth - 4 * mCenterViewWidth / 2) + mDialViewHalfWidth,
                (mCenterViewBottom - mCenterViewHeight / 2) + mDialViewHalfHeight);

        leftRect = new Rect((mScreenHalfWidth - 2 * mCenterViewWidth / 2) - mDialViewHalfWidth,
                (mCenterViewTop + mCenterViewHeight / 2) - mDialViewHalfHeight,
                (mScreenHalfWidth - 2 * mCenterViewWidth / 2) + mDialViewHalfWidth,
                (mCenterViewBottom - mCenterViewHeight / 2) + mDialViewHalfHeight);

        rightRect = new Rect((mScreenHalfWidth + 2 * mCenterViewWidth / 2) - 2 * mSmsViewHalfWidth,
                (mCenterViewTop + mCenterViewHeight / 2) - mSmsViewHalfHeight,
                (mScreenHalfWidth + 2 * mCenterViewWidth / 2) + 2 * mSmsViewHalfWidth,
                (mCenterViewBottom - mCenterViewHeight / 2) + mSmsViewHalfHeight);

    }

    //获取图标，将获取的图标添加入FxLockView，设置图标的可见性
    private void initViews(Context context) {

//        mAlphaView = new ImageView(context);
//        mAlphaView.setImageResource(R.drawable.centure2);
//        setViewsLayout(mAlphaView);
//        mAlphaView.setVisibility(View.INVISIBLE);

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

        mLeftView = new ImageView(context);
        mLeftView.setImageResource(R.drawable.home);
        setViewsLayout(mLeftView);
        mLeftView.setVisibility(View.INVISIBLE);

        mRightView = new ImageView(context);
        mRightView.setImageResource(R.drawable.home);
        setViewsLayout(mRightView);
        mRightView.setVisibility(View.INVISIBLE);

//        mSmsLightView = new ImageView(context);
//        setLightDrawable(mSmsLightView);
//        setViewsLayout(mSmsLightView);
//        mSmsLightView.setVisibility(INVISIBLE);
//
//        mDialLightView = new ImageView(context);
//        setLightDrawable(mDialLightView);
//        setViewsLayout(mDialLightView);
//        mDialLightView.setVisibility(INVISIBLE);
    }

    private void setLightDrawable(ImageView img) {
        img.setImageResource(R.drawable.light);
    }

    //设置获取图标的参数，并添加到FxLockView
    private void setViewsLayout(ImageView image) {
        image.setScaleType(ScaleType.CENTER);
        image.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        addView(image);
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
                if (mCenterViewRect.contains((int) x, (int) y)) {
                    mTracking = true;
                    //stopViewAnimation();
//                    onAnimationEnd();
//                    mAlphaView.setVisibility(View.INVISIBLE);
                    return true;
                }
                break;

            default:
                break;
        }
        if (DBG) Log.d(TAG, "onInterceptTouchEvent()");
        //此处返回false，onClick事件才能监听的到
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*mTracking为true时，说明中心图标被点击移动
         * 即只有在中心图标被点击移动的情况下，onTouchEvent
		 * 事件才会触发。
		 */
        if (mTracking) {
            final int action = event.getAction();
            final float nx = event.getX();
            final float ny = event.getY();

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mLeftView.setVisibility(View.VISIBLE);
                    mRightView.setVisibility(View.VISIBLE);
                    mCenterView.setImageResource(R.drawable.centure2);
                    break;
                case MotionEvent.ACTION_MOVE:
//                    setTargetViewVisible(nx, ny);
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
        if (DBG) Log.d(TAG, "onTouchEvent()");
        return mTracking || super.onTouchEvent(event);
    }

    //平方和计算
    private float dist2(float dx, float dy) {
        return dx * dx + dy * dy;
    }

    private void handleMoveView(float x, float y) {

        int mHalfCenterViewWidth = mCenterViewWidth >> 1;

        //Radius为中心图标移动的限定的圆范围区域半径
        int Radius = mCenterViewWidth;

		/*若用户手指移动的点与中心点的距离长度大于Radius，则中心图标坐标位置限定在移动区域范围圆弧上。
		 * 一般是用户拖动中心图标，手指移动到限定圆范围区域外。
		 */
        if (Math.sqrt(dist2(x - mScreenHalfWidth, y - (mCenterView.getTop() + mCenterViewWidth / 2)
        )) > Radius) {
            //原理为x1 / x = r1 / r
            x = (float) ((Radius / (Math.sqrt(dist2(x - mScreenHalfWidth, y - (mCenterView.getTop() + mHalfCenterViewWidth)
            )))) * (x - mScreenHalfWidth) + mScreenHalfWidth);

            y = (float) ((Radius / (Math.sqrt(dist2(x - mScreenHalfWidth, y - (mCenterView.getTop() + mHalfCenterViewWidth)
            )))) * (y - (mCenterView.getTop() + mHalfCenterViewWidth)) + mCenterView.getTop() + mHalfCenterViewWidth);
        }

        mx = x;
        my = mCenterViewTop + mCenterView.getMeasuredHeight() / 2;
		/*图形的坐标是以左上角为基准的，
		 * 所以，为了使手指所在的坐标和图标的中心位置一致，
		 * 中心坐标要减去宽度和高度的一半。
		 */
        mCenterView.setX((int) x - mCenterView.getWidth() / 2);
        mCenterView.setY(mCenterViewTop);
        ShowLightView(x, mCenterViewTop + mCenterView.getMeasuredHeight() / 2);
        invalidate();
    }

    //监听解锁、启动拨号、相机、短信应用
    private void doTriggerEvent(float a, float b) {
        if (rightRect.contains((int) a, (int) b)) {
//            onAnimationEnd();
            virbate(2);
        } else if (leftRect.contains((int) a, (int) b)) {
//            onAnimationEnd();
            virbate(1);
        }
    }

    //中心图标拖动到指定区域时显示高亮图标
    private void ShowLightView(float a, float b) {
        if(rightRect.contains((int) a, (int) b)){
            setActionRight();
        }else if(leftRect.contains((int) a, (int) b)){
            setActionLeft();
        }else {
            setActionDown();
        }
    }

    /**
     * 中心图标 按下 所有控件状态
     */
    private void setActionDown(){
        mCenterView.setVisibility(View.VISIBLE);
        mCenterView.setImageResource(R.drawable.centure2);
        mLeftView.setVisibility(View.VISIBLE);
        mRightView.setVisibility(View.VISIBLE);
        mSmsView.setImageResource(R.drawable.play);
        mDialView.setImageResource(R.drawable.pause);

    }

    /**
     * 中心图标 向右滑动到箭头位置 控件状态
     */
    private void setActionRight(){
        mLeftView.setVisibility(View.INVISIBLE);
        mRightView.setVisibility(View.INVISIBLE);
        mSmsView.setImageResource(R.drawable.home);
        mCenterView.setVisibility(View.INVISIBLE);
    }

    /**
     * 中心图标 向左滑动到箭头位置 控件状态
     */
    private void setActionLeft(){
        mLeftView.setVisibility(View.INVISIBLE);
        mRightView.setVisibility(View.INVISIBLE);
        mDialView.setImageResource(R.drawable.home);
        mCenterView.setVisibility(View.INVISIBLE);
    }

    //重置中心图标，回到原位置
    private void resetMoveView() {
        mLeftView.setVisibility(View.INVISIBLE);
        mRightView.setVisibility(View.INVISIBLE);
        mSmsView.setImageResource(R.drawable.sms);
        mDialView.setImageResource(R.drawable.dial);
        mCenterView.setImageResource(R.drawable.centure1);
        mCenterView.setX(mWidth / 2 - mCenterViewWidth / 2);
        mCenterView.setY((mCenterView.getTop() + mCenterViewHeight / 2) - mCenterViewHeight / 2);
//        onAnimationStart();
        invalidate();
    }

    //解锁时震动
    private void virbate(int i) {
        Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
        Intent intent = new Intent("wo.shi.jie.suo");
        intent.putExtra("type",i + "");
        mContext.sendBroadcast(intent);
//        Intent i = new Intent(mContext, Service1.class);
//        i.setAction(Service1.UNLOCK_ACTION);
//        mContext.startService(i);
    }

//    //停止中心图标动画
//    @Override
//    protected void onAnimationEnd() {
//        // TODO Auto-generated method stub
//        super.onAnimationEnd();
//        if (alpha != null) {
//            alpha = null;
//        }
//        mAlphaView.setAnimation(null);
//    }
//
//    //显示中心图标动画
//    @Override
//    protected void onAnimationStart() {
//        // TODO Auto-generated method stub
//        super.onAnimationStart();
//        mAlphaView.setVisibility(View.VISIBLE);
//
//        if (alpha == null) {
//            alpha = new AlphaAnimation(0.0f, 1.0f);
//            alpha.setDuration(1000);
//        }
//        alpha.setRepeatCount(Animation.INFINITE);
//        mAlphaView.startAnimation(alpha);
//    }

}
