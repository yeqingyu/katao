package com.nryuncang.pda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nryuncang.pda.bean.RKDSTaskListBean;
import com.nryuncang.pda.plus.R;
import com.rey.material.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 拣货任务列表adapter
 * Created by Wilk on 2015/6/25.
 */
public class RKDSTaskListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<RKDSTaskListBean.ContentEntity> mData = new ArrayList<>();
    private GetTaskOnClickListener mListener;

    public RKDSTaskListAdapter(Context context, GetTaskOnClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    public void setContent(List<RKDSTaskListBean.ContentEntity> content) {
        if (mData != null) mData.clear();
        mData.addAll(content);
        notifyDataSetChanged();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_rukudianshou, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // FIXME: 2016/6/14  没找到具体的原因，会继续查找 由于缓存的原因
        if (mData.get(position) instanceof RKDSTaskListBean.ContentEntity) {
            holder.mDanJu.setText(mData.get(position).getBillCode());
            holder.mKeHu.setText("");
            if (mData.get(position).getCustomer() != null) {
                holder.mKeHu.setText(mData.get(position).getCustomer().getCustomerName() == null ? "" : mData.get(position).getCustomer().getCustomerName());
            }
            holder.mTask.setText(mData.get(position).getTaskCode());
            if ("cancel".equals(mData.get(position).getStatus())) {
                holder.mGetTaskBtn.setText("");
                holder.mGetTaskBtn.setOnClickListener(null);
                holder.mGetTaskBtn.setVisibility(View.GONE);
                holder.mStatus.setText(R.string.canceled);
                holder.mStatus.setTextColor(mContext.getResources().getColor(R.color.md_red_600));
            } else {
                holder.mGetTaskBtn.setText(R.string.start_dianshou);
                holder.mGetTaskBtn.setVisibility(View.VISIBLE);
                holder.mStatus.setText(R.string.dianshou_ing);
                holder.mStatus.setTextColor(mContext.getResources().getColor(R.color.md_grey_900));
                holder.mGetTaskBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onJump(mData.get(position));
                        }
                    }
                });
            }
        }
        return convertView;
    }

    public interface GetTaskOnClickListener {
        void onJump(RKDSTaskListBean.ContentEntity bean);
    }


    class ViewHolder {

        private TextView mDanJu;

        private TextView mKeHu;

        private TextView mTask;

        private TextView mStatus;

        private Button mGetTaskBtn;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            mDanJu = (TextView) view.findViewById(R.id.danju);
            mKeHu = (TextView) view.findViewById(R.id.kehu);
            mTask = (TextView) view.findViewById(R.id.task);
            mStatus = (TextView) view.findViewById(R.id.status);
            mGetTaskBtn = (Button) view.findViewById(R.id.get_task_btn);
        }
    }
}
