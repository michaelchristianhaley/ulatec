#!/data/data/com.termux/files/usr/bin/sh
mkdir -p ~/.termux
cat > ~/.termux/termux.properties <<'EOF'
fullscreen = true
use-fullscreen-workaround = true
extra-keys = []
EOF
termux-reload-settings
pkg install -y tmux
grep -q 'tmux new-session -A -s ulatec' ~/.bashrc || cat >> ~/.bashrc <<'EOF'

# Ulatec tmux home
if [ -z "$TMUX" ]; then
  tmux new-session -A -s ulatec
fi
EOF
mkdir -p ~/.termux/boot
cat > ~/.termux/boot/00-ulatec-start <<'EOF'
#!/data/data/com.termux/files/usr/bin/sh
LOG="$HOME/ulatec-boot.log"
termux-wake-lock
date >> "$LOG"
echo Ulatec >> "$LOG"
tmux has-session -t ulatec 2>/dev/null || tmux new-session -d -s ulatec
EOF
chmod +x ~/.termux/boot/00-ulatec-start
