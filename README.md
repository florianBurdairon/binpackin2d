Alban Bernard \
Florian Burdairon

# Projet Optimisation Discrète - Bin Packing

## Exécution du programme

### Prérequis
- Java 21
- Maven

### Exécution avec Maven
Pour exécuter le programme, il suffit de lancer la commande suivante :
```bash
mvn javafx:run
```

### Exécution avec un IDE
Il est également possible d'exécuter le programme avec un IDE en lançant la classe `Main` située dans le package `tp.optimisation`.
> Nous avons uniquement testé le programme avec IntelliJ IDEA.

## Modélisation du problème

Bin packing 2D : 2BP \
Orientation des items : O (Oriented) / R (Can be Rotated) \
Découpage du packing : G (Guillotine) / F (Free) \
Pour ce projet : **2BPRG**

Les algorithmes mis en place utilisent un système de voisinage pour s'approcher de la solution optimale.
A partir d'une solution, nous prenons pour voisin toute autre solution qui est le résultat d'un de ces changements :
 - Déplacer un item d'une bin à une autre
 - Déplacer un item d'une bin à une autre et le faire tourner de 90°
 - Echanger deux items
 - Echanger deux items en faisant tourner de 90° l'item 1
 - Echanger deux items en faisant tourner de 90° l'item 2
 - Echanger deux items en les faisant tourner de 90°

Afin de calculer la fitnesse d'une solution, nous additionnons pour chaque bin de la solution la racine carrée du pourcentage de son espace vide.

## Calculs théoriques

| **Fichier** 	          | **Surface d'une bin** 	 | **Surface de l'ensemble des items** 	 | **Minimum théorique du nombre de bins** 	 |
|------------------------|-------------------------|---------------------------------------|-------------------------------------------|
| binpacking2d-01.bp2d 	 | 62500 	                 | 163562 	                              | 3 	                                       |
| binpacking2d-02.bp2d 	 | 62500 	                 | 274563 	                              | 5 	                                       |
| binpacking2d-03.bp2d 	 | 62500 	                 | 407651 	                              | 7 	                                       |
| binpacking2d-04.bp2d 	 | 62500 	                 | 731408 	                              | 12 	                                      |
| binpacking2d-05.bp2d 	 | 250000 	                | 545300 	                              | 3 	                                       |
| binpacking2d-06.bp2d 	 | 250000 	                | 1232057 	                             | 5 	                                       |
| binpacking2d-07.bp2d 	 | 250000 	                | 2004791 	                             | 9 	                                       |
| binpacking2d-08.bp2d 	 | 250000 	                | 2805462 	                             | 12 	                                      |
| binpacking2d-09.bp2d 	 | 1000000 	               | 2021830 	                             | 3 	                                       |
| binpacking2d-10.bp2d 	 | 1000000 	               | 5355377 	                             | 6 	                                       |
| binpacking2d-11.bp2d 	 | 1000000 	               | 6536520 	                             | 7 	                                       |
| binpacking2d-12.bp2d 	 | 1000000 	               | 12521992 	                            | 13 	                                      |
| binpacking2d-13.bp2d 	 | 9000000 	               | 14315508 	                            | 2 	                                       |


## Protocole de test
Pour tester les performance de nos métaheuristiques en temps d'exécution et en qualité de solution, nous avons utilisé les fichiers de test fournis.
Pour supprimer le facteur aléatoire, Chaque test d'une métaheuristique sur un jeu de données a été réalisé 10 fois et nous avons fait les moyennes des résultats.
Le nombre d'itération maximum a été fixé à 1000.
La qualité de la solution est exprimée en pourcentage de la solution théorique optimale.

## Résultats

### Méthode de descente
| ** Fichier ** | ** Nombre de bins ** | ** Temps d'exécution ** | ** Qualité de la solution ** |
|---------------|-----------------------|-------------------------|-------------------------------|
| binpacking2d-01.bp2d | 3 | 0.000s | 0.0% |
| binpacking2d-02.bp2d | 5 | 0.000s | 0.0% |
| binpacking2d-03.bp2d | 7 | 0.000s | 0.0% |
| binpacking2d-04.bp2d | 12 | 0.000s | 0.0% |
| binpacking2d-05.bp2d | 3 | 0.000s | 0.0% |
| binpacking2d-06.bp2d | 5 | 0.000s | 0.0% |
| binpacking2d-07.bp2d | 9 | 0.000s | 0.0% |
| binpacking2d-08.bp2d | 12 | 0.000s | 0.0% |
| binpacking2d-09.bp2d | 3 | 0.000s | 0.0% |
| binpacking2d-10.bp2d | 6 | 0.000s | 0.0% |
| binpacking2d-11.bp2d | 7 | 0.000s | 0.0% |
| binpacking2d-12.bp2d | 13 | 0.000s | 0.0% |
| binpacking2d-13.bp2d | 2 | 0.000s | 0.0% |

### Méthode de descente avec recuit simulé
Les paramètres utilisés pour le recuit simulé sont les suivants :
- Température initiale : 10000
- Décroissance de la température : 0.01

| ** Fichier ** | ** Nombre de bins ** | ** Temps d'exécution ** | ** Qualité de la solution ** |
|---------------|-----------------------|-------------------------|-------------------------------|
| binpacking2d-01.bp2d | 3 | 0.000s | 0.0% |
| binpacking2d-02.bp2d | 5 | 0.000s | 0.0% |
| binpacking2d-03.bp2d | 7 | 0.000s | 0.0% |
| binpacking2d-04.bp2d | 12 | 0.000s | 0.0% |
| binpacking2d-05.bp2d | 3 | 0.000s | 0.0% |
| binpacking2d-06.bp2d | 5 | 0.000s | 0.0% |
| binpacking2d-07.bp2d | 9 | 0.000s | 0.0% |
| binpacking2d-08.bp2d | 12 | 0.000s | 0.0% |
| binpacking2d-09.bp2d | 3 | 0.000s | 0.0% |
| binpacking2d-10.bp2d | 6 | 0.000s | 0.0% |
| binpacking2d-11.bp2d | 7 | 0.000s | 0.0% |
| binpacking2d-12.bp2d | 13 | 0.000s | 0.0% |
| binpacking2d-13.bp2d | 2 | 0.000s | 0.0% |

### Méthode de descente avec tabou
Les paramètres utilisés pour la méthode de descente avec tabou sont les suivants :
- Taille de la liste tabou : 4

| ** Fichier ** | ** Nombre de bins ** | ** Temps d'exécution ** | ** Qualité de la solution ** |
|---------------|-----------------------|-------------------------|-------------------------------|
| binpacking2d-01.bp2d | 3 | 0.000s | 0.0% |
| binpacking2d-02.bp2d | 5 | 0.000s | 0.0% |
| binpacking2d-03.bp2d | 7 | 0.000s | 0.0% |
| binpacking2d-04.bp2d | 12 | 0.000s | 0.0% |
| binpacking2d-05.bp2d | 3 | 0.000s | 0.0% |
| binpacking2d-06.bp2d | 5 | 0.000s | 0.0% |
| binpacking2d-07.bp2d | 9 | 0.000s | 0.0% |
| binpacking2d-08.bp2d | 12 | 0.000s | 0.0% |
| binpacking2d-09.bp2d | 3 | 0.000s | 0.0% |
| binpacking2d-10.bp2d | 6 | 0.000s | 0.0% |
| binpacking2d-11.bp2d | 7 | 0.000s | 0.0% |
| binpacking2d-12.bp2d | 13 | 0.000s | 0.0% |
| binpacking2d-13.bp2d | 2 | 0.000s | 0.0% |
