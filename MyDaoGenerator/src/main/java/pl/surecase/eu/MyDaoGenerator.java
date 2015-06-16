package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.smoktech.shop");
        initUserBean(schema);
        new DaoGenerator().generateAll(schema, args[0]);
    }

    private static void initUserBean(Schema schema) {
        Entity userBean = schema.addEntity("User");// 表名
        //userBean.setTableName("user"); // 可以对表重命名
        userBean.addLongProperty("id").primaryKey().index();// 主键，索引
        userBean.addStringProperty("name");
        userBean.addStringProperty("password");
        userBean.addStringProperty("nickname");
    }
}
