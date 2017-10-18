package pda.nryuncang.com.myapplication.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.WindowManager;

import pda.nryuncang.com.myapplication.R;
import pda.nryuncang.com.myapplication.ui.activity.MainActivity;
import pda.nryuncang.com.myapplication.view.SimpleToast;


/**
 * Created by Administrator on 2016/4/20.
 */
public class ShowToastUtil {
    static AlertDialog mLoadingDialog;

    public static void showInfoToast(String msg) {
        SimpleToast.info(msg);
    }

    public static void myShowInfoToast(String msg, int... showTime) {
        MainActivity.speakWords(msg);
        int wShowTime;
        if (showTime.length > 0) {
            wShowTime = showTime[0];
        } else {
            wShowTime = 2;
        }
        SimpleToast.myInfo(msg, wShowTime);

    }

    public static void showMutedToast(String msg) {
        SimpleToast.muted(msg);

    }

    public static void showOkToast(String msg) {
        SimpleToast.ok(msg);

    }

    public static void showErrorToast(String msg) {
        SimpleToast.error(msg);

    }

    public static void showIsSuccessMessage(String message, boolean flag) {
        MainActivity.speakWords(message);
        if (flag)
            showInfoToast(message);
        else
            showErrorToast(message);
    }


    /**
     * 显示加载进度框
     *
     * @param msg title
     */
    public static void showLoadingDialog(Context context, String msg, final DialogInterface.OnCancelListener listener) {
        if(mLoadingDialog!=null) return;
        mLoadingDialog = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog)
                .setTitle(msg)
                .setView(R.layout.progress_loadding)
                .setCancelable(false).setOnKeyListener(keylistener)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        listener.onCancel(dialog);
                    }
                }).create();
        mLoadingDialog.show();
        mLoadingDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 隐藏加载进度框
     */
    public static void dismissLoadingDialog() {
        if (mLoadingDialog != null&&mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog=null;
        }
    }

    private static DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                dismissLoadingDialog();
            }
            return true;
        }
    };
}
