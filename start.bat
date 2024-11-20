@echo off
:: Esegui Maven per ogni servizio
echo Eseguo Maven build per ogni modulo...
mvn clean install -f .\order_service\pom.xml
mvn clean install -f .\inventory_service\pom.xml
:: mvn clean install -f .\payment_service\pom.xml (decommenta se necessario)
:: mvn clean install -f .\orchestrator_service\pom.xml (decommenta se necessario)

:: Avvia Docker Compose
echo Avvio Docker Compose...
docker-compose -f docker-compose.yaml up -d --build