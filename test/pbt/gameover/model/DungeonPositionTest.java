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

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Quelques tests de la classe DungeonPosition.
 *
 * @author Pierre Bettens (pbt) <pbettens@heb.be>
 */
public class DungeonPositionTest {

    private static DungeonPosition POSITION_NO;
    private static DungeonPosition POSITION_NE;
    private static DungeonPosition POSITION_SE;
    private static DungeonPosition POSITION_SO;

    public DungeonPositionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        try {            
            POSITION_NO = new DungeonPosition(0, 0);
            POSITION_NE = new DungeonPosition(0, Dungeon.N - 1);
            POSITION_SO = new DungeonPosition(Dungeon.N - 1, 0);
            POSITION_SE = new DungeonPosition(Dungeon.N - 1, Dungeon.N - 1);
        } catch (GameOverException e) {
            fail("Revoir les tests");
        }
    }

    /**
     * Test of move method, of class DungeonPosition.
     */
    @Test
    public void testMove_usual() throws GameOverException {
        System.out.println("move up, down, left, right usual");
        DungeonPosition instance = new DungeonPosition(2, 3);
        DungeonPosition result = instance.move(Direction.UP);
        assertEquals(result, new DungeonPosition(1, 3));
        result = instance.move(Direction.DOWN);
        assertEquals(result, new DungeonPosition(3, 3));
        result = instance.move(Direction.LEFT);
        assertEquals(result, new DungeonPosition(2, 2));
        result = instance.move(Direction.RIGHT);
        assertEquals(result, new DungeonPosition(2, 4));
    }

    /**
     * Test of move method, of class DungeonPosition.
     */
    @Test(expected = GameOverException.class)
    public void testMove_outofbound_no() throws GameOverException {
        System.out.println("move up, down, left, right out of bound");
        DungeonPosition instance = POSITION_NO;
        DungeonPosition result = instance.move(Direction.UP);
        fail("Test fail");
    }

    /**
     * Test of move method, of class DungeonPosition.
     */
    @Test(expected = GameOverException.class)
    public void testMove_outofbound_ne() throws GameOverException {
        System.out.println("move up, down, left, right out of bound");
        DungeonPosition instance = POSITION_NE;
        DungeonPosition result = instance.move(Direction.RIGHT);
        fail("Test fail");
    }

    /**
     * Test of move method, of class DungeonPosition.
     */
    @Test(expected = GameOverException.class)
    public void testMove_outofbound_so() throws GameOverException {
        System.out.println("move up, down, left, right out of bound");
        DungeonPosition instance = POSITION_SO;
        DungeonPosition result = instance.move(Direction.DOWN);
        fail("Test fail");
    }

    /**
     * Test of move method, of class DungeonPosition.
     */
    @Test(expected = GameOverException.class)
    public void testMove_outofbound_se() throws GameOverException {
        System.out.println("move up, down, left, right out of bound");
        DungeonPosition instance = POSITION_SE;
        DungeonPosition result = instance.move(Direction.RIGHT);
        fail("Test fail");
    }

}
