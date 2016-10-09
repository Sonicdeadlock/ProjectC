package io.sonicdeadlock.projectc.entity;

import java.util.List;

/**
 * Created by Alex on 10/8/2016.
 */
public interface Selectable {
    List<String> getPerformableActions();
    void performAction(String action);

}
