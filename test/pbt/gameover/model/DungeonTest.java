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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pierre Bettens (pbt) <pbettens@heb.be>
 */
public class DungeonTest {

    private Room roomHidden;
    private Room roomVisible;

    public DungeonTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        roomHidden
                = new Room(RoomType.BLORK, true, WeaponType.ARROWS, null);
        roomVisible
                = new Room(RoomType.BLORK, false, null, null);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of isSurrounded method, of class Dungeon.
     *
     * @throws pbt.gameover.model.GameOverException
     */
    @Test
    public void testIsSurrounded_1() throws GameOverException {
        System.out.println("isSurrounded vrai");
        Room[][] configuration = {
            {roomVisible, roomVisible, roomVisible, null, null},
            {roomVisible, roomHidden, roomVisible, null, null},
            {roomVisible, roomVisible, roomVisible, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null}
        };
        Dungeon dungeon = new Dungeon(configuration);
        assertTrue(dungeon.isSurrounded(new DungeonPosition(1, 1)));
    }

    /**
     * Test of isSurrounded method, of class Dungeon.
     *
     * @throws pbt.gameover.model.GameOverException
     */
    @Test
    public void testIsSurrounded_2() throws GameOverException {
        System.out.println("isSurrounded");
        Room[][] configuration = {
            {roomVisible, roomVisible, roomVisible, roomHidden, roomHidden},
            {roomVisible, roomHidden, roomVisible, roomHidden, roomHidden},
            {roomHidden, roomHidden, roomHidden, roomHidden, roomHidden},
            {roomHidden, roomHidden, roomHidden, roomHidden, roomHidden},
            {roomHidden, roomHidden, roomHidden, roomHidden, roomHidden}
        };
        Dungeon dungeon = new Dungeon(configuration);
        assertFalse(dungeon.isSurrounded(new DungeonPosition(2, 3)));
        assertFalse(dungeon.isSurrounded(new DungeonPosition(0, 0)));
        assertFalse(dungeon.isSurrounded(new DungeonPosition(3, 2)));
    }

}
