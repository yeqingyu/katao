package pda.nryuncang.com.myapplication.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import pda.nryuncang.com.myapplication.R;
import pda.nryuncang.com.myapplication.bean.MessageBean;
import pda.nryuncang.com.myapplication.controller.LoginManagerController;
import pda.nryuncang.com.myapplication.utils.PreferencesUtils;
import pda.nryuncang.com.myapplication.utils.ShowToastUtil;


/**
 * Created by Administrator on 2017/8/25.
 */

public class LoginActivity extends BaseActivity{
    private EditText etUser;
    private EditText etPassword;
    private Button btLogin;
    private LoginManagerController controller;
    @Override
    protected void preInitUI() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_login);
        etUser= (EditText) this.findViewById(R.id.et_user);
        etPassword= (EditText) this.findViewById(R.id.et_password);
        btLogin= (Button) this.findViewById(R.id.bt_login);
        controller=new LoginManagerController();
    }

    @Override
    protected void initListener() {
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLoginInfo();
            }
        });
    }


    /**
     * 检查登录信息
     */
    private void checkLoginInfo() {
        if(getPassword().isEmpty()||getUser().isEmpty()){
            Snackbar.make(etUser,"用户名或密码不能为空",Snackbar.LENGTH_SHORT).show();
            return;
        }
        controller.getLoginController(this,getUser(),getPassword());
    }

    @NonNull
    private String getPassword() {
        return etPassword.getText().toString().trim();
    }

    @NonNull
    private String getUser() {
        return etUser.getText().toString().trim();
    }



    @Subscribe
    public void onEventMainThread(MessageBean mb) {
        if (mb != null) {
            if (mb.getStatus().equals("success")) {
                PreferencesUtils.putString(this,"userId",getUser());
               MainActivity.callMe(this);
            } else {
                ShowToastUtil.showIsSuccessMessage("用户名密码错误", false);
            }
        }
    }

}
