
#!/usr/bin/env bash
set -euo pipefail

# =============================================
# CaminhoLivre - Provisionamento Azure
# =============================================

# CONFIGURACOES
RM="${RM:?Defina a variavel RM}"
LOCATION="${AZURE_LOCATION:-brazilsouth}"

RESOURCE_GROUP="rg-caminholivre-${RM}"
APP_PLAN="plan-caminholivre-${RM}"
WEB_APP="app-caminholivre-${RM}"
POSTGRES_SERVER="pg-caminholivre-${RM}"
DATABASE="caminholivre"

POSTGRES_ADMIN="caminholivreadmin"
POSTGRES_PASSWORD="${POSTGRES_PASSWORD:?Defina POSTGRES_PASSWORD}"

echo "Verificando assinatura Azure..."
az account show --query "{name:name,id:id}" -o table

echo "Registrando provedores..."
az provider register --namespace Microsoft.Web --wait
az provider register --namespace Microsoft.DBforPostgreSQL --wait

# 1. RESOURCE GROUP
echo "Criando Resource Group..."

az group create \
  --name "$RESOURCE_GROUP" \
  --location "$LOCATION"

# 2. APP SERVICE PLAN
echo "Criando App Service Plan..."

if ! az appservice plan show \
  --resource-group "$RESOURCE_GROUP" \
  --name "$APP_PLAN" >/dev/null 2>&1; then

  az appservice plan create \
    --name "$APP_PLAN" \
    --resource-group "$RESOURCE_GROUP" \
    --location "$LOCATION" \
    --sku B1 \
    --is-linux
fi

# 3. WEB APP JAVA
echo "Criando Web App..."

if ! az webapp show \
  --resource-group "$RESOURCE_GROUP" \
  --name "$WEB_APP" >/dev/null 2>&1; then

  az webapp create \
    --resource-group "$RESOURCE_GROUP" \
    --plan "$APP_PLAN" \
    --name "$WEB_APP" \
    --runtime "JAVA:21-java21"
fi

# 4. POSTGRESQL
echo "Criando PostgreSQL Flexible Server..."

if ! az postgres flexible-server show \
  --resource-group "$RESOURCE_GROUP" \
  --name "$POSTGRES_SERVER" >/dev/null 2>&1; then

  az postgres flexible-server create \
    --resource-group "$RESOURCE_GROUP" \
    --name "$POSTGRES_SERVER" \
    --location "$LOCATION" \
    --admin-user "$POSTGRES_ADMIN" \
    --admin-password "$POSTGRES_PASSWORD" \
    --tier Burstable \
    --sku-name Standard_B1ms \
    --storage-size 32 \
    --version 16 \
    --public-access none
fi

# 5. DATABASE
echo "Criando banco de dados..."

if ! az postgres flexible-server db show \
  --resource-group "$RESOURCE_GROUP" \
  --server-name "$POSTGRES_SERVER" \
  --database-name "$DATABASE" >/dev/null 2>&1; then

  az postgres flexible-server db create \
    --resource-group "$RESOURCE_GROUP" \
    --server-name "$POSTGRES_SERVER" \
    --database-name "$DATABASE"
fi

# 6. REGRAS DE ACESSO
# Somente para desenvolvimento.
# Permite conexoes originadas de servicos Azure.
echo "Configurando acesso ao PostgreSQL..."

az postgres flexible-server firewall-rule create \
  --resource-group "$RESOURCE_GROUP" \
  --name "$POSTGRES_SERVER" \
  --rule-name AllowAzureServices \
  --start-ip-address 0.0.0.0 \
  --end-ip-address 0.0.0.0

# 7. RESULTADO
echo "======================================"
echo "Provisionamento concluido!"
echo "Resource Group: $RESOURCE_GROUP"
echo "Web App: $WEB_APP"
echo "PostgreSQL: $POSTGRES_SERVER"
echo "Database: $DATABASE"
echo "URL: https://${WEB_APP}.azurewebsites.net"
echo "======================================"
