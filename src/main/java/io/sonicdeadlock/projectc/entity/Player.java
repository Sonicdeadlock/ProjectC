package io.sonicdeadlock.projectc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sonicdeadlock.projectc.entity.attribute.Attribute;
import io.sonicdeadlock.projectc.entity.attribute.Settings;
import io.sonicdeadlock.projectc.entity.skill.EyeSight;
import io.sonicdeadlock.projectc.entity.skill.Skill;
import io.sonicdeadlock.projectc.entity.skill.Sprint;
import io.sonicdeadlock.projectc.item.Item;
import io.sonicdeadlock.projectc.util.RayCaster;
import io.sonicdeadlock.projectc.util.SpacialUtils;
import io.sonicdeadlock.projectc.world.World;
import io.sonicdeadlock.projectc.world.chunk.Chunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public String getType() {
        return TYPE;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    @JsonIgnore
    public int getChunkX() {
        return this.getX() / Chunk.CHUNK_SIZE;
    }

    @JsonIgnore
    public int getChunkY() {
        return this.getY() / Chunk.CHUNK_SIZE;
    }

    @JsonIgnore
    public Sprint getSprint() {
        Sprint playerSprint = (Sprint) getSkill(Sprint.class);
        if (playerSprint == null) {
            playerSprint = new Sprint();
            skills.add(playerSprint);
        }
        return playerSprint;
    }

    @JsonIgnore
    public EyeSight getEyeSight() {
        EyeSight playerEyeSight = (EyeSight) getSkill(EyeSight.class);
        if (playerEyeSight == null) {
            playerEyeSight = new EyeSight();
            skills.add(playerEyeSight);
        }
        return playerEyeSight;
    }


    private Skill getSkill(Class<? extends Skill> type) {
        for (Skill skill : skills) {
            if (skill.getClass().isAssignableFrom(type)) {
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

    private Attribute getAttribute(Class<? extends Attribute> type) {
        for (Attribute attribute : attributes) {
            if (attribute.getClass().isAssignableFrom(type)) {
                return attribute;
            }
        }
        return null;
    }

    @JsonIgnore
    public Settings getSettings() {
        Settings settings = (Settings) getAttribute(Settings.class);
        if (settings == null) {
            settings = new Settings();
            this.attributes.add(settings);
        }
        return settings;
    }

    /**
     * @param x the delta X of the player
     * @param y the delta Y of the player
     */
    public void move(int x, int y) {
        double distance = SpacialUtils.getDistance(0, 0, x, y);
        getSprint().incrementXP((int) distance);
        setX(x + getX());
        setY(y + getY());
    }

    /**
     * @param x the new X of the player
     * @param y the new Y of the player
     */
    public void moveTo(int x, int y) {
        move(x - getX(), y - getY());
    }


    public String toString() {
        StringBuilder response = new StringBuilder();
        for (Skill skill : skills) {
            response.append(skill).append("\n");
        }
        return response.toString();
    }

    public boolean canMoveToPoint(int x, int y, World world) {
        int deltaX = getX() - x;
        int deltaY = y - getY();
        double direction = Math.atan2(deltaY, deltaX);
        double distance = Math.abs(SpacialUtils.getDistance(getX(), getY(), x, y));
        List entities = RayCaster.castRay(getX(), getY(), direction, distance, world, RayCaster.CHECK_ENTITY_EXISTS);
        return entities.size() == 0;
    }

    @Override
    public char getMapCharacter() {
        return 'P';
    }
}
