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

import java.util.Objects;

/**
 * Représente une position *dans* le donjon.
 *
 * En tant que telle, elle doit être sur de plateau et pas à côté.
 * Je vérifie ça et je compte la-dessus partout dans mon code: si une
 * position est instanciée, c'est qu'elle est valide.
 *
 * Je ferai une exception en créant 4 positions hors donjon représentant les
 * portes d'entrée des petits barbares.
 *
 * @author Pierre Bettens (pbt) <pbettens@heb.be>
 */
public class DungeonPosition {

    private int column;
    private int row;

    // On m'a demandé
    // – Pourquoi pas un tableau ?
    // Je n'en sais fichtre rien …
    public static final DungeonPosition P_BARBARIAN_1 =
            new DungeonPosition();
    public static final DungeonPosition P_BARBARIAN_2 =
            new DungeonPosition();
    public static final DungeonPosition P_BARBARIAN_3 =
            new DungeonPosition();
    public static final DungeonPosition P_BARBARIAN_4 =
            new DungeonPosition();

    static {
        // Barbare 1 en haut à gauche et ensuite, je tourne dans
        // le sens horlogique. Et je les places comme dans les règles
        P_BARBARIAN_1.row = -1;
        P_BARBARIAN_1.column = 0;
        P_BARBARIAN_2.row = 0;
        P_BARBARIAN_2.column = Dungeon.N;
        P_BARBARIAN_3.row = Dungeon.N;
        P_BARBARIAN_3.column = Dungeon.N-1;
        P_BARBARIAN_4.row = Dungeon.N-1;
        P_BARBARIAN_4.column = -1;
    }

    private DungeonPosition(){
        // Utilisé uniquement pour les constantes
    }

    /**
     * Constructeur d'une position *dans* le donjon.
     *
     * L'origine se trouve en haut à gauche.
     *
     * @param column la colonne (0 basée)
     * @param row la ligne )0 basée)
     * @throws GameOverException lorsque l'on essaie de créer une position
     * hors du donjon
     */
    public DungeonPosition(int row, int column) throws GameOverException {
        if (column < 0 || column >= Dungeon.N
                || row < 0 || row >= Dungeon.N) {
            throw new GameOverException("Oups, la position est hors donjon");
        }
        this.column = column;
        this.row = row;
    }

    /**
     * Getter
     * @return column
     */
    public int getColumn() {
        return column;
    }

    /**
     * Getter
     * @return row
     */
    public int getRow() {
        return row;
    }

    /**
     * Mouvement vers le haut
     * @return la nouvelle position
     * @throws GameOverException
     */
    public DungeonPosition up() throws GameOverException{
        return new DungeonPosition(row-1, column);
    }

    /**
     * Mouvement vers la droite
     * @return la nouvelle position
     * @throws GameOverException
     */
    public DungeonPosition right() throws GameOverException{
        return new DungeonPosition(row, column+1);
    }

    /**
     * Mouvement vers le bas
     * @return la nouvelle position
     * @throws GameOverException
     */
    public DungeonPosition down() throws GameOverException{
        return new DungeonPosition(row+1, column);
    }

    /**
     * Mouvement vers la gauche
     * @return la nouvelle position
     * @throws GameOverException
     */
    public DungeonPosition left() throws GameOverException{
        return new DungeonPosition(row, column-1);
    }

    /**
     * Mouvement dans une direction …
     * si c'est possible en restant dans le donjon
     *
     * @param d la direction dans laquelle aller
     * @return un nouvelle position
     * @throws GameOverException lancée si je sors du donjon
     */
    public DungeonPosition move(Direction d) throws GameOverException{
        DungeonPosition dp;
        switch (d) {
            case DOWN:
                dp = down();
                break;
            case LEFT:
                dp = left();
                break;
            case RIGHT:
                dp = right();
                break;
            case UP:
                dp = up();
                break;
            default:
                dp = null;
        }
        return dp;
    }

    @Override
    public String toString() {
        return "DungeonPosition{" + "column=" + column + ", row=" + row + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DungeonPosition other = (DungeonPosition) obj;
        if (this.column != other.column) {
            return false;
        }
        if (this.row != other.row) {
            return false;
        }
        return true;
    }









}
