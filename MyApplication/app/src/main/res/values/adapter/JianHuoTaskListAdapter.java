package com.nryuncang.pda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nryuncang.pda.bean.JianHuoTaskListBean;
import com.nryuncang.pda.constant.BaseConstant;
import com.nryuncang.pda.plus.R;
import com.nryuncang.pda.ui.activity.CodeActivity;
import com.nryuncang.pda.ui.activity.JianHuoBoxActivity;
import com.nryuncang.pda.ui.activity.JianHuoCarNewActivity;
import com.rey.material.widget.Button;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * 拣货任务列表adapter
 * Created by Wilk on 2015/6/25.
 */
public class JianHuoTaskListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<JianHuoTaskListBean.ContentEntity> mData = new ArrayList<>();

    public JianHuoTaskListAdapter(Context context, ArrayList<JianHuoTaskListBean.ContentEntity> data) {
        this.mContext = context;
        this.mData = data;
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
        final JianHuoTaskListBean.ContentEntity bean = mData.get(position);
        holder.mTaskId.setText(bean.getTaskCode());
        holder.mOrdersCount.setText(String.valueOf(bean.getOrderQty()));
        holder.mWorkMode.setText(getWorkMode(bean.getFlowType()));
        holder.mTaskType.setText(getTaskType(bean.getPickType()));
        holder.mJianHuoMode.setText(getJianHuoModeCode(bean));
        holder.mGetTaskBtn.setText(getGetTaskBtnText(bean.getStatus()));
        holder.mGetTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TASK_STATUS_11.equals(bean.getStatus())) {
                    JianHuoCarNewActivity.callMe(mContext, bean, BaseConstant.PICK_CODE_2);
                } else if (TASK_STATUS_12.equals(bean.getStatus()) || TASK_STATUS_10.equals(bean.getStatus())) {
                    JianHuoBoxActivity.callMe(mContext, bean);
                } else {
                    CodeActivity.callMe(mContext, bean);
                }
            }
        });
        return convertView;
    }

    public class ViewHolder {

        private TextView mTaskId;

        private TextView mOrdersCount;

        private TextView mTaskType;

        private TextView mWorkMode;

        private TextView mJianHuoMode;

        private Button mGetTaskBtn;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            mTaskId = (TextView) view.findViewById(R.id.task_id);
            mOrdersCount = (TextView) view.findViewById(R.id.orders_count);
            mTaskType = (TextView) view.findViewById(R.id.task_type);
            mWorkMode = (TextView) view.findViewById(R.id.work_mode);
            mJianHuoMode = (TextView) view.findViewById(R.id.jianhuo_mode);
            mGetTaskBtn = (Button) view.findViewById(R.id.get_task_btn);

        }
    }
    public static String TASK_STATUS_11 = "11";
    public static String TASK_STATUS_12 = "12";
    public static String TASK_STATUS_10 = "10";

    public static int getTaskType(String pickType) {
        if (JianHuoTaskListBean.ContentEntity.PICK_TYPE_SINGLE.equals(pickType))
            return R.string.pick_type_single;
        else
            return R.string.pick_type_multi;
    }

    public int getWorkMode(String workMode) {
        if (JianHuoTaskListBean.ContentEntity.WORK_MODE_PRE_PRINT.equals(workMode))
            return R.string.work_mode_pre_print;
        else
            return R.string.work_mode_imd_print;
    }

    public int getJianHuoModeCode(JianHuoTaskListBean.ContentEntity bean) {
        if (JianHuoTaskListBean.ContentEntity.WORK_MODE_PRE_PRINT.equals(bean.getFlowType()) && JianHuoTaskListBean.ContentEntity.PICK_TYPE_SINGLE.equals(bean.getPickType())) {
            return R.string.jianhuo_mode_bjbpd;
        } else if (JianHuoTaskListBean.ContentEntity.PICK_MODEL_CAR.equals(bean.getPickModel()) && JianHuoTaskListBean.ContentEntity.PICK_TYPE_MULTI.equals(bean.getPickType())) {
            return R.string.jianhuo_mode_tcjx;
        } else {
            return R.string.jianhuo_mode_ptjx;
        }
    }


    public static int getGetTaskBtnText(String status) {
        if (TASK_STATUS_11.equals(status) || TASK_STATUS_12.equals(status) || TASK_STATUS_10.equals(status))
            return R.string.task_status_continue;
        else
            return R.string.task_status_start;
    }


}
