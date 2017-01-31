package io.sonicdeadlock.projectc.entity;


/**
 * Created by Alex on 10/5/2016.
 */
public class Widget extends Entity {
    public static final String TYPE = "Widget";
    private static int GLOBAL_COUNTER = 0;
    private int index;

    public Widget(int x, int y) {
        super(x, y);
        index = GLOBAL_COUNTER;
        GLOBAL_COUNTER++;

    }

    public Widget() {
        super();
        addPerformableAction("doFoo");
        addPerformableAction("doBar");
    }


    public String getType() {
        return TYPE;
    }

    @Override
    public void performAction(String action) {
        super.performAction(action);
    }


    public String toString() {
        return "Hey, I'm a widget at " + getX() + "," + getY();
    }

    @Override
    public char getMapCharacter() {
        return 'w';
    }
}
