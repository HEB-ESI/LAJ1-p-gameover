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
 * Représente l'état du petit barbare dans le jeu.
 *
 * Il doit:
 * continuer d'avancer;
 * passer son tour au suivant (c'est Game Over pour cette fois);
 * choisir sa sortie par le passage secret (gate);
 * choisir où il place le blork invincible.
 *
 * @author Pierre Bettens (pbt) <pbettens@heb.be>
 */
public enum BarbarianState {
    /** Fin du tour pour le petit barbare (c'est au joueur suivant d'avancer) */
    GAMEOVER,
    /** Je suis devant la porte du donjon, prêt à entrer … */
    READY_TO_GO,
    /** L'entrée dans la pièce du donjon ne m'a pas tué, je continue. */
    CONTINUE,
    /** Damned ! Je m'ai fait eu (sic) par un blork invincible.
     * Je peux le déplacer. */
    MOVE_BLORK,
    /** Je tombe sur le portail, téléporte-moi … Scotty ! */
    BEAM_ME_UP,
    /** Je suis débutant, je vais jouer mon joker et reproposer une arme */
    JOKER,
    /** Je suis débutant, je vais jouer le joker "change la couleur de la
     * princesse*/
    JOKER_PRINCESS,
    /** Hé, j'ai gagné ! */
    WIN;
}
