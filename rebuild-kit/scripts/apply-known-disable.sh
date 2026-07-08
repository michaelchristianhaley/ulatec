#!/data/data/com.termux/files/usr/bin/sh
set -u

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
LIST="$ROOT/policy/disable-known-good.txt"

while IFS= read -r p; do
  [ -z "$p" ] && continue
  case "$p" in \#*) continue ;; esac
  echo "== disable $p"
  RISH_APPLICATION_ID=com.termux rish -c "pm disable-user --user 0 $p 2>&1" </dev/null
done < "$LIST"
