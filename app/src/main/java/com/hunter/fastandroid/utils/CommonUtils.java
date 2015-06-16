package com.hunter.fastandroid.utils;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.qiniu.android.utils.UrlSafeBase64;
import com.hunter.fastandroid.R;
import com.hunter.fastandroid.app.MyApplication;
import com.hunter.fastandroid.bean.response.Login;

/**
 * 通用工具类
 *
 * @author Ht
 */
public class CommonUtils {

    public static final Gson GSON = new Gson();

    public static SimpleDateFormat formatDate = new SimpleDateFormat(
            "yyyy-MM-dd");
    public static SimpleDateFormat formatDateTime = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    public static void hideSoftKeybord(Activity activity) {

        if (null == activity) {
            return;
        }
        try {
            final View v = activity.getWindow().peekDecorView();
            if (v != null && v.getWindowToken() != null) {
                InputMethodManager imm = (InputMethodManager) activity
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (Exception e) {

        }
    }

    /**
     * 验证json合法性
     *
     * @param jsonContent
     * @return
     */
    public static boolean isJsonFormat(String jsonContent) {
        try {
            new JsonParser().parse(jsonContent);
            return true;
        } catch (JsonParseException e) {
            LogUtils.i("bad json: " + jsonContent);
            return false;
        }
    }

    /**
     * 抖动动画
     *
     * @param context
     * @param view
     */
    public static void startShakeAnim(Context context, View view) {
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        view.startAnimation(shake);
    }

    /**
     * 获取应用版本号
     *
     * @param context
     * @return
     */
    public static String softVersion(Context context) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return info.versionName;
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return 年月日
     */
    public static String formatDate(Date date) {
        return formatDate.format(date);
    }

    /**
     * 解析日期
     *
     * @param timeInMillis
     * @return
     */
    public static String parseDate(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        Date date = calendar.getTime();
        return formatDate(date);
    }

    /**
     * 解析日期
     *
     * @param date
     * @return
     */
    public static Date parseDate(String date) {
        Date mDate = null;
        try {
            mDate = formatDate.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return mDate;
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return 年月日 时分秒
     */
    public static String formatDateTime(Date date) {
        return formatDateTime.format(date);
    }

    /**
     * 解析日期
     *
     * @param timeInMillis
     * @return
     */
    public static String parseDateTime(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        Date date = calendar.getTime();
        return formatDateTime(date);
    }

    /**
     * 解析日期
     *
     * @param datetime
     * @return
     */
    public static Date parseDateTime(String datetime) {
        Date mDate = null;
        try {
            mDate = formatDateTime.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return mDate;
    }

    /**
     * 对指定字符串进行md5加密
     *
     * @param s
     * @return 加密后的数据
     */
    public final static String getMD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断email格式是否正确
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 获取登陆状态
     *
     * @return
     */
    public static boolean isLogin() {
        Login currentUser = MyApplication.getCurrentUser();
        if (currentUser == null || TextUtils.isEmpty(currentUser.getToken())) {
            return false;
        }

        return true;
    }

    /**
     * 时间格式生成字符串
     *
     * @param suffix 文件后缀名
     * @return
     */
    public static String getFileNameByDate(String suffix) {
//        Format dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//        Calendar rightNow = Calendar.getInstance();
//        String datetime = dateFormat.format(rightNow.getTime());
        Calendar calendar = Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis();
        return timeInMillis + suffix;
    }

    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 根据系统语言判断是否为中国
     * @return
     */
    public static boolean isZh() {
        Locale locale = MyApplication.getInstance().getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.startsWith("zh")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据当前系统语言获取价格
     *
     * @param price
     * @return
     */
    public static String getPriceByRate(float price) {
        float rate = MyApplication.getRate();
        if (isZh()) {
            return MyApplication.getInstance().getString(R.string.price_str, rate * price);
        }else{
            return MyApplication.getInstance().getString(R.string.price_str, price);
        }
    }

}
