#!/usr/bin/env bash
set -euo pipefail

RM="${RM:?Defina RM}"
POSTGRES_PASSWORD="${POSTGRES_PASSWORD:?Defina POSTGRES_PASSWORD}"

RESOURCE_GROUP="rg-caminholivre-${RM}"
WEB_APP="app-caminholivre-${RM}"
POSTGRES_SERVER="pg-caminholivre-${RM}"
DATABASE="caminholivre"

DB_HOST="${POSTGRES_SERVER}.postgres.database.azure.com"

echo "Configurando variaveis da aplicacao..."

az webapp config appsettings set \
  --resource-group "$RESOURCE_GROUP" \
  --name "$WEB_APP" \
  --settings \
    "SPRING_DATASOURCE_URL=jdbc:postgresql://${DB_HOST}:5432/${DATABASE}?sslmode=require" \
    "SPRING_DATASOURCE_USERNAME=caminholivreadmin" \
    "SPRING_DATASOURCE_PASSWORD=${POSTGRES_PASSWORD}" \
    "SPRING_PROFILES_ACTIVE=prod" \
    "SERVER_PORT=8080"

echo "Configuracoes basicas aplicadas."
echo "Configure tambem JWT_SECRET, RabbitMQ e Spring AI antes do deploy."
