package wanandroid.rpy.com.helloandroid.ui.tixi.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wanandroid.rpy.com.helloandroid.BaseActivity;
import wanandroid.rpy.com.helloandroid.R;
import wanandroid.rpy.com.helloandroid.model.bean.TixiChildBean;
import wanandroid.rpy.com.helloandroid.net.CommonOkHttpListener;
import wanandroid.rpy.com.helloandroid.ui.tixi.adapter.TixiChildAdapter;
import wanandroid.rpy.com.helloandroid.ui.tixi.biz.TixiBiz;
import wanandroid.rpy.com.helloandroid.until.RequestParams;

public class TiXIActivity extends BaseActivity {


    @BindView(R.id.recyclerview_tixi)
    RecyclerView recyclerviewTixi;
    @BindView(R.id.smartrefreshlayout_tixi)
    SmartRefreshLayout smartrefreshlayoutTixi;
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    int id;
    int page = 0;

    TixiChildAdapter adapter;

    List<TixiChildBean.DataBean.DatasBean> dataBeans = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ti_xi);
        ButterKnife.bind(this);
        getUpLoadData();
        getDownMoreData();
        initRecy();
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra("id", 60);
            toolBar.setTitle(intent.getStringExtra("name"));
            toolBar.setTitleTextColor(Color.WHITE);
            toolBar.setBackgroundColor(Color.parseColor("#11BBFF"));
            toolBar.setNavigationIcon(R.mipmap.back);
            toolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            smartrefreshlayoutTixi.autoRefresh();
            adapter.notifyDataSetChanged();
        }

    }

    private void initRecy() {
        adapter = new TixiChildAdapter(R.layout.item_homepage, dataBeans);
        adapter.openLoadAnimation();
        recyclerviewTixi.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewTixi.setAdapter(adapter);

    }

    /**
     * 上拉刷新
     */

    public void getUpLoadData() {
        smartrefreshlayoutTixi.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                dataBeans.clear();
                page = 0;
                getTixiWorks(page);
            }
        });
    }

    /**
     * 下拉加载
     */

    private void getDownMoreData() {
        smartrefreshlayoutTixi.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getTixiWorks(page);
            }
        });
    }

    private void getTixiWorks(int page) {
        RequestParams params = new RequestParams();
        params.put("cid", id + "");
        TixiBiz.getTixi_Child(page, params, new CommonOkHttpListener<TixiChildBean>() {
            @Override
            public void onError(Exception e) {
                smartrefreshlayoutTixi.finishRefresh();
                smartrefreshlayoutTixi.finishLoadMore();
            }

            @Override
            public void onResponse(TixiChildBean data) {
                //TODO
                dataBeans.addAll(data.getData().getDatas());
                adapter.notifyDataSetChanged();
                smartrefreshlayoutTixi.finishRefresh();
                smartrefreshlayoutTixi.finishLoadMore();
            }

        });
    }
}
