package com.nryuncang.pda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nryuncang.pda.bean.DSUnTakedListBean;
import com.nryuncang.pda.controller.CheckAcceptController;
import com.nryuncang.pda.plus.R;
import com.rey.material.widget.Button;

import java.util.ArrayList;

import butterknife.ButterKnife;


/**
 * 未领取的点收任务adapter
 * Created by Wilk on 2015/09/22 0022.
 */
public class DSNewTaskAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<DSUnTakedListBean.UntakedTaskListEntity> mDatas = new ArrayList<>();
    public DSNewTaskAdapter(Context context, ArrayList<DSUnTakedListBean.UntakedTaskListEntity> data) {
        this.mContext = context;
        this.mDatas = data;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_ds_new, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // TODO: 2016/6/14 由于缓存的原因，需要在这里增加判断 
        if(mDatas.get(position) instanceof DSUnTakedListBean.UntakedTaskListEntity)
        holder.fillView(mDatas.get(position));
        return convertView;
    }

    class ViewHolder {

        private TextView mDanjuTv;

        private TextView mTaskTv;

        private TextView mKehuTv;

        private Button mGetTaskBtn;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            mDanjuTv = (TextView) view.findViewById(R.id.danju);
            mTaskTv = (TextView) view.findViewById(R.id.task);
            mKehuTv = (TextView) view.findViewById(R.id.kehu);
            mGetTaskBtn = (Button) view.findViewById(R.id.get_task_btn);
        }
        void fillView(final DSUnTakedListBean.UntakedTaskListEntity bean) {
            mDanjuTv.setText(bean.getBillCode());
            mKehuTv.setText(bean.getCustomer().getCustomerName());
            mTaskTv.setText(bean.getTaskCode());
            mGetTaskBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckAcceptController.getInstance().getTakeCountTaskDetail(mContext, bean.getId());
                }
            });
        }
    }
}
