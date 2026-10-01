@echo off
setlocal
set GRADLE_VERSION=9.2.0
set DIST=%USERPROFILE%\.gradle\wrapper\dists\gradle-%GRADLE_VERSION%-bin
set ZIP=%DIST%\gradle-%GRADLE_VERSION%-bin.zip
where gradle >nul 2>nul
if %ERRORLEVEL%==0 (
  gradle %*
  exit /b %ERRORLEVEL%
)
if not exist "%DIST%\gradle-%GRADLE_VERSION%\bin\gradle.bat" (
  if not exist "%DIST%" mkdir "%DIST%"
  if not exist "%ZIP%" (
    powershell -NoProfile -ExecutionPolicy Bypass -Command "Invoke-WebRequest -UseBasicParsing -Uri 'https://services.gradle.org/distributions/gradle-%GRADLE_VERSION%-bin.zip' -OutFile '%ZIP%'"
  )
  powershell -NoProfile -ExecutionPolicy Bypass -Command "Expand-Archive -Force '%ZIP%' '%DIST%'"
)
call "%DIST%\gradle-%GRADLE_VERSION%\bin\gradle.bat" %*
endlocal
