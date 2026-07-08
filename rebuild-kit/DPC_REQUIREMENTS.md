# Ulatec DPC Requirements

## Goal

Build a tiny open-source Device Policy Controller for a true kiosk branch.

## Core design

The DPC must be the Device Owner. It is not just a normal app.

It should:

- register a DeviceAdminReceiver
- provide a small owner control Activity
- allowlist lock task packages:
  - Ulatec DPC package
  - Termux package: `com.termux`
- start Termux in lock task mode where supported
- keep a visible owner recovery path while testing

## Important Android facts

Android lock task mode is the real kiosk mode. It restricts a device to a single app or allowlisted apps.

On Android 9/API 28 or later, a DPC can start another app's activity into lock task mode with `ActivityOptions.setLockTaskEnabled(true)`.

This matters because Termux is not our app, so the Ulatec DPC must either:

1. launch Termux into lock task mode using the Android 9+ DPC capability,
2. act as a kiosk launcher that keeps Termux available but locks its own activity,
3. or use a tiny helper/wrapper if direct Termux lock is unreliable on Samsung.

## First build should be safe

Do not make the first DPC impossible to exit.

First version should:

- show an owner exit/recovery button
- show package/policy status
- log what it did
- only lock down harder after manual confirmation

## Later hardening

After the first DPC proves reliable:

- reduce system UI features
- block notifications/recents/home as desired
- set user restrictions carefully
- preserve a recovery method
