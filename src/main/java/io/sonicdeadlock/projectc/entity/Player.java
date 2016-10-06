package io.sonicdeadlock.projectc.entity;

import io.sonicdeadlock.projectc.world.chunk.Chunk;
import org.json.JSONObject;

/**
 * Created by Alex on 10/5/2016.
 */
public class Player extends Entity {
    private static final String TYPE = "Player";

    public Player(int x, int y) {
        super(x, y);
    }

    public Player() {
    }

    @Override
    public JSONObject getSaveObject() {
        return super.getSaveObject();
    }

    @Override
    public void load(JSONObject saveObject) {
        super.load(saveObject);
    }

    public static String getType(){
        return TYPE;
    }

    public int getChunkX(){
        return this.getX()/ Chunk.CHUNK_SIZE;
    }
    public int getChuunkY(){
        return this.getY()/Chunk.CHUNK_SIZE;
    }
}
