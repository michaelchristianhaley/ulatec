# Ulatec DPC v0.2 Provisioning Packet

Status: generated, not used yet.

APK URL:

```text
https://github.com/michaelchristianhaley/ulatec/releases/download/dpc-v0.2/app-debug.apk
```

Device admin component:

```text
com.ulatec.dpc/.UlatecDeviceAdminReceiver
```

APK SHA-256 hex:

```text
669b58db3ef5e818105ddc41299c90f856b6304a6f0b067eb8c1052a074bb206
```

Android provisioning package checksum:

```text
ZptY2z716BgQXdxBKZyQ-Fa2MEpvCwZ-uMEFKgdLsgY
```

Notes:

- This packet is for QR-based Device Owner provisioning after factory reset.
- It intentionally does not store Wi-Fi credentials.
- It leaves system apps enabled during provisioning; post-reset purge happens later from the rebuild kit.
- Do not factory reset until the QR is visually available on another device and this packet has been reviewed.
