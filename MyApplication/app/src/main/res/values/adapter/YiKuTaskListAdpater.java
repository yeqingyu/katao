package com.nryuncang.pda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nryuncang.pda.bean.YiKuTaskListBean;
import com.nryuncang.pda.constant.BaseConstant;
import com.nryuncang.pda.plus.R;
import com.rey.material.widget.Button;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * 未完成移库单列表界面
 * Created by Wilk on 2015/08/31 0031.
 */
public class YiKuTaskListAdpater extends BaseAdapter {
    private Context mContext;
    private ArrayList<YiKuTaskListBean.MoveListEntity> mDatas = new ArrayList<>();
    private ClickListener mOnClickListener;

    public YiKuTaskListAdpater(Context context, ArrayList<YiKuTaskListBean.MoveListEntity> data, ClickListener listener) {
        this.mContext = context;
        this.mDatas = data;
        this.mOnClickListener = listener;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_yiku_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final YiKuTaskListBean.MoveListEntity bean = mDatas.get(position);
        holder.mSku.setText(bean.getSku());
        holder.mSkuName.setText(bean.getSkuName());
        holder.mKehu.setText(bean.getCustomerName());
        holder.mHouse.setText(bean.getWarehouseName());
        holder.mProgress.setText(bean.getInMoveNum() + "/" + mDatas.get(position).getOutMoveNum());
        if (bean.getGoodsStatus().equals(BaseConstant.GOODS_STATUS_SALE)) {
            holder.mStatus.setText("完好件");
            holder.mStatus.setBackgroundColor(mContext.getResources().getColor(R.color.md_green_600));
        } else {
            holder.mStatus.setText("破损件");
            holder.mStatus.setBackgroundColor(mContext.getResources().getColor(R.color.md_red_600));
        }
        if (bean.getStatus().equals("finish")) {
            holder.mEnterBtn.setVisibility(View.GONE);
            holder.mEnterBtn.setOnClickListener(null);
        } else {
            holder.mEnterBtn.setVisibility(View.VISIBLE);
            holder.mEnterBtn.setText("继续移库");
            holder.mEnterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickListener != null) {
                        mOnClickListener.onClick(bean);
                    }
                }
            });
        }
        return convertView;
    }

    public interface ClickListener {
        void onClick(YiKuTaskListBean.MoveListEntity bean);
    }


    class ViewHolder {

        private TextView mSku;

        private TextView mSkuName;

        private TextView mKehu;

        private TextView mHouse;

        private TextView mProgress;

        private TextView mStatus;

        private Button mEnterBtn;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            mSku = (TextView) view.findViewById(R.id.sku);
            mSkuName = (TextView) view.findViewById(R.id.sku_name);
            mKehu = (TextView) view.findViewById(R.id.kehu);
            mHouse = (TextView) view.findViewById(R.id.house);
            mProgress = (TextView) view.findViewById(R.id.progress);
            mStatus = (TextView) view.findViewById(R.id.status);
            mEnterBtn = (Button) view.findViewById(R.id.enter);
        }
    }
}
