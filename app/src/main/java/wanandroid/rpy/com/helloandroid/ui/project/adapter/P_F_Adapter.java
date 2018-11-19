package wanandroid.rpy.com.helloandroid.ui.project.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import wanandroid.rpy.com.helloandroid.model.bean.ProjectBean;
import wanandroid.rpy.com.helloandroid.ui.project.fragment.TabFragment;

public class P_F_Adapter extends FragmentPagerAdapter {
    List<ProjectBean.DataBean> beans=new ArrayList<>();

    public P_F_Adapter(FragmentManager fm, List<ProjectBean.DataBean> beans) {
        super(fm);
        this.beans = beans;
    }

    @Override
    public Fragment getItem(int i) {
        TabFragment fragment=new TabFragment();
        fragment.setId(beans.get(i).getId());

        return fragment;
    }

    @Override
    public int getCount() {
        return beans.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return beans.get(position).getName();
    }
}
