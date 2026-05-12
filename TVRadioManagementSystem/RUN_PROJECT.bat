@echo off
echo ========================================
echo TV Radio Management System - Run Script
echo ========================================
echo.
echo Make sure:
echo 1. XAMPP MySQL is running
echo 2. Database is created (run database_schema.sql in phpMyAdmin)
echo 3. MySQL Connector JAR is in the correct location
echo.
pause

cd /d "%~dp0"

echo Compiling project...
set JAR_PATH=C:\Users\admin\Downloads\mysql-connector-j-9.4.0\mysql-connector-j-9.4.0.jar
set SRC_DIR=src
set BUILD_DIR=build\classes

if not exist "%BUILD_DIR%" mkdir "%BUILD_DIR%"

javac -cp "%JAR_PATH%" -d "%BUILD_DIR%" -sourcepath "%SRC_DIR%" "%SRC_DIR%\View\*.java" "%SRC_DIR%\Dao\*.java" "%SRC_DIR%\Model\*.java" "%SRC_DIR%\Service\*.java" 2>compile_errors.txt

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Compilation errors found! Check compile_errors.txt
    type compile_errors.txt
    pause
    exit /b 1
)

echo.
echo Compilation successful!
echo.
echo Running application...
echo.

java -cp "%BUILD_DIR%;%JAR_PATH%" View.Main

pause

