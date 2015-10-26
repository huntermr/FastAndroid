package com.hunter.fastandroid;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * @author Hunter
 * 这是一个GreenDao的类生成器
 * 如果不使用GreenDao,可以无视此依赖库
 *
 */
public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.hunter.fastandroid");
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
