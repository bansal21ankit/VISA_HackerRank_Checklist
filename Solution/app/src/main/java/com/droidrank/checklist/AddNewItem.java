package com.droidrank.checklist;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewItem extends AppCompatActivity {
    // To take the user input for the new item
    EditText itemName;

    // To add the new item to the list
    Button addItem;

    // To cancel this task
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        final ChecklistHelper helper = new ChecklistHelper(this);
        itemName = findViewById(R.id.et_item_name);
        cancel = findViewById(R.id.bt_cancel);
        addItem = findViewById(R.id.bt_ok);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = itemName.getText().toString().trim();

                // Save the new item, if it does not exist in the list display appropriate error message
                if (helper.exists(item)) toast(R.string.item_already_exists_message);
                else {
                    if (!helper.addItem(item)) toast(R.string.error);
                    else finish();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void toast(@StringRes int messageId) {
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
    }
}
