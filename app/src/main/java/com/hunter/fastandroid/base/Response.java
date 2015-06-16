package com.hunter.fastandroid.base;

import java.lang.reflect.Type;

import org.json.JSONObject;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class Response extends BaseResponse {

    private String dateFormat = "yyyy-MM-dd HH:mm:ss";

    /**
     * 解析json,获取响应对象
     *
     * @param json
     * @return
     */
    public static Response getResponse(String json) {
        Response mResponse = new Response();
        int code = 0;
        String msg = "";
        String data = "";

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            boolean hasCode = jsonObject.has("code");
            if (hasCode) {
                code = jsonObject.getInt("code");
            }

            boolean hasMessage = jsonObject.has("message");
            if (hasMessage) {
                msg = jsonObject.getString("message");
            }

            boolean hasData = jsonObject.has("data");
            if (hasData) {
                data = jsonObject.getString("data");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        mResponse.setCode(code);
        mResponse.setMsg(msg);
        mResponse.setData(data);

        return mResponse;
    }

    /**
     * 不解析json,直接作为响应的data封装后返回
     *
     * @param json
     * @return
     */
    public static Response getOnlyDataResponse(String json) {
        Response mResponse = new Response();

        mResponse.setData(json);

        return mResponse;
    }

    /**
     * 设置Gson解析Date的解析格式
     *
     * @param format
     */
    public void setGsonDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    private Gson getGson() {
        return new GsonBuilder().setDateFormat(dateFormat).create();
    }

    public <T> T getBean(Class<T> clazz) throws IllegalArgumentException,
            JsonSyntaxException {
        if (TextUtils.isEmpty(getData()))
            throw new IllegalArgumentException(
                    "In the Response, data can't be empty");

        Gson gson = getGson();
        T object = gson.fromJson(getData(), clazz);

        return object;
    }

    public <T> T getBeanList(Type typeOfT) throws IllegalArgumentException,
            JsonSyntaxException {
        if (TextUtils.isEmpty(getData()))
            throw new IllegalArgumentException(
                    "In the Response, data can't be empty");

        Gson gson = getGson();
        T object = gson.fromJson(getData(), typeOfT);

        return object;
    }
}
