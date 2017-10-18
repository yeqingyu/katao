package pda.nryuncang.com.myapplication.ui.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;



import java.lang.ref.WeakReference;

import pda.nryuncang.com.myapplication.CustomApplication;
import pda.nryuncang.com.myapplication.R;
import pda.nryuncang.com.myapplication.view.SimpleToast;

/**
 * Activity 基类
 * Created by Wilk on 2015/6/23.
 */
public class BaseActivity extends AppCompatActivity {

    WeakReference<BaseActivity> weakReference = new WeakReference<>(this);

    protected static void showErrorToast(String msg) {
        SimpleToast.error(msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomApplication.addActivity(weakReference);
//        PushAgent.getInstance(this).onAppStart();
        initActionBar();
        preInitUI();
        initUI();
        initListener();
        postInitUI();
    }
    /**
     * this function will be call in {@link #onCreate(Bundle)} before
     * {@link #initUI()}
     */

    protected void  initActionBar() {

    }

    /**
     * 初始之前的操作
     */
    protected void preInitUI() {

    }

    /**
     * 控件初始化
     */
    protected void initUI() {

    }

    /**
     * 载入点击事件
     */
    protected void initListener() {

    }


    protected void postInitUI() {

    }

    @Override
    protected void onDestroy() {
        CustomApplication.removeActivity(weakReference);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
//        if (!(this instanceof LoginActivity))
//            checkUserInfo();
    }

//    protected void checkUserInfo() {
//        if (CustomApplication.mCurrentUser == null ||
//                (CustomApplication.mCurrentUser != null && CustomApplication.mCurrentUser.getJobuuid().isEmpty())) {
//            MobclickAgent.onEvent(this, "error_user_info_lost");
//            Toast.makeText(this, "登录信息丢失,即将重启应用.", Toast.LENGTH_LONG).show();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                Logger.e("error ===> ", e);
//            }
//            Intent intent = new Intent();
//            intent.setClass(this, LoginActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            CustomApplication.exit();
//        }
//    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }


    protected void showErrorToast(int resId) {
        showErrorToast(getString(resId));
    }

    protected void showInfoToast(int resId) {
        showInfoToast(getString(resId));
    }

    protected void showInfoToast(String msg) {
        SimpleToast.info(msg);
    }

    protected void myShowInfoToast(String msg, int... showTime) {
        int wShowTime;
        if (showTime.length > 0) {
            wShowTime = showTime[0];
        } else {
            wShowTime = 2;
        }
        SimpleToast.myInfo(msg, wShowTime);
    }


    protected void showMutedToast(String msg) {
        SimpleToast.muted(msg);
    }

    protected void showOkToast(int resId) {
        showOkToast(getString(resId));
    }

    protected void showOkToast(String msg) {
        SimpleToast.ok(msg);
    }

    /**
     * 获取当前应用版本号
     *
     * @return 版本号 versionName
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            return info.versionName;

        } catch (Exception e) {
            e.printStackTrace();
            return getString(R.string.temp);
        }
    }

}
