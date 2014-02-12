/*
 * Copyright (C) 2014 Pierre Bettens (pbt) <pbettens@heb.be>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package pbt.gameover.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Modèle du jeu.
 *
 * @author Pierre Bettens (pbt) <pbettens@heb.be>
 */
public class Game {

    /* Le donjon */
    private Dungeon dungeon;
    /* La liste des joueurs */
    private List<Player> players;
    /* L'identifiant du jouer courant (0 basé) */
    private int idCurrent;
    /* État du barbare courant. */
    private BarbarianState stateCurrent;
    /* La dernière position du joueur courant.
     * Si c'est son premier mouvement dans ce tour,
     * sa position est sa position de départ
     * (cette position de départ est « hors donjon »)
     */
    private DungeonPosition lastPosition;
    /*
     * Pour gagner, il faut la princesse et la clé.
     */
    private boolean keyFound;
    private boolean princessFound;
    /* La partie est finie dès qu'une princesse est trouvée, idWinner
     * passe de -1 à l'id du gagnant
     */
    private int idWinner;
    /* Je dois mémoriser si je joker a été joué dans un tour de jeu
     * (le sais qu'il ne le sera pas si le joueur n'est pas débutant)
    */
    private boolean jokerUsed;

    /*
     * Pour faciliter le passage au joueur suivant
     */
    private final DungeonPosition[] POSITIONS = {
        DungeonPosition.P_BARBARIAN_1,
        DungeonPosition.P_BARBARIAN_2,
        DungeonPosition.P_BARBARIAN_3,
        DungeonPosition.P_BARBARIAN_4
    };

    /**
     * Un constructeur sans paramètre pour créer une partie sans choix de
     * prénoms.
     *
     * Les prénoms sont imposés; Juste, Pierre, Marlène et François
     *
     * @throws GameOverException
     */
    public Game() throws GameOverException {
        this("Juste", "Pierre", "Marlène", "François");
    }

    /**
     * Constructeur d'une partie de « Game Over ».
     *
     * @param names les prénoms des joueurs
     * @throws GameOverException
     */
    public Game(String... names) throws GameOverException {
        if (names.length < 2 || names.length > 4) {
            throw new GameOverException("Il faut 2,3 ou 4 joueurs");
        }
        players = new ArrayList<>();
        for (String name : names) {
            Player p = new Player();
            p.setName(name);
            // @todo Temporairement, je place tous mes joueurs en débutant
            //p.setBeginner(true);
            players.add(p);
        }
        dungeon = Dungeon.getInstance();
        idCurrent = 0;
        stateCurrent = BarbarianState.CONTINUE;
        lastPosition = POSITIONS[idCurrent];
        keyFound = false;
        princessFound = false;
        idWinner = -1;          // Pas de gagnant
        jokerUsed = false;
    }

    /**
     * Getter du donjon
     *
     * @return le donjon
     */
    public Dungeon getDungeon() {
        //@todo v2 Dungeon devrait être clonebale et je devrais renvoyer une
        // copie. aucune raison que la vue ne modifie mon donjon ! 
        return dungeon;
    }

    /**
     * Setter à visibilité « package » à destination des tests JUnit.
     *
     * @param aDungeon
     */
    void setDonjon(Dungeon aDungeon) {
        dungeon = aDungeon;
    }

    /**
     * Getter le joueur courant.
     *
     * @return le joueur courant
     */
    public Player getCurrentPlayer() {
        // Je sais que idCurrent est tjs valide.
        return players.get(idCurrent);
    }

    /**
     * Précise si la partie est finie. La partie est finie dès qu'un joueur a
     * trouvé la princesse.
     *
     * @return vrai dès qu'un barbare a trouvé la princesse
     */
    public boolean isOver() {
        return idWinner != -1;
    }

    /**
     * Retourne le joueur gagnant.
     *
     * @return le joueur gagnant ou null si la partie est en cours
     */
    public Player getWinner() {
        Player winner = null;
        if (idWinner != -1) {
            winner = players.get(idWinner);
        }
        return winner;
    }

    public BarbarianState getStateCurrent() {
        return stateCurrent;
    }

    /**
     * Jouer un coup. Soit c'est un joueur qui commence son tour, soit c'est le
     * joueur courant qui retourne une tuile supplémentaire.
     *
     * @param d la direction prise par le barbare.
     * @param wt l'arme choisie
     * @return l'état dans lequel se trouve le barbare.
     * @throws pbt.gameover.model.GameOverException
     */
    public BarbarianState play(Direction d, WeaponType wt) throws GameOverException {
        /*
         * Je ne peux me déplacer que si je suis au statut CONTINUE.
         * Sinon, je dois faire un autre mouvement ou passer au joueur suivant
         */
        if (stateCurrent != BarbarianState.CONTINUE) {
            throw new GameOverException("Ce n'est pas le moment de retourner"
                    + " une carte");
        }
        if (idWinner != -1) {
            throw new GameOverException("La partie est finie");
        }
        DungeonPosition newPosition;
        newPosition = lastPosition.move(d);
        return play(newPosition, wt);
    }

