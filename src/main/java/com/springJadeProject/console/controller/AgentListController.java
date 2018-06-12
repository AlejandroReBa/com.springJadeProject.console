package com.springJadeProject.console.controller;


import com.springJadeProject.console.model.entity.Agent;
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

@Scope(value = "session")
@Component(value = "agentListController")
@ELBeanName(value = "agentListController")
@Join(path = "/", to = "/agent-list.xhtml")
public class AgentListController {
    @Autowired
    private AgentServiceInterface agentService;

    private List<Agent> agents;


    @Deferred
    @RequestAction
    @IgnorePostback
    public void loadData(){
        agents = new ArrayList<>(agentService.loadAllCreatedAgents());
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public String init(Agent agent){
        if (agentService.initAgent(agent)){
            //write in the message panel that everything went okay
        }else{
            //write in the message panel that something went wrong
        }
        return "/agent-list.xhtml?faces-redirect=true";
    }

    public String stop(Agent agent){
        if (agentService.stopAgent(agent)){
            //write in the message panel that everything went okay
        }else{
            //write in the message panel that something went wrong
        }
        return "/agent-list.xhtml?faces-redirect=true";
    }

    public String restart(Agent agent){
        if (agentService.restartAgent(agent)){
            //write in the message panel that everything went okay
        }else{
            //write in the message panel that something went wrong
        }
        return "/agent-list.xhtml?faces-redirect=true";
    }

    public String reloadData(){
        loadData();
        return "/agent-list.xhtml?faces-redirect=true";
    }

    public String delete(Agent agent){
        System.out.println("Delete agent called");
        agentService.deleteAgent(agent);
        return "/agent-list.xhtml?faces-redirect=true";
    }

//    public String delete(Product product) {
//        agentService.removeProductById(product.getId());
////        agents.remove(product);
//        return "/agent-list.xhtml?faces-redirect=true";
//    }
}
