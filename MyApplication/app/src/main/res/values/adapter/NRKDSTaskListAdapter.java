package com.nryuncang.pda.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nryuncang.pda.bean.stock.InstorageBillListBean;
import com.nryuncang.pda.plus.R;
import com.nryuncang.pda.ui.viewHolder.AbsViewHolder;
import com.rey.material.widget.Button;

import java.util.ArrayList;

/**
 * Created by libin on 2016/4/15.
 */
public class NRKDSTaskListAdapter extends RecyclerView.Adapter<AbsViewHolder> {
    private Context mContext;
    private ArrayList<InstorageBillListBean.ContentEntity> mData = new ArrayList<>();
    private GetNewTaskOnClickListener mListener;

    public NRKDSTaskListAdapter(Context context, ArrayList<InstorageBillListBean.ContentEntity> data, GetNewTaskOnClickListener listener) {
        this.mContext = context;
        this.mData = data;
        this.mListener = listener;
    }

    public void setData(ArrayList<InstorageBillListBean.ContentEntity> data) {
        if (mData != null) mData.clear();
        this.mData = data;
        notifyDataSetChanged();
    }

    public interface GetNewTaskOnClickListener {
        void onJump(InstorageBillListBean.ContentEntity bean);
    }

    @Override
    public AbsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NRKViewHolder(View.inflate(mContext, R.layout.item_new_rukudianshou, null));
    }

    @Override
    public void onBindViewHolder(AbsViewHolder holder, int position) {
        holder.fillView(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class NRKViewHolder extends AbsViewHolder {
        TextView mDanJu;
        TextView mKeHu;
        TextView mStatus;
        Button mGetTaskBtn;

        public NRKViewHolder(View itemView) {
            super(itemView);
            mDanJu = (TextView) itemView.findViewById(R.id.danju);
            mKeHu = (TextView) itemView.findViewById(R.id.kehu);
            mStatus = (TextView) itemView.findViewById(R.id.status);
            mGetTaskBtn = (Button) itemView.findViewById(R.id.get_task_btn);


        }

        @Override
        public void fillView(Object o) {
            if (o == null) return;
            if (o instanceof InstorageBillListBean.ContentEntity) {
                final InstorageBillListBean.ContentEntity bean = (InstorageBillListBean.ContentEntity) o;
                mDanJu.setText(bean.getBillCode() + "");
                mKeHu.setText("");
                if (bean.getCustomer() != null) {
                    mKeHu.setText(bean.getCustomer().getCustomerName() == null ? "" : bean.getCustomer().getCustomerName());
                }
                if ("0".equals(bean.getNewstatus())|| "1".equals(bean.getNewstatus())  || "2".equals(bean.getNewstatus()) || "3".equals(bean.getNewstatus())) {
                    mGetTaskBtn.setEnabled(true);
                } else {
                    mGetTaskBtn.setEnabled(false);
                }
                mGetTaskBtn.setText(getStringRes(bean.getNewstatus()));
                mGetTaskBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onJump(bean);
                        }
                    }
                });
                mStatus.setText(getStatus(bean.getNewstatus()));
            }

        }

        private String getStringRes(String state) {
            return state.equals("0") ?mContext.getString(R.string.start_dianshou ) :mContext.getString(R.string.goon_dianshou );
        }
        private String getStatus(String state) {
            switch (state) {
                case "0":
                    return mContext.getString(R.string.ins_bill_status_0);
                case "1":
                    return mContext.getString(R.string.ins_bill_status_1);
                case "2":
                    return mContext.getString(R.string.ins_bill_status_2);
                case "3":
                    return mContext.getString(R.string.ins_bill_status_3);
            }
            return mContext.getString(R.string.ins_bill_status_0);
        }
    }
}

