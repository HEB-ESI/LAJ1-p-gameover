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

Les classes métiers (model) seront regroupées dans le package g12345.gameover.model

Les classes de présentation (vue) seront regroupées dans le package g12345.gameover.view

#Plan de test

(*** sera précisé plus tard, après la définition des classes)

#Les classes

##Classe GameOverException
Cette classe est une exception controlée par le compilateur. Cette exception sera lancée dès que l'on demande
à la partie model quelque chose d'incohérent.

###Méthodes
Cette classe hérite de la classe Exception et a 1 constructeur : le constructeur à un paramètre de type *String*,
qui décrit l'erreur qui s'est produite.

##Énumération WeaponType
Cette énumération présente les 4 types possibles pour les armes. Elle a les valeurs suivantes :
* *POTION*, une potion,
* *ARROWS*, représentant un arc à flèches,
* *BLUDGEON*, un gourdin,
* *GUN*, une arme à feu.

##Énumération RoomType
Cette énumération présente les 4 figures possibles que peuvent prendre les cartes, que nous
appellerons ici *Room*.
* *BLORK*, un personnage armé,
* *PRINCESS*, une princesse,
* *KEY*, une clé,
* *GATE*, une porte

##Énumération Direction
Cette énumération définit les 4 types de déplacements possibles.
* *UP*
* *DOWN*
* *RIGHT*
* *LEFT*

##Énumération FightResult
On entend par fight, l'opération d'examiner la valeur de la carte retournée par le joueur. Comme vous le verrez
ci-dessous, une carte est aussi vue dans le jeu comme une pièce (espace, en anglais «room») dans le dongeon.
Les valeurs possibles sont :
* *WIN*
* *LOSE*
* *PRINCESS*
* *GATE*
* *KEY*

##Classe DungeonPosition
Cette classe représente une position dans le plateau de jeu, le donjon. Une position a
comme attribut
* *column* : *int*, un entier positif représentant la colonne, comptée depuis le coin supérieur
gauche (0) et dont la valeur maximale est taille du plateau-1,
* *row* : *int*, un entier positif représentant la ligne, comptée depuis le coin supérieur
gauche (0) et dont la valeur maximale est taille du plateau-1. Cette valeur de la taille du plateau
est une constante publique de la classe Dungeon qui vaut 5.

La classe définit aussi quatre variables constantes publiques, les positions initiales des joueurs sont définies.
Ce sont P_BARBARIAN_1 à P_BARBARIAN_4. Celles-ci sont créés aux positions hors de la grille comme précisées
dans les règles, en tournant dans le sens des aiguilles d'une montre pour les 4 barbares.

###Méthodes
Cette classe contient un constructeur à 2 paramètres (ligne, colonne). Les valeurs des paramètres sont vérifiées par le constructeur,
et une *GameOverException* est lancée si un des paramètres n'est pas dans les bornes autorisées. Sont aussi présents,
les accesseurs *int getRow* et *int getColumn*.

Quatre méthodes publiques de déplacement sont ajoutées : les méthodes *DungeonPosition up()*, *DungeonPosition down()*,
*DungeonPosition left()* et *DungeonPosition right()*. Elles renvoient respectivement des positions au dessus, en dessous,
à gauche et à droite de la position courante.
Ces 4 méthodes méthodes sont aussi utilisées dans la méthode *DungeonPosition move (Direction)* qui, en fonction de
la direction en paramètre renvoit la nouvelle position dans le sens condiéré.

Il y a encore 2 méthodes :
* *boolean isAdjoining(DungeonPosition)* qui permet de voir si une position est adjacente, c'est à dire dans la même ligne
et la colonne immédiatement voisine, ou la même colonne et la ligne immédiatement voisine.
* *boolean isInDungeon()* qui permet de voir si la position est bien dans le plateau de jeu.

Elle réécrit également la méthode toString().

##Classe Player
Cette classe représente un joueur (player). Un joueur a comme attribut
* *name* : *String*, un nom
* *n* : *int*, un numéro (entier positif de 0 à 3). Cette valeur sera utilisée pour identifier la couleur
du joueur par rapport au jeu papier, et associer la princesse et la clé correspondante. Cet identifiant unique
sera incrémenté automatiquement chaque fois que l'on crée une joueur.
* *firsPosition* : *Position*, la position initiale de la première pièce où il passe. Cet attribut ne pourra
qu'être lu.

###Méthodes
Cette classe aura un constructeur à 2 paramètres : le nom et la position initiale, et les accesseurs *int getN*,
*String getName*, *Position getFirstPosition*. Elle réécrit également la méthode toString().

##Classe Room
Cette classe représente un élément du donjon, le plateau de jeu. Nous le voyons comme un des places où
le joueur peut se trouver, d'où le nom. Elle a comme attribut :
* *type* : *RoomType*, le type de figures que l'élément du dongeon peut éventuellement porter. Il sera *null* si

* *weapon* : *WeaponType*, le type d'armes  que l'élément du dongeon peut éventuellement porter,
* *n* : *int*, un entier positif qui représente la couleur de la carte,
* *hidden* : *booléen*, un paramètre qui indique si la carte est cachée (vrai) ou si elle  a été retournée et est donc visible (faux). Ce paramètre est donc vrai au début de la partie. 

###Méthodes
Cette classe contient un constructeur à 4 paramètres *Room()* et définit les 4 attributs, les accesseurs *RoomType getType()*,
*WeaponType  getWeapon()*, *boolean isHidden()* et *int getN(), et les mutateurs de chacun des attributs
*void setType(RoomType)*, *void setWeapon(WeaponType)*, *void setHidden(booléen)*, *void setN(int)*.

##Classe Dungeon
Cette classe représente le donjon, c'est-à-dire le plateau de jeu. Elle a comme attributs :
* *N=5*, une constante publique qui indique la taille d'un côté du plateau de jeu.
* *Room[][] roomss*, un tableau à 2 dimensions qui contiendra la carte mélangées.

###Méthodes
La classe possède une constructeur sans paramètre *Dungeon()*. Celui-ci crée explicitement 
les 25 pièces. Elles sont toutes initialement cachées. Celles-ci sont : 16 cartes de type BLORK séparées en 4 fois 4 avec chaque arme, sans couleur. Une de type GATE, sans couleur. Deux de type KEY, sans couleur.  Quatre de type PRINCESS, chacune d'une couleur. Et deux de type BLORK sans arme ni couleur qui seront les blorks invincibles. Une des manières de remplir le tableau *roomss* est de créer une liste de cartes, de les mélanger, puis dans 
des boucles qui parcourent les éléments du tableau, d'enlever au fur et à mesure les éléments de cette liste et les mettre dans le tableau.

Il y a encore les méthodes
* *Room getRoom(DungeonPosition)*  qui renvoit le type de pièce à la position en paramètre, 
* *void show(DungeonPosition)* qui modifie la valeur de l'attribut *hidden* d'une pièce
pour qu'elle devienne visible. La valeur devient donc *faux*.
* *void hideAll()* qui modifie pour toute les cartes du donjon les valeurs du paramètre
*hidden* pour qu'elles soient à nouveau cachées.












