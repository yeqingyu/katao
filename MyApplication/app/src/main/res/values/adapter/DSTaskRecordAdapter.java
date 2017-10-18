package com.nryuncang.pda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nryuncang.pda.bean.DSRecordBean;
import com.nryuncang.pda.plus.R;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * 点收任务详情列表
 * Created by Wilk on 2015/09/23 0023.
 */
public class DSTaskRecordAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<DSRecordBean.ItemsEntity> mDatas;

    public DSTaskRecordAdapter(Context context, ArrayList<DSRecordBean.ItemsEntity> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_ds_detail, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.fillView(mDatas.get(position));
        return convertView;
    }

    class ViewHolder {

        private TextView mSku;

        private TextView mErrorFlag;

        private TextView mCount, mAdvanceNumber;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            mSku = (TextView) view.findViewById(R.id.sku);
            mErrorFlag = (TextView) view.findViewById(R.id.error_flag);
            mCount = (TextView) view.findViewById(R.id.count);
            mAdvanceNumber = (TextView) view.findViewById(R.id.advance_number);
        }

        void fillView(DSRecordBean.ItemsEntity bean) {
            mSku.setText(bean.getSku());
            mCount.setText(String.valueOf(bean.getNum()));
            mErrorFlag.setText(bean.getErrorFlag().equals("error") ? "破损件" : "完好件");
            mAdvanceNumber.setBackgroundColor(mContext.getResources().getColor(R.color.md_cyan_A700));
            mAdvanceNumber.setText(String.valueOf(bean.getPlanNum()));
        }
    }
}
