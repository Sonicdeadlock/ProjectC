package io.sonicdeadlock.projectc.entity.attribute;

import io.sonicdeadlock.projectc.world.Loadable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Alex on 10/6/2016.
 */
public abstract class Attribute implements Loadable {

    public static String getType(){
        throw new NotImplementedException();
    }

    public boolean isType(String type){
        return getType().equals(type);
    }
}
