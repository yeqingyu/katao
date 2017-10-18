package com.nryuncang.pda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nryuncang.pda.bean.PackageBean;
import com.nryuncang.pda.plus.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;

/**
 * 打包列表adapter
 * Created by Wilk on 2015/7/16.
 */
public class PackageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<PackageBean.PackageBeanDetailItem> mData = new ArrayList<>();
    private HashMap<String, Integer> mPackagedMap = new HashMap<>();

    public PackageAdapter(Context context, ArrayList<PackageBean.PackageBeanDetailItem> data,
                          HashMap<String, Integer> map) {
        this.mContext = context;
        this.mData = data;
        this.mPackagedMap = map;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_ks_package, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PackageBean.PackageBeanDetailItem bean = mData.get(position);
        int mLeft = 0;
        if (mPackagedMap.containsKey(bean.getSku()) && mPackagedMap.get(bean.getSku()) > 0) {
            mLeft = mPackagedMap.get(bean.getSku());
        }
        holder.mSku.setText(bean.getSku());
        holder.mCount.setText(String.valueOf(bean.getQty()));
        holder.mPackaged.setText(String.valueOf(bean.getQty() - mLeft));
        if (bean.getSellFlg() != null && bean.getSellFlg().equals("0")) {
            holder.mTips.setText("赠品");
            holder.mTips.setBackgroundColor(mContext.getResources().getColor(R.color.md_green_600));
        } else if (mLeft == 0) {
            holder.mTips.setText(R.string.finished);
            holder.mTips.setBackgroundColor(mContext.getResources().getColor(R.color.md_green_600));
        } else {
            holder.mTips.setText("剩余:" + mLeft);
            holder.mTips.setBackgroundColor(mContext.getResources().getColor(R.color.md_red_600));
        }
        return convertView;
    }

    public synchronized void updateItem(String sku, int num) {
        if (mPackagedMap.containsKey(sku)) {
            mPackagedMap.put(sku, mPackagedMap.get(sku) - num);
        }
        sortData(sku);
    }

    public synchronized void sortData(String sku) {
        ArrayList<PackageBean.PackageBeanDetailItem> head = new ArrayList<>();
        ArrayList<PackageBean.PackageBeanDetailItem> center = new ArrayList<>();
        ArrayList<PackageBean.PackageBeanDetailItem> last = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            PackageBean.PackageBeanDetailItem bean = mData.get(i);
            int left = 0;
            if (mPackagedMap.containsKey(bean.getSku()) && mPackagedMap.get(bean.getSku()) > 0) {
                left = mPackagedMap.get(bean.getSku());
            }
            if (bean.getSellFlg() != null && bean.getSellFlg().equals("0")) {
                last.add(bean);
            } else if (bean.getSku().equals(sku) && left > 0) {
                head.add(bean);
            } else if (left > 0) {
                center.add(bean);
            } else {
                last.add(bean);
            }
        }
        mData.clear();
        mData.addAll(head);
        mData.addAll(center);
        mData.addAll(last);
        notifyDataSetChanged();
    }

    public void clearAllData() {
        mData.clear();
        mPackagedMap.clear();
    }


    static class ViewHolder {

        private TextView mSku;

        private TextView mCount;

        private TextView mPackaged;

        private TextView mTips;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            mSku = (TextView) view.findViewById(R.id.sku);
            mCount = (TextView) view.findViewById(R.id.count);
            mPackaged = (TextView) view.findViewById(R.id.packaged);
            mTips = (TextView) view.findViewById(R.id.tips);
        }
    }
}
