package com.geekscode;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Doris Muthoni on 26/02/2018.
 */

public class GetStartedFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_getstarted, container, false);
        ((TextView) view.findViewById(R.id.levit)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) view.findViewById(R.id.jerusha)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) view.findViewById(R.id.betty)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) view.findViewById(R.id.doris)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) view.findViewById(R.id.githubURL)).setMovementMethod(LinkMovementMethod.getInstance());

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);


    }

}
