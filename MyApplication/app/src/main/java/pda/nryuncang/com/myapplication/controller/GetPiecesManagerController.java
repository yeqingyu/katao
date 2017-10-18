package pda.nryuncang.com.myapplication.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.util.Log;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import pda.nryuncang.com.myapplication.bean.MessageBean;
import pda.nryuncang.com.myapplication.http.API;
import pda.nryuncang.com.myapplication.http.RequestHelper;
import pda.nryuncang.com.myapplication.utils.ShowToastUtil;
import pda.nryuncang.com.myapplication.utils.VolleyErrorUtil;

/**
 * Created by Administrator on 2016/11/7.
 */
public class GetPiecesManagerController {

    public void getMsgController(final Context context,String userId, String sortPortCode, String packageCode) {
        ShowToastUtil.showLoadingDialog(context, "", new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                RequestHelper.doCancelRequest(API.getPiecesManagerUrl());
            }
        });
        RequestHelper.getPiecesManager(userId,sortPortCode, packageCode,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                ShowToastUtil.dismissLoadingDialog();
                EventBus.getDefault().post(MessageBean.creatMessageBean(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ShowToastUtil.dismissLoadingDialog();
                ShowToastUtil.showIsSuccessMessage(VolleyErrorUtil.getMessage(error, context), false);
            }
        });
    }
}
