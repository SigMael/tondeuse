# Projet tondeuse
Test technique Tondeuse pour Xebia

## Résumé du projet
Ce projet simule les déplacements de N tondeuses sur une pelouse d'une dimension [x,y] données. Les données en entrée du programme sont lue à 
partir d'un fichier texte dont le format est défini.

## Prérequis 
Pour faire fonctionner le programme il est nécessaire d'avoir installer java et le JDK 10+ sur son poste.
Via un IDE tel quie Eclipse ou IntelliJ il est nécessaire de suivre les étapes suivantes :
- Cloner le projet git sur sont post (git clone https://github.com/SigMael/tondeuse.git)
- Installer les dépendances Maven du projet (Maven Clean Install)
- Définir en premier argument du projet le path vers le fichier servant de données d'entrées au programme. Fichier contenant toutes les données 
Tondeuse.
- Run le programme (le main se trouve dans la classe MowerApplication)

## Exemple run de l'application 
Si on fournit en entrée un fichier dont les lignes sont les suivantes :
5 5<br/>
1 2 N<br/>
GAGAGAGAA<br/>
3 3 E<br/>
AADAADADDA

Le programme doit alors généré une sortie indiquant les positions finales des tondeuses :<br/>
1 3 N<br/>
5 1 E<br/>

## Tests
Le projet contient deux lots de tests différents. Chacun testant une fonctionnalité différente de l'application. Ces tests sont 
inclus dans les classes ParseShould & MowShould. Une fois le projet correctement installé sur son poste ces tests doivent
tous être valides.

## Built With
Java 10+
Spring Framework (dependance core & context)
Spring boot starter & starter test
Maven - Dependency Management
Mockito

## Auteur
Mael Sigaroudi
