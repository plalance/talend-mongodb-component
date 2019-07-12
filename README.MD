# Mongo Db Connector for Talend Open Studio (Compatible TOS 7.0.x)

Composant permettant de conencter Talend dans sa version 7.0.x et au delà à une base de données documentaire, MongoDb.

Ce composant n'existe pas dans Talend Open Studio dans sa version gratuite, et n'est fourni par Talend qu'a partir de la solution Big Data, une solution complète apportant énormément de possibilités en terme d'architecture et de management de données d'entrerprise. Pour autant, cette solution logicielle est complexe, et peut être que certaines personne peuvent avoir à utiliser MongoDb sur le Studio Talend Open Source (TOS).

Ce composant répond à un besoin personnel, il ne sera pas adapté à la demande sauf si j'en trouve l'utilité.

Je développe ce composant Talend en respectant les normes actuelle du Framework Talend Component Kit dans sa version 1.1.11.

# Utilisation du composant

Après installation, le plugin apparaît dans la palette, dans la vue Intégration sous la Famille de composant Mongo.
Il ne correspond pour le moment qu'a un composant de type Input, pas de traitement process ni de output pour le moment.

A l'utilisation, les champs suivants sont configurables :

- schema : Les champs qui seront récupérés par Talend (et non pas par le composant)
- host : Serveur MongoDb au format : mongodb://XXX.XX.XX.XX
- user
- password
- database : base de donnée Mongo à laquelle se connecter.
- collection : Collection dans laquell eeffectuer les requêtes.
- commentaire : Juste un commentaire pour décrire ce que l'on fait du composant...

# Informations relatives à MongoDb

## Créer des utilisateurs de sécurité

Créer un admin :

use admin

db.createUser(
  {
    user: "admin",
    pwd: "admin",
    roles: [ { role: "userAdminAnyDatabase", db: "admin" } ]
  }
)

Créer un root :

db.createUser(
  {
    user: "root",
    pwd: "root",
    roles: [ { role: "root", db: "admin" } ]
  }
)

## Activer l'authentification sur le serveur MongoDb


Sous Docker, (Installer vim ou autre édtieur au besoin : apt-get update && apt-get install vim) :

	docker exec -ti <instancemongo> bash
	
	vim /etc/mongod.conf.orig
	
Et éditer comme suit :

	security:
	  authorization: "enabled"
		
    
# Développer le composant

## Génération du formulaire widget du composant

https://talend.github.io/component-runtime/main/1.1.11/gallery.html

## Builder le composant
 
      mvnw clean install
        
## Déployer le composant dans Talend

      mvnw talend-component:deploy-in-studio -Dtalend.component.studioHome="C:\Talend-Studio\studio"
      
## Vérifier le rendu du composant

Lancer le serveur de composant Talend 

    mvn talend-component:web

Visiter cette url : http://localhost:8080