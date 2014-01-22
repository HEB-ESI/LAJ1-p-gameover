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



/**
 * Représente un joueur.
 *
 * Un joueur représente un petit barbare, il a une couleur et une position de
 * départ (porte d'entrée dans le donjon) qu'il ne peut pas choisir.
 *
 * Il a également un nom.
 *
 * @author Pierre Bettens (pbt) <pbettens@heb.be>
 */
public class Player {    
    /*
     * Création automatique de la couleur du player et
     * de sa position.
     *
     * (L'ordre des barbares dans ce tableau permet de les placer en
     * « diagonale » lorsqu'ils sont deux)
     */    
    private static final DungeonPosition[] POSITIONS =
        {DungeonPosition.P_BARBARIAN_1,            
            DungeonPosition.P_BARBARIAN_3,
            DungeonPosition.P_BARBARIAN_2,
            DungeonPosition.P_BARBARIAN_4 };
    private static int n = 0;

    /* Je devrais les écrire en majuscule */
    private final BarbarianColor color;
    private final DungeonPosition initPosition;
    private String name;

    /**
     * Un joueur du jeu.
     * @param aName le nom du joueur (destiné à l'interface graphique)
     * @throws GameOverException
     */
    public Player(String aName) throws GameOverException {
        if (n > 4) throw new GameOverException("Trop de joueurs");
        name = aName;
        color = BarbarianColor.values()[n];
        initPosition = POSITIONS[n];
        n++;
    }

    /**
     * Un joueur
     * @throws GameOverException
     */
    public Player() throws GameOverException {
        this("Anonym");
    }

    /**
     * Getter couleur
     * @return la couleur
     */
    public BarbarianColor getColor() {
        return color;
    }

    /**
     * Getter de la position initiale (la porte d'entrée)
     * @return la position initiale du petit barbare
     */
    public DungeonPosition getInitPosition() {
        return initPosition;
    }

    /**
     * Getter du nom.
     * @return le nom
     */
    public String getName() {
        return name;
    }

    /**
     * Setter du nom.
     * @param name le nom du joueur
     */
    public void setName(String name) {
        this.name = name;
    }

    



}
