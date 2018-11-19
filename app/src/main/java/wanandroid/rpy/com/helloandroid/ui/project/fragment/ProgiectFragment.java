package wanandroid.rpy.com.helloandroid.ui.project.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import wanandroid.rpy.com.helloandroid.R;
import wanandroid.rpy.com.helloandroid.WebActivity;
import wanandroid.rpy.com.helloandroid.model.bean.ProjectBean;
import wanandroid.rpy.com.helloandroid.net.CommonOkHttpListener;
import wanandroid.rpy.com.helloandroid.ui.project.adapter.P_F_Adapter;
import wanandroid.rpy.com.helloandroid.ui.project.biz.ProjectBiz;
import wanandroid.rpy.com.helloandroid.until.Config;

public class ProgiectFragment extends Fragment {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    Unbinder unbinder;
    P_F_Adapter adapter;
    List<ProjectBean.DataBean> dataBeans=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        unbinder = ButterKnife.bind(this, view);
        initTab();
        initViewPage();
        getTagName();
        return view;
    }



    private void initViewPage() {
        adapter=new P_F_Adapter(getChildFragmentManager(),dataBeans);
        viewPager.setAdapter(adapter);
    }

    private void initTab() {
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //tab居中显示
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        //tab的字体选择器,默认黑色,选择时红色
        tabLayout.setTabTextColors(Color.GRAY, Color.parseColor("#44a7d4"));
        //tab的下划线颜色,默认是粉红色
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#44a7d4"));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void getTagName() {
        ProjectBiz.get_Project_Name(null,new CommonOkHttpListener<ProjectBean>() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResponse(ProjectBean data) {
                for (int i = 0; i < data.getData().size(); i++) {
                    tabLayout.addTab(tabLayout.newTab().setText(data.getData().get(i).getName()));
                }
                dataBeans.addAll(data.getData());
                adapter.notifyDataSetChanged();
            }
        });
    }
}
