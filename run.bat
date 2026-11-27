@echo off
title CRM System - Core Java
echo Starting CRM System...

where java >nul 2>nul
if %errorlevel% neq 0 (
    if exist "C:\Users\Lenovo\Downloads\jdk-26_windows-x64_bin\jdk-26.0.2\bin\java.exe" (
        set "PATH=C:\Users\Lenovo\Downloads\jdk-26_windows-x64_bin\jdk-26.0.2\bin;%PATH%"
    )
)

java -jar CRM.jar
if %errorlevel% neq 0 (
    java -cp out com.crm.app.Main
)

pause
