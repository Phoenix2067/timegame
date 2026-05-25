package com.rewinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Records and restores recent world states.
 */
public class RewindManager {
    private final List<RewindState> history = new ArrayList<>();
    private final int maxHistory = 600;

    public void record(Player player, List<Laser> lasers, List<Fire> fires) {
        RewindState state = new RewindState(player);

        for (Laser laser : lasers) {
            state.laserPositions.add(new Vector2(laser.x, laser.y));
        }

        for (Fire fire : fires) {
            state.fireLives.add(fire.life);
            state.fireActive.add(fire.active);
        }

        history.add(state);

        if (history.size() > maxHistory) {
            history.remove(0);
        }
    }

    public boolean rewind(Player player, List<Laser> lasers, List<Fire> fires) {
        if (history.isEmpty()) {
            return false;
        }

        RewindState state = history.remove(history.size() - 1);

        player.x = state.playerX;
        player.y = state.playerY;
        player.vx = state.playerVX;
        player.vy = state.playerVY;

        int laserCount = Math.min(lasers.size(), state.laserPositions.size());
        for (int i = 0; i < laserCount; i++) {
            lasers.get(i).x = state.laserPositions.get(i).x;
            lasers.get(i).y = state.laserPositions.get(i).y;
        }

        while (lasers.size() > state.laserPositions.size()) {
            lasers.remove(lasers.size() - 1);
        }

        int fireCount = Math.min(fires.size(), state.fireLives.size());
        for (int i = 0; i < fireCount; i++) {
            fires.get(i).life = state.fireLives.get(i);
            fires.get(i).active = state.fireActive.get(i);
        }

        return true;
    }

    public void clear() {
        history.clear();
    }
}
