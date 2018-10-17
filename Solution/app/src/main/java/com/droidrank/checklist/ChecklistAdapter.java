package com.droidrank.checklist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;

import java.util.List;

class ChecklistAdapter extends BaseAdapter {
    private final MainActivity mActivity;
    private List<Item> mDataSet;

    ChecklistAdapter(MainActivity activity, List<Item> dataSet) {
        this.mActivity = activity;
        this.mDataSet = dataSet;
    }

    void updateDataSet(List<Item> dataSet) {
        mDataSet = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    @Override
    public Item getItem(int i) {
        return mDataSet.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getId();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ChecklistItemVH holder;

        if (view == null) {
            view = LayoutInflater.from(mActivity).inflate(R.layout.check_list_item, viewGroup, false);
            holder = new ChecklistItemVH(view);
            view.setTag(holder);
        } else holder = (ChecklistItemVH) view.getTag();

        holder.setData(getItem(i),
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        mActivity.onCheckChanged(getItemId(i), isChecked);
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mActivity.onRemoved(getItemId(i));
                    }
                });

        return view;
    }
}
