package com.droidrank.checklist;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

class ChecklistItemVH {
    private CheckBox mCbChecked;
    private TextView mTvName;
    private Button mBtnRemove;

    ChecklistItemVH(View rootView) {
        mCbChecked = rootView.findViewById(R.id.cb_item_status);
        mTvName = rootView.findViewById(R.id.tv_item_name);
        mBtnRemove = rootView.findViewById(R.id.bt_item_delete);
    }

    void setData(Item item, CompoundButton.OnCheckedChangeListener checkedChangeListener, View.OnClickListener clickListener) {
        mCbChecked.setOnCheckedChangeListener(null);
        mCbChecked.setChecked(item.isChecked());
        mTvName.setText(item.getName());

        mCbChecked.setOnCheckedChangeListener(checkedChangeListener);
        mBtnRemove.setOnClickListener(clickListener);
    }
}
