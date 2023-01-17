# Projet Java

Chat Bot project by Evan Roziere and Gwenael Robert


TO DO 

- Database Manager
  - Flush des données *Done* 
  - Init de la base de données au départ *Done*
- Test unitaires et pipelines (CICD) 
  - Network 
    - Envoi et réponse *Done*
    - Update Nickname *Done*
    - Unicity check *Done*
  - Conversation
    - Envoi simple et multiple
    - Gestion de discussions multiple
    - Fermeture de connexion
  - Database 
    - Gestion de liste de contact (Ajout, Suppression, Mise a jour, Chargement simple et multiple) *Done*
    - Gestion de message(Ajout, Suppression,Chargement) *Done*
- Timer broadcast UDP *A implémenter dans le controller du login (Timer+ Timertask ou Gestion avec Millis)*
- Ajout d'un serveur et de threads handlers udp (gestion parallèles)
- Update DB : status column (connected deconnected),setter for the status update and method to get each one (SQL requests) (Envoi des messages à tester avec 2 pcs)
- Singleton System Components (A tester plus profondément mais devrait être fonctionnel) 
- Gestion de l'adresse de broadcast



1. Tous les messages sont envoyés avec son pseudo 
2. Le stockage des messages envoyés sont signés avec le destinataire
(A gérer au niveau des tests)

Envoi des messages à tester avec 2 PCs

Get all connected/disconnected user 


**Rendu = Readme + Tout sur le Git + Le rapport** 
