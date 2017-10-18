package pda.nryuncang.com.myapplication.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.rey.material.widget.Button;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import pda.nryuncang.com.myapplication.R;
import pda.nryuncang.com.myapplication.ui.activity.GetPiecesManagerActivity;

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
            case "LJGL":
                setShowMoudle(view, R.string.get_pieces, R.color.md_red_A200, "main_lanjian", GetPiecesManagerActivity.class);
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
