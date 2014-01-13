#Laboratoire Java - 2013-2014 - Projet « Game Over »
ÉCOLE SUPÉRIEURE D'INFORMATIQUE

#En bref
Ce projet vous permettra de mettre en œuvre la plupart des concepts vus
pendant cette première année : programmation orientée objet, utilisation
du langage Java, tests unitaires et documentation d'une application
Java. Lisez bien et complètement ce document.

Cette lecture peut vous
paraître longue et demander des efforts, mais ce travail vous tiendra en
haleine durant plusieurs semaines.

Ce document ne contient pas que des données relatives à l'exercice. Il vous
renseigne également sur les modalités de remises et vous donne une
progression dans la résolution de l'exercice.

Nous allons coder une application permettant de gérer le jeu « Game Over »
dont les règles sont présentées ci-dessous.


#Échéances
Le projet sera évalué en plusieurs phases. Vous veillerez à remettre chacun des travaux en temps
et heure sous peine de ne pas être évalué.

Le tableau ci-dessous reprend le différentes semaines pendant lesquelles seront fixées, par votre professeur
les dates butoires de remise. On entend par date butoire, un moment défini pas date et heure, pour lequel
le travail doit être remis au professeur, et au delà duquel le travail *ne* sera *plus* accepté. Rien ne vous empêche
de remettre votre travail plus tôt que la date butoire, la veille, la semaine précédente ...

|*Partie*     | Plan de tests | GameOver version 1 | GameOver version 2 | Défense orale |
|*Semaine du* |     10 mars   |   24 mars          |  22 avril          |  28 avril     |

#Les phases du projet

Le projet se décompose en plusieurs phases. La première phase consiste à réfléchir aux tests à
mettre en œuvre, tandis que les deux autres sont la réalisation des différentes versions de
l'application.

Ce n'est qu'au terme de la dernière phase, la défense orale que votre projet reçoit une cote.
En d'autres termes, si vous ne défendez pas oralement votre projet, il vous sera attribué
la cote zéro pour l'ensemble du projet.

##Phase 1, le plan de tests
Dans un premier temps nous vous demandons de réfléchir à un plan de tests pour une
méthode d'une classe particulière (voir XXXX).

Un plan de tests est une description de tous les cas de test que vous jugez nécessaires pour
prouver le bon fonctionnement de votre code. Cette description fera l'objet d'une première
évaluation sur base du document que vous nous remettrez.

##Phase 2, GameOver version 1
Dans un second temps, vous codez la première version de votre application que vous nous
remettrez. Votre professeur fera des commentaires sur cette première version dont vous
tiendrez compte pour coder la suite.

##Phase 3, GameOver version 2
La deuxième version de votre application contiendra des fonctionnalités supplémentaires. Il
est inutile de commencer la version 2 si la première version n'est ni fonctionnelle, ni évaluée.

##Phase 4, défense orale
À la date, c'est-à-dire jour et heure convenue avec votre professeur, vous devez effectuer
une défense orale-machine de votre projet. Le projet est présenté et  défendu sur
l'environnement linux1 qui est le seul environnement de développement considéré de votre projet.
Ceci signifie que les professeurs considèrent que tout doit être fait, fonctionnel, maitrisable
complétement par vous sur cet environnement linux1 à l'école.
Tout ce qui est fait en dehors est de votre ressort personnel et ne sera pas évalué ni même considéré.

##Remises intermédiaires obligatoires
En plus des échéances fixées, une remise hebdomadaire de votre travail est exigée. Vous
déposerez votre projet dans le casier de votre professeur (acr) via la commande
''casier <acr>''.

Attention : cette remise ne sera pas évaluée, mais elle est obligatoire sous peine de ne pas
voir votre projet corrigé.

#Règles du jeu

