package com.springJadeProject.console.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Agent {
    private String nickname; //agent name => localName
    private boolean initiated;
    private String name; //agent name in container => localName@containerAddress
    private String agentClassName;
    private AgentState agentState;

    public Agent(String nickname, Boolean initiated, String name, String agentClassName, AgentState agentState) {
        this.nickname = nickname;
        this.initiated = initiated;
        this.name = name;
        this.agentClassName = agentClassName;
        this.agentState = agentState;
    }

    protected Agent(){}

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean getInitiated() {
        return initiated;
    }

    public void setInitiated(boolean initiated) {
        this.initiated = initiated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgentClassName() {
        return agentClassName;
    }

    public void setAgentClassName(String agentClassName) {
        this.agentClassName = agentClassName;
    }

    public AgentState getAgentState() {
        return agentState;
    }

    public void setAgentState(AgentState agentState) {
        this.agentState = agentState;
    }
}
