package pda.nryuncang.com.myapplication.ui.activity;

import android.content.IntentFilter;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import pda.nryuncang.com.myapplication.R;
import pda.nryuncang.com.myapplication.bean.MessageBean;
import pda.nryuncang.com.myapplication.config.Config;
import pda.nryuncang.com.myapplication.controller.GetPiecesManagerController;
import pda.nryuncang.com.myapplication.utils.FocusUtil;
import pda.nryuncang.com.myapplication.utils.PreferencesUtils;
import pda.nryuncang.com.myapplication.utils.Receiver;
import pda.nryuncang.com.myapplication.utils.ShowToastUtil;

/**
 * Created by Administrator on 2016/11/7.
 */
public class GetPiecesManagerActivity extends BaseActivity {

    private Toolbar mToolBar;
    private MaterialEditText latticeNum, bigPackage;
    private Receiver rv;
    private GetPiecesManagerController controller;

    private Button btRegist;
    @Override
    protected void preInitUI() {
        ButterKnife.inject(this);
        rv = new Receiver(null, this);
        registerReceiver(rv, new IntentFilter(Receiver.SCAN_ACTION));
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_fcb_package_ks);
        mToolBar = (Toolbar) this.findViewById(R.id.toolbar);
        latticeNum = (MaterialEditText) this.findViewById(R.id.lattice_num);
        bigPackage = (MaterialEditText) this.findViewById(R.id.big_package);
        btRegist= (Button) this.findViewById(R.id.bt_regist);
//        btRegist.setVisibility(View.GONE);
//        setSupportActionBar(mToolBar);
    }

    @Override
    protected void initListener() {
        /**
         * 运单号文本框点击事件
         */
        latticeNumOnclick();

        /**
         * sku文本框点击操作
         */
        bigPackageOnclick();

        btRegistOnclick();
    }

    private void bigPackageOnclick() {
        bigPackage.setOnKeyListener(new View.OnKeyListener()

                                    {
                                        @Override
                                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                                            if (KeyEvent.KEYCODE_ENTER == keyCode && event.getAction() == KeyEvent.ACTION_UP) {
                                                if (getLatticeNum().isEmpty()) {
                                                    orderNumEmpty();
                                                    return true;
                                                }
                                                if (getbigPackage().isEmpty()) {
                                                    bigPackageEmpty();
                                                    return true;
                                                }
                                                if (!isRange(getLatticeNum())) {
                                                    ShowToastUtil.showIsSuccessMessage("格口号不在该范围内或不合法", false);
                                                    return true;
                                                }

                                                if (!isBigPackage(getbigPackage())) {
                                                    ShowToastUtil.showIsSuccessMessage("大包号超出长度范围", false);
                                                    return true;
                                                }
                                                if (controller != null)
                                                    controller.getMsgController(GetPiecesManagerActivity.this,getUserId(), getLatticeNum(), getbigPackage());
                                                return true;
                                            }
                                            return false;
                                       }
                                    }

        );
    }

    private void latticeNumOnclick() {
        latticeNum.setOnKeyListener(new View.OnKeyListener() {
                                        @Override
                                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                                            if ((KeyEvent.KEYCODE_ENTER == keyCode && event.getAction() == KeyEvent.ACTION_UP)||(KeyEvent.KEYCODE_BUTTON_R1 == keyCode && event.getAction() == KeyEvent.ACTION_DOWN)) {
                                                if (TextUtils.isEmpty(getLatticeNum())) {
                                                    orderNumHandle();
                                                    return true;
                                                }
                                                if (!isRange(getLatticeNum())) {
                                                    ShowToastUtil.showIsSuccessMessage("格口号不在该范围内或不合法", false);
                                                    latticeNum.setText("");
                                                    latticeNum.requestFocus();
                                                    return true;
                                                }
                                                ShowToastUtil.showIsSuccessMessage(getString(R.string.scan_bigPackage), true);
                                                FocusUtil.getFocus(bigPackage);
                                            }
                                            return false;
                                        }
                                    }
        );
    }


    private void btRegistOnclick() {
        btRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getLatticeNum().isEmpty()) {
                    orderNumEmpty();
                    return ;
                }
                if (getbigPackage().isEmpty()) {
                    bigPackageEmpty();
                    return ;
                }
                if (!isRange(getLatticeNum())) {
                    ShowToastUtil.showIsSuccessMessage("格口号不在该范围内或不合法", false);
                    return ;
                }

                if (!isBigPackage(getbigPackage())) {
                    ShowToastUtil.showIsSuccessMessage("大包号超出长度范围", false);
                    return ;
                }
                if (controller != null)
                    controller.getMsgController(GetPiecesManagerActivity.this,getUserId(), getLatticeNum(), getbigPackage());
            }

        });
    }
    private String getLatticeNum() {
        return latticeNum.getText().toString().trim();
    }


    private void orderNumHandle() {
        ShowToastUtil.showIsSuccessMessage(getString(R.string.error_please_scan_latticeNum), false);
        latticeNum.requestFocus();
    }


    private void orderNumEmpty() {
        ShowToastUtil.showIsSuccessMessage(getString(R.string.please_scan_latticeNum), false);
        latticeNum.setText("");
        bigPackage.setText("");
        bigPackage.requestFocus();
    }

    private String getbigPackage() {
        return bigPackage.getText().toString().trim();
    }


    private void bigPackageEmpty() {
        ShowToastUtil.showIsSuccessMessage(getString(R.string.scan_bigPackage), true);
        latticeNum.setText("");
        latticeNum.requestFocus();
    }


    @Override
    protected void postInitUI() {
        controller = new GetPiecesManagerController();
        ShowToastUtil.showIsSuccessMessage(getString(R.string.please_scan_latticeNum), true);

    }

    @Subscribe
    public void onEventMainThread(MessageBean mb) {
        if (mb != null) {
            if (mb.getStatus().equals("success")) {
                ShowToastUtil.showIsSuccessMessage("验证成功", true);
            } else {
                ShowToastUtil.showIsSuccessMessage(mb.getStatusInfo(), false);
            }
            latticeNum.setText("");
            bigPackage.setText("");
            latticeNum.requestFocus();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unregisterReceiver(rv);
    }


    public boolean isRange(String data) {
        try {
            if (data.trim().length() != 3) {
                return false;
            }
            if (Config.USE_WAREHOUSE.equals(Config.HANGZHOU_WAREHOUSE)) {
                if (Integer.parseInt(data) < 1 || Integer.parseInt(data) > 206) {
                    return false;
                }
                return true;
            }
            if (Config.USE_WAREHOUSE.equals(Config.NANCHAGN_WAREHOUSE)) {
                if (Integer.parseInt(data) < 1 || Integer.parseInt(data) > 500) {
                    return false;
                }
                return true;
            }
            return true;

        } catch (Exception e) {
            return false;
        }
    }


    public boolean isBigPackage(String bigPackage) {
        if (Config.USE_WAREHOUSE.equals(Config.HANGZHOU_WAREHOUSE)) {
            if (bigPackage.length() < 12 || bigPackage.length() > 14) {
                return false;
            } else {
                return true;
            }
        } else if (Config.USE_WAREHOUSE.equals(Config.NANCHAGN_WAREHOUSE)) {
            if (bigPackage.length() < 10 || bigPackage.length() > 20) {
                return false;
            } else {
                return true;
            }
        } else {
            if (bigPackage.length() < 10) {
                return false;
            }
        }
        return true;
    }

    private String getUserId(){
        return PreferencesUtils.getString(this,"userId")==null?"":PreferencesUtils.getString(this,"userId");
    }

}
