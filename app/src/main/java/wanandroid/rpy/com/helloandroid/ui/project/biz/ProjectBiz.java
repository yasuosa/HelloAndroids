package wanandroid.rpy.com.helloandroid.ui.project.biz;

import java.util.Map;

import wanandroid.rpy.com.helloandroid.net.CommonOkHttpListener;
import wanandroid.rpy.com.helloandroid.net.OkHttpClientManger;
import wanandroid.rpy.com.helloandroid.until.Config;
import wanandroid.rpy.com.helloandroid.until.RequestParams;

public class ProjectBiz {
    /**
     * 获取项目的名字
     * @param listener
     */
    public static void get_Project_Name(RequestParams params,CommonOkHttpListener listener){
        OkHttpClientManger.getInstance().get_Asyn(Config.PROJECT,params,listener);
    }

    /**
     * 获取项目的对应文章
     * @param listener
     */
    public static void get_Project_List(int page, RequestParams params,CommonOkHttpListener listener){
        String url=Config.PROJECT_WORKS+page+"/json?";
        OkHttpClientManger.getInstance().get_Asyn(url,params,listener);
    }
}
