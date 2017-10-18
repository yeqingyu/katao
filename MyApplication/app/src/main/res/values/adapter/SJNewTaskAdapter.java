package com.nryuncang.pda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nryuncang.pda.bean.SJNewTaskBean;
import com.nryuncang.pda.controller.StorageShelvesController;
import com.nryuncang.pda.plus.R;
import com.rey.material.widget.Button;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * 领取上架任务的列表 adapter
 * Created by Wilk on 2015/08/20 0020.
 */
public class SJNewTaskAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<SJNewTaskBean.UntakedTaskListEntity> mDatas = new ArrayList<>();

    public SJNewTaskAdapter(Context context ) {
        this.mContext = context;

    }

    public void updateDate(ArrayList<SJNewTaskBean.UntakedTaskListEntity> data){
        this.mDatas = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sj_new, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final SJNewTaskBean.UntakedTaskListEntity bean = mDatas.get(position);
        holder.mDanjuTv.setText(bean.getBillCode());
        holder.mKehuTv.setText(bean.getCustomer().getCustomerName());
        holder.mTaskTv.setText(bean.getSubTaskCode());
        holder.mDanjuType.setText(bean.getTaskFromType().equals("returnOrder")?"取消单":"正常单");
        holder.mHouseTv.setText(bean.getWarehouse().getGroupName());
        holder.mGetTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageShelvesController.getInstance().ajaxTakeUpTask(mContext,bean.getId());
            }
        });
        return convertView;
    }


    class ViewHolder {

        private TextView mDanjuTv;

        private TextView mTaskTv;

        private TextView mKehuTv;

        private TextView mHouseTv;

        private TextView mDanjuType;

        private Button mGetTaskBtn;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            mDanjuTv = (TextView) view.findViewById(R.id.danju);
            mTaskTv = (TextView) view.findViewById(R.id.task);
            mKehuTv = (TextView) view.findViewById(R.id.kehu);
            mHouseTv = (TextView) view.findViewById(R.id.house);
            mDanjuType= (TextView) view.findViewById(R.id.danjuType);
            mGetTaskBtn = (Button) view.findViewById(R.id.get_task_btn);
        }
    }
}
