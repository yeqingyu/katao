package com.nryuncang.pda.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nryuncang.pda.bean.stock.UpstorageItemBean;
import com.nryuncang.pda.plus.R;
import com.nryuncang.pda.ui.viewHolder.AbsViewHolder;

import java.util.ArrayList;

import static com.nryuncang.pda.utils.showNameByCodeUtil.showErrorFlgNameByCode;


/**
 * Created by libin on 2016/4/21.
 */
public class UpsScanListAdapter extends RecyclerView.Adapter<AbsViewHolder> {
    private Context mContext;
    private ArrayList<UpstorageItemBean> mData = new ArrayList<>();

    public UpsScanListAdapter(Context context, ArrayList<UpstorageItemBean> mData) {
        this.mContext = context;
        this.mData = mData;
    }

    public void setData(ArrayList<UpstorageItemBean> tmpDataList) {
        this.mData = tmpDataList;
        notifyDataSetChanged();
    }


    @Override
    public AbsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NRKViewHolder(View.inflate(mContext, R.layout.item_nds_scan2_data, null));
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
        private TextView insDataItem;

        public NRKViewHolder(View itemView) {
            super(itemView);
            insDataItem = (TextView) itemView.findViewById(R.id.ins_data_item);
        }

        @Override
        public void fillView(Object o) {
            if (o == null) return;
            if (o instanceof UpstorageItemBean) {
                final UpstorageItemBean bean = (UpstorageItemBean) o;
                StringBuffer tmpTest = new StringBuffer();
                tmpTest.append("入库单号：").append(bean.getBillCode() + "\n");
                tmpTest.append("商家名称：").append(bean.getCustomer().getCustomerName() + "\n");
                tmpTest.append("商品名称：").append(bean.getSkuName() + "\n");
                tmpTest.append("报损状态：").append(showErrorFlgNameByCode(bean.getErrorFlag()) + "\n");
                tmpTest.append("LPN编码：").append(bean.getLpnNo() + "\n");
                tmpTest.append("SKU编码：").append(bean.getSku() + "\n");
                tmpTest.append("库位编码：").append(bean.getStockCode() + "\n");
                tmpTest.append("收货数量：").append(bean.getCountNum() + "\n");
                tmpTest.append("上架数量：").append(bean.getUpNum() + "\n");
                tmpTest.append("已上架数：").append(bean.getUpNum());
                insDataItem.setText(tmpTest.toString());
            }
        }
    }
}