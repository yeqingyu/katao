package com.nryuncang.pda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nryuncang.pda.bean.SJTaskDetailBean;
import com.nryuncang.pda.plus.R;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * 上架记录 adapter
 * Created by Wilk on 2015/08/19 0019.
 */
public class SJTaskRecordAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<SJTaskDetailBean.ItemListEntity> mData = new ArrayList<>();

    public SJTaskRecordAdapter(Context context, ArrayList<SJTaskDetailBean.ItemListEntity> data) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sj_record, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SJTaskDetailBean.ItemListEntity bean = mData.get(position);
        holder.sku.setText(bean.getSku());
        holder.count.setText(String.valueOf(bean.getNum()));
        holder.errorFlag.setText(bean.getErrorFlag().equals("error") ? "破损件" : "完好件");
        holder.box.setText(bean.getBoxCode());
        holder.kuwei.setText(bean.getStockCode());
        return convertView;
    }


    class ViewHolder {

        private TextView sku;

        private TextView errorFlag;

        private TextView count;

        private TextView kuwei;

        private TextView box;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            sku = (TextView) view.findViewById(R.id.sku);
            errorFlag = (TextView) view.findViewById(R.id.error_flag);
            count = (TextView) view.findViewById(R.id.count);
            kuwei = (TextView) view.findViewById(R.id.kuwei);
            box = (TextView) view.findViewById(R.id.box);
        }
    }
}
