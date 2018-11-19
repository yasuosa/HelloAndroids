package wanandroid.rpy.com.helloandroid.ui.tixi.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import wanandroid.rpy.com.helloandroid.R;
import wanandroid.rpy.com.helloandroid.WebActivity;
import wanandroid.rpy.com.helloandroid.model.bean.TixiChildBean;

public class TixiChildAdapter extends BaseQuickAdapter<TixiChildBean.DataBean.DatasBean,BaseViewHolder> {


    public TixiChildAdapter(int layoutResId, @Nullable List<TixiChildBean.DataBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final TixiChildBean.DataBean.DatasBean item) {

        //TODO
        helper.setText(R.id.tv_tag, item.getTitle())
                .setText(R.id.tv_content, item.getTitle())
                .setText(R.id.tv_author, item.getAuthor())
                .setText(R.id.tv_time, item.getNiceDate())
                .setText(R.id.tv_type, item.getChapterName());

        helper.setOnClickListener(R.id.item_card_homepage, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, WebActivity.class);
                intent.putExtra("url",item.getLink());
                mContext.startActivity(intent);
            }
        });
    }


}
