package demo.app;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit Service管理器
 *
 * @author Hunter
 */
public class ServiceManager {
    private Retrofit retrofit;
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
        if(retrofit == null){
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(Constants.HTTP_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .readTimeout(Constants.HTTP_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .writeTimeout(Constants.HTTP_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            Request.Builder newBuilder = request.newBuilder();

                            UserManager userManager = UserManager.getInstance();
                            if(userManager.isLogin()){
                                newBuilder.addHeader(Constants.TOKEN_KEY, userManager.getCurrentUserToken())
                                        .addHeader(Constants.UID_KEY, userManager.getCurrentUserID());
                            }

                            return chain.proceed(newBuilder.build());
                        }
                    })
                    .addInterceptor(httpLoggingInterceptor);

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }

        return retrofit.create(t);
    }
}
