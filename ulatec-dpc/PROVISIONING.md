# Provisioning Ulatec DPC

Do not do this on the current hardened tablet unless we intentionally enter the factory-reset branch.

## Device Owner target

```text
com.ulatec.dpc/.UlatecDeviceAdminReceiver
```

## ADB provisioning sketch

After factory reset, before adding accounts or completing normal setup:

```sh
adb install app-debug.apk
adb shell dpm set-device-owner com.ulatec.dpc/.UlatecDeviceAdminReceiver
adb shell dumpsys device_policy | grep -Ei 'Device Owner|com.ulatec.dpc'
```

If Android says the device is already provisioned, this path is closed and requires another reset.

## First DPC actions

Open Ulatec DPC and press:

1. Refresh status
2. Allowlist Ulatec + Termux for lock task
3. Set Ulatec DPC as Home
4. Launch Termux in kiosk mode

Keep recovery buttons available until the behavior is proven.
