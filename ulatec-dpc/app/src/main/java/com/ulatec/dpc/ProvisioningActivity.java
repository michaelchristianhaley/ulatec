package com.ulatec.dpc;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ProvisioningActivity extends Activity {
    private static final String TERMUX_PACKAGE = "com.termux";

    private DevicePolicyManager dpm;
    private ComponentName admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        admin = new ComponentName(this, UlatecDeviceAdminReceiver.class);

        String action = getIntent() == null ? "" : getIntent().getAction();

        if (DevicePolicyManager.ACTION_GET_PROVISIONING_MODE.equals(action)) {
            handleGetProvisioningMode();
            return;
        }

        if (DevicePolicyManager.ACTION_ADMIN_POLICY_COMPLIANCE.equals(action)) {
            handleAdminPolicyCompliance();
            return;
        }

        TextView t = new TextView(this);
        t.setText("Ulatec provisioning helper. No provisioning action received.");
        t.setPadding(32, 32, 32, 32);
        setContentView(t);
    }

    private void handleGetProvisioningMode() {
        ArrayList<Integer> allowed = getIntent().getIntegerArrayListExtra(
                DevicePolicyManager.EXTRA_PROVISIONING_ALLOWED_PROVISIONING_MODES);

        int desired = DevicePolicyManager.PROVISIONING_MODE_FULLY_MANAGED_DEVICE;

        if (allowed != null && !allowed.contains(desired)) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }

        Intent result = new Intent();
        result.putExtra(DevicePolicyManager.EXTRA_PROVISIONING_MODE, desired);
        setResult(RESULT_OK, result);
        finish();
    }

    private void handleAdminPolicyCompliance() {
        if (dpm != null && dpm.isDeviceOwnerApp(getPackageName())) {
            dpm.setLockTaskPackages(admin, new String[] { getPackageName(), TERMUX_PACKAGE });
        }

        setResult(RESULT_OK);
        finish();
    }
}
