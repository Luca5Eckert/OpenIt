FROM nodered/node-red@sha256:4b51d083b403c603bea91a35a0d817c6c56e98bb93d52373b7af855ece5bf887

# Install envsubst (provided by gettext package) and install extra nodes
USER root
RUN apk add --no-cache gettext

# Install extra nodes
RUN npm install node-red-dashboard node-red-node-mysql

# Copy flow templates (as root, then fix permissions for node-red user)
COPY --chown=node-red:node-red flows.json.template /data/flows.json.template
COPY --chown=node-red:node-red flows_cred.json.template /data/flows_cred.json.template
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

USER node-red

ENTRYPOINT ["/entrypoint.sh"]
