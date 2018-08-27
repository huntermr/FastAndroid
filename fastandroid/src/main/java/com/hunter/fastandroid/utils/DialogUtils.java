package com.hunter.fastandroid.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.hunter.fastandroid.R;


/**
 * Created by Administrator on 2017/6/8.
 */

public class DialogUtils {
    public static void showConfirmDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        showConfirmDialog(context, message, true, onClickListener);
    }

    public static void showConfirmDialog(Context context, int messageRes, DialogInterface.OnClickListener onClickListener) {
        showConfirmDialog(context, context.getString(messageRes), onClickListener);
    }

    /**
     * 显示一个确认对话框
     * @param context
     * @param message
     * @param onClickListener
     */
    public static void showConfirmDialog(Context context, String message, boolean cancelable, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog
                .Builder(context)
                .setMessage(message)
                .setCancelable(cancelable)
                .setPositiveButton(R.string.confirm, onClickListener)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
