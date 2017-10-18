package com.nryuncang.pda.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nryuncang.pda.bean.JianHuoNewTaskListBean;
import com.nryuncang.pda.http.API;
import com.nryuncang.pda.http.PickingRequest;
import com.nryuncang.pda.http.RequestHelper;
import com.nryuncang.pda.plus.R;
import com.nryuncang.pda.ui.activity.MainActivity;
import com.nryuncang.pda.utils.Receiver;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Button;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * 拣货任务列表adapter
 * Created by Wilk on 2015/6/25.
 */
public class JianHuoNewTaskListAdapter extends BaseAdapter {
    public static String PICK_TYPE_SINGLE = "single";
    public static String PICK_TYPE_MULTI = "multi";
    public static String WORK_MODE_PRE_PRINT = "prePrint";
    public static String PICK_MODEL_CAR = "car";
    private Context mContext;
    private ArrayList<JianHuoNewTaskListBean.ContentEntity> mData = new ArrayList<>();
    private AlertDialog mCodeInputDialog;
    private RequestListener mLoadListener;

    private Response.Listener<JSONObject> mRequestListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            mLoadListener.onEndLoad();
            if (RequestHelper.isSuccess(response)) {
                MainActivity.speakWords("拣货任务领取成功");
                mLoadListener.onTips(false, "拣货任务领取成功");
                mLoadListener.onSuccess();
            } else {
                String error = RequestHelper.parseContent(mContext, response);
                if (error.startsWith(mContext.getString(R.string.temp_operator))) {
                    error = error.substring(2);
                }
                MainActivity.speakWords("领取失败," + error);
                mLoadListener.onTips(true, "领取失败," + error);
                if (mCodeInputDialog != null) {
                    mCodeInputDialog.dismiss();
                }
            }
        }
    };

    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            mLoadListener.onEndLoad();
            MainActivity.speakWords(mContext.getString(R.string.tips_request_error));
            mLoadListener.onTips(true, mContext.getString(R.string.tips_request_error));
        }
    };

    public JianHuoNewTaskListAdapter(Context context, ArrayList<JianHuoNewTaskListBean.ContentEntity> data, RequestListener listener) {
        this.mContext = context;
        this.mData = data;
        this.mLoadListener = listener;
    }

    public static int getTaskType(String pickType) {
        if (PICK_TYPE_SINGLE.equals(pickType))
            return R.string.pick_type_single;
        else
            return R.string.pick_type_multi;
    }

    public static int getWorkMode(String workMode) {
        if (WORK_MODE_PRE_PRINT.equals(workMode))
            return R.string.work_mode_pre_print;
        else
            return R.string.work_mode_imd_print;
    }

    public static int getJianHuoModeCode(JianHuoNewTaskListBean.ContentEntity bean) {
        if (PICK_TYPE_SINGLE.equals(bean.getPickType())) {
            return 1;
        } else if (PICK_MODEL_CAR.equals(bean.getPickModel()) && PICK_TYPE_MULTI.equals(bean.getPickType())) {
            return 2;
        } else if (PICK_TYPE_MULTI.equals(bean.getPickType())) {
            return 3;
        }
        return -1;
    }

    public static int getJianHuoMode(int code) {
        switch (code) {
            case 1:
            case 3:
                return R.string.jianhuo_mode_ptjx;
            case 2:
                return R.string.jianhuo_mode_tcjx;
        }
        return -1;
    }

    public static int getGetTaskBtnText(int mode) {
        if (mode == 2) {
            return R.string.get_new_jianhuo_task_car;
        } else {
            return R.string.get_new_jianhuo_task_box;
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_jianhuo, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final JianHuoNewTaskListBean.ContentEntity bean = mData.get(position);
        holder.mTaskId.setText(bean.getTaskCode());
        holder.mOrdersCount.setText(String.valueOf(bean.getOrderQty()));
        holder.mWorkMode.setText(getWorkMode(bean.getFlowType()));
        holder.mTaskType.setText(getTaskType(bean.getPickType()));
        holder.mJianHuoMode.setText(getJianHuoMode(getJianHuoModeCode(bean)));
        holder.mGetTaskBtn.setText(getGetTaskBtnText(getJianHuoModeCode(bean)));
        holder.mGetTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCodeInputDialog(bean);
            }
        });
        return convertView;
    }

    private void showCodeInputDialog(final JianHuoNewTaskListBean.ContentEntity bean) {
        final int mode = getJianHuoModeCode(bean);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_code, null);
        final MaterialEditText codeEt = (MaterialEditText) view.findViewById(R.id.et_code);
        mContext.registerReceiver(new Receiver(codeEt, null, "JHNT"), new IntentFilter("com.android.receive_scan_action"));

        if (mode == 2) {
            codeEt.setHint("请扫描推车条码");
            codeEt.setFloatingLabelText("推车条码");
        } else {
            codeEt.setHint("请扫描分拣框条码");
            codeEt.setFloatingLabelText("分拣框条码");
        }
        codeEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (KeyEvent.KEYCODE_ENTER == keyCode && event.getAction() == KeyEvent.ACTION_UP) {
                    getTask(mode, codeEt.getText().toString().trim(), bean);
                    return true;
                }
                return false;
            }
        });
        if (mCodeInputDialog != null) {
            mCodeInputDialog.dismiss();
        }
        mCodeInputDialog = new AlertDialog.Builder(mContext, R.style.Theme_AppCompat_Light_Dialog)
                .setTitle("")
                .setView(view)
                .setPositiveButton(mContext.getString(R.string.btn_confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getTask(mode, codeEt.getText().toString().trim(), bean);
                    }
                })
                .setNegativeButton(mContext.getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        mCodeInputDialog.show();
        mCodeInputDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private void getTask(int mode, String code, JianHuoNewTaskListBean.ContentEntity bean) {
        if (mode == 2) {
            if (code.isEmpty()) {
                MainActivity.speakWords("请扫描正确的推车条码");
                mLoadListener.onTips(true, "请扫描正确的推车条码");
                return;
            }
            mLoadListener.onStartLoad(API.getAjaxBindCar());
            PickingRequest.ajaxBindCar(code, bean.getTaskCode(), mRequestListener, mErrorListener);
        } else if (mode == 1 || mode == 3) {
            if (code.isEmpty()) {
                MainActivity.speakWords("请扫描正确的分拣筐条码");
                mLoadListener.onTips(true, "请扫描正确的分拣筐条码");
                return;
            }
            mLoadListener.onStartLoad(API.getAjaxBindBox());
            PickingRequest.ajaxBindBox(code, bean.getTaskCode(), mRequestListener, mErrorListener);
        }
    }

    public interface RequestListener {
        void onStartLoad(String ajaxBindCar);

        void onEndLoad();

        void onTips(boolean isAlert, String tips);

        void onSuccess();
    }


    class ViewHolder {

        private TextView mTaskId;

        private TextView mOrdersCount;

        private TextView mTaskType;

        private TextView mWorkMode;

        private TextView mJianHuoMode;

        private Button mGetTaskBtn;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            mTaskId = (TextView) view.findViewById(R.id.task_id);
            mOrdersCount= (TextView) view.findViewById(R.id.orders_count);
            mTaskType= (TextView) view.findViewById(R.id.task_type);
            mWorkMode= (TextView) view.findViewById(R.id.work_mode);
            mJianHuoMode= (TextView) view.findViewById(R.id.jianhuo_mode);
            mGetTaskBtn= (Button) view.findViewById(R.id.get_task_btn);
        }
    }
}
