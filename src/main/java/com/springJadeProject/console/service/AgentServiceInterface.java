package com.springJadeProject.console.service;

import com.springJadeProject.console.model.agentModel.JsonBehavioursModel;
import com.springJadeProject.console.model.entity.Agent;
import com.springJadeProject.console.model.entity.Behaviour;

import java.util.Collection;
import java.util.Map;

public interface AgentServiceInterface {
    Collection<Agent> loadAllAvailableAgents();

    Collection<Agent> loadAllCreatedAgents();

    Map<String, Agent> loadAllCreatedAgentsInMap();

    Collection<String> loadAllCreatedAgentsNames();

    Agent loadAgentByName(String agentName);

    boolean createAgent (String className, String agentName);

    boolean deleteAgent (Agent agent);

    boolean initAgent(Agent agent);

    boolean stopAgent(Agent agent);

    boolean restartAgent(Agent agent);

    Collection<Behaviour> loadBehavioursAvailable();

    Collection<JsonBehavioursModel> loadAllAgentsAndItsBehaviours();

    boolean addBehavioursToAgent(Agent agent, Collection<Behaviour> behaviours);

    boolean removeBehavioursFromAgent(Agent agent, Collection<Behaviour> behaviours);

    boolean resetBehaviour(Agent agent, Behaviour behaviour);

    Collection<String> loadAllAgentClassNames();
}
