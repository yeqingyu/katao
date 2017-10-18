package com.nryuncang.pda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nryuncang.pda.bean.RKSJTaskListBean;
import com.nryuncang.pda.plus.R;
import com.rey.material.widget.Button;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * 拣货任务列表adapter
 * Created by Wilk on 2015/6/25.
 */
public class RKSJTaskListAdapter extends BaseAdapter {
    public static String TASK_STATUS_UNDO = "undo";
    public static String TASK_STATUS_FINISH = "finish";
    public static String TASK_STATUS_CANCEL = "cancel";
    private Context mContext;
    private ArrayList<RKSJTaskListBean.ContentEntity> mData = new ArrayList<>();
    private GetTaskOnClickListener mListener;

    public RKSJTaskListAdapter(Context context, GetTaskOnClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    public void updateDate(ArrayList<RKSJTaskListBean.ContentEntity> data){
        this.mData = data;
        notifyDataSetChanged();
    }
    public static int getTaskStatus(String status) {
        if (TASK_STATUS_CANCEL.equals(status))
            return R.string.canceled;
        else if (TASK_STATUS_UNDO.equals(status))
            return R.string.created;
        else if (TASK_STATUS_FINISH.equals(status))
            return R.string.finished;
        else
            return R.string.shangjia_ing;
    }

    public static int getGetTaskBtnText(String status) {
        if (TASK_STATUS_UNDO.equals(status))
            return R.string.start_shangjia;
        else if (TASK_STATUS_FINISH.equals(status))
            return R.string.finish_shangjia;
        else
            return R.string.goon_shangjia;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_rukushangjia, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final RKSJTaskListBean.ContentEntity bean = mData.get(position);
        holder.mDanJu.setText(bean.getBillCode());
        holder.mKeHu.setText(bean.getCustomer() == null ? mContext.getString(R.string.temp) :
                bean.getCustomer().getCustomerName());
        holder.mTask.setText(bean.getSubTaskCode());
        holder.mHouse.setText(bean.getWarehouse() == null ? mContext.getString(R.string.temp) : bean.getWarehouse().getGroupName());
        holder.mStatus.setText(getTaskStatus(bean.getStatus()));
        holder.tvDocumentsType.setText(bean.getTaskFromType().equals("returnOrder")?"取消单":"正常单");
        holder.mStatus.setTextColor(mContext.getResources().getColor(R.color.md_grey_900));
        holder.mStatus.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
        holder.mGetTaskBtn.setText(getGetTaskBtnText(bean.getStatus()));
        holder.mGetTaskBtn.setVisibility(View.VISIBLE);
        holder.mGetTaskBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onJump(bean);
                    }
                }
            });

        return convertView;
    }

    public interface GetTaskOnClickListener {
        void onJump(RKSJTaskListBean.ContentEntity bean);
    }


    class ViewHolder {

        private TextView mDanJu;

        private TextView mKeHu;

        private TextView mTask;

        private TextView mHouse;

        private TextView mStatus;

        private Button mGetTaskBtn;

        private TextView tvDocumentsType;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            mDanJu = (TextView) view.findViewById(R.id.danju);
            mKeHu = (TextView) view.findViewById(R.id.kehu);
            mTask = (TextView) view.findViewById(R.id.task);
            mHouse = (TextView) view.findViewById(R.id.house);
            mStatus = (TextView) view.findViewById(R.id.status);
            mGetTaskBtn = (Button) view.findViewById(R.id.get_task_btn);
            tvDocumentsType= (TextView) view.findViewById(R.id.documents_type);
        }
    }
}
