package com.hunter.fastandroid.app;

import com.hunter.fastandroid.utils.CommonUtils;

import java.io.IOException;
import java.util.TreeSet;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit Service管理器
 *
 * @author Hunter
 */
public class ServiceManager {
    private static ServiceManager instance;

    private ServiceManager() {

    }

    public static ServiceManager getInstance() {
        if (instance == null) {
            instance = new ServiceManager();
        }

        return instance;
    }

    public <T> T getService(Class<T> t) {
        return getService(URLs.BASE_URL, t);
    }

    public <T> T getService(String baseUrl, Class<T> t) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();

                        HttpUrl url = request.url();
                        TreeSet<String> sortParams = new TreeSet<>(url.queryParameterNames());
                        StringBuilder stringBuilder = new StringBuilder();

                        for(String param : sortParams){
                            String paramValue = url.queryParameter(param);
                            stringBuilder.append(param + ":" + paramValue + "&");
                        }

                        String sign = CommonUtils.EncryptMD5(stringBuilder.toString() + "abc123");

                        Request build = request.newBuilder()
                                .addHeader("appVersion", "")
                                .addHeader("uid", "")
                                .addHeader("time", "")
                                .addHeader("sign", sign)
                                .build();

                        return chain.proceed(build);
                    }
                });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(t);
    }
}
