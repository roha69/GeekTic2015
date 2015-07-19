
##GeekTic
###A Spring MVC & AngularJs Sample App

Un simple projet pour démontrer le fonctionnement d'une application construite avec le framwork  **Spring MVC** et un client web en  **AngularJs**. 

GeekTic est un site de rencontre pour les Geek. On créer son compte, remplis son profil et recherche des personnes.

Le frontend est entièrement basé sur Angular, lodash et requirejs.
Le backend est composé de  **web services JSON REST** propulsés par  **Spring MVC**/  **JPA** et sécurisé avec  **Spring Security**.  La base de données est hsql pour les tests (légère et en mémoire vive) et Postgres pour la production.

Le squelette de cette application provient de ce [blog](http://blog.jhades.org/developing-a-modern-java-8-web-app-with-spring-mvc-and-angularjs/).

##Sommaire

{:toc}

## Installation et démarrage
###<i class="icon-cog"></i> Installation des dépendances ###

L'application dépend de : 

 - Java 8
 - Node 0.10
 - bower
 - maven 3

### Installer les dépendances du frontend ###

Après avoir cloné ce répertoire, les commandes suivantes installent les dépendances JavaScript :

    bower install

### <i class="icon-cog"></i>Build et Start le serveur ###

Pour build le backend et démarrer le serveur, il faut lancer ses commandes dans le répertoire racine de l’application :

Avec la base de données [hsqlDB](http://hsqldb.org/)  (IN MEMORY) :

    mvn clean install tomcat7:run-war -Dspring.profiles.active=test
 
 Avec le base de données <i class="icon-hdd"></i> [Postgres](http://www.postgresql.org/) :
 
    mvn clean install tomcat7:run-war -Dspring.profiles.active=develop

Le profil test avec la base de données en mémoire. Après le démarrage du serveur, l'application est accessible à l'adresse URL suivante :

    http://localhost:8080/

Pour avoir un utilisateur avec des données, identifiez vous avec l'utilisateur suivant :

    identifiant: test123 / Mot de passe: Password2

### Lancer le projet en HTTPS uniquement ###

L'application peut démarrer en HTTPS uniquement en ajoutant le paramètre httpsOnly=true. Voici un exemple de commande pour lancer en HTTPS :

    mvn clean install tomcat7:run-war -Dspring.profiles.active=test -DhttpsOnly=true

Le projet peut donc être accéder par l'adresse URL :

    https://localhost:8443/
    
Un message d'avertissement peut être afficher car le navigateur n'accepte pas le certificat, il suffit de l'accepter et la page d'authentification s'ouvre.


##L'application 
### Aperçu du Frontend ###

Le projet est un exemple d'application web entièrement basé sur AngularJs pour le frontend et un serveur Spring/Hibernate pour le backend. L'application est responsive, elle adopte différentes tailles d'écran.

Pour le frontend, Les librairies suivantes sont utilisée :  [Yahoo PureCss](http://http://purecss.io/) (pure CSS baseline)  et [lodash](https://lodash.com/) pour la manipulation de donnée. Le module système  [require.js](http://requirejs.org/) est utilisé pour charger les dépendances du frontend. Les librairies sont obtenues via [bower](http://bower.io/).

Le module [angular-messages](https://egghead.io/lessons/angularjs-introduction-to-ng-messages-for-angularjs) est utilisé pour la validation des formulaires.

### Aperçu du Backend ###

Le backend est développé en Java 8 avec le framwork Spring 4, JPA 2 et Hibernate 4. La configuration Spring est en Java (le xml étant trop verbeux et illisible). les principaux modules Spring modules utilisés sont Spring MVC et Spring Security. Le backend a été construit suivant l'approche  [Domain-driven design](https://fr.wikipedia.org/wiki/Conception_pilot%C3%A9e_par_le_domaine), lequel inclus un domaine modèle (entités), services, répertoires (liste d'entités) et DTO (Data Transfer Object) pour le transfère frontend/backend en JSON. 

Les web services REST sont basés sur Spring MVC et JSON. Les tests unitaires sont fait avec Spring Test et les tests fonctionnels de l'API REST sont fait avec [Spring test MVC](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/testing.html#spring-mvc-test-framework).

#### Sécurisation du Backend####

Le module Spring Securitya été utilisé pour la sécurisation des services REST du backend (ses [guidelines](https://www.owasp.org/index.php/REST_Security_Cheat_Sheet) ont été appliquées). L'application peut tournée en mode HTTPS-only via un paramètre du serveur, ce qui signifie qu'aucune page ne sera délivrée aux utilisateurs en HTTP.

Le formulaire d'indentification de Spring Security a été utilisé, avec un fallback en HTTP-Basic Authentication pour les clients non basés en HTTP. Il y a une protection en place contre le CSRF ([cross-site request forgery](https://www.owasp.org/index.php/Cross-Site_Request_Forgery_%28CSRF%29)). 

Les validations Frontend du coté utilisateurs sont seulement à titre de signalement, car les vérification sont également faites niveau backend. L'utilisation d'Angular donne une bonne protection contre les problèmes récurrents comme [cross-site scripting ou les injections HTML ](https://docs.angularjs.org/misc/faq). Les requêtes du backend sont réalisées par des requêtes nommées ou par l'API criteria, laquelle offre de bonnes protections contre les injections SQL.

La réglementation du mot de passe est d'être au moins de 6 caractères avec au minimum une majuscule et un chiffre. Les mots de passe ne sont pas stockés directement en base mais en cryptés grâce à l'utilisation de Spring Security [Bcrypt](http://docs.spring.io/autorepo/docs/spring-security/3.2.0.RELEASE/apidocs/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.html) password encoder.

#### REST API ####

L'API REST du backend est composée de 3 services:

##### Service d'Authentication #####

Url           |Verb          | Description
--------------|------------- | -------------
/authenticate |POST          | authentifie l'utilisateur
/logout       |POST          | termine la session


##### Service d'Utilisateur#####

Url           |Verb          | Description
--------------|------------- | -------------
/user         |GET           | retrouve les infos de l'utilisateur
/user         |PUT           | sauvegarde les infos de l'utilisateur
/user         |POST          | créé un nouveau utilisateur



##### Service Profile#####

Url           		|Verb          | Description
--------------	|------------- | -------------
/profile/id/{id}	|GET          | retrouve le profile par son id
/profile/count  |GET|retourne le nombre de tous les utilisateurs ayant un profil (exclus donc les administrateurs et modérateurs)
/profile/gender/{gender}/interests/{interests}              |GET| retrouve les profils correspondants au genre et aux intérêts passés

##### Service Interest#####

Url           		|Verb          | Description
--------------	|------------- | -------------
/interet|GET          | retourne la liste des intérêts disponibles




### Quelques images de l'application ###

Cette image montre le design de l'application

![alt GeekTic Main Page Coverage](http://i.imgur.com/XQC5D31.png)
<i>page d'identification


