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
 *sho
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package pbt.gameover.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Représente le donjon du jeu, l'endroit où se trouve les blorks, les clés, la
 * porte de transfert … et les princesses.
 *
 * Le plateau de jeu est principalement les cartes posées sur celui-ci. Inutile
 * de représenter les barbares ni les cartes « armes ».
 *
 * @author Pierre Bettens (pbt) <pbettens@heb.be>
 */
public class Dungeon {

    public final static int N = 5;

    /**
     * Les cartes 5x5 représentant le donjon.
     *
     * Je parle en terme de ligne, colonne (pas abscisse, ordonnée) Je considère
     * que le côté en haut à gauche est 0,0 Je ne donne pas de getter vers le
     * tableau.
     *
     */
    private Room[][] roomss;
    private static Dungeon instance = null;

    /**
     * Retourne l'instance unique du donjon. (singleton pattern)
     *
     * @return le donjon
     */
    public static Dungeon getInstance() {
        if (instance == null) {
            instance = new Dungeon();
        }
        return instance;
    }

    /**
     * Constructeur permettant d'obtenir un donjon dans une configuration donnée
     * à destination des tests.
     *
     * (Dans la foulée, on illustre l'utilisation de la visibilité package pour
     * l'implémentation des tests).
     *
     * @param configuration
     * @return le donjon créé sur base de la config en paramètre
     */
    Dungeon(Room[][] configuration) {
        roomss = configuration;
    }

    private Dungeon() {
        roomss = new Room[N][N];
        Room[] cards = {
            new Room(RoomType.GATE, true, null, null),
            new Room(RoomType.KEY, true, null, null),
            new Room(RoomType.KEY, true, null, null),
            new Room(RoomType.PRINCESS, true, null, BarbarianColor.RED),
            new Room(RoomType.PRINCESS, true, null, BarbarianColor.GREEN),
            new Room(RoomType.PRINCESS, true, null, BarbarianColor.BLUE),
            new Room(RoomType.PRINCESS, true, null, BarbarianColor.YELLOW),
            new Room(RoomType.BLORK, true, WeaponType.ARROWS, null),
            new Room(RoomType.BLORK, true, WeaponType.ARROWS, null),
            new Room(RoomType.BLORK, true, WeaponType.ARROWS, null),
            new Room(RoomType.BLORK, true, WeaponType.ARROWS, null),
            new Room(RoomType.BLORK, true, WeaponType.BLUDGEON, null),
            new Room(RoomType.BLORK, true, WeaponType.BLUDGEON, null),
            new Room(RoomType.BLORK, true, WeaponType.BLUDGEON, null),
            new Room(RoomType.BLORK, true, WeaponType.BLUDGEON, null),
            new Room(RoomType.BLORK, true, WeaponType.GUN, null),
            new Room(RoomType.BLORK, true, WeaponType.GUN, null),
            new Room(RoomType.BLORK, true, WeaponType.GUN, null),
            new Room(RoomType.BLORK, true, WeaponType.GUN, null),
            new Room(RoomType.BLORK, true, WeaponType.POTION, null),
            new Room(RoomType.BLORK, true, WeaponType.POTION, null),
            new Room(RoomType.BLORK, true, WeaponType.POTION, null),
            new Room(RoomType.BLORK, true, WeaponType.POTION, null),
            // les deux blorks invicibles (pas d'arme pour les vaincre)
            new Room(RoomType.BLORK, true, null, null),
            new Room(RoomType.BLORK, true, null, null),};
        List<Room> alCards = Arrays.asList(cards);
        Collections.shuffle(alCards);
        for (int i = 0; i < roomss.length; i++) {
            for (int j = 0; j < roomss[i].length; j++) {
                roomss[i][j] = alCards.get(i * roomss.length + j);
            }
        }
    }

    /**
     * Retourne une carte du donjon.
     *
     * @param p la position dans de donjon
     * @return la carte en question
     */
    public Room getRoom(DungeonPosition p) {
        return roomss[p.getRow()][p.getColumn()];
    }

    /**
     * Accéder à une pièce du donjon … et ce battre contre le blork le cas
     * échéant.
     *
     * Lorsqu'on entre dans une pièce, il faut ensuite montrer la pièce.
     *
     * @param p la position de la pièce dans laquelle on entre
     */
    public void show(DungeonPosition p) {
        Room room = roomss[p.getRow()][p.getColumn()];
        room.setHidden(false);
    }

    /**
     * Vérifie si les cases autours sont visibles.
     *
     * @param p la position testée
     * @return vrai si la case est entourée de cases face visible
     */
    public boolean isSurrounded(DungeonPosition p) {
        int row = p.getRow();
        int column = p.getColumn();
        // À priori, la position est encerclée … sauf si je trouve une
        // case cachée.
        boolean isSurrounded = true;
        if (row-1 > 0) {
            if (column-1 > 0 && isSurrounded) {
                isSurrounded &= !roomss[row-1][column-1].isHidden();
            }
            isSurrounded &= !roomss[row-1][column].isHidden();
            if (column+1 < N && isSurrounded) {
                isSurrounded &= !roomss[row-1][column+1].isHidden();
            }
        }
        if (column-1 > 0 && isSurrounded) {
            isSurrounded &= !roomss[row][column-1].isHidden();
        }
        if (column+1 < N && isSurrounded) {
            isSurrounded &= !roomss[row][column+1].isHidden();
        }
        if (row+1 < N && isSurrounded) {
            if (column-1 > 0 && isSurrounded) {
                isSurrounded &= !roomss[row+1][column-1].isHidden();
            }
            isSurrounded &= !roomss[row+1][column].isHidden();
            if (column+1 < N && isSurrounded) {
                isSurrounded &= !roomss[row+1][column+1].isHidden();
            }
        }
        return isSurrounded;
    }

    /**
     * Cacher une pièce du donjon.
     *
     * @param p
     */
    public void hide(DungeonPosition p) {
        Room room = roomss[p.getRow()][p.getColumn()];
        room.setHidden(true);
    }

    /**
     * Permet d'échanger deux pièces.
     *
     * @param one la position de la première pièce
     * @param two la position de la deuxième pièce
     */
    void swap(DungeonPosition one, DungeonPosition two) {
        Room r = roomss[one.getRow()][one.getColumn()];
        roomss[one.getRow()][one.getColumn()]
                = roomss[two.getRow()][two.getColumn()];
        roomss[two.getRow()][two.getColumn()] = r;
    }

    /**
     * Cache toutes les pièces du donjon.
     */
    public void hideAll() {
        for (Room[] squares : roomss) {
            for (Room square : squares) {
                square.setHidden(true);
            }
        }
    }

	/**
	 * Cherche la pièce dont les données sont passée en paramètre
	 * @param rt le type de pièce
	 * @param bc sa couleur
	 * @return la position de la première pièce correspondante trouvée ou null
	 */
	DungeonPosition find(RoomType rt, BarbarianColor bc){
		boolean isFind = false;
		DungeonPosition dp = null;
		for (int i=0; i< N && !isFind; i++){
			for (int j=0; j<N && !isFind; j++){
				if (roomss[i][j].getColor() == bc 
						&& roomss[i][j].getType() == rt){
					isFind = true;
					try {
						dp = new DungeonPosition(i,j);
					} catch (GameOverException ex) {
						// Je sais que la position est valide
					}
				}
			}
		}
		return dp;
	}
}
