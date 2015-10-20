package com.hunter.fastandroid.net;

import android.os.Handler;
import android.os.Message;

import com.hunter.fastandroid.base.JsonResponse;
import com.hunter.fastandroid.utils.Logger;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * 该类用于封装Okhttp回调方法,返回解析服务器的json数据后的data数据
 * 由于该方法执行于子线程,所以需要使用Handler操作位于UI线程的事务处理监听
 */
public class TransactionOkhttpStringHandler implements Callback {
    TransactionListener mTransactionListener;
    private MyHandler myHandler;
    // 错误状态码
    private static final int FAILURE = 1;
    // 成功状态码
    private static final int SUCCESS = 2;

    /**
     * 自定义Handler()
     */
    private static class MyHandler extends Handler {
        private WeakReference<TransactionOkhttpStringHandler> httpHandler;

        public MyHandler(TransactionOkhttpStringHandler handler) {
            httpHandler = new WeakReference<TransactionOkhttpStringHandler>(handler);
        }

        @Override
        public void handleMessage(Message msg) {
            TransactionOkhttpStringHandler transactionOkhttpHandler = httpHandler.get();
            if (transactionOkhttpHandler != null) {
                int statusCode = msg.what;
                if (statusCode == SUCCESS) {
                    String responseString = (String) msg.obj;
                    transactionOkhttpHandler.sendResponse(responseString);
                } else if (statusCode == FAILURE) {
                    transactionOkhttpHandler.mTransactionListener.onFailure(ResponseCode.ERROR_NETWORK);
                }
            }
        }
    }

    public TransactionOkhttpStringHandler(TransactionListener mTransactionListener) {
        this.mTransactionListener = mTransactionListener;
        this.myHandler = new MyHandler(this);
    }

    void sendResponse(String responseString) {
        mTransactionListener.onSuccess(responseString);
    }

    @Override
    public void onFailure(Request request, IOException e) {
        myHandler.sendEmptyMessage(FAILURE);
    }

    @Override
    public void onResponse(Response response) throws IOException {
        String data = response.body().string();

        Logger.e("HTTP-Response,data：" + data);

        if (response.isSuccessful()) {
            Message message = new Message();
            message.what = SUCCESS;
            message.obj = data;
            myHandler.sendMessage(message);
        }else{
            myHandler.sendEmptyMessage(FAILURE);
        }
    }
}
