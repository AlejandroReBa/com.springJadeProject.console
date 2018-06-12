package com.springJadeProject.console.model.dao;

import com.springJadeProject.console.model.agentModel.JsonBehavioursModel;
import com.springJadeProject.console.model.entity.Agent;
import com.springJadeProject.console.model.entity.Behaviour;

import java.util.Collection;
import java.util.Map;

public interface AgentDaoInterface {
    Collection<Agent> getAllAvailableAgents();

    Collection<Agent> getAllCreatedAgents();

    Collection<String> getAllCreatedAgentsNames();

    Agent getAgentByName(String name);

    boolean createAgent (String className, String agentName);

    boolean deleteAgent (Agent agent);

    boolean initAgent(Agent agent);

    boolean stopAgent(Agent agent);

    boolean restartAgent(Agent agent);

    Collection<Behaviour> loadBehavioursAvailable();

    Collection<JsonBehavioursModel> loadAllAgentsAndItsBehaviours();

    boolean addBehavioursToAgent (Agent agent, Collection<Behaviour> behaviours);

    boolean removeBehavioursFromAgent (Agent agent, Collection<Behaviour> behaviours);

    boolean resetBehaviourFromAgent (Agent agent, Behaviour behaviour);

    Collection <String> loadAllAgentClassNames();
}
