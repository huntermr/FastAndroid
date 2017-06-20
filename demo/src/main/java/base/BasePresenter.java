package demo.base;


import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cn.tbl.android.R;
import demo.app.ServiceManager;
import demo.rx.JsonResponseFunc;
import demo.utils.MyStringUtils;
import demo.vo.JsonResponse;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Presenter基类
 *
 * @author Hunter
 */
public abstract class BasePresenter {

    public BasePresenter() {
        initService();
    }

    protected abstract void initService();

    public <T> T getService(Class<T> clazz) {
        ServiceManager serviceManager = ServiceManager.getInstance();
        return serviceManager.getService(clazz);
    }

    /**
     * 转换响应实体的类型
     *
     * @param observable
     * @param <T>
     * @return
     */
    public <T> Observable<T> convertResponse(Observable<JsonResponse<T>> observable) {
        return observable.map(new JsonResponseFunc<T>());
    }

    /**
     * 绑定请求和响应
     *
     * @param view
     * @param observable
     * @param subscriber
     * @param <T>
     */
    public <T> void subscribe(IBaseView view, Observable<T> observable, Subscriber<T> subscriber) {
        observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view.<T>bind())
                .subscribe(subscriber);
    }

    /**
     * 转换请求实体类
     *
     * @param object
     * @return
     */
    public Map<String, String> converParams(Object object) {
        if (object == null) return Collections.emptyMap();

        Class clazz = object.getClass();
        Class superclass = clazz.getSuperclass();

        Field[] fields = clazz.getDeclaredFields();
        Field[] superFields = superclass.getDeclaredFields();

        if (fields == null || fields.length == 0) {
            return Collections.emptyMap();
        }

        Map<String, String> params = new HashMap<String, String>();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if(field.get(object) != null){
                    params.put(field.getName(), String.valueOf(field.get(object)));
                }
            }

            for (Field superField : superFields) {
                superField.setAccessible(true);
                if(superField.get(object) != null) {
                    params.put(superField.getName(), String.valueOf(superField.get(object)));
                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return params;
    }

    /**
     * 校验字符串是否为空
     * @param baseView
     * @param content   需要校验的内容
     * @param strRes    为空时的提示语
     * @return
     */
    public boolean isEmpty(IBaseView baseView, String content, int strRes) {
        if (TextUtils.isEmpty(content)) {
            baseView.showToast(strRes);
            return true;
        }

        return false;
    }

    /**
     * 校验手机号是否有效
     * @param baseview
     * @param mobile
     * @return
     */
    public boolean isMobile(IBaseView baseview, String mobile){
        if(!MyStringUtils.checkCellphone(mobile)){
            baseview.showToast(R.string.mobile_valid);
            return false;
        }

        return true;
    }

    /**
     * 校验字符串是否为空
     * @param baseView
     * @param content   需要校验的内容
     * @param str       为空时的提示语
     * @return
     */
    public boolean isEmpty(IBaseView baseView, String content, String str) {
        if (TextUtils.isEmpty(content)) {
            baseView.showToast(str);
            return true;
        }

        return false;
    }

}
