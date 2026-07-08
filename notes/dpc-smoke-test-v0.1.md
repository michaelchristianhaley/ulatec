# Ulatec DPC v0.1 Smoke Test

Result: PASS

Device: current hardened Samsung tablet

Observed:

- Ulatec DPC v0.1 installed after uninstalling v0.
- App opens normally.
- Device Owner: false
- Admin: false
- Termux installed: true
- Lock task mode: none
- Launch Termux in kiosk mode opens Termux normally while not Device Owner.
- No lock task trap occurred.
- No factory reset performed.

Conclusion:

DPC v0.1 is good enough for the next planning gate: Device Owner provisioning path.
