package pda.nryuncang.com.myapplication.utils;

import android.widget.EditText;

/**
 * Created by 15032065 on 17/4/12.
 * 焦点工具类
 */

public class FocusUtil {

    public static void getFocus(EditText editText)
    {
        final EditText ed = editText;
        if (ed == null)return;
        editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                ed.setFocusable(true);
                ed.setFocusableInTouchMode(true);
                ed.requestFocus();
                ed.findFocus();
            }
        },500);
    }
}
