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
import pbt.gameover.model.BarbarianColor;
import pbt.gameover.model.Dungeon;
import pbt.gameover.model.DungeonPosition;
import pbt.gameover.model.GameOverException;
import pbt.gameover.model.Player;
import pbt.gameover.model.Room;
import pbt.gameover.model.RoomType;
import pbt.gameover.model.WeaponType;

import static pbt.gameover.view.CouleurTerminal.*;

/**
 * Classe utilitaire ne contenant que des méthodes permettant l'affichage en
 * texte des divers éléments du jeu.
 *
 * Remarque sur les caractères spéciaux
 * 𝄞 = U+1D11E	pas représentable en char
 *
 *   2/ Soustraire 0x10000 → 0xD11E
 *   1/ convertir en binaire 1101000100011110
 *   2/ 10 bits faibles 0100011110 = 11E
 *   3/ 10 bits forts 0000110100 = 34
 *   4/ Ajouter aux faibles DC00  → DC00 + 11E = DD1E
 *   5/ Ajouter aux forts D800 → D800 + 34 = D834
 *
 * @author Pierre Bettens (pbt) <pbettens@heb.be>
 */
public class Display {

    private static final Console CONSOLE = System.console();

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
     * Affiche le donjon.
     *
     * @param d le donjon
     */
    public static void display(Dungeon d) {        
        Room room;
        try {
            for (int i = 0; i < Dungeon.N; i++) {
                for (int j = 0; j < Dungeon.N; j++) {
                    room = d.getRoom(new DungeonPosition(i, j));
                    display(room);
                }
                CONSOLE.printf("\n");
            }
        } catch (GameOverException e) {
            CONSOLE.printf("Tentative d'affichage de pièces hors donjon !"
                    + "\nEnvoyer rapport de bug … ");
            System.exit(1);
        }
    }

    public static void display(Player p) {

    }

    public static void display(String s) {

    }

    public static void display(Room r){
        String s = "";
        if (r.getColor() != null) {
            switch (r.getColor()){
                case RED: s += CouleurTerminal.RED; break;
                case GREEN: s += CouleurTerminal.GREEN; break;
                case BLUE: s += CouleurTerminal.BLUE; break;
                case YELLOW: s += CouleurTerminal.YELLOW; break;
            }
        } else {
            s += CouleurTerminal.GREY;
        }
        switch (r.getType()){
            case BLORK: s += "B"; break;
            case GATE: s += "G"; break;
            case KEY: s += "K"; break;
            case PRINCESS: s += "P";
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
            switch (r.getWeapon()){
                case ARROWS: s += "\u2B31";
                    break;
                case BLUDGEON: s += "\u2680";
                    break;
                case GUN:s += "g"; //\uD83D\uDD2B";
                    break; //D834, DD1E
                case POTION: s += "\u2620";
                    break;
            }
        }
        s += CouleurTerminal.DEFAULT;
        CONSOLE.printf(s);
    }

    public static void displayWinner(Player winner) {
        if (winner == null) {
            CONSOLE.printf("Pas de gagnant");
        } else {
            CONSOLE.printf("Le gagnant est ");
        }
    }

    public static void main(String[] args) {
        CONSOLE.printf("\n");
        display(new Room(RoomType.BLORK, false, WeaponType.GUN, BarbarianColor.RED));
        CONSOLE.printf("\n");
        display(new Room(RoomType.BLORK, false, WeaponType.POTION, BarbarianColor.GREEN));
        CONSOLE.printf("\n");
        display(new Room(RoomType.BLORK, false, WeaponType.ARROWS, BarbarianColor.GREEN));
        CONSOLE.printf("\n");
        display(new Room(RoomType.BLORK, false, WeaponType.BLUDGEON, BarbarianColor.GREEN));
        CONSOLE.printf("\n");
    }

}
