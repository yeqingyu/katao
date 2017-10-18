package pda.nryuncang.com.myapplication.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import pda.nryuncang.com.myapplication.R;
import pda.nryuncang.com.myapplication.constant.BaseConstant;
import pda.nryuncang.com.myapplication.constant.PreferencesConstant;
import pda.nryuncang.com.myapplication.ui.adapter.FCBAdapter;
import pda.nryuncang.com.myapplication.ui.adapter.MainPagerAdapter;
import pda.nryuncang.com.myapplication.utils.PreferencesUtils;

/**
 * 主页
 * Created by Wilk on 2015/6/24.
 */
public class MainActivity extends BaseActivity implements TextToSpeech.OnInitListener {
    public static TextToSpeech mCommonTTS; // TTS对象

    private Toolbar mToolbar;

    private DrawerLayout mDrawerLayout;

    private ViewPager mViewPager;
    MainPagerAdapter mAdapter;  // 主页viewpager的adapter

    public static void callMe(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }


    @Override
    protected void preInitUI() {

    }

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) this.findViewById(R.id.toolbar);
        mViewPager = (ViewPager) this.findViewById(R.id.viewpager);
        setSupportActionBar(mToolbar);

        try {
            TextView view = (TextView) mDrawerLayout.getRootView().findViewById(R.id.name);
            view.setText(PreferencesUtils.getString(MainActivity.this,
                    PreferencesConstant.LOGIN_NAME, ""));
        } catch (NullPointerException ex) {

        }
        initViewPager();
    }

    /**
     * 调用TTS引擎朗读文字
     *
     * @param speech 要朗读的文本
     */
    public static void speakWords(String speech) {
        if (mCommonTTS != null) {
            speakWords(speech, false);
        }
    }

    /**
     * 调用TTS引擎朗读文字
     *
     * @param speech 要朗读的文本
     * @param isAdd  朗读前清空之前的列表
     */
    public synchronized static void speakWords(String speech, boolean isAdd) {
        if (mCommonTTS != null) {
            mCommonTTS.speak(speech, isAdd ? TextToSpeech.QUEUE_ADD : TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    protected void postInitUI() {
        initTTS();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initTTS() {
        try {
            Intent checkTTSIntent = new Intent();
            checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
            startActivityForResult(checkTTSIntent, BaseConstant.TTS_DATA_CHECK_CODE);
        } catch (ActivityNotFoundException e) {
//            Logger.e("无TTS,无法使用语音提醒功能...");
            showErrorToast(getString(R.string.tips_missing_tts_error));
        }
    }


    /**
     * 初始化viewpager
     */
    private void initViewPager() {
        String[] title = {getString(R.string.main_tab_fcb)};
        mAdapter = new MainPagerAdapter(MainActivity.this, initPagers(), title);
        mViewPager.setAdapter(mAdapter);
    }

    /**
     * 初始化pager
     *
     * @return 初始化完毕的view列表
     */
    private ArrayList<View> initPagers() {
        ArrayList<View> views = new ArrayList<>();
        View mFCB = getLayoutInflater().inflate(R.layout.pager_fcb, null);
//        ((GridView) mFCB.findViewById(R.id.grid)).setAdapter(new FCBAdapter(MainActivity.this, datas));
        ((GridView) mFCB.findViewById(R.id.grid)).setAdapter(new FCBAdapter(MainActivity.this, "LJGL"));
        views.add(mFCB);
        return views;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BaseConstant.TTS_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                mCommonTTS = new TextToSpeech(getApplicationContext(), this);
            } else {
                try {
                    Intent installTTSIntent = new Intent();
                    installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                    startActivity(installTTSIntent);
                } catch (ActivityNotFoundException e) {
                    showErrorToast(getString(R.string.tips_missing_tts_error));
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mCommonTTS != null) {
            mCommonTTS.shutdown();//关闭tts引擎
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mCommonTTS.setLanguage(Locale.CHINA);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                showErrorToast(getString(R.string.tips_missing_tts_error));
            }
        }
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