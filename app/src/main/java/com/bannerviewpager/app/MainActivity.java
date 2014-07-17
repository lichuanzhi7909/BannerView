package com.bannerviewpager.app;

import android.app.Activity;
import android.os.Bundle;

import com.android.banner.BannerViewPager;
import com.android.banner.CircularPagerAdapter;
import com.android.banner.IndicatorView;


public class MainActivity extends Activity {

    private CircularPagerAdapter mBannerAdapter;
    private BannerViewPager mBannerViewPager;
    private IndicatorView mBannerIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBannerViewPager = (BannerViewPager) findViewById(R.id.product_detail_image_viewpager);
        mBannerIndicatorView = (IndicatorView) findViewById(R.id.banner_indicator);

        mBannerAdapter = new CircularPagerAdapter(mBannerViewPager, this, mBannerIndicatorView);
        mBannerViewPager.setAdapter(mBannerAdapter);
        mBannerAdapter.setData(new int[]{R.drawable.img, R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4});
        mBannerViewPager.setCurrentItem(1, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        mBannerViewPager.startLoopingBanner();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBannerViewPager.stopLoopingBanner();
    }
}
