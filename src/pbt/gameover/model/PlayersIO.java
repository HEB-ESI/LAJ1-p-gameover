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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitaire permettant le gestion du fichier contentant les
 * joueurs éventuels.
 *
 * Cette classe offre les méthodes de lecture de d'écriture du fichier.
 * Le fichier a la forme suivante:
 *   nom1 [débutant]
 *   nom2 [débutant]
 *
 * @author Pierre Bettens (pbt) <pbettens@heb.be>
 */
public class PlayersIO {

    /**
     * Lit le fichier afin d'y charger le noms des joueurs et leur statut.
     *
     * @param path le fichier à lire
     * @return la liste de joueurs
     * @throws GameOverException dès que l'on rencontre un problème de fichier
     */
    public static List<Player> read(Path path) throws GameOverException {
        List<Player> players = new ArrayList<>(4);
        try (BufferedReader in =
                Files.newBufferedReader(path, Charset.defaultCharset())){
            String s;
            while ((s=in.readLine()) != null) {
                String[] junk = s.split("[\\[\\]]");
                if (junk.length == 2
                        && junk[1].toLowerCase().matches("d[eé]butant")) {
                    players.add(new Player(junk[0], true));
                } else if (junk.length == 1) {
                    players.add(new Player(junk[0]));
                } else {
                    throw new GameOverException(
                            "Problème de structure de fichier");
                }
            }
        } catch (IOException e) {
            throw new GameOverException("Problème de lecture ("
                    + e.getMessage() + ")");
        }
        return players;
    }

}
