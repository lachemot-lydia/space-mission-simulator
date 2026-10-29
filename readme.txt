Projet : SIMULATION SPATIAL 

Univérsité : Cergy-Pontoise

Encadrant : Tianxiao LIU

Réalisé par des étudiants en deuxième année de licence informatique : 

    -Lydia LACHEMOT
    -Thomas LENGLIN
    -Sabri GHOUDANE


Année universitaire : 2025/2026
    

1-Objectifs du projet :  

    -Développer nos compétences en Génie Logiciel
    -Permettre à l'utilisateur de visualiser et d'analyser des données géospatiales de manière intuitive et efficace
    -Simuler la traversée d'un engin spatial dans le système solaire

2-Execution du programme : 

	2.1-Importer le projet :
            -Ouvrir Eclipse
            -Créer un projet puis supprimer le dossier src de celui-ci 
            -Ajouter le dossier src de ce projet 
            -Cliquer sur "Finish"
    
    2.2-Ajouter les Librairies Externes disponibles sur le site de l'encadrant Tianxiao LIU :
        https://depinfo.u-cergy.fr/~tliu/glp.php
        le projet utilise log4j pour la journalisation et JFreeChart (affichage des graphiques)
        Pour les ajouter :
            -Clic droit sur le projet -> Build Path -> Configure Build Path
            -Onglet "Librairies" -> Add External JARs 
            -Selectionner les fichiers .jar disponibles 
            -Cliquer sur "Apply and Close"

    2.3-Les fichiers de configurations sont presents :
        -src/test/input/planets.csv  (données des planètes du système solaire)
        -src/test/input/images/ (images de la fusée, planètes, satellites...)
        -src/log/log4j-text.properties
        -src/log/log4j-html.properties
	-src/ressources/musique.wav 
    
    2.4-Lancement du programme :
        -Dans le package "main" se trouve la classe "Main"
        -Clique droit sur ce fichier -> Run As -> Java Application
         La fenetre de configuration de la mission s'ouvre

3-Utilisation du simulateur :
    -un menu est disponible en haut des deux fenêtres :  "Help"

4. Prérequis :

    -Language :Java 8 ou supérieur
    -IDE recommandé : Eclipse