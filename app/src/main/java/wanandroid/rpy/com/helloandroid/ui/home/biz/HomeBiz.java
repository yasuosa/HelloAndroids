package wanandroid.rpy.com.helloandroid.ui.home.biz;



import wanandroid.rpy.com.helloandroid.net.CommonOkHttpListener;
import wanandroid.rpy.com.helloandroid.net.OkHttpClientManger;
import wanandroid.rpy.com.helloandroid.until.Config;
import wanandroid.rpy.com.helloandroid.until.RequestParams;

public class HomeBiz {
    /**
     * 获得首页轮播
     * @param commonOkHttpListener
     */
    public static  void  getHomeBannerData(RequestParams params,CommonOkHttpListener commonOkHttpListener){
        OkHttpClientManger.getInstance().get_Asyn(Config.BANNNER,params,commonOkHttpListener);
    }


    /**
     * 获得首页文章
     * @param commonOkHttpListener
     */
    public static void getHomeWorkData(int code,RequestParams params,CommonOkHttpListener commonOkHttpListener){
        OkHttpClientManger.getInstance().get_Asyn(Config.HOME_WORKS+code+"/json",params,commonOkHttpListener);
    }

}
