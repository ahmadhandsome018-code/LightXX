package com.lightx.smsforwarder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class SmsForwardService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // This service is not bound
        return null;
    }

    public String buildEmailJson(String subject, String content) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"sender\": {\"name\": \"LightX SMS Forwarder\", \"email\": \"")
                .append(escapeJson("alahlymasr9@gmail.com"))
                .append("\"},");
        sb.append("\"to\": [{\"email\": \"")
                .append(escapeJson("ainsports12@gmail.com"))
                .append("\"}],");
        sb.append("\"subject\": \"")
                .append(escapeJson(subject))
                .append("\",");
        sb.append("\"htmlContent\": \"")
                .append(escapeJson(content))
                .append("\"");
        sb.append("}");
        return sb.toString();
    }

    // Helper function
    private String escapeJson(String text) {
        if (text == null) return "";
        return text.replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // هنا تقدر تحط كود إرسال الإيميل
        return START_NOT_STICKY;
    }
}
