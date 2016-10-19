package io.sonicdeadlock.projectc.entity;

import io.sonicdeadlock.projectc.entity.attribute.Attribute;
import io.sonicdeadlock.projectc.entity.attribute.Settings;
import io.sonicdeadlock.projectc.entity.skill.EyeSight;
import io.sonicdeadlock.projectc.entity.skill.Skill;
import io.sonicdeadlock.projectc.entity.skill.Sprint;
import io.sonicdeadlock.projectc.item.Item;
import io.sonicdeadlock.projectc.util.LoaderFactory;
import io.sonicdeadlock.projectc.util.RayCaster;
import io.sonicdeadlock.projectc.util.SpacialUtils;
import io.sonicdeadlock.projectc.world.World;
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
    public static final String TYPE = "Player";
    private static final Logger LOGGER = LogManager.getLogger(Player.class);
    private List<Attribute> attributes;
    private List<Skill> skills;
    private Selectable selected;
    private List<Item> inventory;


    public Player(int x, int y) {
        super(x, y);
        attributes = new ArrayList<>();
        skills = new ArrayList<>();
        inventory = new ArrayList<>();
    }

    public Player() {
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    @Override
    public JSONObject getSaveObject() {
        JSONObject saveObject = super.getSaveObject();
        JSONArray saveAttributes = new JSONArray();
        JSONArray saveInventory = new JSONArray();
        for (Attribute attribute : attributes) {
            JSONObject attributeWrapper = new JSONObject();
            try {
                attributeWrapper.put("type",attribute.getClass().getField("TYPE").get(null));
            }  catch (NoSuchFieldException  | IllegalAccessException e) {
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
                skillWrapper.put("type",skill.getClass().getField("TYPE").get(null));
            } catch (NoSuchFieldException  | IllegalAccessException e) {
                LOGGER.error("Error getting skill type ",e);
            }
            skillWrapper.put("skill",skill.getSaveObject());
            saveSkills.put(skillWrapper);
        }
        saveObject.put("skills",saveSkills);
        for (Item item : inventory) {
            JSONObject itemWrapper = new JSONObject();
            try {
                itemWrapper.put("type",item.getClass().getField("TYPE").get(null));
            } catch (NoSuchFieldException  | IllegalAccessException e) {
                LOGGER.error("Error getting item type ",e);
            }
            itemWrapper.put("item",item.getSaveObject());
            saveInventory.put(itemWrapper);
        }
        saveObject.put("inventory",saveInventory);
        return saveObject;
    }

    @Override
    public void load(JSONObject saveObject) {
        super.load(saveObject);
        JSONArray saveAttributes = saveObject.getJSONArray("attributes");
        JSONArray saveSkills = saveObject.getJSONArray("skills");
        JSONArray saveInventory = saveObject.getJSONArray("inventory");
        this.attributes = new ArrayList<>(saveAttributes.length());
        this.skills = new ArrayList<>(saveSkills.length());
        this.inventory = new ArrayList<>(saveInventory.length());
        for (Object o : saveAttributes) {
            if(o instanceof JSONObject){
                JSONObject saveAttribute = (JSONObject)o;
                this.attributes.add(LoaderFactory.getAttributeLoaderFactoryInstance().getLoadable(saveAttribute.getString("type"),saveAttribute.getJSONObject("attribute")));
            }else{
                LOGGER.debug("Object in save attributes that isn't a JSONObject --- "+o.getClass());
            }
        }
        for (Object o : saveSkills) {
            if(o instanceof JSONObject){
                JSONObject saveSkill = (JSONObject)o;
                try{
                    this.skills.add((Skill) LoaderFactory.getAttributeLoaderFactoryInstance().getLoadable(saveSkill.getString("type"),saveSkill.getJSONObject("skill")));
                }catch (ClassCastException cce){
                    LOGGER.error("Normal attribute in save skills",cce);
                }
            }else{
                LOGGER.debug("Object in save skills that isn't a JSONObject --- "+o.getClass());
            }
        }
        for (Object o : saveInventory) {
            if(o instanceof JSONObject){
                JSONObject saveItem = (JSONObject)o;
                try{
                    this.inventory.add(LoaderFactory.getItemLoaderFactoryInstance().getLoadable(saveItem.getString("type"),saveItem.getJSONObject("item")));
                }catch (ClassCastException cce){
                    LOGGER.error("Normal attribute in save item",cce);
                }
            }else{
                LOGGER.debug("Object in save inventory that isn't a JSONObject --- "+o.getClass());
            }
        }
    }

    public String getType(){
        return TYPE;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public int getChunkX(){
        return this.getX()/ Chunk.CHUNK_SIZE;
    }
    public int getChunkY(){
        return this.getY()/Chunk.CHUNK_SIZE;
    }

    public Sprint getSprint(){
        Sprint playerSprint =(Sprint) getSkill(Sprint.class);
        if(playerSprint ==null){
            playerSprint = new Sprint();
            skills.add(playerSprint);
        }
        return playerSprint;
    }

    public EyeSight getEyeSight(){
        EyeSight playerEyeSight = (EyeSight) getSkill(EyeSight.class);
        if(playerEyeSight==null){
            playerEyeSight = new EyeSight();
            skills.add(playerEyeSight);
        }
        return playerEyeSight;
    }


    private Skill getSkill(Class<? extends Skill> type){
        for (Skill skill : skills) {
            if(skill.getClass().isAssignableFrom(type)){
                return skill;
            }
        }
        return null;
    }

    public Selectable getSelected() {
        return selected;
    }

    public void setSelected(Selectable selected) {
        this.selected = selected;
    }

    private Attribute getAttribute(Class<? extends Attribute> type){
        for (Attribute attribute : attributes) {
            if(attribute.getClass().isAssignableFrom(type)){
                return attribute;
            }
        }
        return null;
    }

    public Settings getSettings(){
        Settings settings = (Settings) getAttribute(Settings.class);
        if (settings == null) {
            settings = new Settings();
            this.attributes.add(settings);
        }
        return settings;
    }

    /**
     *
     * @param x the delta X of the player
     * @param y the delta Y of the player
     */
    public void move(int x,int y){
        double distance = SpacialUtils.getDistance(0,0,x,y);
        getSprint().incrementXP((int)distance);
        setX(x+getX());
        setY(y+getY());
    }

    /**
     *
     * @param x the new X of the player
     * @param y the new Y of the player
     */
    public void moveTo(int x,int y){
        move(x-getX(),y-getY());
    }


    public String toString(){
        StringBuilder response = new StringBuilder();
        for (Skill skill : skills) {
            response.append(skill).append("\n");
        }
        return response.toString();
    }

    public boolean canMoveToPoint(int x, int y, World world){
        int deltaX = getX()-x;
        int deltaY = y-getY();
        double direction = Math.atan2(deltaY,deltaX);
        double distance = Math.abs(SpacialUtils.getDistance(getX(), getY(), x, y));
        List entities = RayCaster.castRay(getX(),getY(),direction,distance,world,RayCaster.CHECK_ENTITY_EXISTS);
        return entities.size()==0;
    }


}
