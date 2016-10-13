package io.sonicdeadlock.projectc.util;

import io.sonicdeadlock.projectc.entity.Entity;
import io.sonicdeadlock.projectc.world.World;

import java.util.List;

/**
 * Created by Alex on 10/12/2016.
 */
public class RayCaster {

    private static final Config CHECK_ENTITY_EXISTS = new Config();

    static {
        CHECK_ENTITY_EXISTS.setCheckEntityExists(true);
    }

    /**
     *
     * @param x
     * @param y
     * @param direction in radians
     * @param distance
     * @param world
     * @param config
     * @return
     */
    public static Entity castRay(int x,int y,double direction,double distance,World world,Config config){
        Ray r = new Ray(direction,distance,x,y);
        while (r.hasNextPoint()){
            Point point = r.getNextPoint();
            List<Entity> entities = world.pointSearch(((int) point.getX()), ((int) point.getY()));
            for (Entity entity : entities) {
                if (checkConditions(entity,config)) {
                    return entity;
                }
            }
        }
        return null;
    }

    private static boolean checkConditions(Entity entity,Config config){
        if (entity != null && config.isCheckEntityExists()) {
            return true;
        }



        return false;
    }

    private static class Ray{
        double direction,amplitude;
        int x,y;
        Point previousPoint = null;
        private Ray(double direction, double amplitude, int x, int y) {
            this.direction = direction;
            this.amplitude = amplitude;
            this.x = x;
            this.y = y;
        }

        private Point getNextPoint(){
            double deltaX = Math.cos(direction);
            double deltaY = Math.sin(direction);
            if (previousPoint != null) {
                previousPoint = new Point(previousPoint.getX()+deltaX,previousPoint.getY()+deltaY);
            }else{
                previousPoint = new Point(x+deltaX,y+deltaY);
            }
            return previousPoint;
        }

        private boolean hasNextPoint() {
            return previousPoint == null || !(SpacialUtils.getDistance(x, y, previousPoint.getX(), previousPoint.getY()) >= amplitude);
        }

    }

    private static class Point{
        double x,y;

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
    }

    public static class Config{
        private boolean checkEntityExists=false;

        private boolean isCheckEntityExists() {
            return checkEntityExists;
        }

        private void setCheckEntityExists(boolean checkEntityExists) {
            this.checkEntityExists = checkEntityExists;
        }
    }
}
