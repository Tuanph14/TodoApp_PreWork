package com.example.hoang.todoapp_prework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText etItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String strItemEdit = getIntent().getStringExtra("editItemText");
        etItem = (EditText) findViewById(R.id.etEditItem);
        etItem.setText(strItemEdit);
        etItem.setSelection(strItemEdit.length());
    }

    public  void saveEditedItem(View view){

        Intent data = new Intent();

        data.putExtra("strItemEdited",etItem.getText().toString());
        data.putExtra("code",100);
        setResult(RESULT_OK,data);
        finish();
    }
}
