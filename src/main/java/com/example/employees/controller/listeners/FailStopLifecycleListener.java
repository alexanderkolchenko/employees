package com.example.employees.controller.listeners;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.LifecycleState;

public class FailStopLifecycleListener implements LifecycleListener {

    @Override
    public void lifecycleEvent(LifecycleEvent lifecycleEvent) {
        Lifecycle lifecycle = lifecycleEvent.getLifecycle();
        LifecycleState state = lifecycle.getState();
        System.out.println("State of TomCat: " + state);
    }
}
