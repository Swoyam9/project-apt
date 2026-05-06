#!/bin/sh

PROJECT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
TOOLS_DIR="$PROJECT_DIR/tools"
DATA_DIR="$TOOLS_DIR/mysql-local-data"
SOCKET_FILE="/tmp/autospare-mysql.sock"
PID_FILE="/tmp/autospare-mysql.pid"
LOG_FILE="$TOOLS_DIR/mysql-local.log"

MYSQLD="/Applications/XAMPP/xamppfiles/sbin/mariadbd"
MYSQL="/Applications/XAMPP/xamppfiles/bin/mysql"
INSTALL_DB="/Applications/XAMPP/xamppfiles/bin/mariadb-install-db"

mkdir -p "$TOOLS_DIR"

if ! [ -x "$MYSQLD" ] || ! [ -x "$MYSQL" ] || ! [ -x "$INSTALL_DB" ]; then
    echo "XAMPP MariaDB files were not found."
    echo "Install XAMPP first, then run this script again."
    exit 1
fi

if lsof -iTCP:3306 -sTCP:LISTEN >/dev/null 2>&1; then
    echo "Database is already running on port 3306."
    exit 0
fi

if ! [ -d "$DATA_DIR/mysql" ]; then
    echo "Creating local database folder..."
    "$INSTALL_DB" \
        --no-defaults \
        --basedir=/Applications/XAMPP/xamppfiles \
        --user="$(whoami)" \
        --datadir="$DATA_DIR" \
        --auth-root-authentication-method=normal \
        --skip-name-resolve \
        --force
fi

echo "Starting local database..."
nohup "$MYSQLD" \
    --no-defaults \
    --datadir="$DATA_DIR" \
    --port=3306 \
    --socket="$SOCKET_FILE" \
    --pid-file="$PID_FILE" \
    --skip-networking=0 \
    --bind-address=127.0.0.1 \
    > "$LOG_FILE" 2>&1 &

READY=0
COUNT=0
while [ "$COUNT" -lt 20 ]; do
    if "$MYSQL" --socket="$SOCKET_FILE" -u root -e "SELECT 1;" >/dev/null 2>&1; then
        READY=1
        break
    fi
    COUNT=$((COUNT + 1))
    sleep 1
done

if [ "$READY" -ne 1 ]; then
    echo "Database did not start."
    echo "Check this log file:"
    echo "$LOG_FILE"
    exit 1
fi

if ! "$MYSQL" --socket="$SOCKET_FILE" -u root -e "USE auto_spare_parts_db;" >/dev/null 2>&1; then
    echo "Importing project database..."
    tail -n +2 "$PROJECT_DIR/src/main/resources/database.sql" | "$MYSQL" --socket="$SOCKET_FILE" -u root
fi

echo "Database ready on localhost:3306."
