package com.huntermr.fastandroid;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.huntermr.fastandroid");
        initUserBean(schema);
        new DaoGenerator().generateAll(schema, args[0]);
    }

    private static void initUserBean(Schema schema) {
        Entity userBean = schema.addEntity("User");// ����
        //userBean.setTableName("user"); // ���ԶԱ�������
        userBean.addLongProperty("id").primaryKey().index();// ��������
        userBean.addStringProperty("name");
        userBean.addStringProperty("password");
        userBean.addStringProperty("nickname");
    }
}
