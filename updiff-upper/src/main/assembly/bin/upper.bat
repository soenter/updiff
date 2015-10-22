@ECHO OFF
rem #
rem # Copyright (c) 2012-2015
rem #

set "CURRENT_DIR=%cd%"
if not "%UPPER_HOME%" == "" goto gotHome
set "UPPER_HOME=%CURRENT_DIR%"
if exist "%UPPER_HOME%\bin\upper.bat" goto okHome
cd ..
set "UPPER_HOME=%cd%"
cd "%CURRENT_DIR%"
:gotHome
if exist "%UPPER_HOME%\bin\upper.bat" goto okHome
    echo 环境变量UPPER_HOME设置的不正确
goto end
:okHome

rem Set JavaHome if it exists
if exist { "%JAVA_HOME%\bin\java" } (
    set "JAVA="%JAVA_HOME%\bin\java""
)

echo Using JAVA_HOME:    "%JAVA_HOME%"
echo Using UPPER_HOME:   "%UPPER_HOME%"

set JAVA_OPTS=-server -Xms128m -Xmx128m -XX:MaxGCPauseMillis=400 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=logs/updiff.memory.dump -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCApplicationStoppedTime -Xloggc:logs/updiff.gc.log

%JAVA% %JAVA_OPTS% -cp %UPPER_HOME%\lib\* com.sand.updiff.upper.Upper %*

