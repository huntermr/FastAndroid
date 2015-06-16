package com.hunter.fastandroid.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hunter.fastandroid.DaoMaster;

/**
 * Created by user on 2015/5/11.
 */
public class CustomDevOpenHelper extends DaoMaster.OpenHelper {
    public CustomDevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            // 创建新表，注意createTable()是静态方法
            // xxxDao.createTable(db, true);

            // 加入新字段
            // db.execSQL("ALTER TABLE 'moments' ADD 'audio_path' TEXT;");
        }
    }
}
