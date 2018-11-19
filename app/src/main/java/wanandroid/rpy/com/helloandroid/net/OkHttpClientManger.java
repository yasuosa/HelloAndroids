package wanandroid.rpy.com.helloandroid.net;



import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import wanandroid.rpy.com.helloandroid.model.bean.HomeWorks;
import wanandroid.rpy.com.helloandroid.until.GsonUntil;
import wanandroid.rpy.com.helloandroid.until.RequestParams;

public class OkHttpClientManger {
    private static OkHttpClientManger mInstance;
    private OkHttpClient mOkHttpClient;
    private static Handler mHandler;
    private static Gson mGson;


    private OkHttpClientManger () {
        mOkHttpClient=new OkHttpClient();
        mHandler=new Handler(Looper.getMainLooper());
        mGson= GsonUntil.getGson();
    }

    public static OkHttpClientManger getInstance(){
        if(mInstance==null){
            synchronized (OkHttpClientManger.class) {
                return new OkHttpClientManger();
            }
        }
        return mInstance;
    }

    /**
     * 同步自己写
     * 懒得写了
     */

    /**
     * 异步get请求
     * @param url
     * @param commonOkHttpListener
     */
    public  void get_Asyn(String url,RequestParams params, CommonOkHttpListener commonOkHttpListener){
        Request request;
        if(params!=null){
            StringBuffer sb=new StringBuffer(url);
            for (Map.Entry<String,String> entry:params.urlParams.entrySet()){
                sb.append(entry.getKey()).append("=").append(entry.getValue());
            }
            request=new Request.Builder().url(sb.substring(0,sb.length())).build();
        }else{
            request=new Request.Builder().url(url).build();
        }
        deliveryResult(request,commonOkHttpListener);
    }



    /**
     * 异步post请求
     * @param url
     * @param commonOkHttpListener
     * @param params
     */
    public void post_Asyn(String url, CommonOkHttpListener commonOkHttpListener, RequestParams params){
        if(params!=null){
            FormBody.Builder mFromBodyBuilder=new FormBody.Builder();
            for (Map.Entry<String,String> entry:params.urlParams.entrySet()){
                mFromBodyBuilder.add(entry.getKey(),entry.getValue());
            }
            FormBody formBody=mFromBodyBuilder.build();
            Request request=new Request.Builder().url(url).post(formBody).post(formBody).build();
            deliveryResult(request,commonOkHttpListener);
        }
    }


    public void sendSuccess(final Object o, final CommonOkHttpListener listener){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onResponse(o);
            }
        });
    }


    public void sendFail(final Exception e, final CommonOkHttpListener listener){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onError(e);
            }
        });
    }


    private void deliveryResult(Request request, final CommonOkHttpListener commonOkHttpListener) {
        Call call =mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFail(e,commonOkHttpListener);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data=response.body().string();
                if(commonOkHttpListener.mtype==String.class){
                    sendSuccess(data,commonOkHttpListener);
                }else {
                    Object o=mGson.fromJson(data,commonOkHttpListener.mtype);
                    System.out.println(o.toString()+"AAA");
                    sendSuccess(o,commonOkHttpListener);
                }
            }
        });

    }
}
