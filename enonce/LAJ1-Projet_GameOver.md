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

|*Partie*     | Plan de tests | Version 1 | Version 2 | Défense orale |
|*Semaine du* |   24 février  |  24 mars  |  28 avril |    5   mai    |

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
à partir de n’importe quel autre emplacement du plateau de jeu non encore retourné.

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
l’exception des entrées du donjon. Ceci signifie que les entrées du donjon ne peuvent absolument pas contenir les blorks invincibles.

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

#GameOver, version 1
Dans cette première version de l'application, nous gèrerons l'inscription et le jeu de base. En particulier, nous ne nous occuperons pas des déplacements lorsqu'une carte GATE (porte) est identifiée ni de ce qui se passe lorsqu'un blork invincible est rencontré. Ces 2 cas seront laissés pour la version 2.

#Les classes

Ce premier ensemble de classes font partie du modèle.

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
* *BLUDGEON*, une massue (un bâton noueux, beaucoup plus gros d'un bout que de l'autre),
* *GUN*, une arme à feu.

##Énumération BarbarianColor
Cette énumération présente les 4 types possibles de couleurs des barbares et des princesses.
Elle a les valeurs suivantes :
* *RED*
* *GREEN*
* *BLUE*
* *YELLOW*

##Énumération RoomType
Cette énumération présente les 4 figures possibles que peuvent prendre les cartes, que nous
appellerons ici *Room*. Ce nom a été choisi car les emplacements représentent les pièces du donjon dans lequel se trouvent les princesses.
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
dans les règles. Nous suggérons que ces positions initiales soient choisies de telle sorte que si 2 joueurs
seulement participent, ils partent de positions opposées. Les positions initiales seraient par exemple
respectivement, pour 1 le nord-ouest, pour 2 le sud-est, pour 3 le nord-est et pour 4 le sud-ouest.

###Méthodes
Cette classe contient un constructeur à 2 paramètres (ligne, colonne). Les valeurs des paramètres
sont vérifiées par le constructeur,
et une *GameOverException* est lancée si un des paramètres n'est pas dans les bornes autorisées. Sont aussi présents,
les accesseurs *int getRow* et *int getColumn*.

Une méthode *DungeonPosition move (Direction)* qui, en fonction de
la direction en paramètre renvoie la nouvelle position dans le sens considéré. Si
la position de destination n'est pas autorisée, une exception sera lancée.

Elle réécrit également la méthode toString().

##Classe Player
Cette classe représente un joueur (player). Un joueur a comme attribut
* *name* : *String*, un nom
* *n* : *int*, un numéro unique (entier positif de 0 à 3). Cet indentifiant unique est
 incrémenté automatiquement chaque fois que l'on crée un participant et sert à déterminer dans
 le constructeur, les valeurs initiales de position et couleur.
* *color* : *BarbarianColor*, la couleur du personnage,
* *firsPosition* : *Position*, la position initiale de la première pièce où il passe.
Cet attribut ne pourra qu'être lu.

###Méthodes
Cette classe aura un constructeur à 1 paramètres : le nom. La position  initiale et la
couleur sont définies automatiquement au départ, par exemple de tableaux définissant la couleur et la position. Le
constructeur lance une exception si on essaye de construire plus de 4 joueurs.

La méthode possède aussi les accesseurs *BarbarianColor getColor()*, *String getName()*,
*Position getFirstPosition()*. Elle réécrit également la méthode toString().

##Classe Room
Cette classe représente un élément du donjon, le plateau de jeu. Ceci représente donc
aussi une carte déposée sur le plateau de jeu. Nous le voyons comme une des places où
le joueur peut se trouver, d'où le nom. Elle a comme attribut :
* *type* : *RoomType*, le type de figures que l'élément du dongeon peut éventuellement porter.
* *weapon* : *WeaponType*, le type d'armes  que l'élément du dongeon peut éventuellement porter,
* *color* : *BarbarianColor*, qui représente la couleur de la carte,
* *hidden* : *booléen*, un paramètre qui indique si la carte est cachée (vrai) ou si elle
a été retournée et est donc visible (faux). Ce paramètre est donc vrai au début de la partie.

