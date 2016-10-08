package io.sonicdeadlock.projectc.util;

/**
 * Created by Alex on 10/7/2016.
 */
public class SpacialUtils {
    public static Double getDistance(double x1,double y1,double x2,double y2){
        return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }
}
