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

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests de la méthode play principalement.
 *
 * Dans ce cas, ce sont plutôt des tests d'intégration que des tests unitaires.
 * (Pour moi la limite est encore un peu floue)
 *
 * @author Pierre Bettens (pbt) <pbettens@heb.be>
 */
public class GameTest {

    private Game game;
    private Room blorkArrows;
    private Room blorkInvincible;
    private Room princessGreen;
    private Room princessRed;
    private Room princessYellow;
    private Room princessBlue;
    private Room gate;
    private Room key;

    public GameTest() {
    }

    @Before
    public void setUp() {
        try {
            game = new Game();
            blorkArrows
                    = new Room(RoomType.BLORK, true, WeaponType.ARROWS, null);
            blorkInvincible
                    = new Room(RoomType.BLORK, true, null, null);
            princessGreen
                    = new Room(RoomType.PRINCESS, true, null, BarbarianColor.GREEN);
            princessRed
                    = new Room(RoomType.PRINCESS, true, null, BarbarianColor.RED);
            princessYellow
                    = new Room(RoomType.PRINCESS, true, null, BarbarianColor.YELLOW);
            princessBlue
                    = new Room(RoomType.PRINCESS, true, null, BarbarianColor.BLUE);
            gate = new Room(RoomType.GATE, true, null, null);
            key = new Room(RoomType.KEY, true, null, null);
        } catch (GameOverException e) {
            fail("Revoir les tests");
        }
    }

    @Test
    public void testPlay_1() throws GameOverException {
        System.out.println("play - gagner ou perdre contre un blork");
        Room[][] configuration = {
            {blorkArrows, blorkArrows, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null}
        };
        game.setDonjon(new Dungeon(configuration));
        // Je gagne
        assertTrue(game.play(Direction.DOWN, WeaponType.ARROWS)
                == BarbarianState.CONTINUE);
        // Je perd
        assertTrue(game.play(Direction.RIGHT, WeaponType.BLUDGEON)
            == BarbarianState.GAMEOVER);
    }

    @Test
    public void testPlay_2() throws GameOverException {
        System.out.println("play - perdre contre un blork invincible ");
        Room[][] configuration = {
            {blorkArrows, blorkInvincible, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null}
        };
        game.setDonjon(new Dungeon(configuration));
        assertTrue(game.play(Direction.DOWN, WeaponType.ARROWS)
            == BarbarianState.CONTINUE);
        assertTrue(game.play(Direction.RIGHT, WeaponType.BLUDGEON)
            == BarbarianState.MOVE_BLORK);
    }

    @Test
    public void testPlay_3() throws GameOverException {
        System.out.println("play - ne pas perdre contre les gentils");
        Room[][] configuration = {
            {key, princessBlue, princessRed, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null}
        };
        game.setDonjon(new Dungeon(configuration));
        assertTrue(game.play(Direction.DOWN, WeaponType.ARROWS)
                == BarbarianState.CONTINUE);
        assertTrue(game.play(Direction.RIGHT, WeaponType.ARROWS)
                == BarbarianState.CONTINUE);
        assertTrue(game.play(Direction.RIGHT, WeaponType.ARROWS)
                == BarbarianState.CONTINUE);
        // En plus j'ai gagné car le premier joueur est rouge
        assertTrue(game.isOver());
    }

    @Test
    public void testPlay_4() throws GameOverException {
        System.out.println("play - ne pas gagner si l'on se trompe de princesse");
        Room[][] configuration = {
            {princessGreen, blorkArrows, key, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null}
        };
        game.setDonjon(new Dungeon(configuration));
        assertTrue(game.play(Direction.DOWN, WeaponType.ARROWS)
                == BarbarianState.CONTINUE);
        assertTrue(game.play(Direction.RIGHT, WeaponType.ARROWS)
                == BarbarianState.CONTINUE);
        assertTrue(game.play(Direction.RIGHT, WeaponType.ARROWS)
                == BarbarianState.CONTINUE);
        // Attention, je n'ai pas gagné
        assertFalse(game.isOver());
    }

    @Test
    public void testPlay_5() throws GameOverException {
        System.out.println("play - vais-je trouver la bonne princesse ?");
        Room[][] configuration = {
            {princessBlue, null, null, null, null},
            {princessGreen, null, null, null, null},
            {princessYellow, null, null, null, null},
            {princessRed, key, null, null, null},
            {null, null, null, null, null}
        };
        game.setDonjon(new Dungeon(configuration));
        assertTrue(game.play(Direction.DOWN, WeaponType.ARROWS)
                == BarbarianState.CONTINUE);
        assertFalse(game.isOver());
        assertTrue(game.play(Direction.DOWN, WeaponType.ARROWS)
                == BarbarianState.CONTINUE);
        assertFalse(game.isOver());
        assertTrue(game.play(Direction.DOWN, WeaponType.ARROWS)
                == BarbarianState.CONTINUE);
        assertFalse(game.isOver());
        assertTrue(game.play(Direction.DOWN, WeaponType.ARROWS)
                == BarbarianState.CONTINUE);
        assertFalse(game.isOver());
        assertTrue(game.play(Direction.RIGHT, WeaponType.ARROWS)
                == BarbarianState.CONTINUE);
        assertTrue(game.isOver());
    }

    @Test
    public void testPlay_6() throws GameOverException {
        System.out.println("play - blork invicible (v2)");
        Room[][] configuration = {
            {princessBlue, null, null, null, null},
            {blorkInvincible, null, null, null, null},
            {princessYellow, null, null, null, null},
            {princessRed, key, null, gate, null},
            {null, null, null, null, null}
        };
        game.setDonjon(new Dungeon(configuration));
        assertTrue(game.play(Direction.DOWN, WeaponType.ARROWS)
                == BarbarianState.CONTINUE);
        assertFalse(game.isOver());
        assertTrue(game.play(Direction.DOWN, WeaponType.ARROWS)
                == BarbarianState.MOVE_BLORK);
        assertTrue(game.playBlorkInvincible(new DungeonPosition(3, 3))
                == BarbarianState.GAMEOVER);
        // Vérification que le blork est délacé
        // (pour la v2)
        Room room = game.getDungeon().getRoom(new DungeonPosition(1, 0));
        assertNotSame(room,blorkInvincible);
    }

    @Test
    public void testPlay_7() throws GameOverException {
        System.out.println("play - déplacement du joueur si GATE");
        fail("gestion du gate pour la v2");
    }

}
