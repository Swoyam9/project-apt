#!/bin/sh

SOCKET_FILE="/tmp/autospare-mysql.sock"
MYSQLADMIN="/Applications/XAMPP/xamppfiles/bin/mysqladmin"

if ! [ -x "$MYSQLADMIN" ]; then
    echo "mysqladmin was not found. Is XAMPP installed?"
    exit 1
fi

"$MYSQLADMIN" --socket="$SOCKET_FILE" -u root shutdown
echo "Database stopped."
