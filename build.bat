Echo "<------------------ MAVEN BUILD ----------------->"
call mvn clean install

Echo "<------------------ DEPLOY TO STUDIO ----------------->"
call mvn talend-component:deploy-in-studio -Dtalend.component.studioHome="C:\Talend-Studio\studio"

pause