package com.baidu.news.engine;

import com.baidu.news.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetWork {
    private static Retrofit retrofit;

    /**返回Retrofit*/
    public static Retrofit getRetrofit(){
        if(retrofit==null){
            OkHttpClient httpClient;
            OkHttpClient.Builder builder=new OkHttpClient.Builder();

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(logging);
            }
            httpClient=builder.build();
            //创建Retrfit构建器
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://v.juhe.cn/")
                    //返回的数据通过Gson解析
                    .addConverterFactory(GsonConverterFactory.create())
                    //使用RxJava模式
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }
    public static Api createApi() {
        return NetWork.getRetrofit().create(Api.class);
    }
}
