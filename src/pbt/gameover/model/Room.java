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
 * Représente une des cartes du jeu formant le plateau de jeu.
 *
 * Remarque
 * Idéalement, je n'utiliserais pas des attributs qui ne sont pas destinés à
 * chaque instance de la classe (par exemple weapon type qui n'est pas utile
 * pour la princesse) et j'utiliserais l'héritage.
 * L'héritage sera vu en deuxième. 
 *
 * @author Pierre Bettens (pbt) <pbettens@heb.be>
 */
public class Room {

    private RoomType type;    /* Le type représenté sur la carte */
    private boolean hidden;     /* La carte est-elle cachée ? */
    private WeaponType weapon;  /* Le type d'arme permettan de vaincre le blork
                                   si c'est un blork (null sinon) */
    private BarbarianColor color;        /* La couleur de la carte s'il y a lieu
                                   (princesse) */

    public Room(RoomType type, boolean hidden,
            WeaponType weapon, BarbarianColor color) {
        // @todo éventuellement remplacer par les setters
        this.type = type;
        this.hidden = hidden;
        this.weapon = weapon;
        this.color = color;
    }

    public final RoomType getType() {
        return type;
    }


    public final boolean isHidden() {
        return hidden;
    }

    public final void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public final WeaponType getWeapon() {
        return weapon;
    }


    public BarbarianColor getColor() {
        return color;
    }



    







}
