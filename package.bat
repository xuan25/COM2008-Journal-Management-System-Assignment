@echo off
cd Client\JournalManagementSystem\src\main\java
javac com\com2008\journalmanagementsystem\Main.java
jar cvfm JournalManagementSystem.jar MANIFEST.MF com\
cd ..\..\..\..\..\
md Packaged\team018\lib
move /Y Client\JournalManagementSystem\src\main\java\JournalManagementSystem.jar Packaged\team018\
copy /Y Client\JournalManagementSystem\lib\ Packaged\team018\lib\
copy /Y Database\ Packaged\team018\
move /Y Packaged\team018\COM2008_Tables.sql Packaged\team018\InitializeDatabase.sql
echo Done!!!
pause