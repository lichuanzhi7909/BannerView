package com.android.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * Indicator
 *
 * @author chuanzhiL
 */
public class IndicatorView extends LinearLayout implements OnPageChangeListener {
    private ViewPager mViewPager;
    private Context context;
    private int mSelectedIndicator = R.drawable.indicator_yellow;
    private int mUnSelectedIndicator = R.drawable.indicator_white;

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        this.context = context;
        setOrientation(LinearLayout.HORIZONTAL);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView, defStyle, 0);
        mSelectedIndicator = a.getResourceId(R.styleable.IndicatorView_selected_indicator, mSelectedIndicator);
        mUnSelectedIndicator = a.getResourceId(R.styleable.IndicatorView_unSelected_indicator, mUnSelectedIndicator);
    }

    public void setViewPagerInfo(ViewPager viewPager, boolean isCircularPagerAdapter) {
        this.mViewPager = viewPager;
        if (!isCircularPagerAdapter) {
            mViewPager.setOnPageChangeListener(this);
        }
        setChildInfo();
    }

    public void refreshView() {
        setChildInfo();
    }

    public void setViewPagerInfo(ViewPager viewPager) {
        setViewPagerInfo(viewPager, false);
    }

    private void setChildInfo() {
        this.removeAllViews();
        PagerAdapter mAdapter = mViewPager.getAdapter();
        int pagerCount = 0;
        if (mAdapter != null) {
            if (mAdapter instanceof CircularPagerAdapter) {
                int count = mAdapter.getCount();
                pagerCount = mAdapter.getCount() - (count == 1 ? 0 : 2);
            } else {
                pagerCount = mAdapter.getCount();
            }
        }
        if (mViewPager != null && pagerCount > 1) {
            LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, this.getResources().getDisplayMetrics());
            for (int i = 0; i < pagerCount; i++) {
                ImageView imgView = new ImageView(context);
                imgView.setLayoutParams(params);
                this.addView(imgView);
            }
            selectPage(0);
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int position) {
        selectPage(position);
    }

    public void selectPage(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            if (this.getChildAt(i) instanceof ImageView) {
                if (position == i) {
                    this.getChildAt(i).setBackgroundResource(mSelectedIndicator);
                } else {
                    this.getChildAt(i).setBackgroundResource(mUnSelectedIndicator);
                }
            }
        }
    }
}
