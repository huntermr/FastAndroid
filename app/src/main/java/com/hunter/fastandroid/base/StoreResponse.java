package com.hunter.fastandroid.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class StoreResponse extends BaseResponse {
    private boolean successed;

    public void setSuccessed(boolean successed) {
        this.successed = successed;
    }

    public boolean isSuccessed() {

        return successed;
    }

    private String dateFormat = "yyyy-MM-dd HH:mm:ss";

    /**
     * 解析json,获取响应对象
     *
     * @param json
     * @return
     */
    public static StoreResponse getResponse(String json) {
        StoreResponse mResponse = new StoreResponse();
        boolean successed = false;
        String msg = "";
        int code = 0;
        String data = "";

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            boolean hasSuccess = jsonObject.has("successed");
            if (hasSuccess) {
                successed = jsonObject.getBoolean("successed");
            }

            boolean hasMessage = jsonObject.has("message");
            if (hasMessage) {
                msg = jsonObject.getString("message");
            }

            boolean hasCode = jsonObject.has("code");
            if (hasCode) {
                code = jsonObject.getInt("code");
            }

            boolean hasData = jsonObject.has("data");
            if (hasData) {
                data = jsonObject.getString("data");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        mResponse.setSuccessed(successed);
        mResponse.setMsg(msg);
        mResponse.setCode(code);
        mResponse.setData(data);

        return mResponse;
    }

    /**
     * 不解析json,直接作为响应的data封装后返回
     *
     * @param json
     * @return
     */
    public static StoreResponse getOnlyDataResponse(String json) {
        StoreResponse mResponse = new StoreResponse();

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

        Gson gson = getGson();
        T object = gson.fromJson(getData(), clazz);

        return object;
    }

    public <T> T getBeanList(Type typeOfT) throws IllegalArgumentException,
            JsonSyntaxException {

        Gson gson = getGson();
        T object = gson.fromJson(getData(), typeOfT);

        return object;
    }
}
