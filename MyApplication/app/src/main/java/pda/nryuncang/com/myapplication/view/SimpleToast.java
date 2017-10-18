package pda.nryuncang.com.myapplication.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import pda.nryuncang.com.myapplication.R;

/*
* Copyright (C) 2015 Pierry Borges
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
public class SimpleToast {

    private static Toast mToast;

    public static void init(Context context) {
        mToast = new Toast(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.toast_view, null);
        mToast.setView(mView);
        mToast.setDuration(Toast.LENGTH_SHORT);
    }

    public static void ok(String msg) {
        mToast.getView().setBackgroundResource(R.drawable.list_border_back_blue);
        ImageView img = (ImageView) mToast.getView().findViewById(R.id.img);
        TextView text = (TextView) mToast.getView().findViewById(R.id.button);
        img.setImageResource(R.mipmap.ok);
        text.setText(msg);
        mToast.show();
    }

    public static void error(int res_Id) {
        error(mToast.getView().getContext().getString(res_Id));
    }

    public static void error(String msg) {
        mToast.getView().setBackgroundResource(R.drawable.list_border_back_red);
        ImageView img = (ImageView) mToast.getView().findViewById(R.id.img);
        TextView text = (TextView) mToast.getView().findViewById(R.id.button);
        img.setImageResource(R.mipmap.cancel);
        text.setText(msg);
        mToast.show();
    }

    public static void info(String msg) {
        mToast.getView().setBackgroundResource(R.drawable.list_border_back_green);
        ImageView img = (ImageView) mToast.getView().findViewById(R.id.img);
        TextView text = (TextView) mToast.getView().findViewById(R.id.button);
        img.setImageResource(R.mipmap.info);
        text.setText(msg);
        mToast.show();
    }
    public static void myInfo(String msg,int showTime) {
        mToast.getView().setBackgroundResource(R.drawable.list_border_back_green);
        ImageView img = (ImageView) mToast.getView().findViewById(R.id.img);
        TextView text = (TextView) mToast.getView().findViewById(R.id.button);
        img.setImageResource(R.mipmap.info);
        text.setText(msg);
        showMyToast(mToast,showTime);
    }

    /**
     * 循环显示提示框，达到页面一直显示的效果
     * @param toast 显示的内容
     * @param cnt 显示的时间 单位 ‘秒’
     */
    private static void showMyToast(Toast toast, int cnt) {
        if (cnt < 0)
            return;
        toast.show();
        execToast(toast, cnt);
    }
    private static void execToast(final Toast toast, final int cnt) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                showMyToast(toast, cnt - 1);
            }
        }, 1000);//默认1秒显示一次
    }

    /**
     *
     * @param toast
     * @param cnt
     */
    private void showMyToast1(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt);
    }


    public static void muted(String msg) {
        mToast.getView().setBackgroundResource(R.drawable.list_border_back_gray);
        ImageView img = (ImageView) mToast.getView().findViewById(R.id.img);
        TextView text = (TextView) mToast.getView().findViewById(R.id.button);
        img.setImageResource(R.mipmap.info);
        text.setText(msg);
        mToast.show();
    }

    public static void warning(String msg) {
        mToast.getView().setBackgroundResource(R.drawable.list_border_back_yellow);
        ImageView img = (ImageView) mToast.getView().findViewById(R.id.img);
        TextView text = (TextView) mToast.getView().findViewById(R.id.button);
        img.setImageResource(R.mipmap.info);
        text.setText(msg);
        mToast.show();
    }
}
