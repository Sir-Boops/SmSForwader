package org.boops.smsforwader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

public class SmSRecive extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {

        SmsManager smgr = SmsManager.getDefault();

        // Make sure we should be running
        if (MainActivity.run) {
            // Recive SmS
            if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
                Bundle bundle = intent.getExtras();

                if (bundle != null) {
                    // get sms objects
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    if (pdus.length == 0) {
                        return;
                    }
                    // large message might be broken into many
                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < pdus.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        sb.append(messages[i].getMessageBody());
                    }
                    String sender = messages[0].getOriginatingAddress();
                    String message = sb.toString();

                    if (sender.contains(MainActivity.number)) {
                        if (message.charAt(0) == '!') {
                            String[] new_msg = message.split(" ", 2);
                            smgr.sendTextMessage(new_msg[0].substring(1),null, new_msg[1],null,null);
                        }
                    } else {
                        smgr.sendTextMessage(MainActivity.number,null, "Message From: " + sender + "\n" + message,null,null);
                    }
                }
            }
        }
    }
}
