# Ulatec DPC

Tiny Device Policy Controller for the Ulatec true-kiosk branch.

Package:

```text
com.ulatec.dpc
```

Device admin receiver:

```text
com.ulatec.dpc/.UlatecDeviceAdminReceiver
```

Initial purpose:

- become Device Owner during first-boot provisioning
- allowlist Ulatec DPC and Termux for lock task mode
- optionally set Ulatec DPC as Home
- launch Termux into lock task mode on Android 9+
- keep visible owner recovery controls while testing

This is v0. It is intentionally not a hardened no-exit kiosk yet.


## v0.2 provisioning support

Adds handlers for Android managed provisioning:

- `android.app.action.GET_PROVISIONING_MODE`
- `android.app.action.ADMIN_POLICY_COMPLIANCE`

The provisioning mode handler requests fully managed device mode.
The policy compliance handler allowlists Ulatec DPC and Termux for lock task mode after Device Owner setup.
