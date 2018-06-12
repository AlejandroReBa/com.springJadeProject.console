package com.springJadeProject.console.controller;


import com.springJadeProject.console.model.agentModel.JsonBehavioursModel;
import com.springJadeProject.console.model.entity.Agent;
import com.springJadeProject.console.model.entity.Behaviour;
import com.springJadeProject.console.service.AgentServiceInterface;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Scope(value = "session")
@Component(value = "agentBehaviourListController")
@ELBeanName(value = "agentBehaviourListController")
@Join(path = "/agent/behaviour/list", to = "/agent-behaviour-list.xhtml")
public class AgentBehaviourListController {
    @Autowired
    private AgentServiceInterface agentService;

//    private List<Agent> availableAgents;
    private Map<String, Agent> agents;
    private List<Behaviour> behavioursAvailable; //all behaviours I can add to agents (factory behaviours in the micro-service)
    private List<JsonBehavioursModel> agentAndBehavioursList;

    private Agent agentSelected;
    private List<Behaviour> agentSelectedBehaviours; //behaviours agent selected has attached
    private List<Behaviour> agentOriginalSelectedBehaviours; //behavious agent selected has attached. Original, not modified by interface
                                                             //so we know which are the new behaviours we have to add via API
    private List<Behaviour> agentSelectedBehavioursAvailable; //behaviours that can be added to selected agent (from behavioursAvailable)

    boolean resettingBehaviour;

    public boolean getResettingBehaviour() {
        return resettingBehaviour;
    }

    public void setResettingBehaviour(boolean resettingBehaviour) {
        this.resettingBehaviour = resettingBehaviour;
    }



    @Deferred
    @RequestAction
    @IgnorePostback
    public void loadData(){
//        availableAgents = new ArrayList<>(agentService.loadAllAvailableAgents());
        agents = agentService.loadAllCreatedAgentsInMap();
        behavioursAvailable = new ArrayList<>(agentService.loadBehavioursAvailable());
        agentAndBehavioursList = new ArrayList<>();
        agentAndBehavioursList = new ArrayList<>(agentService.loadAllAgentsAndItsBehaviours());
        resettingBehaviour = false;
    }

    public List<Agent> getAgents() {
        return new ArrayList<>(agents.values());
    }

    public List<JsonBehavioursModel> getAgentAndBehavioursList() {
        return agentAndBehavioursList;
    }

    public List<Behaviour> getBehavioursAvailable(){
        return behavioursAvailable;
    }

    public Agent getAgentSelected(){
        return agentSelected;
    }

    public List<Behaviour> getAgentSelectedBehaviours(){
        return agentSelectedBehaviours;
    }

    public List<Behaviour> getAgentSelectedBehavioursAvailable() {
        return agentSelectedBehavioursAvailable;
    }

    public String manageBehaviours (JsonBehavioursModel jsonBehavioursModel){
        agentSelected = agents.get(jsonBehavioursModel.getAgentName());
        agentSelectedBehaviours = jsonBehavioursModel.getBehaviourList();
        agentOriginalSelectedBehaviours = new ArrayList<>(agentSelectedBehaviours);
        agentSelectedBehavioursAvailable = new ArrayList<>();
        for (Behaviour behaviour : behavioursAvailable){
            if (!agentSelectedBehaviours.contains(behaviour)){ //contains makes use of our override equals on behaviour class
                agentSelectedBehavioursAvailable.add(behaviour);
            }
        }

        //todo manage agent behaviours. go to new screen to add and remove behaviours
        return "/behaviour-list.xhtml?faces-redirect=true";
    }

    public String addBehaviours(){
        List<Behaviour> iterateList = new ArrayList<>(agentSelectedBehavioursAvailable);
        for (Behaviour b : iterateList){
            if (b.getChecked()){
                agentSelectedBehavioursAvailable.remove(b);
                b.setChecked(false);
                agentSelectedBehaviours.add(b);
            }
        }
        return "/behaviour-list.xhtml?faces-redirect=true";
    }

    public String removeBehaviours(){
        List<Behaviour> iterateList = new ArrayList<>(agentSelectedBehaviours);
        for (Behaviour b : iterateList){
            if (b.getChecked()){
                agentSelectedBehaviours.remove(b);
                if (behavioursAvailable.contains(b)){
                    //because there are some methods that we can't replicate through getInstance in the backend (microservice)
                    //some methods are added in the flow, so only methods implementing Behaviour with Factory Interface can do that
                    b.setChecked(false);
                    agentSelectedBehavioursAvailable.add(b);
                }
            }
        }
        return "/behaviour-list.xhtml?faces-redirect=true";
    }

    public String resetBehaviour(Behaviour behaviour){
//        resettingBehaviour = true;
        //todo fix!!! check if the behaviour was already added to the agent to reset it!!!!!!!!!
        agentService.resetBehaviour(agentSelected, behaviour);
//        resettingBehaviour = false;
        return "/behaviour-list.xhtml?faces-redirect=true";
    }


    public String acceptChanges(){
        List<Behaviour> behavioursAdded = new ArrayList<>();
        List<Behaviour> behavioursRemoved = new ArrayList<>();

        for (Behaviour b : agentSelectedBehaviours){
            if (!agentOriginalSelectedBehaviours.contains(b)){
                behavioursAdded.add(b);
            }
        }

        for (Behaviour b : agentOriginalSelectedBehaviours){
            if (!agentSelectedBehaviours.contains(b)){
                behavioursRemoved.add(b);
            }
        }
        agentService.addBehavioursToAgent(agentSelected, behavioursAdded);
        agentService.removeBehavioursFromAgent(agentSelected, behavioursRemoved);

        return "/agent-behaviour-list.xhtml?faces-redirect=true";
    }

    //check if the behaviour is already attached to agent in the JADE container
    public boolean isBehaviourAttachedToAgent(Behaviour behaviour){
        return agentOriginalSelectedBehaviours.contains(behaviour);
    }
}
