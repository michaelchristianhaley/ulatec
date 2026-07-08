package com.ulatec.dpc;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String TERMUX_PACKAGE = "com.termux";

    private DevicePolicyManager dpm;
    private ComponentName admin;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        admin = new ComponentName(this, UlatecDeviceAdminReceiver.class);

        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(32, 32, 32, 32);
        scroll.addView(root);

        TextView title = new TextView(this);
        title.setText("Ulatec DPC");
        title.setTextSize(26);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        root.addView(title);

        status = new TextView(this);
        status.setTextSize(16);
        status.setPadding(0, 24, 0, 24);
        root.addView(status);

        root.addView(button("Refresh status", v -> refreshStatus()));
        root.addView(button("Allowlist Ulatec + Termux for lock task", v -> setLockTaskPackages()));
        root.addView(button("Set Ulatec DPC as Home", v -> setUlatecHome()));
        root.addView(button("Launch Termux in kiosk mode", v -> launchTermuxKiosk()));
        root.addView(button("Start lock task here", v -> startLockTaskHere()));
        root.addView(button("Stop lock task here", v -> stopLockTaskHere()));
        root.addView(button("Clear Ulatec Home preference", v -> clearUlatecHome()));

        setContentView(scroll);
        refreshStatus();
    }

    private Button button(String label, View.OnClickListener listener) {
        Button b = new Button(this);
        b.setText(label);
        b.setAllCaps(false);
        b.setOnClickListener(listener);
        return b;
    }

    private boolean isDeviceOwner() {
        return dpm != null && dpm.isDeviceOwnerApp(getPackageName());
    }

    private void refreshStatus() {
        StringBuilder s = new StringBuilder();
        s.append("Package: ").append(getPackageName()).append("\n");
        s.append("Device Owner: ").append(isDeviceOwner()).append("\n");
        s.append("Admin: ").append(dpm != null && dpm.isAdminActive(admin)).append("\n");
        s.append("Termux installed: ").append(isPackageLaunchable(TERMUX_PACKAGE)).append("\n");
        s.append("Lock task mode: ").append(lockTaskMode()).append("\n");
        status.setText(s.toString());
    }

    private boolean isPackageLaunchable(String pkg) {
        return getPackageManager().getLaunchIntentForPackage(pkg) != null;
    }

    private String lockTaskMode() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return "unknown";
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int state = am.getLockTaskModeState();
            if (state == ActivityManager.LOCK_TASK_MODE_NONE) {
                return "none";
            }
            if (state == ActivityManager.LOCK_TASK_MODE_LOCKED) {
                return "locked";
            }
            if (state == ActivityManager.LOCK_TASK_MODE_PINNED) {
                return "pinned";
            }
            return String.valueOf(state);
        }
        return am.isInLockTaskMode() ? "active" : "none";
    }

    private void requireOwnerOrToast() {
        if (!isDeviceOwner()) {
            Toast.makeText(this, "Ulatec DPC is not Device Owner", Toast.LENGTH_LONG).show();
            throw new IllegalStateException("not device owner");
        }
    }

    private void setLockTaskPackages() {
        try {
            requireOwnerOrToast();
            dpm.setLockTaskPackages(admin, new String[] { getPackageName(), TERMUX_PACKAGE });
            Toast.makeText(this, "Lock task packages set", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            showError(e);
        }
        refreshStatus();
    }

    private void setUlatecHome() {
        try {
            requireOwnerOrToast();
            IntentFilter filter = new IntentFilter(Intent.ACTION_MAIN);
            filter.addCategory(Intent.CATEGORY_HOME);
            filter.addCategory(Intent.CATEGORY_DEFAULT);
            dpm.addPersistentPreferredActivity(admin, filter, new ComponentName(this, MainActivity.class));
            Toast.makeText(this, "Ulatec set as Home", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            showError(e);
        }
        refreshStatus();
    }

    private void clearUlatecHome() {
        try {
            requireOwnerOrToast();
            dpm.clearPackagePersistentPreferredActivities(admin, getPackageName());
            Toast.makeText(this, "Ulatec Home preference cleared", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            showError(e);
        }
        refreshStatus();
    }

    private void launchTermuxKiosk() {
        try {
            Intent intent = getPackageManager().getLaunchIntentForPackage(TERMUX_PACKAGE);
            if (intent == null) {
                Toast.makeText(this, "Termux is not installed", Toast.LENGTH_LONG).show();
                return;
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (isDeviceOwner() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ActivityOptions options = ActivityOptions.makeBasic();
                options.setLockTaskEnabled(true);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        } catch (Exception e) {
            showError(e);
        }
        refreshStatus();
    }

    private void startLockTaskHere() {
        try {
            startLockTask();
            Toast.makeText(this, "Lock task started in Ulatec DPC", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            showError(e);
        }
        refreshStatus();
    }

    private void stopLockTaskHere() {
        try {
            stopLockTask();
            Toast.makeText(this, "Lock task stopped", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            showError(e);
        }
        refreshStatus();
    }

    private void showError(Exception e) {
        Toast.makeText(this, e.getClass().getSimpleName() + ": " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}
