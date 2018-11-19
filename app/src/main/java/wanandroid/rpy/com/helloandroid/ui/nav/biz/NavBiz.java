package wanandroid.rpy.com.helloandroid.ui.nav.biz;

import wanandroid.rpy.com.helloandroid.net.CommonOkHttpListener;
import wanandroid.rpy.com.helloandroid.net.OkHttpClientManger;
import wanandroid.rpy.com.helloandroid.until.Config;
import wanandroid.rpy.com.helloandroid.until.RequestParams;

public class NavBiz  {
    public static void getNav(RequestParams params, CommonOkHttpListener listener){
        OkHttpClientManger.getInstance().get_Asyn(Config.Nav,params,listener);
    }
}
