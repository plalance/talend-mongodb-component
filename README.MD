# Mongo Db Connector for Talend Open Studio (Compatible TOS 7.0.x)

Composant permettant de connecter Talend dans sa version 7.0.x et au delà à une base de données documentaire, MongoDb.

Ce composant n'existe pas dans Talend Open Studio dans sa version gratuite, et n'est fourni par Talend qu'a partir de la solution Big Data, une solution complète apportant énormément de possibilités en terme d'architecture et de management de données d'entrerprise. Pour autant, cette solution logicielle est complexe, et peut être que certaines personne peuvent avoir à utiliser MongoDb sur le Studio Talend Open Source (TOS).

Le composant est développé sur la base du Framework Talend Component Kit dans sa version 1.1.11.

# Utilisation du composant

Après installation, le plugin apparaît dans la palette, dans la vue Intégration sous la Famille de composant CERP RRM / Mongo.

A l'utilisation, les champs suivants sont configurables :

Soure de données :
- Hôte: IP du Serveur MongoDb, basiquement 127.0.0.1
- Port: Port du Serveur MongoDb, basiquement 27017
- Authentification (Checkbox) : Définit si l'on souhaite utiliser l'authentification Mongo ou non. 
- User: Utilisateur
- Password: Mot de passe
- Base de données d'authentification: Base dans laquelle l'utilisateur est enregistré.

Set de données :
- database: base de donnée dans laquelle trouver les données.
- collection: collection pour laquelle la reqûete est appliquée.
- query: requête à appliquer (argument au format Json de la méthode find() ).
- limit: limite du nombre de documents à rammener pour cete requête. C'est un filtre mongo, et non pas un filtre sur les données retournées.
- rawMode / JSON mode: si cochée, les résultats seront donnés sous forme de String Json au lieu du format colonne Talend classique. L'unique champs sera alors : string et l'outil "Guess Schema" ne donenra qu'un champs String. Cette fonction est à utiliser pour tirer partie de composants tels que tExtractJsonFields par exemple. Utile aussi pour traiter de la donnée brute (WebServices...).

Flux de données :
- schema : Les champs qui seront récupérés par Talend (et non pas par le composant)

Autre :
- commentaire : Juste un commentaire pour décrire ce que l'on fait du composant.

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

	  autre exemple :
	        
      mvnw talend-component:deploy-in-studio -Dtalend.component.studioHome="C:\Talend-Open-Studio\TOS_DI-Win32-20190620_1446-V7.2.1"

      
## Vérifier le rendu du composant

Lancer le serveur de composant Talend 

    mvn talend-component:web

Visiter cette url : http://localhost:8080