package io.sonicdeadlock.projectc.entity.attribute;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 10/7/2016.
 */
public class AttributeFactory {
    private static AttributeFactory ourInstance = new AttributeFactory();
    private Map<String,Class<? extends Attribute>> attributeTypes = new HashMap<>();
    private static final Logger LOGGER = LogManager.getLogger(AttributeFactory.class);
    public static AttributeFactory getInstance() {
        return ourInstance;
    }

    private AttributeFactory() {

    }

    public void registerAttribute(String type ,Class<? extends Attribute> clazz){
        attributeTypes.put(type,clazz);
    }

    public Attribute getAttribute(String type, JSONObject saveObject){
        if(!attributeTypes.containsKey(type))
            throw new IllegalArgumentException("Unregistered Attribute type: "+ type);
        Class<? extends Attribute> clazz =attributeTypes.get(type);
        try {
            Attribute newAttribute = clazz.newInstance();
            newAttribute.load(saveObject);
            return newAttribute;
        } catch (InstantiationException e) {
            LOGGER.error("Error when creating Attribute: "+clazz.getName(),e);
        } catch (IllegalAccessException e) {
            LOGGER.error("Error when creating Attribute: "+clazz.getName(),e);
        }
        return null;
    }
}
