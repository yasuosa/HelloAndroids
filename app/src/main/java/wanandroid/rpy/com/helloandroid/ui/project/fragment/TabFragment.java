package wanandroid.rpy.com.helloandroid.ui.project.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import wanandroid.rpy.com.helloandroid.R;
import wanandroid.rpy.com.helloandroid.WebActivity;
import wanandroid.rpy.com.helloandroid.model.bean.ProjectListBean;
import wanandroid.rpy.com.helloandroid.net.CommonOkHttpListener;
import wanandroid.rpy.com.helloandroid.ui.project.adapter.P_L_Adapter;
import wanandroid.rpy.com.helloandroid.ui.project.biz.ProjectBiz;
import wanandroid.rpy.com.helloandroid.until.RequestParams;

@SuppressLint("ValidFragment")
public class TabFragment extends Fragment {

    
    Unbinder unbinder;
    P_L_Adapter adapter;
    List<ProjectListBean.DataBean.DatasBean> datasBeans = new ArrayList<>();
    @BindView(R.id.recyclerview_project)
    RecyclerView recyclerviewProject;
    @BindView(R.id.smartrefreshlayout_pro)
    SmartRefreshLayout smartrefreshlayoutPro;

    public void setId(int id) {
        this.id = id;
    }

    private int id = 294;
    private int page = 0;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_tag, container, false);
        unbinder = ButterKnife.bind(this, view);
        adapter = new P_L_Adapter(R.layout.item_projectpage,datasBeans);
        adapter.openLoadAnimation();
        recyclerviewProject.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerviewProject.setAdapter(adapter);
        smartrefreshlayoutPro.autoRefresh();
        getUpLoadData();
        getDownMoreData();
        return view;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void getProjectListData(int page) {
        RequestParams params = new RequestParams();
        params.put("cid", String.valueOf(id));
        ProjectBiz.get_Project_List(page, params, new CommonOkHttpListener<ProjectListBean>() {
            @Override
            public void onError(Exception e) {
                smartrefreshlayoutPro.finishRefresh();
            }

            @Override
            public void onResponse(ProjectListBean data) {
                if(data.getData().getDatas().size()==0){
                    Toast.makeText(getContext(), "我可是有底线的!", Toast.LENGTH_SHORT).show();
                    smartrefreshlayoutPro.finishLoadMore();
                }else {
                    datasBeans.addAll(data.getData().getDatas());
                    adapter.notifyDataSetChanged();
                }
            }


        });
    }

    public void getDownMoreData() {
        smartrefreshlayoutPro.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getProjectListData(page);
                smartrefreshlayoutPro.finishLoadMore();
            }
        });
    }

    public void getUpLoadData() {
        smartrefreshlayoutPro.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                datasBeans.clear();
                page = 0;
                getProjectListData(page);
                smartrefreshlayoutPro.finishRefresh();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("BBBB", "onStop: ");
        smartrefreshlayoutPro.finishLoadMore();
    }
}
