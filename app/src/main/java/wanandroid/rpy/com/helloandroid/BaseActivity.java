package wanandroid.rpy.com.helloandroid;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#11BBFF"));
        ActivityCompat.requestPermissions(this,new String[]{
                "android.permission.WRITE_EXTERNAL_STORAGE",
                        "android.permission.ACCESS_NETWORK_STATE" ,
                        "android.permission.ACCESS_WIFI_STATE" ,
                        "android.permission.INTERNET",
                        "android.permission.READ_PHONE_STATE"
        },0);
    }



}
