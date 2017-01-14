package io.sonicdeadlock.projectc.util;

import io.sonicdeadlock.projectc.entity.Entity;
import io.sonicdeadlock.projectc.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alex on 10/12/2016.
 */
public class RayCaster {

    public static final Config CHECK_ENTITY_EXISTS = new Config();
    public static final double DIRECTION_NORTH = Math.PI * .5, DIRECTION_SOUTH = Math.PI * 1.5, DIRECTION_EAST = 0, DIRECTION_WEST = Math.PI;

    static {
        CHECK_ENTITY_EXISTS.setCheckEntityExists(true);
    }

    /**
     * @param x
     * @param y
     * @param direction in radians
     * @param distance
     * @param world
     * @param config
     * @return
     */
    public static List<Entity> castRay(int x, int y, double direction, double distance, World world, Config config) {
        Ray r = new Ray(direction, distance, x, y);
        List<Entity> matching = new ArrayList<>();
        while (r.hasNextPoint()) {
            Point point = r.getNextPoint();
            List<Entity> entities = world.pointSearch(((int) point.getX()), ((int) point.getY()));
            matching.addAll(entities.stream().filter(entity -> checkConditions(entity, config)).collect(Collectors.toList()));
        }
        return matching;
    }

    public static List<Entity> rayCastCircle(int x, int y, int radius, World world, Config config) {
        return world.radialSearch(x, y, radius).stream().filter(entity -> checkConditions(entity, config)).collect(Collectors.toList());
    }

    private static boolean checkConditions(Entity entity, Config config) {
        if (entity != null && config.isCheckEntityExists()) {
            return true;
        }


        return false;
    }

    private static class Ray {
        double direction, amplitude;
        int x, y;
        Point previousPoint = null;

        private Ray(double direction, double amplitude, int x, int y) {
            this.direction = direction;
            this.amplitude = amplitude;
            this.x = x;
            this.y = y;
        }

        private Point getNextPoint() {
            double deltaX = Math.cos(direction);
            double deltaY = Math.sin(direction);
            Point p;
            if (previousPoint != null) {
                p = new Point(previousPoint.getX() + deltaX, previousPoint.getY() + deltaY);
            } else {
                p = new Point(x + deltaX, y + deltaY);
            }
            if (p.equals(previousPoint)) {
                return getNextPoint();
            }
            previousPoint = p;
            return previousPoint;
        }

        private boolean hasNextPoint() {
            return previousPoint == null || !(SpacialUtils.getDistance(x, y, previousPoint.getX(), previousPoint.getY()) >= amplitude);
        }

    }

    private static class Point {
        double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        @Override
        public boolean equals(Object object) {
            if (object == null) {
                return false;
            }
            if (!(object instanceof Point))
                return false;
            Point p = (Point) object;
            return this.x == p.getX() && this.y == p.getY();
        }
    }

    public static class Config {
        private boolean checkEntityExists = false;

        private boolean isCheckEntityExists() {
            return checkEntityExists;
        }

        private void setCheckEntityExists(boolean checkEntityExists) {
            this.checkEntityExists = checkEntityExists;
        }
    }
}
