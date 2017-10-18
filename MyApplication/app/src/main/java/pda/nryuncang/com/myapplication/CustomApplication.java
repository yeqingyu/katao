package pda.nryuncang.com.myapplication;

import android.app.Application;


import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

import pda.nryuncang.com.myapplication.http.RequestHelper;
import pda.nryuncang.com.myapplication.ui.activity.BaseActivity;
import pda.nryuncang.com.myapplication.view.SimpleToast;

/**
 * 自定义应用类
 * Created by Wilk on 2015/6/23.
 */
public class CustomApplication extends Application {
//    public static UserBean mCurrentUser;
    private static List<WeakReference<BaseActivity>> mActivityList = new LinkedList<>();

    /**
     * 添加Activity到容器中
     */
    public static void addActivity(WeakReference<BaseActivity> activity) {
        mActivityList.add(activity);
    }

    /**
     * 从容器中移除
     */
    public static void removeActivity(WeakReference<BaseActivity> activity) {
        mActivityList.remove(activity);
    }

    /**
     * 退出应用
     */
    public static void exit() {
        boolean isSaved = false;
        for (WeakReference<BaseActivity> activity : mActivityList) {
            if (activity != null && !activity.get().isFinishing() && !isSaved) {
//                MobclickAgent.onKillProcess(activity.get().getApplicationContext());
                isSaved = true;
            }
            if (activity != null) {
                activity.get().finish();
            }
        }
        System.exit(0);
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());

//        LeakCanary.install(this);

//        Logger.init("WILK");
////        Logger.init("WILK").setLogLevel(LogLevel.NONE);
//        // 初始化http请求封装类
        SimpleToast.init(this);
        RequestHelper.init(this);
//        // 友盟自动更新组件
//        UmengUpdateAgent.setUpdateOnlyWifi(false);
//        UmengUpdateAgent.setRichNotification(true);
//        // 友盟在线参数初始化
//        OnlineConfigAgent.getInstance().updateOnlineConfig(this);
    }
}
