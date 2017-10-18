package pda.nryuncang.com.myapplication.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 首页viewpager的adpater
 * Created by Wilk on 2015/6/24.
 */
public class MainPagerAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<View> mViews = new ArrayList<>();
    private String[] mTitles;

    public MainPagerAdapter(Context context, ArrayList<View> views, String[] title) {
        this.mContext = context;
        this.mViews = views;
        this.mTitles = title;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
