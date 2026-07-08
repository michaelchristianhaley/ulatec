#!/system/bin/sh
disable_list='
com.android.vending
com.google.android.gms
com.google.android.gsf
com.google.android.configupdater
com.google.android.onetimeinitializer
com.google.android.partnersetup
com.google.android.feedback
com.google.android.federatedcompute
com.google.android.adservices.api
com.google.android.ondevicepersonalization.services
com.google.mainline.telemetry
com.google.mainline.adservices
com.sec.android.app.samsungapps
com.samsung.android.app.updatecenter
com.samsung.android.app.omcagent
com.sec.android.soagent
com.wssyncmldm
com.samsung.android.sdm.config
com.samsung.android.scpm
com.samsung.android.mobileservice
com.samsung.android.scloud
com.samsung.android.beaconmanager
com.microsoft.appmanager
com.microsoft.skydrive
'
for p in $disable_list; do
  echo "== disable $p"
  RISH_APPLICATION_ID=com.termux rish -c "pm disable-user --user 0 $p 2>&1" </dev/null
done
for p in $disable_list; do
  echo "== uninstall-user0 $p"
  RISH_APPLICATION_ID=com.termux rish -c "pm uninstall --user 0 $p 2>&1" </dev/null
done
for p in $disable_list; do
  echo "== force-stop $p"
  RISH_APPLICATION_ID=com.termux rish -c "am force-stop --user 0 $p 2>&1" </dev/null
done
