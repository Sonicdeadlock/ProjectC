package io.sonicdeadlock.projectc.entity;

import io.sonicdeadlock.projectc.entity.attribute.Attribute;
import io.sonicdeadlock.projectc.entity.attribute.AttributeFactory;
import io.sonicdeadlock.projectc.entity.skill.Skill;
import io.sonicdeadlock.projectc.entity.skill.Sprint;
import io.sonicdeadlock.projectc.world.chunk.Chunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 10/5/2016.
 */
public class Player extends Entity {
    private static final String TYPE = "Player";
    private static final Logger LOGGER = LogManager.getLogger(Player.class);
    private List<Attribute> attributes;
    private List<Skill> skills;

    public Player(int x, int y) {
        super(x, y);
        attributes = new ArrayList<>();
        skills = new ArrayList<>();
    }

    public Player() {
    }

    @Override
    public JSONObject getSaveObject() {
        JSONObject saveObject = super.getSaveObject();
        JSONArray saveAttributes = new JSONArray();
        for (Attribute attribute : attributes) {
            JSONObject attributeWrapper = new JSONObject();
            try {
                attributeWrapper.put("type",attribute.getClass().getMethod("getType").invoke(null));
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
               LOGGER.error("Error getting attribute type ",e);
            }
            attributeWrapper.put("attribute",attribute.getSaveObject());
            saveAttributes.put(attributeWrapper);
        }
        saveObject.put("attributes",saveAttributes);
        JSONArray saveSkills = new JSONArray();
        for (Skill skill : skills) {
            JSONObject skillWrapper = new JSONObject();
            try {
                skillWrapper.put("type",skill.getClass().getMethod("getType").invoke(null));
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                LOGGER.error("Error getting skill type ",e);
            }
            skillWrapper.put("skill",skill.getSaveObject());
            saveSkills.put(skillWrapper);
        }
        saveObject.put("skills",saveSkills);
        return saveObject;
    }

    @Override
    public void load(JSONObject saveObject) {
        super.load(saveObject);
        JSONArray saveAttributes = saveObject.getJSONArray("attributes");
        JSONArray saveSkills = saveObject.getJSONArray("skills");
        this.attributes = new ArrayList<>(saveAttributes.length());
        this.skills = new ArrayList<>(saveSkills.length());
        for (Object o : saveAttributes) {
            if(o instanceof JSONObject){
                JSONObject saveAttribute = (JSONObject)o;
                this.attributes.add(AttributeFactory.getInstance().getAttribute(saveAttribute.getString("type"),saveAttribute.getJSONObject("attribute")));
            }else{
                LOGGER.debug("Object in save attributes that isn't a JSONObject --- "+o.getClass());
            }
        }
        for (Object o : saveSkills) {
            if(o instanceof JSONObject){
                JSONObject saveSkill = (JSONObject)o;
                try{
                    this.skills.add((Skill) AttributeFactory.getInstance().getAttribute(saveSkill.getString("type"),saveSkill.getJSONObject("skill")));

                }catch (ClassCastException cce){
                    LOGGER.error("Normal attribute in save skills",cce);
                }
                
            }else{
                LOGGER.debug("Object in save skills that isn't a JSONObject --- "+o.getClass());
            }
        }
    }

    public static String getType(){
        return TYPE;
    }

    public int getChunkX(){
        return this.getX()/ Chunk.CHUNK_SIZE;
    }
    public int getChunkY(){
        return this.getY()/Chunk.CHUNK_SIZE;
    }

    public Sprint getSprint(){
        Sprint playerSprint =(Sprint) getSkill(Sprint.getType());
        if(playerSprint ==null){
            playerSprint = new Sprint();
            skills.add(playerSprint);
        }
        return playerSprint;
    }


    private Skill getSkill(String type){
        for (Skill skill : skills) {
            if(skill.isType(type)){
                return skill;
            }
        }
        return null;
    }

    private Attribute getAttribute(String type){
        for (Attribute attribute : attributes) {
            if(attribute.isType(type)){
                return attribute;
            }
        }
        return null;
    }


}
