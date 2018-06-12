package com.springJadeProject.console.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Behaviour {
    private int restartCounter;
    private String executionState;
    private String agentLocalName;
    private String behaviourName;
    //variable required for interface purposes. I want to know which behaviour is checked to remove or add it to an agent
    private boolean checked;

    public int getRestartCounter() {
        return restartCounter;
    }

    public void setRestartCounter(int restartCounter) {
        this.restartCounter = restartCounter;
    }

    public String getExecutionState() {
        return executionState;
    }

    public void setExecutionState(String executionState) {
        this.executionState = executionState;
    }

    public String getAgentLocalName() {
        return agentLocalName;
    }

    public void setAgentLocalName(String agentLocalName) {
        this.agentLocalName = agentLocalName;
    }

    public String getBehaviourName() {
        return behaviourName;
    }

    public void setBehaviourName(String behaviourName) {
        this.behaviourName = behaviourName;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean equals (Object object){
        boolean result = false;
        if (object instanceof Behaviour){
            if (((Behaviour)object).behaviourName.equals(this.behaviourName)){
                result = true;
            }
        }

        return result;
    }
}
