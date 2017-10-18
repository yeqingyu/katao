package com.nryuncang.pda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nryuncang.pda.plus.R;

import java.util.HashSet;

import butterknife.ButterKnife;

/**
 * 发货记录列表adapter
 * Created by Wilk on 2015/7/22.
 */
public class SendOutAdapter extends BaseAdapter {
    private Context mContext;
    private HashSet<String> mData = new HashSet<>();

    public SendOutAdapter(Context context, HashSet<String> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.toArray()[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sendout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mExpressNum.setText((String) mData.toArray()[position]);
        return convertView;
    }

    class ViewHolder {

        private TextView mExpressNum;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            mExpressNum = (TextView) view.findViewById(R.id.express_num);

        }
    }
}