Les règles originales du jeu se trouvent
* Le site [lahautroche] (http://www.lahauteroche.eu/gameover/gameover_home.html)
* Les règles [pdf](http://www.lahauteroche.eu/gameover/gameover_GRAFIK/montage%20regles_depliant.pdf)

Le jeu GameOver est un jeu de mémoire joué par 2 à 4 joueurs. Le but du jeu est de parcourir un
plateau de cartes inconnues de 5 * 5, le donjon, partant d'une position de départ fixe afin de trouver
2 cartes, la Princesse et la Clé, de sa propre couleur.

Pour ceci, à chaque tour, le joueur part de sa position initiale sur un bord du donjon, choisit une des
cartes «arme» et découvre successivement des cartes adjacentes en les retournant et en choisissant une nouvelle
«arme». Suivant la nature de la carte retournée et de l'«arme», plusieurs cas sont possibles.

Le joueur continue son exploration si la carte découverte est :
* un blork qui peut être vaincu avec l’arme choisie (icône correspondante),
* une princesse,
* la clé,
* la porte de transfert. Dans ce dernier cas, le joueur peut continuer son exploration
à partir de n’importe quelle autre emplacement du plateau de jeu non encore retourné.

Lorsque le joueur continue son exploration, il laisse sa carte entrée
visible et choisit de nouveau une arme : il peut garder l’arme
déjà utilisée ou l’échanger contre une autre. Puis il retourne une
carte adjacente par l’un des côtés (jamais en diagonale). S’il peut
encore poursuivre son exploration, il laisse cette nouvelle carte
visible et continue son chemin en choisissant son arme pour
chaque nouvelle carte adjacente retournée.

Le tour d'un joueur prend fin (GameOver) lorsque la carte découverte est :
* un blork qui ne peut pas être vaincu avec l’arme choisie,
* un blork invincible. Dans ce cas, le joueur doit intervertir ce blork
invincible et l’une des cartes encore face cachée du plateau, à
l’exception des entrées du donjon.

Lorsque le tour d'un joueur prend fin (GameOver), le joueur retourne toutes les
cartes visibles et replace l’arme près du plateau. Au tour suivant, il
devra repartir de son entrée à son prochain
tour. C’est alors au joueur suivant d’explorer le donjon
avec les informations déjà dévoilées qu’il a, bien sûr,
mémorisées....

Si un joueur n’a plus la possibilité de
continuer son exploration parce que toutes
les cartes adjacentes à sa dernière carte
retournée sont visibles, c’est également
GameOver. C'est alors au tour d'un autre joueur.

Le jeu prend fin dès qu’un joueur parvient, au cours de son exploration, à avoir
simultanément visibles la clé et la princesse de sa couleur. Il remporte
alors la victoire.

#Présentation des classes

Cette présentation est une présentation sommaire des classes qui vont intervenir dans le
projet afin de pouvoir travailler sur le plan de tests.  Une description détaillée est présentée plus loin.

Nous allons distinguer la partie **métier** (business) de la partie **vue** (view) de l'application.
Cette manière de distinguer les classes en fonction de leur rôle est un patron de développement appelé
Modèle/Vue/Controleur (en anglais, design pattern Model/View/Controller). Vous allez approfondir celui-ci en
deuxième année. Nous ne mettrons en œuvre ici qu'une première approche.
[Modèle MVC : quelques informations](https://en.wikibooks.org/wiki/Computer_Science_Design_Patterns/Model%E2%80%93view%E2%80%93controller)

(*** à développer plus tard avec un résumé de la liste des classes ci-dessous)

La partie vue (view) concerne les classes qui s'occupent de la présentation et de l'interaction avec l'utilisateur.

#Packages
Dans ce projet, vous travaillerez dans 2 *packages*, chacun regroupant les classes d'une des
parties définies ci-dessus.

Les classes métiers (business) seront regroupées dans le package g12345.gameover.business

Les classes de présentation (vue) seront regroupées dans le package g12345.gameover.view

#Plan de test

(*** sera précisé plus tard, après la définition des classes)

#Les classes

##Classe Position
Cette classe représente une position dans le plateau de jeu, le Donjon. Une position a
comme attribut
* *column* : *int*, un entier positif représentant la colonne, comptée depuis le coin supérieur
gauche (0) et dont la valeur maximale est 4,
* *row* : *int*, un entier positif représentant la ligne, comptée depuis le coin supérieur
gauche (0) et dont la valeur maximale est 4.

###Méthodes
Cette classe contient un constructeur à 2 paramètres (ligne,colonne) et les accesseurs *int getRow* et
*int getColumn*.

##Classe Player
Cette classe représente un joueur (player). Un joueur a comme attribut
* *name* : *String*, un nom
* *n* : *int*, un numéro (entier positif de 0 à 3). Cette valeur sera utilisée pour identifier la couleur
du joueur par rapport au jeu papier, et associer la princesse et la clé correspondante. Cet identifiant unique
sera incrémenté automatiquement chaque fois que l'on crée une joueur.
* *firsPosition* : *Position*, la position initiale de la première pièce où il passe. Cet attribut ne pourra
qu'être lu.
* *position* : *Position*

###Méthodes
Cette classe aura un constructeur à 2 paramètres : le nom et la position initiale, et les accesseurs *int getN*,
*String getName*, *Position getFirstPosition* et *Position getPosition*.

Elle aura aussi un mutateur *setPosition (column,row)*.
