package com.nryuncang.pda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nryuncang.pda.bean.QueryBean;
import com.nryuncang.pda.plus.R;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * 查询结果列表adapter
 * Created by Wilk on 2015/7/15.
 */
public class QueryListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<QueryBean.ContentEntity> mData = new ArrayList<>();

    public QueryListAdapter(Context context, ArrayList<QueryBean.ContentEntity> data) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_query, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        QueryBean.ContentEntity bean = mData.get(position);
        holder.mKuwei.setText(bean.getStockCode());
        holder.mSku.setText(bean.getSku());
        holder.mCount.setText(String.valueOf(bean.getSalesNum()));
        return convertView;
    }


    class ViewHolder {

        private TextView mKuwei;

        private TextView mSku;

        private TextView mCount;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            mKuwei = (TextView) view.findViewById(R.id.kuwei);
            mSku = (TextView) view.findViewById(R.id.sku);
            mCount = (TextView) view.findViewById(R.id.count);
        }
    }
}
