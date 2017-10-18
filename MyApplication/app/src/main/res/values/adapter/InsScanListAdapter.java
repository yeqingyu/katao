package com.nryuncang.pda.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nryuncang.pda.bean.stock.InstorageItemListBean;
import com.nryuncang.pda.plus.R;
import com.nryuncang.pda.ui.viewHolder.AbsViewHolder;

import java.util.ArrayList;

import static com.nryuncang.pda.utils.showNameByCodeUtil.showErrorFlgNameByCode;


/**
 * Created by libin on 2016/4/21.
 */
public class InsScanListAdapter extends RecyclerView.Adapter<AbsViewHolder> {
    private Context mContext;
    private ArrayList<InstorageItemListBean.ContentEntity> mData = new ArrayList<>();

    public InsScanListAdapter(Context context, ArrayList<InstorageItemListBean.ContentEntity> mData) {
        this.mContext = context;
        this.mData = mData;
    }

    public void setData(ArrayList<InstorageItemListBean.ContentEntity> tmpDataList) {
        this.mData = tmpDataList;
        notifyDataSetChanged();
    }


    @Override
    public AbsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NRKViewHolder(View.inflate(mContext, R.layout.item_nds_scan2_data, null));
    }

    @Override
    public void onBindViewHolder(AbsViewHolder holder, int position) {
        if(mData.get(position)==null) return;
        holder.fillView(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class NRKViewHolder extends AbsViewHolder {
        private TextView insDataItem;

        public NRKViewHolder(View itemView) {
            super(itemView);
            insDataItem = (TextView) itemView.findViewById(R.id.ins_data_item);
        }

        @Override
        public void fillView(Object o) {
            if (o == null) return;
            if (o instanceof InstorageItemListBean.ContentEntity) {
                final InstorageItemListBean.ContentEntity bean = (InstorageItemListBean.ContentEntity) o;
                StringBuffer tmpTest = new StringBuffer();
                tmpTest.append("商家名称：").append(bean.getCustomer().getCustomerName() + "\n");
                tmpTest.append("商品名称：").append(bean.getSkuName() + "\n");
                tmpTest.append("报损状态：").append(showErrorFlgNameByCode(bean.getErrorFlag()) + "\n");
                tmpTest.append("LPN编码：").append(bean.getLpn() + "\n");
                tmpTest.append("SKU编码：").append(bean.getSku() + "\n");
                tmpTest.append("预收数量：").append(bean.getGoodsNum() + "\n");
                tmpTest.append("收货数量：").append(bean.getCountNum() + "\n");
                tmpTest.append("上架数量：").append(bean.getUpNum() + "\n");
                tmpTest.append("扫描数量：").append(bean.getScan_num());
                insDataItem.setText(tmpTest.toString());
            }
        }
    }
}