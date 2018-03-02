package com.geekscode;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Doris Muthoni on 26/02/2018.
 */

public class EditTimeTableActivity extends AppCompatActivity {
    SharedPreferences shared;
    SharedPreferences.Editor editor;
    String code = "000";

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_fragment);

        Bundle bundle = getIntent().getExtras();

        if(bundle !=null){
          code = bundle.getString("coursecode");
            //Toast.makeText(getApplicationContext(), code, Toast.LENGTH_LONG).show();
        }

        ActionBar actionBar = getSupportActionBar();

        if(actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Editing Course Code "+code);
        }


        shared = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = shared.edit();

        Button loginBtn = (Button) findViewById(R.id.saveBtn);
        final EditText codeInput = (EditText) findViewById(R.id.courseCode);
        final EditText timeTableInput = (EditText) findViewById(R.id.timeTableContent);

        if (shared.getString(code, null) != null) {
            codeInput.setText(code);
            timeTableInput.setText(shared.getString(code, null));
        }

        codeInput.setEnabled(false);//disable editing

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (timeTableInput.getText().length() != 0) {
                    if (shared.getString(code, null) != null) {

                        editor.putString(code, timeTableInput.getText().toString());
                        editor.commit();
                        //startActivity(new Intent(EditTimeTableActivity.this, DashboardActivity.class));
                        Toast.makeText(v.getContext(), "Changes Saved!", Toast.LENGTH_LONG).show();
                        finish();

                    }
                } else {
                    Toast.makeText(v.getContext(), "Field cannot be empty", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
