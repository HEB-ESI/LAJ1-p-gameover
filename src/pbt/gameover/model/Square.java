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

import java.awt.Color;

/**
 * Représente une des cartes du jeu formant le plateau de jeu.
 *
 * (Une heure plus tard, je ne sais plus pourquoi j'ai appelé cette classe comme
 * ça … enfin, c'est un carré du plateau.)
 *
 * @author Pierre Bettens (pbt) <pbettens@heb.be>
 */
public class Square {

    private SquareType type;    /* Le type représenté sur la carte */
    private boolean hidden;     /* La carte est-elle cachée ? */
    private WeaponType weapon;  /* Le type d'arme permettan de vaincre le blork
                                   si c'est un blork (null sinon) */
    private Color color;        /* La couleur de la carte s'il y a lieu
                                   (princesse) */

//    public Square() {
//    }

    Square(SquareType type, boolean hidden, 
            WeaponType weapon, Color color) {
        // @todo éventuellement remplacer par les setters
        this.type = type;
        this.hidden = hidden;
        this.weapon = weapon;
        this.color = color;
    }

    public final SquareType getType() {
        return type;
    }

    public final  void setType(SquareType type) {
        this.type = type;
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

    public final void setWeapon(WeaponType weapon) {
        this.weapon = weapon;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    







}
