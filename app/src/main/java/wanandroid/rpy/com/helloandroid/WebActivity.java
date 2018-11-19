package wanandroid.rpy.com.helloandroid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import wanandroid.rpy.com.helloandroid.TX_X5.X5WebView;

/**
 * @author geqipeng
 * @date 2018/1/18
 */

public class WebActivity extends BaseActivity {

    private static String mHomeUrl = null;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private X5WebView mX5WebView;
    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);

        initHardwareAccelerate();
        initView();
        if (getIntent() != null) {
            Intent intent = getIntent();
            String url = intent.getStringExtra("url");
            if (!TextUtils.isEmpty(url)) {
                mHomeUrl = url;
                mX5WebView.loadUrl(mHomeUrl);
                mX5WebView.setWebChromeClient(new WebChromeClient() {
                    @Override
                    public void onProgressChanged(WebView webView, int i) {
                        if (i == 100) {
                            progressBar.setVisibility(View.GONE);
                        } else {
                            progressBar.setVisibility(View.VISIBLE);
                            progressBar.setProgress(i);

                        }
                    }
                });
            } else {
                textView.setVisibility(View.VISIBLE);
            }
        }


    }

    /**
     * 启用硬件加速
     */
    private void initHardwareAccelerate() {
        try {
            if (Integer.parseInt(Build.VERSION.SDK) >= 11) {
                getWindow()
                        .setFlags(
                                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }
    }

    private void initView() {
        mX5WebView = findViewById(R.id.x5_webview);
        textView = findViewById(R.id.tv);
        textView.setVisibility(View.GONE);
    }

    /**
     * 返回键监听
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mX5WebView != null && mX5WebView.canGoBack()) {
                mX5WebView.goBack();
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        //释放资源
        if (mX5WebView != null)
            mX5WebView.destroy();
        super.onDestroy();
    }
}