Lors de l'usage des objets instanciés de cette classe, certains attributs seront à *null*
si ils ne sont pas pertinents. Par exemple, si la carte est une clé, elle n'a pas de couleur
et pas d'arme. Si c'est un blork, il n'a pas de couleur.

###Méthodes
Cette classe contient un constructeur à 4 paramètres *Room()* et définit les 4 attributs,
les accesseurs *RoomType getType()*,
*WeaponType  getWeapon()*, *boolean isHidden()* et *BarbarianColor getColor(), et
le mutateur de *void setHidden(booléen)*.

##Classe Dungeon
Cette classe représente le donjon, c'est-à-dire le plateau de jeu. Elle a comme attributs :
* *N=5*, une constante publique qui indique la taille d'un côté du plateau de jeu.
* *roomss : Room[][]*, un tableau à 2 dimensions qui contiendra les cartes mélangées.

Pour être sûr de ne créer qu'une seule instance de donjon, nous allons mettre en œuvre
le [*design pattern singleton*](http://www.oodesign.com/singleton-pattern.html)
qui consiste en une classe contenant une méthode
qui crée une instance uniquement s'il n'en existe pas encore.
Dans le cas contraire, elle renvoie une référence vers l'objet qui existe déjà.

Pour cela, le constructeur de la classe doit etre privé,
afin de s'assurer que la classe ne puisse être instanciée
autrement que par la méthode de création contrôlée. On crée un attribut privé *instance* et
une méthode de classe publique *Dungeon getInstance()* seule autorisée à créer
le donjon si l'instance n'existe pas. Cette méthode doit donc être appelée pour
créer le donjon.

Nous introduisons donc un attribut de classe
* *instance : Dungeon* initialement mis à null lors de l'initialisation.

###Méthodes
La classe possède une constructeur **privée* sans paramètre *Dungeon()*.
Le fait que le constructeur soit privé fait qu'il ne peut pas être utilisé par un
autre objet. On introduit la méthode *Dungeon getInstance()* qui est la seule
par laquelle il
est possible de créer l'objet, et uniquement si l'attribut *instance* était
préalablement à null.

Ce constructeur crée explicitement
les 25 pièces. Elles sont toutes initialement cachées. Celles-ci sont : 16 cartes de
type BLORK séparées en 4 fois 4 avec chaque arme, sans couleur. Une de type GATE,
sans couleur. Deux de type KEY, sans couleur.  Quatre de type PRINCESS, chacune
d'une couleur. Et deux de type BLORK sans arme ni couleur qui seront les blorks
invincibles. Une des manières de remplir le tableau *roomss* est de créer un
tableau de cartes transformé ensuite en une liste avec la méthode adéquate
de la classe Arrays. De mélanger cette liste, puis dans
des boucles qui parcourent les éléments du tableau, d'enlever au fur et à
mesure les éléments de cette liste et les mettre dans le tableau.

Il y a encore les méthodes
* *Room getRoom(DungeonPosition)*  qui renvoie le type de pièce à la position en paramètre,
* *void show(DungeonPosition)* qui modifie la valeur de l'attribut *hidden* d'une pièce
pour qu'elle devienne visible. La valeur devient donc *faux*.
* *void hideAll()* qui modifie pour toutes les cartes du donjon les valeurs du paramètre
*hidden* pour qu'elles soient à nouveau cachées.

##Classe Game
C'est la classe qui contient le plus d'«intelligence». Elle sera la principale avec
laquelle échangeront les classes de présentation de la partie *view*.

Cette classe contient les attributs :
* *dungeon : Dungeon*, le donjon
* *players : List<Player>*, la liste des joueurs
* *idCurrent : int*, le numéro du joueur courant,
* *lastPosition : DungeonPosition*, la dernière position du joueur courant. Si
c'est son premier mouvement du tour, sa position est sa position de départ.
* *findKey : booléen*, et
* *findPrincess : booléen*, 2 valeurs qui sont mises à vrai lorsque une clé et
la princesse de la bonne couleur sont trouvées, ce qui est nécessaire pour gagner.
* *idWinner : int*, le numéro du joueur gagnant. Ce numéro est initialement mis à -1
par le constructeur, indiquant qu'il n'y a pas encore de vainqueur. Il est mis à l'id du
gagnant lorsque celui-ci est défini.
* *turnInProgress* : *booléen*, une variable qui permet de mémoriser si
le tour du joueur est en cours. Elle est mise à *false* par le constructeur.

###Méthodes
Cette classe a les méthodes suivantes :

* un constructeur *Game(String[] names)* où *names* sont les noms des joueurs.
Le constructeur vérifie qu'il y a 2, 3 ou 4 joueurs et envoie une GameOverException si
ce n'est pas le cas. Le constructeur va créer les joueurs et les ajouters à
la liste *players*. Il crée un donjon unique par appel à la méthode getInstance de Dungeon.
Le joueur courant est le premier, la dernière position est la première du
premier barbare, les variables *findKey* et *findPrincess* sont mises à faux.

* des getters :
 * *public Dungeon getDungeon()*,
 * *public Player getCurrentPlayer()*, qui renvoie le joueur courant
 * *boolean isTurnInProgress()*

* une méthode *boolean isOver()* qui permet de savoir si la partie est finie.
C'est la valeur de idWinner qui indique cela.

* une méthode *Player getWinner()* qui renvoie null si aucun joueur n'est
encore gagnant et
si la partie n'est pas finie, renvoie le joueur dont l'idWinner est donné.

* une méthode *void play(Direction, WeaponType)*
Cette méthode décrit un coup. Elle commence par tester que la partie n'est pas
finie et lance une exception si elle est finie.
Elle indique ensuite que la partie est en cours.

Avec la direction en paramètre, une nouvelle position est définie depuis la
précédente. La carte du dongeon est mise à visible. Selon la valeur de
type de la pièce correspondante,
les variables *findKey* et *findPrincess* sont mises à vrai et examine si
les clés et princesse ont été trouvées, et le cas échéant, définit le joueur
gagnant comme le gagnant. Si un blork est identifié, il faut comparer les
armes. Si elles ne sont pas les mêmes, on passe au joueur suivant avec *nextPlayer()*.

Dans cette version 1, on ne traite pas le cas où l'on rencontre une porte (GATE),
ni le cas où l'on rencontrerait un blork invincible. Ces 2 cas seront laissés pour la version 2.

* une méthode *void nextPlayer()*. Celle-ci incrémente l'identité du
joueur (en revenant au premier si l'on dépasse le nombre de joueurs),
remet la variable *findKey*, *findPrincess*, *turnInProgress* à faux,
redéfinit la position à la position initiale du nouveau courant joueur et
remet toutes les cartes à l'état caché.

##Classe GameView
Cette classe fait partie du package «view». Elle est destinée à l'interface
utilisateur qui sera dans notre cas en mode **console**. Ceci signifie que
pour faire une interface graphique, il ne faudrait modifier que les classes
de ce package. C'est cette classe qui contient la méthode *main*.

Cette classe instancie le jeu. Tant que le jeu n'est pas fini, c'est-à-dire
qu'un joueur n'a pas trouvé la princesse de sa couleur et une clé, il faut
demander au joueur de choisir et introduire un mouvement et une arme. Et
bien sûr afficher le tableau de jeu et des questions

C'est sans doute une bonne idée d'ajouter une classe *Display* qui gère
les présentations à l'utilisateur. Celle-ci typiquement possède des
méthodes pour présenter une pièce, le donjon
et du texte. Elle utilisera par exemple la classe java.io.Console et ses méthodes.

#GameOver, version 2
Dans cette seconde version de l'application, vous rajouterez les cas qui décrivent
ce qui a lieu lorsqu'une carte GATE ou blork invincible est identifiée.

XXX À compléter XXX






