package com.springJadeProject.console.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AgentState {
    String name;
    int value;

    public AgentState(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public AgentState(){};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return this.name.concat("(").concat(String.valueOf(this.value)).concat(")");
    }
}
