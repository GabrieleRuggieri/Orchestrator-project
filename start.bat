@echo off

echo Cleaning and building all services...

cd common
mvn clean install
IF ERRORLEVEL 1 (
    echo Build failed in common
    pause
    exit /b 1
)
cd ..

cd inventory_service
mvn clean install
IF ERRORLEVEL 1 (
    echo Build failed in inventory_service
    pause
    exit /b 1
)
cd ..

cd order_service
mvn clean install
IF ERRORLEVEL 1 (
    echo Build failed in order_service
    pause
    exit /b 1
)
cd ..

cd payment_service
mvn clean install
IF ERRORLEVEL 1 (
    echo Build failed in payment_service
    pause
    exit /b 1
)
cd ..

cd orchestrator_service
mvn clean install
IF ERRORLEVEL 1 (
    echo Build failed in orchestrator_service
    pause
    exit /b 1
)
cd ..

echo Starting Docker containers...
docker-compose -f docker-compose.yaml up -d --build
IF ERRORLEVEL 1 (
    echo Docker Compose failed
    pause
    exit /b 1
)

echo All services are up and running!
pause