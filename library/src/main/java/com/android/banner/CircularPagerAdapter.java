package com.android.banner;


import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

public class CircularPagerAdapter extends PagerAdapter {

    private int fakeSize;
    private int[] bannerList;
    private Map<Integer, View> mMap;
    private IndicatorView mIndicatorView;

    private LayoutInflater mLayoutInflater;

    public void setData(int[] list) {
        mMap.clear();
        bannerList = list;
        int realSize = list.length;  // 真实的Banner数量
        fakeSize = realSize + 2;

        if (mIndicatorView != null) {
            mIndicatorView.refreshView();
        }
        this.notifyDataSetChanged();
    }

    public CircularPagerAdapter(final ViewPager pager, Activity mActivity, final IndicatorView indicatorView) {
        super();
        mLayoutInflater = LayoutInflater.from(mActivity);
        bannerList = new int[1];
        mMap = new HashMap<Integer, View>();
        this.mIndicatorView = indicatorView;
        if (indicatorView != null) {
            indicatorView.setViewPagerInfo(pager, true);
        }
        pager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (indicatorView != null) {
                    int realCount = fakeSize - 2;
                    int realPosition = (position - 1) % realCount;
                    if (realPosition < 0) {
                        realPosition += realCount;
                    }
                    indicatorView.selectPage(realPosition);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    int pageCount = getCount();
                    int currentItem = pager.getCurrentItem();
                    if (currentItem == 0) {
                        pager.setCurrentItem(pageCount - 2, false);
                    } else if (currentItem == pageCount - 1) {
                        pager.setCurrentItem(1, false);
                    }
                }
            }
        });
    }

    @Override
    public int getCount() {
        return fakeSize == 3 ? 1 : fakeSize;
    }

    @Override
    public Object instantiateItem(View container, int position) {
        int realCount = fakeSize - 2;

        int realPosition = (position - 1) % realCount;
        if (realPosition < 0) {
            realPosition += realCount;
        }
        final int pos = realPosition;

        View view = null;
        if (mMap.containsKey(position)) {
            view = mMap.get(position);
        } else {
            final int banner = bannerList[pos];
            if (banner != 0) {
                view = mLayoutInflater.inflate(R.layout.banner_item, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.img);
                imageView.setImageResource(banner);
                mMap.put(position, view);
            }
        }

        try {
            ((ViewPager) container).addView(view);
        } catch (Exception e) {
            // destroyItem中不进行移除，所以这里需要捕获异常
        }
        return view;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
