package com.rewinder;

import javafx.scene.input.KeyCode;

import java.util.Set;

/**
 * Controls normal time, slow time, and fast time.
 */
public class TimeManager {
    public enum TimeMode {
        NORMAL,
        SLOW,
        FAST
    }

    private TimeMode mode = TimeMode.NORMAL;
    private double energy = 100;
    private final double maxEnergy = 100;

    /**
     * Updates time mode from keyboard input.
     *
     * @param delta seconds since last frame
     * @param keys current keys
     */
    public void update(double delta, Set<KeyCode> keys) {
        if (keys.contains(KeyCode.SHIFT) && energy > 0) {
            mode = TimeMode.SLOW;
            energy -= 18 * delta;
        } else if (keys.contains(KeyCode.E) && energy > 0) {
            mode = TimeMode.FAST;
            energy -= 28 * delta;
        } else {
            mode = TimeMode.NORMAL;
            energy += 12 * delta;
        }

        if (energy < 0) {
            energy = 0;
            mode = TimeMode.NORMAL;
        }

        if (energy > maxEnergy) {
            energy = maxEnergy;
        }
    }

    public double getWorldTimeScale() {
        if (mode == TimeMode.SLOW) return 0.25;
        if (mode == TimeMode.FAST) return 3.0;
        return 1.0;
    }

    public TimeMode getMode() {
        return mode;
    }

    public double getEnergy() {
        return energy;
    }

    public double getMaxEnergy() {
        return maxEnergy;
    }

    public void reset() {
        mode = TimeMode.NORMAL;
        energy = maxEnergy;
    }
}
