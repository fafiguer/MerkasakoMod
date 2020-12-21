@echo off

echo.
echo.
echo Compilation: start.
echo.
echo.

call gradlew.bat build

if not errorlevel 0 (
    echo.
    echo Compilation: error.
    echo.
    goto :PRG_EXIT
)

echo.
echo.
echo Compilation: end.
echo.
echo.

echo.
echo.
echo RunClient: start.
echo.
echo.

call gradlew.bat runClient 1> logs/client_log.txt 2> logs/client_log_err.txt

if not errorlevel 0 (
    echo.
    echo.
    echo RunClient: error.
    echo.
    echo.
    goto :PRG_EXIT
)

echo.
echo RunClient: end.
echo.


:PRG_EXIT


pause
