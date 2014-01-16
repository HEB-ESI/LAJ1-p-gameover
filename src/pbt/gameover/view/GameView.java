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

package pbt.gameover.view;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;
import pbt.gameover.model.Direction;
import pbt.gameover.model.Game;
import pbt.gameover.model.GameOverException;
import pbt.gameover.model.Player;
import pbt.gameover.model.WeaponType;

import static pbt.gameover.view.Display.*;

/**
 * Cette classe est la classe principale.
 *
 * Cette classe instancie le jeu et sert d'interface avec les joueurs.
 *
 * Par soucis de facilité et de rapidité, je ne demande pas les noms des joueurs,
 * (ils s'appelleront Juste, François and co) et j'en crée d'office 4.
 * <b>Le jeu se joue donc à 4</b>
 * 
 * @author Pierre Bettens (pbt) <pbettens@heb.be>
 */
public class GameView {
          
    private static final WeaponType[] WEAPONS = WeaponType.values();
    private static final Map<Character, Direction> CHAR_DIRECTIONS
            = new HashMap<>(4);
    static {
        CHAR_DIRECTIONS.put('u', Direction.UP);
        CHAR_DIRECTIONS.put('r', Direction.RIGHT);
        CHAR_DIRECTIONS.put('d', Direction.DOWN);
        CHAR_DIRECTIONS.put('l', Direction.LEFT);
    }

    public static void main(String[] args) {
        Game game = null;
        // Tentative de création d'un jeu
        try {
            game = new Game("Juste", "Marlène", "François", "Pierre");
        } catch (GameOverException e){
            System.err.printf("Tentative abordée de création d'un jeu");
            System.exit(1);
        }        

        // On joue
        while (!game.isOver()) {
            if (!game.isTurnInProgress()) {
                display("Appuie sur une ENTER …\n");
                readLine();
            }
            clear();
            display(game.getCurrentPlayer());
            display(game.getDungeon());            
            // Demander le mouvement
            String s;
            do {
                display("\nEntre un mouvement et une arme (ou exit): \n"
                        + "u = UP, d = DOWN, l = LEFT, r = RIGHT, 0 = EXIT\n"
                        + "1 = potion magique, 2 = flèches, "
                        + "3 = massue, 4 = revolver\n\n"
                        + "Par exemple: u2\n\n"
                        + "→ ");
                s = readLine();
            } while (!s.matches("[udlr0]{1}[1234]{0,1}"));
            char move = s.charAt(0);
            if (move == '0') {
                System.exit(0);
            }
            int weapon = Integer.parseInt("" + s.charAt(1)) - 1;
            try {                
                Direction d = CHAR_DIRECTIONS.get(move);
                WeaponType wt = WEAPONS[weapon];
                display("On tente la direction " + d + " avec " + wt + "\n");
                game.play(d,wt);
            } catch (GameOverException ex) {
                display("Erreur (" + ex.getMessage() + ")\n");
                continue;
                // Je m'autorise un break pour réitérer
            }
        }
        Player winner = game.getWinner();
        displayWinner(winner);
    }

}
