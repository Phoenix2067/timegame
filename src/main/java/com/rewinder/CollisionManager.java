package com.rewinder;

import java.util.List;

/**
 * Checks collisions.
 */
public class CollisionManager {
    public boolean playerTouchesFire(Player player, List<Fire> fires) {
        for (Fire fire : fires) {
            if (fire.active && player.intersects(fire)) {
                return true;
            }
        }
        return false;
    }

    public boolean playerTouchesLaser(Player player, List<Laser> lasers) {
        for (Laser laser : lasers) {
            if (player.intersects(laser)) {
                return true;
            }
        }
        return false;
    }

    public boolean playerTouchesEnemy(Player player, List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (player.intersects(enemy)) {
                return true;
            }
        }
        return false;
    }
}
