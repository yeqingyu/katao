package com.nryuncang.pda.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nryuncang.pda.plus.R;
import com.nryuncang.pda.ui.viewHolder.AbsViewHolder;


/**
 * Created by Administrator on 2016/4/27.
 */
public class BatchTaskAdapter extends RecyclerView.Adapter<AbsViewHolder> {
    private String item;
    Context context;

    public BatchTaskAdapter(Context context) {
        this.context = context;
    }

    public void setContent(String item) {
        this.item = item;
        notifyDataSetChanged();
    }

    @Override
    public AbsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new BatchTaskViewHolder(View.inflate(context, R.layout.item_task_detail, null));
    }

    @Override
    public void onBindViewHolder(AbsViewHolder holder, int position) {
        holder.fillView(item);

    }

    @Override
    public int getItemCount() {
        if(item==null) return 0;
        return 1;
    }

    class BatchTaskViewHolder extends AbsViewHolder {
        private TextView tv;
        public BatchTaskViewHolder(View itemView) {
            super(itemView);
            tv= (TextView) itemView.findViewById(R.id.tv_task);
        }

        @Override
        public void fillView(Object o) {
            if(o==null) return;
            if(o instanceof String){
                tv.setText(o.toString());
            }
        }
    }
}
