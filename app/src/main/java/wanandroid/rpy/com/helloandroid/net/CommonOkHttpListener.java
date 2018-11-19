package wanandroid.rpy.com.helloandroid.net;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract  class CommonOkHttpListener<T> {

    Type mtype;
    public CommonOkHttpListener() {
        mtype=getType(getClass());
    }

    static Type getType(Class<?> subclass) {
        Type type=subclass.getGenericSuperclass();
        if(type instanceof Class){
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterizedType= (ParameterizedType) type;
        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
    }

    public abstract void onError(Exception e);
    public abstract void onResponse(T data);
}
