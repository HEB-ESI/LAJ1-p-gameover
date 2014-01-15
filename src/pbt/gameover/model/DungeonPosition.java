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

import javax.swing.text.Position;

/**
 * Représente une position *dans* le dongon.
 *
 * En tant que telle, elle doit être sur de plateau et pas à côté.
 * Je vérifie ça et je compte la-dessus partout dans mon code: si une
 * position est instanciée, c'est qu'elle est valide. 
 *
 * @author Pierre Bettens (pbt) <pbettens@heb.be>
 */
public class DungeonPosition {

    private int column;
    private int row;

    // @todo Guestion pourquoi pas un tableau ?
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

    public DungeonPosition up() throws GameOverException{
        return new DungeonPosition(row-1, column);
    }

    public DungeonPosition right() throws GameOverException{
        return new DungeonPosition(row, column+1);
    }

    public DungeonPosition down() throws GameOverException{
        return new DungeonPosition(row+1, column);
    }

    public DungeonPosition left() throws GameOverException{
        return new DungeonPosition(row, column-1);
    }

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

    public boolean isAdjoining(DungeonPosition p){
        return (row == p.row && column-p.column == 1)
                || (column == p.column && row-p.row == 1);        
    }

    public boolean isInDungeon() {
        // copy/paste
        return !(column < 0 || column >= Dungeon.N
                || row < 0 || row >= Dungeon.N);
    }







}
