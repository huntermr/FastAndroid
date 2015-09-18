# FastAndroid

# QQ交流群：310262562

这是一个封装了多方面开源库后基于MVP模式的一个Android快速开发框架,融入了MVP模式,将Activity或Fragment做为View层、抽象出Presenter用于处理业务逻辑、Model处理网络访问，数据封装等。
降低模块之间耦合，提高可维护性及扩展性<br />
关于项目的详细说明，请访问：http://blog.csdn.net/ht_android/article/details/44852407，欢迎你提供更加详细的使用说明，请联系作者！

主要功能有：网络访问、上传下载、数据库操作、图片加载、View注解等等

用到的开源项目有：<br />
网络访问：android-async-http、数据库：GreenDao、图片加载：Universal-image-loader、View注解：Butterknife、Json解析：Gson

项目说明：<br />
1.在项目中自定义了Header视图,可自定义左右按钮,点击事件以及标题<br />
2.项目中有演示MVP的源码,请自行翻阅<br />
3.封装了BaseAdapter,提供了更加完善的方法,具体请查看CustomBaseAdapter源码<br />
4.以单例模式封装了网络访问层,整个项目的访问接口共用一个,并且初始化时设置了一些访问配置,方便开发者自行修改。请查看NetCenter源码<br />
5.抽象出公共请求参数及响应参数,所有的请求实体类都继承自BaseRequest,方便设置公共请求参数<br />
6.所有响应实体类都可通过Response解析获得,Response继承自BaseResponse,请自行查阅源码<br />
7.请求时可直接传入请求实体类,框架会自动封装成相应的请求参数及公共请求参数.<br />
8.该框架自定义了Activity的回退栈,方便用户在任何地方获取当前的Context.可自行查阅AppManager源码<br />
9.框架中新增了一个BaseCameraActivity,该Activity主要用于调用系统摄像头及相册,并自带剪切功能<br />
10.IBaseView中封装了常用的View操作,如Toast,进度条等等,并通过BaseActivity实现了,所以建议所有Activity都继承自BaseActivity以便更好的使用本框架<br />
11.TransactionListener该监听是Presenter用于监听Model的处理状态,可接受泛型后在onSuccess(T t)中接受处理结果<br />

关于项目维护:<br />由于作者平时工作较忙,更新进度比较慢,所以希望能有一些志同道合的朋友共同来维护此项目,真诚期待有意者能联系本人！


联系作者：Hunter <br />
Email:hunter-android@163.com  <br />
QQ 381959281  <br />
Blog:http://blog.csdn.net/ht_android<br />

