package com.zhangyp.higo.dropupmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zhangyp.higo.dropupmenu.adapter.SettingFragmentStatePagerAdapter;
import com.zhangyp.higo.dropupmenu.bean.SettingItem;
import com.zhangyp.higo.dropupmenu.fragment.BookSettingFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements BookSettingFragment.OnFragmentInteractionListener, View.OnClickListener {

    @Bind(R.id.ll_point)
    LinearLayout llPoint;
    @Bind(R.id.bt_share)
    Button btShare;
    @Bind(R.id.vp_up_menu)
    ViewPager vpUpMenu;
    @Bind(R.id.fl_up_menu)
    FrameLayout flUpMenu;
    @Bind(R.id.view_cover)
    View viewCover;

    private int coverColor = 0x88888888;
    private int prePosition;
    //item的个数
    private int num = 22;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //设置覆盖层颜色
        viewCover.setBackgroundColor(coverColor);

        initBookSetting();

        viewCover.setOnClickListener(this);
        btShare.setOnClickListener(this);
    }


    private void initBookSetting() {

        ArrayList<SettingItem> list = new ArrayList<SettingItem>();
        for (int i = 0; i < num; i++) {
            SettingItem item = new SettingItem();
            item.setName("QQ-" + i);
            item.setIcon(R.mipmap.book_qq);
            list.add(item);
        }


        fragments = new ArrayList<Fragment>();
        //分页逻辑
        final int page = (int) (list.size() / 8);
        int yu = list.size() % 8;
        initPoint(page, yu);
        llPoint.getChildAt(0).setBackgroundResource(R.drawable.ok_point);


        for (int i = 0; i < page; i++) {
            ArrayList<SettingItem> datas = new ArrayList<SettingItem>();
            for (int j = 8 * i; j < 8 * (i + 1); j++) {
                datas.add(list.get(j));
            }
            fragments.add(BookSettingFragment.newInstance(datas));

        }
        if (yu != 0) {
            ArrayList<SettingItem> datas = new ArrayList<SettingItem>();
            for (int j = 8 * page; j < 8 * page + list.size() % 8; j++) {
                datas.add(list.get(j));
            }
            fragments.add(BookSettingFragment.newInstance(datas));
        }

        SettingFragmentStatePagerAdapter settingAdapter = new SettingFragmentStatePagerAdapter(getSupportFragmentManager(), fragments);
        vpUpMenu.setAdapter(settingAdapter);
        settingAdapter.notifyDataSetChanged();


        vpUpMenu.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                llPoint.getChildAt(position).setBackgroundResource(R.drawable.ok_point);
                llPoint.getChildAt(prePosition).setBackgroundResource(R.drawable.no_point);

                prePosition = position;
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private float getDensity() {
        return getResources().getDisplayMetrics().density;
    }


    private void initPoint(int page, int yu) {
        if (yu != 0) {
            page = page + 1;
        }
        for (int i = 0; i < page; i++) {
            View view = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (10 * getDensity()), (int) (10 * getDensity()));
            params.leftMargin = (int) (10 * getDensity());
            params.bottomMargin = (int) (10 * getDensity());
            params.gravity = Gravity.CENTER_HORIZONTAL;
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.no_point);
            llPoint.addView(view);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_share:

                //显示menu
                show();
                break;
            case R.id.view_cover:
                hide();
                break;
        }
    }

    @Override
    public void onFragmentInteraction() {
        //隐藏menu
        hide();

    }

    private void show() {

        btShare.setClickable(false);
        flUpMenu.setVisibility(View.VISIBLE);
        flUpMenu.setAnimation(AnimationUtils.loadAnimation(this, R.anim.up_menu_in));
        viewCover.setVisibility(View.VISIBLE);
        viewCover.setAnimation(AnimationUtils.loadAnimation(this, R.anim.cover_in));

    }

    private void hide() {

        btShare.setClickable(true);
        flUpMenu.setVisibility(View.GONE);
        flUpMenu.setAnimation(AnimationUtils.loadAnimation(this, R.anim.up_menu_out));
        viewCover.setVisibility(View.GONE);
        viewCover.setAnimation(AnimationUtils.loadAnimation(this, R.anim.cover_out));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hide();
    }
}
