package com.springJadeProject.console.model.dao;

import com.springJadeProject.console.model.agentModel.JsonAgentBehaviourModel;
import com.springJadeProject.console.model.agentModel.JsonBehavioursModel;
import com.springJadeProject.console.model.agentModel.JsonResponseMessage;
import com.springJadeProject.console.model.entity.Agent;
import com.springJadeProject.console.model.entity.Behaviour;
import com.springJadeProject.console.model.exceptions.ExternalCallBadRequestException;
import com.springJadeProject.console.model.exceptions.ExternalCallServerErrorException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**collect data from agents via Microservice API **/
@Repository
public class APIAgentDao implements AgentDaoInterface{
    private static final String API_URL = "http://localhost:9090/api";

    @Override
    public Collection<Agent> getAllAvailableAgents() {
        String url = API_URL.concat("/agents/available");
        return requestAgentListToAPI(url);
    }

    @Override
    public Collection<Agent> getAllCreatedAgents() {
        String url = API_URL.concat("/agents/created");
        return requestAgentListToAPI(url);
    }

    @Override
    public Collection<String> getAllCreatedAgentsNames() {
        String url = API_URL.concat("/agents/created/names");
        return requestStringListToAPI(url);
    }

    @Override
    public Collection<Behaviour> loadBehavioursAvailable() {
        String url = API_URL.concat("/behaviours/available");
        return requestBehaviourListToAPI(url);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Collection<JsonBehavioursModel> loadAllAgentsAndItsBehaviours() {
        String url = API_URL.concat("/agents/available/behaviours");
        ParameterizedTypeReference<List<JsonBehavioursModel>> typeResponse = new ParameterizedTypeReference<List<JsonBehavioursModel>>() {};

        return (Collection<JsonBehavioursModel>) requestListToAPI(url, typeResponse);
    }

    @Override
    public Agent getAgentByName(String agentName) {
        return null;
    }

    @Override
    public boolean createAgent(String className, String agentName) {
//        String url = API_URL.concat("/agent/create");
        String url = API_URL.concat("/agent");
        HttpMethod httpMethod = HttpMethod.PUT;
        JsonAgentBehaviourModel jsonRequest = createJsonRequest(agentName);
        jsonRequest.setClassName(className);
        return sendJsonRequestToAPI(url, httpMethod, jsonRequest);
    }

    @Override
    public boolean deleteAgent(Agent agent) {
        System.out.println("deleteAgentDao IN");
        String url = API_URL.concat("/agent");
        HttpMethod httpMethod = HttpMethod.DELETE;
        JsonAgentBehaviourModel jsonRequest = createJsonRequest(agent.getNickname());
        return sendJsonRequestToAPI(url, httpMethod, jsonRequest);
    }

    @Override
    public boolean initAgent(Agent agent) {
//        String url = API_URL.concat("/agent/init");
        String url = API_URL.concat("/agent");
        JsonAgentBehaviourModel jsonRequest = createJsonRequest(agent.getNickname());
        jsonRequest.setAction(JsonAgentBehaviourModel.INIT);
        return sendJsonRequestToAPI(url, jsonRequest);

//        return sendJsonAgentNameRequestToAPI(url, agent.getNickname());
    }

    @Override
    public boolean stopAgent(Agent agent) {
//        String url = API_URL.concat("/agent/stop");
        String url = API_URL.concat("/agent");
        JsonAgentBehaviourModel jsonRequest = createJsonRequest(agent.getNickname());
        jsonRequest.setAction(JsonAgentBehaviourModel.STOP);
        return sendJsonRequestToAPI(url, jsonRequest);
    }

    @Override
    public boolean restartAgent(Agent agent) {
//        String url = API_URL.concat("/agent/restart");
        String url = API_URL.concat("/agent");
        JsonAgentBehaviourModel jsonRequest = createJsonRequest(agent.getNickname());
        jsonRequest.setAction(JsonAgentBehaviourModel.RESTART);
        return sendJsonRequestToAPI(url, jsonRequest);
    }

    @Override
    public boolean addBehavioursToAgent(Agent agent, Collection<Behaviour> behaviours) {
//        String url = API_URL.concat("/agent/add/behaviour");
        String url = API_URL.concat("/agent/behaviour");
        return manageBehavioursFromAgent(url, JsonAgentBehaviourModel.ADD, agent.getNickname(), behaviours);
    }

    @Override
    public boolean removeBehavioursFromAgent(Agent agent, Collection<Behaviour> behaviours) {
//        String url = API_URL.concat("/agent/remove/behaviour");
        String url = API_URL.concat("/agent/behaviour");
        return manageBehavioursFromAgent(url, JsonAgentBehaviourModel.REMOVE, agent.getNickname(), behaviours);
    }

    @Override
    public boolean resetBehaviourFromAgent(Agent agent, Behaviour behaviour) {
//        String url = API_URL.concat("/agent/reset/behaviour");
        String url = API_URL.concat("/agent/behaviour");
        JsonAgentBehaviourModel jsonRequest = createJsonRequest(agent.getNickname(), behaviour.getBehaviourName());
        jsonRequest.setAction(JsonAgentBehaviourModel.RESET);
        return sendJsonRequestToAPI(url, jsonRequest);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<String> loadAllAgentClassNames() {
        String url = API_URL.concat("/agents/available/names");
        return requestStringListToAPI(url);
    }

    private boolean manageBehavioursFromAgent(String url, String action, String agentName, Collection<Behaviour> behaviours){
        JsonAgentBehaviourModel jsonRequest;
        boolean allBehavioursDone = true; //=>false if server have had problems to add/remove some behaviour

        for (Behaviour behaviour : behaviours){
            //On the server, startNow is only read when you add a behaviour. RemoveForever is only read when you remove a behaviour
            jsonRequest = createJsonRequest(agentName, behaviour.getBehaviourName(), true, true);
            jsonRequest.setAction(action);
            allBehavioursDone = allBehavioursDone && sendJsonRequestToAPI(url, jsonRequest);
        }
        return allBehavioursDone;
    }


    @SuppressWarnings("unchecked")
    private Collection<Agent> requestAgentListToAPI(String url) {
        ParameterizedTypeReference<List<Agent>> typeResponse = new ParameterizedTypeReference<List<Agent>>() {};
        return (Collection<Agent>)requestListToAPI(url, typeResponse);
    }

    @SuppressWarnings("unchecked")
    private Collection<String> requestStringListToAPI(String url) {
        ParameterizedTypeReference<List<String>> typeResponse = new ParameterizedTypeReference<List<String>>() {};
        return (Collection<String>)requestListToAPI(url, typeResponse);
    }

    @SuppressWarnings("unchecked")
    private Collection<Behaviour> requestBehaviourListToAPI(String url) {
        ParameterizedTypeReference<List<Behaviour>> typeResponse = new ParameterizedTypeReference<List<Behaviour>>() {};
        return (Collection<Behaviour>)requestListToAPI(url, typeResponse);
    }

    private Collection<?> requestListToAPI(String url, ParameterizedTypeReference<?> responseType) {
        RestTemplate restTemplate = new RestTemplate();
        Collection<?> res = new ArrayList<>();

        try {
            ResponseEntity<?> responseEntity = restTemplate.exchange(url,
                    HttpMethod.GET, null, responseType);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                res = (Collection<?>) responseEntity.getBody();
            }
        } catch (final HttpClientErrorException httpClientErrorException) {
            throw new ExternalCallBadRequestException(httpClientErrorException.getMessage() + " for " + url + " with " + httpClientErrorException.getResponseBodyAsString());
        } catch (HttpServerErrorException httpServerErrorException) {
            throw new ExternalCallServerErrorException(httpServerErrorException.getMessage() + " for " + url + " with " + httpServerErrorException.getResponseBodyAsString());
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        return res;
    }

    private JsonAgentBehaviourModel createJsonRequest(String agentName, String behaviourName, boolean startNow, boolean removeForever){
        JsonAgentBehaviourModel jsonRequest = new JsonAgentBehaviourModel();
        if (agentName != null) jsonRequest.setAgentName(agentName);
        if (behaviourName != null) jsonRequest.setBehaviourName(behaviourName);
        jsonRequest.setStartNow(startNow);
        jsonRequest.setForever(removeForever);
        return jsonRequest;
    }

    private JsonAgentBehaviourModel createJsonRequest(String agentName, String behaviourName, boolean startNow){
        return createJsonRequest(agentName, behaviourName, startNow, false);
    }

    private JsonAgentBehaviourModel createJsonRequest(String agentName, String behaviourName){
        return createJsonRequest(agentName, behaviourName, false, false);
    }

    private JsonAgentBehaviourModel createJsonRequest(String agentName){
        return createJsonRequest(agentName, null, false, false);
    }

    private boolean sendJsonRequestToAPI (String url, JsonAgentBehaviourModel jsonRequest){
        return sendJsonRequestToAPI(url, HttpMethod.POST, jsonRequest);
    }

    private boolean sendJsonRequestToAPI(String url, HttpMethod httpMethod, JsonAgentBehaviourModel jsonRequest) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<JsonAgentBehaviourModel> entity = new HttpEntity<>(jsonRequest, headers);

        try {
            ResponseEntity<JsonResponseMessage> agentResponse =
                    restTemplate.exchange(url,
                            httpMethod, entity, JsonResponseMessage.class);
//                            HttpMethod.POST, entity, JsonResponseMessage.class);

            if (agentResponse.getStatusCode() == HttpStatus.OK) {
                //todo do something. give information to the user
                System.out.println(agentResponse.getBody().getMessage());
            }
        } catch (final HttpClientErrorException httpClientErrorException) {
            //the agent was already running for init, or was already stopped for stop and restart,but it is not actually a problem.
            // When we manage the facelets buttons it will be unable to click, so this sentence is only for robustness
            if (httpClientErrorException.getStatusCode() == HttpStatus.CONFLICT){
                return true;
            }else {
                throw new ExternalCallBadRequestException(httpClientErrorException.getMessage() + " for " + url + " with " + httpClientErrorException.getResponseBodyAsString());
            }
        } catch (HttpServerErrorException httpServerErrorException) {
            throw new ExternalCallServerErrorException(httpServerErrorException.getMessage() + " for " + url + " with " + httpServerErrorException.getResponseBodyAsString());
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            //connection refused for example
            return false;
        }

        return true;
    }
}
