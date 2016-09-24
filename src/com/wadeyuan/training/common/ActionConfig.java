package com.wadeyuan.training.common;

public class ActionConfig {
    private String controller;
    private String method;

    public ActionConfig(String controller, String method) {
        this.controller = controller;
        this.method = method;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
