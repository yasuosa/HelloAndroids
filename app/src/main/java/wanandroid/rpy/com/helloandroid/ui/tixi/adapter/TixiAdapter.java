package wanandroid.rpy.com.helloandroid.ui.tixi.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import wanandroid.rpy.com.helloandroid.R;
import wanandroid.rpy.com.helloandroid.WebActivity;
import wanandroid.rpy.com.helloandroid.model.bean.TixiNavBean;
import wanandroid.rpy.com.helloandroid.ui.tixi.activity.TiXIActivity;

public class TixiAdapter extends BaseQuickAdapter<TixiNavBean.DataBean,BaseViewHolder> {

    public TixiAdapter(int layoutResId, @Nullable List<TixiNavBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final TixiNavBean.DataBean item) {
        helper.setText(R.id.tixi_name,item.getName());
        TagFlowLayout tagFlowLayout=helper.getView(R.id.id_flowlayout);
        final List<String> msgName = new ArrayList<>();
        for (int i = 0; i < item.getChildren().size(); i++) {
            msgName.add(item.getChildren().get(i).getName());
        }
        tagFlowLayout.setAdapter(new TagAdapter<String>(msgName){
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                int a= new Random().nextInt(255);
                int b= new Random().nextInt(255);
                int c= new Random().nextInt(255);
                TextView tv= (TextView) LayoutInflater.from(mContext).inflate(R.layout.tixi_tv_style,parent,false);
                tv.setTextColor(Color.rgb(a,b,c));
                tv.setText(msgName.get(position));
                return tv;
            }

        });

        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Intent intent=new Intent(mContext, TiXIActivity.class);
                int id = item.getChildren().get(position).getId();
                intent.putExtra("id", id);
                intent.putExtra("name",item.getChildren().get(position).getName());
                mContext.startActivity(intent);
                return true;
            }
        });


    }
}
