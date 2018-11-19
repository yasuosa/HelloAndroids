package wanandroid.rpy.com.helloandroid.ui.home.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import wanandroid.rpy.com.helloandroid.R;
import wanandroid.rpy.com.helloandroid.WebActivity;
import wanandroid.rpy.com.helloandroid.model.bean.HomeWorks;

public class WorksAdapter extends BaseQuickAdapter<HomeWorks.DataBean.DatasBean, BaseViewHolder> {


//    @BindView(R.id.tv_content)
//    TextView tvContent;
//    @BindView(R.id.tv_type)
//    TextView tvType;
//    @BindView(R.id.tv_tag)
//    TextView tvTag;
//    @BindView(R.id.image_collect)
//    ImageView imageCollect;
//    @BindView(R.id.item_card_homepage)
//    CardView itemCardHomepage;

    public WorksAdapter(int layoutResId, @Nullable List<HomeWorks.DataBean.DatasBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, final HomeWorks.DataBean.DatasBean item) {
//        int position=helper.getLayoutPosition();
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
