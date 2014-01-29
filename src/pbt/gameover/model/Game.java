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
    /* La dernière position du joueur courant.
     * Si c'est son premier mouvement dans ce tour,
     * sa position est sa position de départ
     * (cette position de départ est « hors donjon »)
     */
    private DungeonPosition lastPosition;
    /*
     * Pour gagner, il faut la princesse et la clé.
     */
    private boolean findKey;
    private boolean findPrincess;
    /*
     * Je dois mémoriser si la partie est en cours (si c'est un barbare dans
     * le donjon ou bien s'il vient de mourir et qu'il faut passer au suivant)
    */
    private boolean turnInProgress;
    /* La partie est finie dès qu'une princesse est trouvée, idWinner
     * passe de -1 à l'id du gagnant
     */
    private int idWinner;

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
     * @throws GameOverException
     */
    public Game() throws GameOverException{
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
            players.add(p);
        }
        dungeon = Dungeon.getInstance();
        idCurrent = 0;
        lastPosition = POSITIONS[idCurrent];
        findKey = false;
        findPrincess = false;
        turnInProgress = false;
        idWinner = -1;          // Pas de gagnant
    }

    static Game getTestInstance(){
        Game instance = null;
        try {
            instance = new Game();
            instance.dungeon = Dungeon.getInstance();
            //@todo continuer l'écriture des tessts 
        } catch (GameOverException e){

        }
        instance.dungeon = Dungeon.getInstance();
        return instance;
    }

    /**
     * Getter du donjon
     * @return le donjon
     */
    public Dungeon getDungeon() {
        //@todo v2 Dungeon devrait être clonebale et je devrais renvoyer une
        // copie. aucune raison que la vue ne modifie mon donjon ! 
        return dungeon;
    }

    /**
     * Getter le joueur courant.
     * @return le joueur courant
     */
    public Player getCurrentPlayer(){
        // Je sais que idCurrent est tjs valide.
        return players.get(idCurrent);
    }

    /**
     * Précise si un barbare est dans la place ou s'il faut passer au joueur
     * suivant.
     *
     * @return vrai si un barbare est dans le donjon
     */
    public boolean isTurnInProgress() {
        return turnInProgress;
    }

    /**
     * Précise si la partie est finie.
     * La partie est finie dès qu'un joueur a trouvé la princesse.
     * @return vrai dès qu'un barbare a trouvé la princesse
     */
    public boolean isOver(){
        return idWinner != -1;
    }

    /**
     * Retourne le joueur gagnant.
     *
     * @return le joueur gagnant ou null si la partie est en cours
     */
    public Player getWinner(){
        Player winner = null;
        if ( idWinner != -1) {
            winner = players.get(idWinner);
        }
        return winner;
    }
    

    /**
     * Jouer un coup.
     * Soit c'est un joueur qui commence son tour, soit c'est le joueur courant
     * qui retourne une tuile supplémentaire.
     * @param d la direction prise par le barbare.
     * @param wt l'arme choisie
     * @return true si le barbare n'est pas mort, false sinon !
     * @throws pbt.gameover.model.GameOverException
     */
    public boolean play(Direction d, WeaponType wt)throws GameOverException{
        //@todo vérifier que c'est le bon joueur qui joue (pour l'instant, on
        // compte sur la vue)
        boolean isWin = true;
        if (idWinner != -1) {
            throw new GameOverException("La partie est finie");
        }
        // La partie n'est pas finie
        turnInProgress = true;
        DungeonPosition newPosition;        
        newPosition = lastPosition.move(d);
        dungeon.show(newPosition);
        Room room = dungeon.getRoom(newPosition);
        // Je mets déjà à jour ma position pour le tour suivant 
        // (elle changera peut-être si je meurs !)
        lastPosition = newPosition;
        switch (room.getType()) {
            case GATE:
                    // On ne fait rien dans la v1
            case KEY:
                findKey = true;
                checkIfIWin();
                break;
            case PRINCESS:
                findPrincess = true;
                checkIfIWin();
                break;
            case BLORK:
                WeaponType blorkWeakness = room.getWeapon();                
                // Si blorkWeakness vaut null, c'est un blork invincible
                // ce sera pour la v2
                // @todo v2
                if (blorkWeakness != wt) {                    
                    //nextPlayer();
                    isWin = false;
                }
            default:
        }
        return isWin;
    }

    private void checkIfIWin() {
        if (findKey && findPrincess) {
            idWinner = idCurrent;
        }
    }

    /**
     * Permet de passer au joueur suivant dès que c'est « Game Over ».
     */
    public void nextPlayer(){
        idCurrent = ++idCurrent % players.size();
        findKey = false;
        findPrincess = false;
        lastPosition = POSITIONS[idCurrent];
        turnInProgress = false;
        dungeon.hideAll();
    }

}