    /**
     * Permet de jouer le déplacement du blork invincible.
     *
     * v2 Si le joueur est un joueur débutant, il peut continuer de jouer après
     * avoir déplacé son blork.
     *
     * @param position la nouvelle position
     * @return le nouvel état du barbare
     * @throws GameOverException
     */
    public BarbarianState playBlorkInvincible(DungeonPosition position)
            throws GameOverException {
        if (stateCurrent != BarbarianState.MOVE_BLORK) {
            throw new GameOverException("Ce n'est pas le moment "
                    + "de bouger un blork");
        }
        if (idWinner != -1) {
            throw new GameOverException("La partie est finie");
        }
        if (position.isCorner()) {
            throw new GameOverException("On ne peut pas déplacer un "
                    + "blork invincible dans un coin");
        }
        dungeon.swap(lastPosition, position);
        dungeon.hide(lastPosition);
        dungeon.show(position);
        if (players.get(idCurrent).isBeginner() && !jokerUsed) {
            stateCurrent = BarbarianState.CONTINUE;
            dungeon.show(lastPosition);
            jokerUsed = true;
        } else {
            stateCurrent = BarbarianState.GAMEOVER;
        }
        //@todo la dernière position est probablement incorrecte maintenant
        // envisager d'avoir une liste des positions finalement
        return stateCurrent;
    }

    /**
     * Permet de jouer le déplacement du barbare(via la gate).
     *
     * @param position la nouvelle position
     * @param wt l'arme éventuelle
     * @return le nouvel état du barbare
     * @throws GameOverException
     */
    public BarbarianState playGate(DungeonPosition position, WeaponType wt)
            throws GameOverException {
        if (stateCurrent != BarbarianState.BEAM_ME_UP) {
            throw new GameOverException("Ce n'est pas le moment de se déplacer "
                    + "ou de bouger un blork");
        }
        if (idWinner != -1) {
            throw new GameOverException("La partie est finie");
        }
        // Hop, je saute
        stateCurrent = play(position, wt);
        return stateCurrent;
    }

    /**
     * Je joue mon joker, c'est-à-dire que je peux reproposer une arme si je
     * m'ai fait eu (sic).
     *
     * @param wt la nouvelle arme
     * @return mon nouvel état
     * @throws GameOverException
     */
    public BarbarianState playJoker(WeaponType wt) throws GameOverException {
        if (stateCurrent != BarbarianState.JOKER) {
            throw new GameOverException("Ce n'est pas le moment de jouer "
                    + "son joker");
        }
        if (idWinner != -1) {
            throw new GameOverException("La partie est finie");
        }
        // Je rejoue ma position
        jokerUsed = true;
        stateCurrent = play(lastPosition, wt);        
        return stateCurrent;
    }

    private BarbarianState play(DungeonPosition newPosition, WeaponType wt)
            throws GameOverException {
        if (!dungeon.getRoom(newPosition).isHidden()) {
            throw new GameOverException("La position est déjà visible");
        }
        if (dungeon.isSurrounded(newPosition)) {
            throw new GameOverException("Il est interdit d'être entouré de "
                    + "pièces « visibles »");
        }
        dungeon.show(newPosition);
        Room room = dungeon.getRoom(newPosition);
        // Je mets déjà à jour ma position pour le tour suivant
        // (elle changera peut-être si je meurs !)
         lastPosition = newPosition;
        switch (room.getType()) {
            case GATE:
                stateCurrent = BarbarianState.BEAM_ME_UP;
                break;
            case KEY:
                keyFound = true;
                stateCurrent = BarbarianState.CONTINUE;
               // lastPosition = newPosition;
                checkIfIWin();
                break;
            case PRINCESS:
                princessFound
                        = players.get(idCurrent).getColor() == room.getColor();
                stateCurrent = BarbarianState.CONTINUE;
              //  lastPosition = newPosition;
                checkIfIWin();
                break;
            case BLORK:
                WeaponType blorkWeakness = room.getWeapon();
                // À priori je gagne … on verra dans les tests en dessous ! 
                stateCurrent = BarbarianState.CONTINUE;
                // Si blorkWeakness vaut null, c'est un blork invincible
                if (blorkWeakness == null) {
                    stateCurrent = BarbarianState.MOVE_BLORK;
                } else if (blorkWeakness != wt) {
                    if (players.get(idCurrent).isBeginner() && !jokerUsed) {
                        stateCurrent = BarbarianState.JOKER;
                        dungeon.hide(newPosition);                        
                    } else {
                        stateCurrent = BarbarianState.GAMEOVER;                        
                    }
                }
            default:
        }
        return stateCurrent;
    }

    private void checkIfIWin() {
        if (keyFound && princessFound) {
            idWinner = idCurrent;
        }
    }

    /**
     * Permet de passer au joueur suivant dès que c'est « Game Over ».
     */
    public void nextPlayer() {
        // Pas de test sur la valeur de stateCurrent car je peux éventuellement
        // passer au joueur suivant si c'est pas Game Over
        idCurrent = ++idCurrent % players.size();
        stateCurrent = BarbarianState.CONTINUE;
        keyFound = false;
        princessFound = false;
        jokerUsed = false;
        lastPosition = POSITIONS[idCurrent];
        dungeon.hideAll();
    }

}
