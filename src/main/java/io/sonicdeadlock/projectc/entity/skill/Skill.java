package io.sonicdeadlock.projectc.entity.skill;

import io.sonicdeadlock.projectc.entity.attribute.Attribute;
import org.apache.commons.math3.primes.Primes;
import org.json.JSONObject;

/**
 * Created by Alex on 10/6/2016.
 */
public abstract class Skill extends Attribute{
    private int currentXP=0;

    public abstract String getName();

    public int getCurrentLevel(){
        int level=0;
        while (getXPForLevel(level)<currentXP){
            level++;
        }
        return level;
    }

    public int getXpForNextLevel(){
        return getXPForLevel(getCurrentLevel()+1);
    }

    public static int getXPForLevel(int level){
        int xp=0;
        for (int i = 0; i < level; i++) {
            xp += Primes.nextPrime(xp+1);
        }
        return xp*10;
    }

    public void incrementXP(int amount){
        currentXP+=amount;
    }
    public void incrementXP(){
        incrementXP(1);
    }

    public int getCurrentXP() {
        return currentXP;
    }

    @Override
    public JSONObject getSaveObject() {
        JSONObject saveObject = new JSONObject();
        saveObject.put("currentXP",this.currentXP);
        return saveObject;
    }

    @Override
    public void load(JSONObject saveObject) {
        this.currentXP =saveObject.getInt("currentXP");
    }

    public String toString(){
        return getName()+": Current XP: "+currentXP+
                " Current Level:"+getCurrentLevel()+
                " XP to next Level: "+(getXpForNextLevel()-currentXP);
    }
}
