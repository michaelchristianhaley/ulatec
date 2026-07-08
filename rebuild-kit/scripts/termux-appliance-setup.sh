#!/data/data/com.termux/files/usr/bin/sh
set -eu

mkdir -p "$HOME/.termux"

cat > "$HOME/.termux/termux.properties" <<'PROPS'
fullscreen = true
use-fullscreen-workaround = true
extra-keys = []
PROPS

termux-reload-settings || true

pkg install -y tmux

grep -q 'tmux new-session -A -s ulatec' "$HOME/.bashrc" 2>/dev/null || cat >> "$HOME/.bashrc" <<'BASHRC'

# Ulatec tmux home
if [ -z "$TMUX" ]; then
  tmux new-session -A -s ulatec
fi
BASHRC

mkdir -p "$HOME/.termux/boot"

cat > "$HOME/.termux/boot/00-ulatec-start" <<'BOOT'
#!/data/data/com.termux/files/usr/bin/sh
LOG="$HOME/ulatec-boot.log"
termux-wake-lock
date >> "$LOG"
echo Ulatec >> "$LOG"
tmux has-session -t ulatec 2>/dev/null || tmux new-session -d -s ulatec
BOOT

chmod +x "$HOME/.termux/boot/00-ulatec-start"
