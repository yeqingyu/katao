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
 * 上架任务详情 adapter
 * Created by Wilk on 2015/08/18 0018.
 */
public class SJTaskDetailAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<SJTaskDetailBean.UnUpItemEntity> mDatas = new ArrayList<>();

    public SJTaskDetailAdapter(Context context, ArrayList<SJTaskDetailBean.UnUpItemEntity> data) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sj_detail, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SJTaskDetailBean.UnUpItemEntity bean = mDatas.get(position);
        holder.sku.setText(bean.getSkuCode().split("_")[0]);
        holder.errorFlag.setText(bean.getErrorFlag().equals("error") ? "破损件" : "完好件");
        holder.count.setText(String.valueOf(bean.getNum()));
        if (bean.getNum() == bean.getUpNum()) {
            holder.unUpNum.setText(R.string.finished);
            holder.unUpNum.setBackgroundColor(mContext.getResources().getColor(R.color.md_green_600));
        } else {
            holder.unUpNum.setText("未上架数:" + (bean.getNum() - bean.getUpNum()));
            holder.unUpNum.setBackgroundColor(mContext.getResources().getColor(R.color.md_red_600));
        }
        return convertView;
    }


   class ViewHolder {

        private TextView sku;

        private TextView errorFlag;

        private TextView count;

        private TextView unUpNum;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            sku = (TextView) view.findViewById(R.id.sku);
            errorFlag = (TextView) view.findViewById(R.id.error_flag);
            count = (TextView) view.findViewById(R.id.count);
            unUpNum = (TextView) view.findViewById(R.id.un_up_num);
        }
    }
}
