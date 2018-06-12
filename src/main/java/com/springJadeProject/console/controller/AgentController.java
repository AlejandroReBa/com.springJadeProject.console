package com.springJadeProject.console.controller;

import com.springJadeProject.console.service.AgentServiceInterface;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Scope(value = "session")
@Component(value = "agentController")
@ELBeanName(value = "agentController")
@Join(path = "/agent", to = "/agent-form.xhtml")
public class AgentController {

    private AgentServiceInterface agentService;

    private List<String> agentClassNamesList;
    private List<String> agentBannedNamesList;
    private String agentName;
    private String selectedClassName;
    private String firstBannedName;

    @Deferred
    @RequestAction
    @IgnorePostback
    public void loadData() {
        agentClassNamesList = new ArrayList<>(agentService.loadAllAgentClassNames());
        agentBannedNamesList = new ArrayList<>(agentService.loadAllCreatedAgentsNames());
    }

    public AgentController(@Autowired AgentServiceInterface agentServiceIn) {
        this.agentService = agentServiceIn;
    }

    public List<String> getAgentClassNamesList() {
        return agentClassNamesList;
    }

    public List<String> getAgentBannedNamesList() {
        return agentBannedNamesList;
    }

    @Size(min=3, max=25, message="Error: Min 3 and max 25 characters")
    @Pattern(regexp ="[a-zA-Z0-9]+", message="Error: String is not valid (only characters a-z A-Z 0-9)")
    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getSelectedClassName() {
        return selectedClassName;
    }

    public void setSelectedClassName(String selectedClassName) {
        this.selectedClassName = selectedClassName;
    }

    public String getFirstBannedName() {
        return firstBannedName;
    }

    public void setFirstBannedName(String firstBannedName) {
        this.firstBannedName = firstBannedName;
    }

    public String create(){
//        if (agentBannedNamesList.contains(getAgentName())){
//            FacesContext.getCurrentInstance().addMessage("agentForm:panelForm:panelGrid:name", new FacesMessage("kk","Error: Your agent name is banned. Please, pick another one"));
//            return "/agent-form.xhtml?faces-redirect=true";
//        }else{
            agentService.createAgent(selectedClassName, agentName);
            return "/agent-list.xhtml?faces-redirect=true";
//        }
    }

    public boolean checkName() {
        boolean isNameCorrect = true;
        if (agentBannedNamesList.contains(getAgentName())) {
            isNameCorrect = false;
        }
        return isNameCorrect;
    }
}
