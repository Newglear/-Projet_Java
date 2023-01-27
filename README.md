# Chador
Evan ROZIERE, Gwenael ROBERT, 4IR-A1 2022-2023

## Présentation du projet

Ce dépot contient le résultat de nos travaux dans le cadre de l'UF Conception et Programmation avancée. 
Il s'agit d'une application de caht non centralisée réalisée en Java. 
Elle supporte les fonctions suivantes: 
- **phase d'établissement du réseau local** par broadcast UDP 
- Configuration de l'utilisateur
- Modification du pseudonyme en cours d'éxécution 
- Conversation avec fiabilité d'ordre conservés localement dans une base de données SQLlite 
- Historique de message avec les utilisateurs connectés et déconnectés

## Spécificités techniques 

- **Configuration**:Projet maven configuré par [pom.xml](./Projet/pom.xml) pour gérer les dépendances
- **Interface graphique**: Réalisé à l'aide de JavaFX pour simplifier la conception.
- **Accès sécurisé**: L'accès aux ressources partagées est protégé grâce au mot-clef synchronized de Java 
- **Tests Unitaires**: Implémentation de [tests unitaires](./ChatBotFX/src/test/java/chatsystem) avec Junit
- **Pattern Observer**: Réalisé dans [DataBaseManager](ChatBotFX/src/main/java/org/database/DatabaseManager.java) pour mettre à jour l'interface graphique
- **Pattern Singleton**: Implémenté sur [SystemComponents](ChatBotFX/src/main/java/org/SystemComponents.java) pour assurer l'unicité des composants globaux 

- **intégration continue**: Utilisant les action de GitHub pour automatiser les tests unitaires *(voir [fichier de configuration](./.github/workflows/integration.yaml))*


## Procédure de lancement 
### Prérequis 
Le lancement nécessite l'installation préalable de java *(version 11 minimum)* et de Maven *(sudo apt install maven)*
```sh
# cloner le projet 
git clone https://github.com/Newglear/Projet_Java/
cd ChatBotFX
# compiler le projet  
mvn compile
# exécuter les tests 
mvn test
# démarrer l'application 
mvn javafx:run
```
### Crédits 
Logo de l'application :
<img src="ChatBotFX/src/main/resources/org/gui/images/loginChad.png">


### Quelques liens 
  - [Code Source ](./Projet/src/main/java/chavardage)
  - [Rapport sur la conception du projet](./RapportProjetJava.pdf)
  - [Conception](./UML/UML%20Final)  

### UF "Conception et Programmation avancées" de 4ème année INSA 

  - [UML et patrons de conception](https://moodle.insa-toulouse.fr/course/view.php?id=1283)
	- [nos TDs de "prise en main" de l'UML](./TD/TDs_UML) 
  - [Programmation avancée en Java](https://moodle.insa-toulouse.fr/course/view.php?id=1228) 
	- [nos TDs de "prise en main" de programmation avancée en Java](./TD/TDs_Java)
  - [Conduite de projet](https://moodle.insa-toulouse.fr/course/view.php?id=1759) 
  - [PDLA](https://moodle.insa-toulouse.fr/course/view.php?id=1758)
