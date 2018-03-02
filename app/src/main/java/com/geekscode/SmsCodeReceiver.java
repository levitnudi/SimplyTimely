package com.geekscode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;
/**
 * Created by Levit Nudi on 26/02/2018.
 */


public class SmsCodeReceiver extends BroadcastReceiver {
    SmsMessage[] msgs = null;
    String str = "";
    @Override
    public void onReceive(Context context, Intent intent)
    {
        final Bundle bundle = intent.getExtras();
        try
        {
            if (bundle != null)
            {
                //---retrieve the SMS message received---
                Object[] pdus = (Object[]) bundle.get("pdus");
                msgs = new SmsMessage[pdus.length];
                for (int i = 0; i < msgs.length; i++)
                {
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    str += msgs[i].getMessageBody().toString();
                }
                String replyPhone = msgs[0].getOriginatingAddress();
                String request = msgs[0].getMessageBody().toString();

                //you can add your specific filter here conditions
                Intent i = new Intent(context, SmsResponseService.class);
                i.putExtra("num", replyPhone);
                i.putExtra("msg", request);
                context.startService(i);

            }
        }
        catch (Exception e){}
    }
}
