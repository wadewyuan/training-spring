package com.wadeyuan.training.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class ModelAndView {
    private boolean isRedirect;
    private boolean isSessionInvalidated;
    private String view;
    private Map<String, Object> requestAttributes;
    private Map<String, Object> sessionAttributes;
    private Set<String> requestAttributesToRemove;
    private Set<String> sessionAttributesToRemove;

    public ModelAndView() {
        this.isRedirect = false;
        this.isSessionInvalidated = false;
        this.requestAttributes = new HashMap<String, Object>();
        this.sessionAttributes = new HashMap<String, Object>();
        this.requestAttributesToRemove = new HashSet<String>();
        this.sessionAttributesToRemove = new HashSet<String>();
    }

    public boolean isRedirect() {
        return isRedirect;
    }
    public void setRedirect(boolean isRedirect) {
        this.isRedirect = isRedirect;
    }

    public boolean isSessionInvalidated() {
        return isSessionInvalidated;
    }

    public void invalidateSession() {
        this.isSessionInvalidated = true;
    }

    public String getView() {
        return view;
    }
    public void setView(String view) {
        this.view = view;
    }

    public Map<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public Set<String> getRequestAttributesToRemove() {
        return requestAttributesToRemove;
    }

    public Set<String> getSessionAttributesToRemove() {
        return sessionAttributesToRemove;
    }

    public void addRequestAttribute(String key, Object value) {
        requestAttributes.put(key, value);
    }

    public void removeRequestAttribute(String key) {
        requestAttributesToRemove.add(key);
    }

    public void addSessionAttribute(String key, Object value) {
        sessionAttributes.put(key, value);
    }

    public void removeSessionAttribute(String key) {
        sessionAttributesToRemove.add(key);
    }
}
