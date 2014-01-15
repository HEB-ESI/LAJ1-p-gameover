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
 *
 * @author Pierre Bettens (pbt) <pbettens@heb.be>
 */
public class Player {    
    /*
     * Création automatique de la couleur du player et
     * de sa position.
     */
    private static final BarbarianColor[] COLORS =
        {BarbarianColor.RED, BarbarianColor.GREEN, BarbarianColor.BLUE, BarbarianColor.YELLOW};
    private static final DungeonPosition[] POSITIONS =
        {DungeonPosition.P_BARBARIAN_1,
            DungeonPosition.P_BARBARIAN_2,
            DungeonPosition.P_BARBARIAN_3,
            DungeonPosition.P_BARBARIAN_4 };
    private static int n = 0;

    /* Je devrais les écrire en majuscule */
    private final BarbarianColor color;
    private final DungeonPosition initPosition;
    private String name;

    public Player() throws GameOverException {
        if (n > 4) throw new GameOverException("Trop de joueurs");
        name = "Anonym";
        color = COLORS[n];
        initPosition = POSITIONS[n];
        n++;
    }

    public BarbarianColor getColor() {
        return color;
    }

    public DungeonPosition getInitPosition() {
        return initPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    



}
