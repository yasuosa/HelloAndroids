package wanandroid.rpy.com.helloandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

public class SlpashActivity extends Activity {

    String[] dm =new String[]{
            "安卓--从入门到弃坑",
            "我的头发呢?",
            "论杀死进程的各种方法",
            "你程序有bug? 傻叉",
            "debug 申请出战",
            "我就这样飘过",
            "加班，加班，加班",
            "没文化真可怕",
            "不被媳妇吐槽的程序员不是好码农",
            "我也想要女朋友啊",
            "在杭州的姑娘读书你给我听好了",
            "我喜欢你",
            "4s进去程序,小老弟"
    };

    String[] col=new String[]{
          "#FFf9382a",
          "#11BBFF",
          "#FF4081",
          "#3ddd62",
          "#EEF8FC",
          "#ffcc49",
    };
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    @BindView(R.id.danmaku_view)
    DanmakuView danmakuView;

    private boolean showDanmaku=true;
    private DanmakuContext danmakuContext;
    private BaseDanmakuParser parser=new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slpash);
        ButterKnife.bind(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SlpashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);

        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                generateSomeDanmaku();
                danmakuView.start();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        danmakuContext=DanmakuContext.create();
        danmakuView.prepare(parser,danmakuContext);
    }

    private void generateSomeDanmaku() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(showDanmaku) {
                    int id = new Random().nextInt(13);
                    int size=new Random().nextInt(48);
                    int index=new Random().nextInt(6);
                    String color=col[index];
                    String content =dm[id];
                    addDanmaku(content, false,size,color);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    /**
     * 向弹幕View中添加一条弹幕
     * @param content
     *          弹幕的具体内容
     * @param  withBorder
     *          弹幕是否有边框
     */
    private void addDanmaku(String content, boolean withBorder,int size,String color) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = sp2px(size);
        danmaku.textColor = Color.parseColor(color);
        danmaku.setTime(danmakuView.getCurrentTime());
        if (withBorder) {
            danmaku.borderColor = Color.GREEN;
        }
        danmakuView.addDanmaku(danmaku);

    }


    /**
     * sp转px的方法。
     */
    public int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    @Override
    protected void onStop() {
        super.onStop();
        showDanmaku=false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showDanmaku=false;
        if (danmakuView != null) {
            danmakuView.release();
            danmakuView = null;
        }
    }
}
