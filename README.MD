# Mongo Db Connector for Talend Open Studio (Compatible TOS 7.0.x)
    
# Partition Mapper



## Builder le composant
 
      mvnw clean install
        
## Déployer le composant dans Talend

      mvnw talend-component:deploy-in-studio -Dtalend.component.studioHome="C:\Talend-Studio\studio"
      
## Vérifier le rendu du composant

Lancer le serveur de composant Talend 

    mvn talend-component:web

Visiter cette url : http://localhost:8080