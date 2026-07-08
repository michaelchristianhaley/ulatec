#!/system/bin/sh
while IFS= read -r p; do
  [ -z "$p" ] && continue
  case "$p" in \#*) continue ;; esac
  echo "== disable $p"
  RISH_APPLICATION_ID=com.termux rish -c "pm disable-user --user 0 $p 2>&1" </dev/null
done < disable-known-good.txt
