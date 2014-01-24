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
import pbt.gameover.model.Dungeon;
import pbt.gameover.model.DungeonPosition;
import pbt.gameover.model.Game;
import pbt.gameover.model.GameOverException;
import pbt.gameover.model.Player;
import pbt.gameover.model.Room;

import static pbt.gameover.view.CouleurTerminal.*;

/**
 * Classe utilitaire ne contenant que des méthodes permettant l'affichage en
 * texte des divers éléments du jeu.
 *
 * Remarque sur les caractères spéciaux 𝄞 = U+1D11E	pas représentable en char
 *
 * 1/ convertir en binaire 1101000100011110
 * 2/ 10 bits faibles 0100011110 = 11E
 * 3/ 10 bits forts 0000110100 = 34
 * 4/ Ajouter aux faibles DC00 → DC00 + 11E = DD1E
 * 5/ Ajouter aux forts D800 → D800 + 34 = D834
 *
 * Remarque au sujet de la méthode readLine Dans une classe dédiée à
 * l'affichage, c'est dommage d'ajouter une méthode qui fait une lecture … mais
 * ça m'évite de définir deux fois une console. Je ne sais pas ce qui est le
 * mieux ?
 *
 * @author Pierre Bettens (pbt) <pbettens@heb.be>
 */
public class Display {

    private static final Console CONSOLE = System.console();

    private static final String CLEAR = "\033[2J";

    // Caractières spéciaux pour marquer les entrées dans le donjon
    // http://www.unicode.org/charts/PDF/U2900.pdf
    private static final String ENTRY_UP_DOWN = "\u294E";
    private static final String ENtRY_DOWN_UP = "\u2950";
    private static final String ENTRY_LEFT_RIGHT = "\u2951";
    private static final String ENTRY_RIGHT_LEFT = "\u294F";

    private static final String CHAR_POTION = "1";
    private static final String CHAR_ARROW = "2";
    private static final String CHAR_BLUDGEON = "3";
    private static final String CHAR_GUN = "4";

    private static final String CHAR_PRINCESS = "\u263A";// "\u2655";
    private static final String CHAR_KEY = "\u26BF";

    static {
        if (CONSOLE == null) {
            System.err.printf("Pas de console connectée … as-tu un clavier ?");
            System.exit(1);
        }
    }

    /**
     * Permet la lecture d'une ligne sur la console.
     *
     * @return la ligne lue
     */
    public static String readLine() {
        return CONSOLE.readLine();
    }

    /**
     * Affiche le donjon et les barbares à leur position initiale.
     *
     * @param d le donjon
     */
    public static void display(Dungeon d) {
        Room room;
        try {            
            CONSOLE.printf(RED + "  " + ENTRY_UP_DOWN + DEFAULT + "\n");
            for (int i = 0; i < Dungeon.N; i++) {
                if (i == Dungeon.N - 1) {
                    CONSOLE.printf(YELLOW + ENTRY_LEFT_RIGHT + DEFAULT);
                }
                if (i != Dungeon.N - 1) {
                    CONSOLE.printf(" ");
                }
                for (int j = 0; j < Dungeon.N; j++) {
                    room = d.getRoom(new DungeonPosition(i, j));
                    if (j != 0) {
                        CONSOLE.printf(" ");
                    }
                    display(room);
                    if (j != Dungeon.N - 1) {
                        CONSOLE.printf(" ");
                    }
                }
                if (i == 0) {
                    CONSOLE.printf(GREEN + ENTRY_RIGHT_LEFT + DEFAULT);
                }
                if (i != Dungeon.N - 1) {
                    CONSOLE.printf("\n");
                }
                CONSOLE.printf("\n");
            }            
            CONSOLE.printf(BLUE + "                      "
                    + ENtRY_DOWN_UP + DEFAULT);
            CONSOLE.printf("\n");
        } catch (GameOverException e) {
            CONSOLE.printf("Tentative d'affichage de pièces hors donjon !"
                    + "\nEnvoyer rapport de bug … ");
            System.exit(1);
        }
    }

    /**
     * Affiche un joueur.
     *
     * @param p le joueur
     */
    public static void display(Player p) {
        String s = "";
        switch (p.getColor()) {
            case RED:
                s += RED + "Joueur rouge ";
                break;
            case GREEN:
                s += GREEN + "Joueur vert ";
                break;
            case BLUE:
                s += BLUE + "Joueur bleu ";
                break;
            case YELLOW:
                s += YELLOW + "Joueur jaune ";
                break;
        }
        s += p.getName() + DEFAULT + "\n";
        CONSOLE.printf(s);
    }

    /**
     * Affiche une chaine de caractère.
     *
     * @param s la chaine à afficher
     */
    public static void display(String s) {
        CONSOLE.printf(s);
    }

    /**
     * Affiche une pièce du donjon.
     *
     * N'affiche pas la décoration du la pièce, simplement son état par le
     * biais de 2 caractères.
     *
     * @param r la pièce du donjon à afficher
     */
    public static void display(Room r) {
        String s = " ";        
        if (r.isHidden()) {
            s = " \u26BF ";
        } else {
            if (r.getColor() != null) {
                switch (r.getColor()) {
                    case RED:
                        s += CouleurTerminal.RED;
                        break;
                    case GREEN:
                        s += CouleurTerminal.GREEN;
                        break;
                    case BLUE:
                        s += CouleurTerminal.BLUE;
                        break;
                    case YELLOW:
                        s += CouleurTerminal.YELLOW;
                        break;
                }
            } else {
                s += CouleurTerminal.GREY;
            }
            switch (r.getType()) {
                case BLORK:
                    s += "B";
                    break;
                case GATE:
                    s += "G";
                    break;
                case KEY:
                    s += CHAR_KEY; //"K";
                    break;
                case PRINCESS:
                    s += CHAR_PRINCESS; //"P";
            }
            s += "" + CouleurTerminal.DEFAULT + CouleurTerminal.WHITE;
            if (r.getWeapon() != null) {
                /*
                 * Pour les caractères spéciaux,
                 * voir « formules » en début de classe
                 * PISTOL u+1F52B (F52B = 111101 0100101011 → D83D DD2B)
                 * ARROWS u+2B31
                 * BLUDGEON u+1F364 (F364 = 111100 1101100100 → D83C DF64
                 */
                switch (r.getWeapon()) {
                    case ARROWS:
                        s += CHAR_ARROW;
                        break;
                    case BLUDGEON:
                        s += CHAR_BLUDGEON;
                        break;
                    case GUN:
                        s += CHAR_GUN;
                        break;
                    case POTION:
                        s += CHAR_POTION;
                        break;
                }
            } else {
                s += " ";
            }
            s += CouleurTerminal.DEFAULT;
        }
        CONSOLE.printf(s);
    }

    /**
     * Affiche le gagnant s'il existe et « Pas de gagnant » sinon.
     *
     * @param winner le gagnant éventuel.
     */
    public static void displayWinner(Player winner) {
        if (winner == null) {
            CONSOLE.printf("Pas de gagnant");
        } else {
            CONSOLE.printf("Le gagnant est %s", winner.getName());
        }
    }

    /**
     * Est sensé effacer (un peu) l'écran … mais c'est bancal comme
     * implémentation.
     */
    public static void clear() {
        CONSOLE.printf(CLEAR);
    }

}
