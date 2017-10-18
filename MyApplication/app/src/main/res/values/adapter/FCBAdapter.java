package com.nryuncang.pda.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nryuncang.pda.plus.R;
import com.nryuncang.pda.ui.activity.BatchReviewActivity;
import com.nryuncang.pda.ui.activity.CompositeOrderActivity;
import com.nryuncang.pda.ui.activity.DGSJActivity;
import com.nryuncang.pda.ui.activity.ElectronicSinglePrintActivity;
import com.nryuncang.pda.ui.activity.ExpressOrdersActivity;
import com.nryuncang.pda.ui.activity.JianHuoTaskListActivity;
import com.nryuncang.pda.ui.activity.KSPackageActivity;
import com.nryuncang.pda.ui.activity.KSSendoutActivity;
import com.nryuncang.pda.ui.activity.NRKDSTaskListActivity;
import com.nryuncang.pda.ui.activity.NRKSJTaskListActivity;
import com.nryuncang.pda.ui.activity.PackageActivity;
import com.nryuncang.pda.ui.activity.PackingListActivity;
import com.nryuncang.pda.ui.activity.RKDSTaskListActivity;
import com.nryuncang.pda.ui.activity.RKSJTaskListActivity;
import com.nryuncang.pda.ui.activity.SendoutActivity;
import com.nryuncang.pda.ui.activity.YiKuTaskListActivity;
import com.rey.material.widget.Button;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * 首页分仓宝功能清单adapter
 * Created by Wilk on 2015/10/14 0014.
 */
public class FCBAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mDatas;

    public FCBAdapter(Context context, String datas) {
        this.mContext = context;

        if (datas != null)
            if (!datas.contains(":")) {
                datas += ":";
            }
        this.mDatas = datas.split(":");
    }

    @Override
    public int getCount() {
        if (mDatas == null) return 0;
        return mDatas.length;
    }

    @Override
    public Object getItem(int position) {
        return mDatas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_main_function, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setView(holder.mBtn, mDatas[position]);
        return convertView;
    }

    // FIXME: 2016/6/1 第三阶段修改，抽出多态性
    private void setView(Button view, String code) {
        switch (code) {
            case "RFJH":
                setShowMoudle(view, R.string.jianhuo, R.color.md_red_A200, "main_jianhuo", JianHuoTaskListActivity.class);
                break;
            case "RFDGSJ":
                setShowMoudle(view, R.string.dengguangshangjia, R.color.md_pink_A200, "main_dgsj", DGSJActivity.class);
                break;
            case "RFBZ":
                setShowMoudle(view, R.string.baozhuang, R.color.md_purple_A200, "main_package", PackageActivity.class);
                break;
            case "RFKSBZ":
                setShowMoudle(view, R.string.kuaisubaozhuang, R.color.md_deep_purple_A200, "main_ks_package", KSPackageActivity.class);
                break;
            case "RFFH":
                setShowMoudle(view, R.string.fahuo, R.color.md_indigo_A200, "main_send", SendoutActivity.class);
                break;
            case "RFKSFH":
                setShowMoudle(view, R.string.kuaisufahuo, R.color.md_blue_A200, "main_ks_send", KSSendoutActivity.class);
                break;
            case "RFRKDS":
                setShowMoudle(view, R.string.rukudianshou, R.color.md_light_blue_A200, "main_rkds", RKDSTaskListActivity.class);
                break;
            case "RFRKSJ":
                setShowMoudle(view, R.string.rukushangjia, R.color.md_cyan_A700, "main_rksj", RKSJTaskListActivity.class);
                break;
            case "RFYK":
                setShowMoudle(view, R.string.yiku, R.color.md_yellow_800, "main_yk", YiKuTaskListActivity.class);
                break;
            case "RFKDJJ":
                setShowMoudle(view, R.string.kdjj, R.color.md_deep_orange_500, "main_kdjj", ExpressOrdersActivity.class);
                break;
            case "RFESP":
                setShowMoudle(view, R.string.esp, R.color.md_light_green_A700, "main_esp", ElectronicSinglePrintActivity.class);
                break;
            case "RFCO":
                setShowMoudle(view, R.string.composite_order, R.color.md_teal_A200, "main_co", CompositeOrderActivity.class);
                break;
            case "RFNRKDS":
                setShowMoudle(view, R.string.rukunewdianshou, R.color.md_yellow_800, "main_nrkds", NRKDSTaskListActivity.class);
                break;
            case "RFNRKSJ":
                setShowMoudle(view, R.string.rukunewshangjia, R.color.md_yellow_800, "main_nrksj", NRKSJTaskListActivity.class);
                break;
            case "RFPLFH":
                setShowMoudle(view, R.string.batch_review, R.color.md_blue_grey_500, "main_plfh", BatchReviewActivity.class);
                break;
            case "RFSMZX":
                setShowMoudle(view, R.string.packing_list, R.color.md_red_A200, "main_smzx", PackingListActivity.class);
                break;

            default:
                break;
        }
    }

    private void setShowMoudle(Button view, int stringId, int colorId, final String eventDes, final Class c) {
        if ("no".equals(eventDes)) {
            view.setText("Null");
            view.setBackgroundColor(mContext.getResources().getColor(R.color.md_red_A200));
            view.setOnClickListener(null);
        } else {
            view.setText(stringId);
            view.setBackgroundColor(mContext.getResources().getColor(colorId));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MobclickAgent.onEvent(mContext, eventDes);
                    mContext.startActivity(new Intent(mContext, c));
                }
            });
        }
    }


    static class ViewHolder {

        private Button mBtn;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            mBtn= (Button) view.findViewById(R.id.btn);
        }
    }
}
