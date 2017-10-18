package com.nryuncang.pda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nryuncang.pda.bean.JianHuoDetailListBean;
import com.nryuncang.pda.bean.JianHuoTaskBean;
import com.nryuncang.pda.plus.R;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * 拣货任务的货品列表详情adapter
 * Created by Wilk on 2015/7/30.
 */
public class JianHuoDetailListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<JianHuoDetailListBean.TaskItemsEntity> mData = new ArrayList<>();

    public JianHuoDetailListAdapter(Context context, ArrayList<JianHuoDetailListBean.TaskItemsEntity> data) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_jianhuo_detail, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        JianHuoDetailListBean.TaskItemsEntity bean = mData.get(position);
        holder.mSku.setText(bean.getSku());
        holder.mSkuName.setText(bean.getSkuName());
        holder.mKuwei.setText(bean.getStockCode());
        holder.mBox.setText(bean.getBoxCode());
        if (bean.getStockCode().equals(bean.getBoxCode())) {
            holder.mBoxTitle.setVisibility(View.GONE);
            holder.mBox.setVisibility(View.GONE);
        } else {
            holder.mBoxTitle.setVisibility(View.VISIBLE);
            holder.mBox.setVisibility(View.VISIBLE);
        }
        int num = bean.getNum() - bean.getPickedNum();
        if (num == 0) {
            bean.setPickedNum(bean.getNum());
            holder.mTips.setBackgroundColor(mContext.getResources().getColor(R.color.md_green_600));
            holder.mTips.setText(mContext.getString(R.string.tips_alert_done));
        } else {
            holder.mTips.setBackgroundColor(mContext.getResources().getColor(R.color.md_red_600));
            holder.mTips.setText(String.valueOf(num));
        }
        if (0!=bean.getPickbyone()){
            holder.mPickTips.setText(String.valueOf(bean.getPickbyone()));
            holder.mPickTips.setVisibility(View.VISIBLE);
        }else {
            holder.mPickTips.setVisibility(View.GONE);
        }
        holder.mShowxh.setText(bean.getShowxh());
        clearShowxh();
        return convertView;
    }

    public void clearShowxh(){

    }
    public void updateItem(JianHuoDetailListBean.TaskItemsEntity bean) {
        sortData(bean);
        notifyDataSetChanged();
    }

    public void updateItem(JianHuoTaskBean bean, JianHuoDetailListBean.TaskItemsEntity itemBean) {
        if (bean != null && bean.getTaskItem() != null) {
            for (JianHuoDetailListBean.TaskItemsEntity item : mData) {
                if (item.getStockCode().equals(bean.getTaskItem().getStockCode())
                        && item.getSku().equals(bean.getTaskItem().getSku())) {
                    item.setPickedNum(bean.getTaskItem().getPickedNum());
                    item.setNum(bean.getTaskItem().getNum());
                    break;
                }
            }

            for (JianHuoDetailListBean.TaskItemsEntity item : mData) {
                if (item.getStockCode().equals(itemBean.getStockCode())
                        && item.getSku().equals(itemBean.getSku())) {
                    item.setShowxh(itemBean.getShowxh());
                    break;
                }
            }
        }
        sortData(bean);
        notifyDataSetChanged();
    }

    private void sortData(JianHuoDetailListBean.TaskItemsEntity bean) {
        ArrayList<JianHuoDetailListBean.TaskItemsEntity> head = new ArrayList<>();
        ArrayList<JianHuoDetailListBean.TaskItemsEntity> center = new ArrayList<>();
        ArrayList<JianHuoDetailListBean.TaskItemsEntity> last = new ArrayList<>();
        for (JianHuoDetailListBean.TaskItemsEntity item : mData) {
            if (item.getNum() - item.getPickedNum() == 0) {
                last.add(item);
            } else if (item.getPickedNum() < item.getNum()
                    && (bean != null && bean.getSku().equals(item.getSku()))) {
                head.add(item);
            } else {
                center.add(item);
            }
        }
        mData.clear();
        mData.addAll(head);
        mData.addAll(center);
        mData.addAll(last);
    }

    private void sortData(JianHuoTaskBean bean) {
        ArrayList<JianHuoDetailListBean.TaskItemsEntity> head = new ArrayList<>();
        ArrayList<JianHuoDetailListBean.TaskItemsEntity> center = new ArrayList<>();
        ArrayList<JianHuoDetailListBean.TaskItemsEntity> last = new ArrayList<>();
        for (JianHuoDetailListBean.TaskItemsEntity item : mData) {
            if (item.getNum() - item.getPickedNum() == 0) {
                last.add(item);
            } else if (item.getPickedNum() < item.getNum()
                    && (bean != null && bean.getTaskItem() != null
                    && bean.getTaskItem().getSku().equals(item.getSku()))) {
                head.add(item);
            } else {
                center.add(item);
            }
        }
        mData.clear();
        mData.addAll(head);
        mData.addAll(center);
        mData.addAll(last);
    }


    static class ViewHolder {

        private TextView mSku;

        private TextView mSkuName;

        private TextView mKuwei;

        private TextView mBoxTitle;

        private TextView mShowxh;

        private TextView mBox;

        private TextView mTips;

        TextView mPickTips;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            mSku= (TextView) view.findViewById(R.id.sku);
            mSkuName= (TextView) view.findViewById(R.id.sku_name);
            mKuwei= (TextView) view.findViewById(R.id.kuwei);
            mBoxTitle= (TextView) view.findViewById(R.id.title_box);
            mShowxh= (TextView) view.findViewById(R.id.showxh);
            mBox= (TextView) view.findViewById(R.id.box);
            mTips= (TextView) view.findViewById(R.id.tips);
            mPickTips= (TextView) view.findViewById(R.id.picktips);
        }
    }
}
