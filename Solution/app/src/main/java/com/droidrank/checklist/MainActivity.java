package com.droidrank.checklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ChecklistHelper mChecklistHelper;
    private ChecklistAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mChecklistHelper = new ChecklistHelper(this);

        ListView listView = findViewById(R.id.list_view);
        mAdapter = new ChecklistAdapter(this, getDataSet());
        listView.setAdapter(mAdapter);

        findViewById(R.id.bt_add_new_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNewItem.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter != null) mAdapter.updateDataSet(getDataSet());
    }

    private List<Item> getDataSet() {
        List<Item> data = mChecklistHelper.getItems();

        if (data == null || data.isEmpty()) { // Add default items in database and show them
            String[] defaultItems = getResources().getStringArray(R.array.default_items);
            for (String item : defaultItems) mChecklistHelper.addItem(item);
            data = mChecklistHelper.getItems();
        }

        return data;
    }

    public void onCheckChanged(long id, boolean isChecked) {
        if (mChecklistHelper.updateSelection(id, isChecked)) {
            toast(isChecked ? R.string.checked : R.string.unchecked);
            mAdapter.updateDataSet(getDataSet());
        }
    }

    public void onRemoved(long id) {
        if (mChecklistHelper.delete(id)) {
            mAdapter.updateDataSet(getDataSet());
            toast(R.string.removed);
        }
    }

    private void toast(@StringRes int messageId) {
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
    }
}
