package wanandroid.rpy.com.helloandroid.ui.project.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import wanandroid.rpy.com.helloandroid.R;
import wanandroid.rpy.com.helloandroid.WebActivity;
import wanandroid.rpy.com.helloandroid.model.bean.HomeWorks;
import wanandroid.rpy.com.helloandroid.model.bean.ProjectListBean;

public class P_L_Adapter extends BaseQuickAdapter<ProjectListBean.DataBean.DatasBean,BaseViewHolder> {


    public P_L_Adapter(int layoutResId, @Nullable List<ProjectListBean.DataBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ProjectListBean.DataBean.DatasBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_content, item.getTitle())
                .setText(R.id.tv_author, item.getAuthor())
                .setText(R.id.tv_time, item.getNiceDate());

        ImageView imageView=helper.getView(R.id.image_simple);
        Picasso.get().load(item.getEnvelopePic()).error(R.drawable.black_background).into(imageView);

        helper.setOnClickListener(R.id.item_card_projectpage, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, WebActivity.class);
                intent.putExtra("url",item.getLink());
                mContext.startActivity(intent);
            }
        });
    }
}
