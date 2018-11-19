package wanandroid.rpy.com.helloandroid.ui.tixi.biz;

import wanandroid.rpy.com.helloandroid.net.CommonOkHttpListener;
import wanandroid.rpy.com.helloandroid.net.OkHttpClientManger;
import wanandroid.rpy.com.helloandroid.until.Config;
import wanandroid.rpy.com.helloandroid.until.RequestParams;

public class TixiBiz {
    /**
     * 获取Tixi的目录栏
     * @param listener
     */
    public static void getTixi_Nav(RequestParams params,CommonOkHttpListener listener){
        OkHttpClientManger.getInstance().get_Asyn(Config.TIXI,params,listener);
    }

    public static void getTixi_Child(int page,RequestParams params,CommonOkHttpListener listener){
        String url=Config.TIXI_WORKS+page+"/json?";
        OkHttpClientManger.getInstance().get_Asyn(url,params,listener);
    }
}
