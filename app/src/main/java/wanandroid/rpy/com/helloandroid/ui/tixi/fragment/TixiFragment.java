package wanandroid.rpy.com.helloandroid.ui.tixi.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import wanandroid.rpy.com.helloandroid.R;
import wanandroid.rpy.com.helloandroid.model.bean.TixiNavBean;
import wanandroid.rpy.com.helloandroid.net.CommonOkHttpListener;
import wanandroid.rpy.com.helloandroid.ui.tixi.adapter.TixiAdapter;
import wanandroid.rpy.com.helloandroid.ui.tixi.biz.TixiBiz;
import wanandroid.rpy.com.helloandroid.until.Config;


public class TixiFragment extends Fragment {
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;
    @BindView(R.id.recyclerview_tixi)
    RecyclerView recyclerviewTixi;
    Unbinder unbinder;

    private TixiAdapter adapter;
    private List<TixiNavBean.DataBean> dataBeans=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tixi, container, false);
        unbinder = ButterKnife.bind(this, view);
        adapter=new TixiAdapter(R.layout.item_tixi,dataBeans);
        smartrefreshlayout.autoRefresh();
        recyclerviewTixi.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerviewTixi.setAdapter(adapter);

        smartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if(dataBeans.size()==0){
                    getTixiNav();
                }else{
                    smartrefreshlayout.finishRefresh();
                }

            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void getTixiNav() {
        TixiBiz.getTixi_Nav(null,new CommonOkHttpListener<TixiNavBean>() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResponse(TixiNavBean data) {
                dataBeans.clear();
                dataBeans.addAll(data.getData());
                adapter.notifyDataSetChanged();
                smartrefreshlayout.finishRefresh();
            }


        });
    }
}
