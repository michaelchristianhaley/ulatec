# Task Order

## Task 1 — Rebuild kit

Goal: preserve the known-good purge/hardening knowledge and prepare post-reset scripts.

Deliverables:

- package policy files
- post-reset checklist
- hardening scripts
- rollback notes
- DPC requirements

## Task 2 — Build tiny Ulatec DPC

Goal: create a minimal Android Device Policy Controller that can become Device Owner and launch kiosk/lock-task behavior.

Initial DPC responsibilities:

- be provisionable as Device Owner
- allowlist itself and Termux for lock task mode
- launch Termux into lock task if Android permits it
- set lock task UI features to a minimal set
- provide an obvious owner exit/recovery screen before we make it stricter

## Task 3 — Factory reset

Only after Task 1 and Task 2 are complete.

## Task 4 — Enable DPC during first boot

Provision the DPC as Device Owner during first setup.

Candidate methods:

- ADB `dpm set-device-owner` after installing the APK on a reset/unprovisioned/no-account device
- Android Enterprise provisioning flow if needed

## Task 5 — Restore hardened state

Install only:

- DPC
- F-Droid
- Termux
- Termux:Boot
- Shizuku
- Canta
- Privacy Browser
- Rethink DNS + Firewall

Then reapply purge/update-lockdown/network-containment steps.
