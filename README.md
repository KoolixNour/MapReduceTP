## TP3 - MapReduce avec AKKA 
Le but de ce TP est de développer une application Spring qui fournit un service de comptage
de mots à l’aide du paradigme MapReduce mis en œuvre avec des acteurs Akka 
## Architecture 
* MapService : Ce service initialise l'architecture en créant 3 acteurs Mapper et 2 acteurs Reducer. Il distribue ensuite les lignes du fichier alternativement à chaque acteur Mapper et interroge les acteurs Reducer pour obtenir le nombre d'occurrences d'un mot.

* Mapper : Ces acteurs Mapper identifient chaque mot de la ligne et transmettent le mot à un des acteurs Reducer en utilisant une méthode de partition.

* Reducer : Ces acteurs Reducer additionnent les décomptes qu'ils reçoivent pour chaque mot.

 
