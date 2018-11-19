package wanandroid.rpy.com.helloandroid.ui.home.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import wanandroid.rpy.com.helloandroid.MainActivity;
import wanandroid.rpy.com.helloandroid.R;
import wanandroid.rpy.com.helloandroid.WebActivity;
import wanandroid.rpy.com.helloandroid.model.bean.BannerBean;
import wanandroid.rpy.com.helloandroid.model.bean.HomeWorks;
import wanandroid.rpy.com.helloandroid.net.CommonOkHttpListener;
import wanandroid.rpy.com.helloandroid.ui.home.adapter.WorksAdapter;
import wanandroid.rpy.com.helloandroid.ui.home.biz.HomeBiz;
import wanandroid.rpy.com.helloandroid.ui.home.img.BannerImgLoader;
import wanandroid.rpy.com.helloandroid.until.Config;

public class HomeFragment extends Fragment {
    @BindView(R.id.recyclerview_home)
    RecyclerView recyclerviewHome;
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;

    Unbinder unbinder;
    WorksAdapter adapter;
    List<HomeWorks.DataBean.DatasBean> homeWorks = new ArrayList<>();
    List<String> imgPath = new ArrayList<>();
    List<String> titleStr = new ArrayList<>();
    private static int page = 0;


    Banner banner;

    private View bannerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        bannerView = inflater.inflate(R.layout.banner_home, container, false);
        banner=bannerView.findViewById(R.id.banner);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new WorksAdapter(R.layout.item_homepage, homeWorks);
        adapter.openLoadAnimation();
        recyclerviewHome.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerviewHome.setAdapter(adapter);
        smartrefreshlayout.autoRefresh();
        getBannerData();
        setUpLoadData();
        setDownLoadData();
        initBanner();
    }

    /**
     * 初始化Banner
     */
    private void initBanner() {
        banner.setImageLoader(new BannerImgLoader());
        banner.setDelayTime(3000);
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        banner.setBannerAnimation(Transformer.BackgroundToForeground);
        banner.isAutoPlay(true);
        banner.setIndicatorGravity(BannerConfig.CENTER);

    }


    /**
     * 上拉刷新数据
     */
    private void setUpLoadData() {
        smartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                homeWorks.clear();
                getHomeWorksData(page);
            }
        });
    }

    /**
     * 下拉加载数据
     */
    private void setDownLoadData() {
        smartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getHomeWorksData(page);
            }
        });
    }


    /**
     * 获取首页文章
     */
    public void getHomeWorksData(int code) {
        HomeBiz.getHomeWorkData(code ,null, new CommonOkHttpListener<HomeWorks>() {
            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                smartrefreshlayout.finishRefresh();
                smartrefreshlayout.finishLoadMore();
            }

            @Override
            public void onResponse(HomeWorks data) {
                List<HomeWorks.DataBean.DatasBean> datas = data.getData().getDatas();
                if(datas.size()==0){
                    Toast.makeText(getContext(), "我可是有底线的！", Toast.LENGTH_SHORT).show();
                }else {
                    homeWorks.addAll(datas);
                    adapter.notifyDataSetChanged();
                }
                smartrefreshlayout.finishRefresh();
                smartrefreshlayout.finishLoadMore();
            }


        });
    }

    /**
     * 加载Banner
     *
     * @return
     */
    public void getBannerData() {
        HomeBiz.getHomeBannerData(null,new CommonOkHttpListener<BannerBean>() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResponse(final BannerBean data) {
                for (BannerBean.DataBean dataBean : data.getData()) {
                    imgPath.add(dataBean.getImagePath());
                    titleStr.add(dataBean.getTitle());
                }
                banner.setImages(imgPath);
                banner.setBannerTitles(titleStr);
                banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Intent intent=new Intent(getContext(), WebActivity.class);
                        intent.putExtra("url",data.getData().get(position).getUrl());
                        getContext().startActivity(intent);
                    }
                });
                banner.start();
                adapter.addHeaderView(bannerView);
            }


        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }
}
