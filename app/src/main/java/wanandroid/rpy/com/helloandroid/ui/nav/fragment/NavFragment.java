package wanandroid.rpy.com.helloandroid.ui.nav.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import wanandroid.rpy.com.helloandroid.R;
import wanandroid.rpy.com.helloandroid.WebActivity;
import wanandroid.rpy.com.helloandroid.model.bean.NavBean;
import wanandroid.rpy.com.helloandroid.net.CommonOkHttpListener;
import wanandroid.rpy.com.helloandroid.ui.nav.biz.NavBiz;

public class NavFragment extends Fragment {
    @BindView(R.id.tagFlowlayout)
    TagFlowLayout tagFlowlayout;
    Unbinder unbinder;

    List<NavBean.DataBean> beans = new ArrayList<>();
    List<String> navName = new ArrayList<>();
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        smartrefreshlayout.autoRefresh();
        getData();

        tagFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                return true;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void getData() {
        NavBiz.getNav(null, new CommonOkHttpListener<NavBean>() {
            @Override
            public void onError(Exception e) {
            }

            @Override
            public void onResponse(final NavBean data) {
                beans.clear();
                beans.addAll(data.getData());
                for (int di = 0; di < data.getData().size(); di++) {
                    for (int i = 0; i < data.getData().get(di).getArticles().size(); i++) {
                        navName.add(data.getData().get(di).getArticles().get(i).getTitle());
                    }
                }
                tagFlowlayout.setAdapter(new TagAdapter<String>(navName) {
                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        int a = new Random().nextInt(255);
                        int b = new Random().nextInt(255);
                        int c = new Random().nextInt(255);
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.tixi_tv_style, parent, false);
                        tv.setTextColor(Color.rgb(a, b, c));
                        tv.setText(s);
                        return tv;
                    }
                });

                tagFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        Toast.makeText(getContext(), "点击好难", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                smartrefreshlayout.finishLoadMore();
            }


        });
    }
}
