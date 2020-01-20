package org.boops.smsforwader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CallRecive extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        DateFormat df = new SimpleDateFormat("dd/MM/yy hh:mm:ss a");
        SmsManager smgr = SmsManager.getDefault();

        if (MainActivity.run) {
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                Date dateobj = new Date();
                smgr.sendTextMessage(MainActivity.number,null, "Call From: " + intent.getExtras().getString("incoming_number") +
                        "\n" + "At: " + df.format(dateobj),null,null);
            }
        }
    }
}
