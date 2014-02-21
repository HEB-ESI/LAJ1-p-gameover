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
	 * Test du constructeur.
	 */
	@Test
	public void testConstructeur() throws GameOverException {
		System.out.println("constructeur true");
		new DungeonPosition(0,0);
		new DungeonPosition(0,4);
		new DungeonPosition(4,0);
		new DungeonPosition(4,4);
		new DungeonPosition(0,2);
		new DungeonPosition(2,2);
		new DungeonPosition(1,3);
		assertTrue(true);
	}

	/**
	 * Test du constructeur.
	 * Erreurs
	 */
     @Test(expected = GameOverException.class)
	 public void testConstructeurFalse() throws GameOverException {
		 new DungeonPosition(-1,0);
		 new DungeonPosition(0,-1);
		 new DungeonPosition(-1,-1);
		 new DungeonPosition(5,5);
		 fail("Test fail");
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
        instance = POSITION_NO;
        result = instance.move(Direction.LEFT);
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
        instance = POSITION_NE;
        result = instance.move(Direction.UP);
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
        instance = POSITION_SO;
        result = instance.move(Direction.LEFT);
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
        instance = POSITION_SE;
        result = instance.move(Direction.DOWN);
        fail("Test fail");
    }

      /**
     * Test of move method, of class DungeonPosition.
     */
    @Test
    public void testMove_corner_ok() throws GameOverException {
        System.out.println("mouvements corrects à partir des coins");
        DungeonPosition instance = POSITION_NO;
        DungeonPosition result = instance.move(Direction.DOWN);
        assertEquals(result, new DungeonPosition(1, 0));
        result = instance.move(Direction.RIGHT);
        assertEquals(result, new DungeonPosition(0, 1));
        instance = POSITION_NE;
        result = instance.move(Direction.DOWN);
        assertEquals(result, new DungeonPosition(1, Dungeon.N-1));
        result = instance.move(Direction.LEFT);
        assertEquals(result, new DungeonPosition(0, Dungeon.N-2));
        instance = POSITION_SO;
        result = instance.move(Direction.UP);
        assertEquals(result, new DungeonPosition(Dungeon.N-2, 0));
        result = instance.move(Direction.RIGHT);
        assertEquals(result, new DungeonPosition(Dungeon.N-1, 1));
        instance = POSITION_SE;
        result = instance.move(Direction.UP);
        assertEquals(result, new DungeonPosition(Dungeon.N-2, Dungeon.N-1));
        result = instance.move(Direction.LEFT);
        assertEquals(result, new DungeonPosition(Dungeon.N-1, Dungeon.N-2));
    }

    @Test
    public void testCorner() throws GameOverException{
        System.out.println("corner");
        assertTrue(POSITION_NE.isCorner());
        assertTrue(POSITION_NO.isCorner());
        assertTrue(POSITION_SE.isCorner());
        assertTrue(POSITION_SO.isCorner());
        assertFalse(new DungeonPosition(2, 3).isCorner());
        assertFalse(new DungeonPosition(0, 3).isCorner());
        assertFalse(new DungeonPosition(2, 4).isCorner());
    }

}
