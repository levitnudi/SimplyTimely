package com.geekscode;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Doris Muthoni on 26/02/2018.
 */

public class AddTimetableFragment extends Fragment {
SharedPreferences shared;
SharedPreferences.Editor editor;
    EditText codeInput, timeTableInput;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_fragment, container, false);
        shared = getActivity().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = shared.edit();

        Button saveBtn = view.findViewById(R.id.saveBtn);
        codeInput = view.findViewById(R.id.courseCode);
        timeTableInput = view.findViewById(R.id.timeTableContent);

        //Toast.makeText(getContext(), shared.getString("100", ""), Toast.LENGTH_LONG).show();

        timeTableInput.setHint(getString(R.string.input_demo));

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(codeInput.getText().length()!=0 && timeTableInput.getText().length()!=0) {
                    if (shared.getString(codeInput.getText().toString(), null) == null) {

                        editor.putString(codeInput.getText().toString().replaceAll(" ", ""), timeTableInput.getText().toString());
                        editor.commit();
                        codeInput.setText(null);
                        timeTableInput.setText(null);
                        timeTableInput.setHint(getString(R.string.input_demo));
                        Toast.makeText(v.getContext(), "Saved Successfully!", Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(v.getContext(), "This course already exists", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(v.getContext(), "Fields cannot be empty", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser && timeTableInput!=null) {
            timeTableInput.setHint(getString(R.string.input_demo));
        }

    }




}
