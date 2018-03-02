package com.geekscode;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.telephony.SmsManager;
import java.util.ArrayList;

/**
 * Created by Levit Nudi on 26/02/2018.
 */

public class SmsResponseService extends Service {
	Intent intent;
	private static SmsResponseService inst;
    String response = "Thank you for using SimplyTimely Please ensure you send the correct course code";
    SharedPreferences shared;
    SharedPreferences.Editor editor;
	public static SmsResponseService instance() {
        return inst;
    }
    @SuppressWarnings("deprecation")
	public void onStart() {
        super.onStart(intent, 0);
        inst = this;
    }

@Override
public void onCreate()
{
   super.onCreate();


}
@Override
public int onStartCommand(Intent intent, int flags, int startId) {
    String code = intent.getStringExtra("msg");
    String number = intent.getStringExtra("num");

    shared = getSharedPreferences("SimplyTimely", Context.MODE_PRIVATE);
    editor = shared.edit();

    //remove unnecessary spaces
    code = code.replaceAll(" ", "");

    //let's check if the course code exists and that it has content
    if(shared.getString(code, null) != null) {
        //Toast.makeText(getApplicationContext(), "this code "+code+" exists", Toast.LENGTH_LONG).show();
        try {

            response = shared.getString(code, null);
            //append message footer/signature right after our timetable schedule content
            response += "\nPowered by SimplyTimely";
            sendSMS(number, response);

        } catch (Exception e) {
            response = "Thank you for using SimplyTimely. Please ensure you send the correct course code";
            sendSMS(number, response);
        }

    }
	return startId;

}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
    //SMS repsonse method back to our originating address (PhoneNo)
    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager sms = SmsManager.getDefault();
            ArrayList<String> parts = sms.divideMessage(msg);
            sms.sendMultipartTextMessage(phoneNo, null, parts, null, null);
        } catch (Exception ex) {}
    }
}