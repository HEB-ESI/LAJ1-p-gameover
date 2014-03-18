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

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pbt.gameover.model.BarbarianState;
import pbt.gameover.model.Direction;
import pbt.gameover.model.DungeonPosition;
import pbt.gameover.model.Game;
import pbt.gameover.model.GameOverException;
import pbt.gameover.model.Player;
import pbt.gameover.model.PlayersIO;
import pbt.gameover.model.WeaponType;

import static pbt.gameover.view.Display.*;

/**
 * Cette classe est la classe principale.
 *
 * Cette classe instancie le jeu et sert d'interface avec les joueurs.
 *
 * Par soucis de facilité et de rapidité, je ne demande pas les noms des
 * joueurs, (ils s'appelleront Juste, François and co) et j'en crée d'office 4.
 * <b>Le jeu se joue donc à 4</b>
 *
 * @author Pierre Bettens (pbt) <pbettens@heb.be>
 */
public class GameView {

    private static final WeaponType[] WEAPONS = WeaponType.values();
    private static final Map<Character, Direction> CHAR_DIRECTIONS
            = new HashMap<>(4);
    private static final Map<BarbarianState, String> STATE_REGEX
            = new EnumMap<>(BarbarianState.class);

    static {
        CHAR_DIRECTIONS.put('u', Direction.UP);
        CHAR_DIRECTIONS.put('r', Direction.RIGHT);
        CHAR_DIRECTIONS.put('d', Direction.DOWN);
        CHAR_DIRECTIONS.put('l', Direction.LEFT);
        STATE_REGEX.put(BarbarianState.CONTINUE, "[p0]{1}"
                + "|[udlr]{1}\\s*[1234]{1}");
        STATE_REGEX.put(BarbarianState.READY_TO_GO, "[p0]{1}"
                + "|[udlr]{1}\\s*[1234]{1}");
        STATE_REGEX.put(BarbarianState.BEAM_ME_UP, "[p0]{1}"
                + "|[b]{1}\\s*[(]{0,1}[01234]{1}\\,{0,1}[01234]{1}[)]{0,1}"
                + "\\s+[1234]{1}\\s*");
        STATE_REGEX.put(BarbarianState.MOVE_BLORK, "[p0]{1}"
                + "|[m]{1}\\s*[(]{0,1}[01234]{1}\\,{0,1}[01234]{1}[)]{0,1}"
                + "\\s*");
        STATE_REGEX.put(BarbarianState.JOKER, "[p0]{1}"
                + "|[j]{1}\\s*[01234]{1}"
                + "\\s*");
        STATE_REGEX.put(BarbarianState.GAMEOVER, ".*");
    }

    public static void main(String[] args) {
        Game game = null;
        BarbarianState barbarianState = BarbarianState.READY_TO_GO;
        int row, column;
        // Tentative de création d'un jeu
        try {
            // Ajout de la gestion du paramètre
            if (args.length == 1) {
                Path p = Paths.get(args[0]);
                List<Player> players = PlayersIO.read(p);
                game = new Game(players);
            } else {
                game = new Game("Juste", "Marlène", "François", "Pierre");
            }
            barbarianState = game.getStateCurrent();
        } catch (GameOverException e) {
            System.err.printf("Tentative abordée de création d'un jeu");
            System.exit(1);
        }
        // On joue
        while (!game.isOver()) {
            clear();
            display(game.getCurrentPlayer());
            display(game.getDungeon());
            /*
             * Proposer une action
             */
            String s;
            do {
                displayMenu(barbarianState);
                s = readLine();
            } while (!s.matches(STATE_REGEX.get(barbarianState)));
            // INVARIANT l'action a effectuer est toujours caractérisée par
            // la première lettre
            char action = s.charAt(0);
            switch (action) {
                case '0':
                    System.exit(1);
                // break; inutile
                case 'p':
                    display("Abandon pour ce tour.");
                    barbarianState = BarbarianState.GAMEOVER;
                    // Je sais que je passerai au joueur suivant un
                    // peu plus bas.
                    break;
                case 'u':
                case 'r':
                case 'l':
                case 'd':
                    // Mouvement normal
                    int weapon = Integer.parseInt("" + s.charAt(1)) - 1;
                    try {
                        Direction d = CHAR_DIRECTIONS.get(action);
                        WeaponType wt = WEAPONS[weapon];
                        display("On tente la direction " + d + " avec " + wt + "\n");
                        barbarianState = game.play(d, wt);
                    } catch (GameOverException ex) {
                        display("Erreur (" + ex.getMessage() + ")\n");
                        continue;
                        // Je m'autorise un continue pour réitérer
                    }
                    break;
                case 'm':
                    // Déplacement du blork
                    s = s.replaceAll("[\\(\\)\\, ]", "");
                    // Après nettoyage, c'est de la forme m12
                    row = Integer.parseInt("" + s.charAt(1));
                    column = Integer.parseInt("" + s.charAt(2));
                    try {
                        display("On déplace le blork à la position (" + row
                                + "," + column + ")\n");
                        barbarianState = game.playBlorkInvincible(
                                new DungeonPosition(row, column));
                    } catch (GameOverException ex) {
                        display("Erreur (" + ex.getMessage() + ")\n");
                        continue;
                        // Je m'autorise un continue pour réitérer
                    }
                    break;
                case 'b':
                    // Beam me up, Scotty !                    
                    s = s.replaceAll("[\\(\\)\\, ]", "");
                    // Après nettoyage, c'est de la forme b123
                    row = Integer.parseInt("" + s.charAt(1));
                    column = Integer.parseInt("" + s.charAt(2));
                    weapon = Integer.parseInt("" + s.charAt(3)) - 1;
                    try {
                        WeaponType wt = WEAPONS[weapon];
                        display("On tente la position (" + row + ","
                                + column + ") avec " + wt + "\n");
                        barbarianState = game.playGate(
                                new DungeonPosition(row, column), wt);
                    } catch (GameOverException ex) {
                        display("Erreur (" + ex.getMessage() + ")\n");
                        continue;
                        // Je m'autorise un continue pour réitérer
                    }
                    break;
                case 'j':
                    // Yes ! Un joker.
                    s = s.replaceAll("[ ]", "");
                    weapon = Integer.parseInt("" + s.charAt(1)) - 1;
                    try {
                        WeaponType wt = WEAPONS[weapon];
                        display("On retente avec " + wt + "\n");
                        barbarianState = game.playJoker(wt);
                    } catch (GameOverException ex) {
                        display("Erreur (" + ex.getMessage() + ")\n");
                        continue;
                        // Je m'autorise un continue pour réitérer
                    }
                    break;
                default:
                    display("Action non prise en charge (" + action + ")");
                    continue;
            }
            /*
             * Traitement du résultat s'il échet
             * En général, je ne fais rien et redemande une action
             */
            switch (barbarianState) {
                case CONTINUE:
                case BEAM_ME_UP:
                case MOVE_BLORK:
                case READY_TO_GO:
                case JOKER:
                    break;
                case GAMEOVER:
                    // Game Over
                    display(CouleurTerminal.RED + "GAME OVER\n\n"
                            + CouleurTerminal.DEFAULT);
                    display(game.getDungeon());
                    game.nextPlayer();
                    barbarianState = game.getStateCurrent();
                    display("Appuie sur une ENTER …\n");
                    readLine();
                    break;
            }
        }
        Player winner = game.getWinner();
        displayWinner(winner);
    }

}
