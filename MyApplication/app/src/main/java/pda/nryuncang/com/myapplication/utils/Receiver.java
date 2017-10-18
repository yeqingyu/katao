package pda.nryuncang.com.myapplication.utils;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.orhanobut.logger.Logger;

/**
 * Created by libin on 2016/2/27.
 */

public class Receiver extends BroadcastReceiver {
    private EditText editText;
    private Activity activity;
    //用于区分领取拣货任务
    private String tmpFlg = "";
    Instrumentation instrumentation;
    /**
     * SCAN_ACTION 定义扫描行为
     */
    public static final String SCAN_ACTION = "com.android.receive_scan_action";
    /**
     * SAVA_DATA 从Avtivity中传入的数据
     */
    public static final String SAVA_DATA = "data";
    /**
     * JHNT 区分任务
     */
    public static final String JHNT = "JHNT";

    public Receiver() {
    }

    public Receiver(EditText editText, Activity activity, String... tmpFlg) {
        this.editText = editText;
        this.activity = activity;
        if (tmpFlg.length > 0) {
            this.tmpFlg = tmpFlg[0];
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SCAN_ACTION)) {
            try {
                if (!JHNT.equals(tmpFlg)) {
                    creatEditText();
                }
                if (editText != null) {
                    setEnterEvent(intent);
                }
            } catch (Exception e) {
                Logger.e("重新扫描！！");
            }
        }
    }

    /**
     * 发送确认事件
     * @param intent
     */
    private void setEnterEvent(Intent intent) {
        editText.setText(getEtData(intent));
        instrumentation = new Instrumentation();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);
            }
        });
        t.start();
    }

    /**
     * 拣货任务，建立creatEditText
     */
    private void creatEditText() {
        View view = activity.getCurrentFocus(); //activity.getWindow().getDecorView();
        editText = (EditText) view;
    }

    /**
     * @param intent
     * @return 返回从Activty中传入的扫描数据
     */
    private String getEtData(Intent intent) {
        if (intent == null) return "";
        return intent.getStringExtra(SAVA_DATA) == null ? "" : intent.getStringExtra(SAVA_DATA);
    }
}

