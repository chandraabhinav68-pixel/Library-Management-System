@echo off
setlocal
cd /d "%~dp0"

where java >nul 2>nul
if errorlevel 1 (
  echo JDK 21 or newer is required. Install a JDK, then reopen this script.
  pause
  exit /b 1
)

if exist "%~dp0.tools\apache-maven-3.9.11\bin\mvn.cmd" (
  set "MVN=%~dp0.tools\apache-maven-3.9.11\bin\mvn.cmd"
) else (
  where mvn >nul 2>nul
  if errorlevel 1 (
    echo Apache Maven 3.9 or newer is required and was not found in PATH.
    echo Install Maven, reopen this script, and try again.
    pause
    exit /b 1
  )
  set "MVN=mvn"
)

echo Starting Book Haven. The first run needs internet to download dependencies.
call "%MVN%" clean compile exec:java
if errorlevel 1 (
  echo.
  echo Book Haven did not start. Check the Maven error above.
  pause
  exit /b 1
)
endlocal
