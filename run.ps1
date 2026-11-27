# PowerShell Launcher for CRM System
Write-Host "Starting CRM System..." -ForegroundColor Green

if (-not (Get-Command java -ErrorAction SilentlyContinue)) {
    if (Test-Path "C:\Users\Lenovo\Downloads\jdk-26_windows-x64_bin\jdk-26.0.2\bin\java.exe") {
        $env:PATH = "C:\Users\Lenovo\Downloads\jdk-26_windows-x64_bin\jdk-26.0.2\bin;" + $env:PATH
    }
}

if (Test-Path "CRM.jar") {
    java -jar CRM.jar
} else {
    java -cp out com.crm.app.Main
}
