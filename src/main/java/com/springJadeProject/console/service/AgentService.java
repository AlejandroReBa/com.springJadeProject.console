package com.springJadeProject.console.service;

import com.springJadeProject.console.model.agentModel.JsonBehavioursModel;
import com.springJadeProject.console.model.dao.AgentDaoInterface;
import com.springJadeProject.console.model.entity.Agent;
import com.springJadeProject.console.model.entity.Behaviour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class AgentService implements AgentServiceInterface{
    private AgentDaoInterface agentDao;

    public AgentService(@Autowired AgentDaoInterface agentDaoIn) {
        agentDao = agentDaoIn;
    }

    @Override
    public Collection<Agent> loadAllAvailableAgents() {
        Collection<Agent> result = agentDao.getAllAvailableAgents();
        //todo modify this, I don't think I will need all that data
        for (Agent agent: result){
            if (agent.getName() == null){
                agent.setName("-");
            }
        }
        return result;
    }

    @Override
    public Collection<Agent> loadAllCreatedAgents() {
        Collection<Agent> result = agentDao.getAllCreatedAgents();
        for (Agent agent: result){
            if (agent.getName() == null){
                agent.setName("-");
            }
        }
        return result;
    }

    @Override
    public Map<String, Agent> loadAllCreatedAgentsInMap() {
        Map<String, Agent> result = new HashMap<>();
        Collection<Agent> agents = loadAllCreatedAgents();
        for (Agent agent: agents){
            result.put(agent.getNickname(), agent);
        }
        return result;
    }

    @Override
    public Collection<String> loadAllCreatedAgentsNames() {
        return agentDao.getAllCreatedAgentsNames();
    }

    @Override
    public Collection<Behaviour> loadBehavioursAvailable() {
        return agentDao.loadBehavioursAvailable();
    }

    @Override
    public Collection<JsonBehavioursModel> loadAllAgentsAndItsBehaviours() {
        return agentDao.loadAllAgentsAndItsBehaviours();
    }

    @Override
    public Agent loadAgentByName(String agentName) {
        return null;
    }

    @Override
    public boolean createAgent(String className, String agentName) {
        return agentDao.createAgent(className, agentName);
    }

    @Override
    public boolean deleteAgent(Agent agent) {
       return agentDao.deleteAgent(agent);
    }

    //similar to update some parameter from an agent. This is why we use a DAO
    @Override
    public boolean initAgent(Agent agent) {
        return agentDao.initAgent(agent);
    }

    @Override
    public boolean stopAgent(Agent agent) {
        return agentDao.stopAgent(agent);
    }

    @Override
    public boolean restartAgent(Agent agent) {
        return agentDao.restartAgent(agent);
    }

    @Override
    public boolean addBehavioursToAgent(Agent agent, Collection<Behaviour> behaviours) {
        return agentDao.addBehavioursToAgent(agent, behaviours);
    }

    @Override
    public boolean removeBehavioursFromAgent(Agent agent, Collection<Behaviour> behaviours) {
        return agentDao.removeBehavioursFromAgent(agent, behaviours);
    }

    @Override
    public boolean resetBehaviour(Agent agent, Behaviour behaviour) {
        return agentDao.resetBehaviourFromAgent(agent, behaviour);
    }

    @Override
    public Collection<String> loadAllAgentClassNames() {
        return agentDao.loadAllAgentClassNames();
    }
}
