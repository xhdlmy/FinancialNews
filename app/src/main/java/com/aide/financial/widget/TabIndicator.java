package com.aide.financial.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aide.financial.FinancialApplication;
import com.aide.financial.R;
import com.aide.financial.util.LogUtils;
import com.aide.financial.util.ScreenUtils;
import com.aide.financial.util.SizeUtils;

import java.util.List;

/**
 * ViewPager 指示器(三角形)
 */

public class TabIndicator extends LinearLayout {

    private static final String TAG = "TabIndicator";

    // 自定义属性
    private int mTabNum;
    private int mTriangleWidth;
    private int mTriangleHeight;
    private int mTriangleCenterPos;
    private int mTriangleOffset;

    private int mBackgroundColor;
    private int mTabIndicatorColor;
    private int mTextSelectedColor;
    private int mTextColor;
    private int mTextSize;
    private int SCREEN_WIDTH = ScreenUtils.getScreenWidth(FinancialApplication.getContext());
    private int DEFAULT_TRIANGLE_WIDTH = SCREEN_WIDTH / COUNT_NUM / 4; // 最大三角形底边宽度 (默认 3 个 Tab，宽度为每个 Tab 的 1/4)
    private static final int COUNT_NUM = 3;
    private int DEFAULT_TEXT_SIZE = 16;

    private Context mContext;
    private Paint mPaint;
    private Path mPath;
    private List<String> mTitles;

    private ViewPager mViewPager;

    public TabIndicator(Context context) {
        this(context, null);
    }

    public TabIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabIndicator);
        try {
            mTabNum = a.getInt(R.styleable.TabIndicator_tab_num, COUNT_NUM);
            mBackgroundColor = a.getColor(R.styleable.TabIndicator_tab_background, getResources().getColor(android.R.color.transparent));
            mTabIndicatorColor = a.getColor(R.styleable.TabIndicator_tab_indicator_color, getResources().getColor(android.R.color.white));
            mTriangleWidth = (int) a.getDimension(R.styleable.TabIndicator_tab_triangle_width, DEFAULT_TRIANGLE_WIDTH);
            mTextSelectedColor = a.getColor(R.styleable.TabIndicator_tab_selected_text_color, getResources().getColor(android.R.color.black));
            mTextColor = a.getColor(R.styleable.TabIndicator_tab_text_color, getResources().getColor(R.color.shadow));
            mTextSize = SizeUtils.px2sp(context, (int) a.getDimension(R.styleable.TabIndicator_tab_text_size, SizeUtils.sp2px(context, DEFAULT_TEXT_SIZE)));
        } finally {
            a.recycle();
        }
        setBackgroundColor(mBackgroundColor);
        mTriangleHeight =  mTriangleWidth / 2 - 8;
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(mTabIndicatorColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private void initTriangle(int width) {
        mTriangleWidth = width;
        if(mTriangleWidth > DEFAULT_TRIANGLE_WIDTH) mTriangleWidth = DEFAULT_TRIANGLE_WIDTH;
        mTriangleHeight =  mTriangleWidth / 2 - 8;
    }

    private void initPath() {
        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
    }

    // 当布局大小发生变化时回调
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        LogUtils.i(TAG, "onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        SCREEN_WIDTH = w;
        initTriangle(mTriangleWidth);
        mTriangleCenterPos = w / mTabNum / 2 - mTriangleWidth / 2;
        initPath();
    }

    // 分发给 子View 重绘
    @Override
    protected void dispatchDraw(Canvas canvas) {
        LogUtils.i(TAG, "dispatchDraw");
        super.dispatchDraw(canvas);
        canvas.translate(mTriangleCenterPos + mTriangleOffset, getHeight());
        canvas.drawPath(mPath, mPaint);
    }

    // 移动三角形指示器
    private void scrollTriangle(int position, float positionOffset) {
        int tabWidth = ScreenUtils.getScreenWidth(mContext) / mTabNum;
        mTriangleOffset = (int) (tabWidth * position + tabWidth * positionOffset);
        invalidate();
    }

    // 设置 Title
    public void setPageTitle(List<String> titles) {
        LogUtils.i(TAG, "setPageTitle");
        this.mTitles = titles;
        if(mTitles == null && mTitles.size() <= 0) return;
        removeAllViews();
        for (int i = 0; i < mTitles.size(); i++) {
            final int pageIndex = i;
            TextView textView = new TextView(getContext());
            LayoutParams layoutParams = new LayoutParams(SCREEN_WIDTH / mTabNum, LayoutParams.WRAP_CONTENT);
            textView.setBackgroundColor(mBackgroundColor);
            textView.setPadding(0,0,0, mTriangleHeight);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(mTitles.get(i));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
            textView.setTextColor(mTextColor);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setHighColor(pageIndex);
                    if(mViewPager != null) mViewPager.setCurrentItem(pageIndex);
                }
            });
            addView(textView, layoutParams);
        }
        setHighColor(0);
    }

    // 设置标题高亮
    private void setHighColor(int pos) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(mTextColor);
            }
        }
        View view = getChildAt(pos);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(mTextSelectedColor);
        }
    }

    // 绑定 ViewPager
    public void setupWithViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scrollTriangle(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                setHighColor(position);
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}
