package wanandroid.rpy.com.helloandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wanandroid.rpy.com.helloandroid.ui.home.fragment.HomeFragment;
import wanandroid.rpy.com.helloandroid.ui.my.fragment.MyFragment;
import wanandroid.rpy.com.helloandroid.ui.nav.fragment.NavFragment;
import wanandroid.rpy.com.helloandroid.ui.project.fragment.ProgiectFragment;
import wanandroid.rpy.com.helloandroid.ui.project.fragment.TabFragment;
import wanandroid.rpy.com.helloandroid.ui.tixi.fragment.TixiFragment;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rgp_home)
    RadioButton rgpHome;
    @BindView(R.id.rgp_tixi)
    RadioButton rgpTixi;
    @BindView(R.id.rgp_nav)
    RadioButton rgpNav;
    @BindView(R.id.rgp_project)
    RadioButton rgpProject;
    @BindView(R.id.fragment)
    FrameLayout fragment;

    private Fragment commentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        showFragment("home");

    }

    @OnClick({R.id.rgp_home, R.id.rgp_tixi, R.id.rgp_nav, R.id.rgp_project,R.id.rgp_my})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rgp_home:
                showFragment("home");
                break;
            case R.id.rgp_tixi:
                showFragment("tixi");
                break;
            case R.id.rgp_nav:
                showFragment("nav");
                break;
            case R.id.rgp_project:
                showFragment("project");
                break;
            case R.id.rgp_my:
                showFragment("my");
                break;
        }
    }

    public void showFragment(String tag) {
        if (commentFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(commentFragment).commit();
        }
        commentFragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (commentFragment == null) {
            switch (tag) {
                case "home":
                    commentFragment = new HomeFragment();
                    break;
                case "tixi":
                    commentFragment=new TixiFragment();
                    break;
                case "nav":
                    commentFragment=new NavFragment();
                    break;
                case "project":
                    commentFragment=new ProgiectFragment();
                    break;
                case "my":
                    commentFragment=new MyFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.fragment,commentFragment,tag).commit();
        }else {
            getSupportFragmentManager().beginTransaction().show(commentFragment).commit();
        }
    }

    long time=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if (System.currentTimeMillis()-time>2000){
                Toast.makeText(this, "在按一次退出!", Toast.LENGTH_SHORT).show();
                time=System.currentTimeMillis();
            }else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
