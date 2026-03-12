#!/bin/sh
set -e

# Substitute only the expected environment variables in the flows template
envsubst '${DB_HOST} ${DB_PORT} ${DB_NAME} ${MQTT_BROKER_HOST} ${MQTT_BROKER_PORT}' \
  < /data/flows.json.template > /data/flows.json

# Substitute only the expected environment variables in the credentials template
envsubst '${MQTT_USER} ${MQTT_PASSWORD} ${DB_USER} ${DB_PASSWORD}' \
  < /data/flows_cred.json.template > /data/flows_cred.json

# Restrict permissions on the credentials file
chmod 600 /data/flows_cred.json

# Start Node-RED
exec /usr/local/bin/node-red --settings /data/settings.js "$@"
