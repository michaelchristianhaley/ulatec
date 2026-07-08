# Ulatec DPC Requirements

Goal: tiny open-source Device Policy Controller for a true kiosk branch.

Must:
- register a DeviceAdminReceiver
- be provisionable as Device Owner
- allowlist Ulatec DPC and Termux (`com.termux`) for lock task mode
- launch Termux into lock task mode where Android/Samsung permits
- include an owner recovery/exit screen while testing
- set lock task UI features only after proof

Important design fact:
On Android 9/API 28+, a DPC can start another app activity into lock task mode using ActivityOptions.setLockTaskEnabled(true). This matters because Termux itself is not our app.
