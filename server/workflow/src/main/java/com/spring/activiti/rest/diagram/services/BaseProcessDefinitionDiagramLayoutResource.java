/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.spring.activiti.rest.diagram.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.bpmn.behavior.BoundaryEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.CallActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.ErrorEventDefinition;
import org.activiti.engine.impl.bpmn.parser.EventSubscriptionDeclaration;
import org.activiti.engine.impl.jobexecutor.TimerDeclarationImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author dell
 */
public class BaseProcessDefinitionDiagramLayoutResource {

  @Autowired
  private RuntimeService runtimeService;
  
  @Autowired
  private RepositoryService repositoryService;
  
  @Autowired
  private HistoryService historyService;
  
  public ObjectNode getDiagramNode(String processInstanceId, String processDefinitionId) {
    
    List<String> highLightedFlows = Collections.emptyList();
    List<String> highLightedActivities = Collections.emptyList();

    Map<String, ObjectNode> subProcessInstanceMap = new HashMap<String, ObjectNode>(16);
    
    ProcessInstance processInstance = null;
    if (processInstanceId != null) {
      processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
      if (processInstance == null) {
        throw new ActivitiObjectNotFoundException("Process instance could not be found");
      }
      processDefinitionId = processInstance.getProcessDefinitionId();

      List<ProcessInstance> subProcessInstances = runtimeService
          .createProcessInstanceQuery()
          .superProcessInstanceId(processInstanceId).list();
      
      for (ProcessInstance subProcessInstance : subProcessInstances) {
        String subDefId = subProcessInstance.getProcessDefinitionId();

        String superExecutionId = subProcessInstance
            .getSuperExecutionId();
        ProcessDefinitionEntity subDef = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(subDefId);

        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("processInstanceId", subProcessInstance.getId());
        objectNode.put("superExecutionId", superExecutionId);
        objectNode.put("processDefinitionId", subDef.getId());
        objectNode.put("processDefinitionKey", subDef.getKey());
        objectNode.put("processDefinitionName", subDef.getName());

        subProcessInstanceMap.put(superExecutionId, objectNode);
      }
    }

    if (processDefinitionId == null) {
      throw new ActivitiObjectNotFoundException("No process definition id provided");
    }

    ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);

    if (processDefinition == null) {
      throw new ActivitiException("Process definition " + processDefinitionId + " could not be found");
    }

    ObjectNode nodes = new ObjectMapper().createObjectNode();

    // Process definition
    JsonNode response = getProcessDefinitionResponse(processDefinition);

    if (response != null) {
      nodes.put("processDefinition", response);
    }

    // Highlighted activities
    if (processInstance != null) {
      ArrayNode activityArray = new ObjectMapper().createArrayNode();
      ArrayNode flowsArray = new ObjectMapper().createArrayNode();

      highLightedActivities = runtimeService.getActiveActivityIds(processInstanceId);
      highLightedFlows = getHighLightedFlows(processInstanceId, processDefinition);

      for (String activityName : highLightedActivities) {
        activityArray.add(activityName);
      }

      for (String flow : highLightedFlows) {
        flowsArray.add(flow);
      }

      nodes.put("highLightedActivities", activityArray);
      nodes.put("highLightedFlows", flowsArray);
    }

    // Pool shape, if process is participant in collaboration
    if (processDefinition.getParticipantProcess() != null) {
      ParticipantProcess pProc = processDefinition.getParticipantProcess();

      ObjectNode nodes1 = new ObjectMapper().createObjectNode();
      nodes1.put("id", pProc.getId());
      if (StringUtils.isNotEmpty(pProc.getName())) {
        nodes1.put("name", pProc.getName());
      } else {
        nodes1.put("name", "");
      }
      nodes1.put("x", pProc.getX());
      nodes1.put("y", pProc.getY());
      nodes1.put("width", pProc.getWidth());
      nodes1.put("height", pProc.getHeight());

      nodes.put("participantProcess", nodes1);
    }

    // Draw lanes

    if (processDefinition.getLaneSets() != null && !processDefinition.getLaneSets().isEmpty()) {
      ArrayNode laneSetArray = new ObjectMapper().createArrayNode();
      for (LaneSet laneSet : processDefinition.getLaneSets()) {
        ArrayNode laneArray = new ObjectMapper().createArrayNode();
        if (laneSet.getLanes() != null && !laneSet.getLanes().isEmpty()) {
          for (Lane lane : laneSet.getLanes()) {
            ObjectNode node = new ObjectMapper().createObjectNode();
            node.put("id", lane.getId());
            if (StringUtils.isNotEmpty(lane.getName())) {
              node.put("name", lane.getName());
            } else {
              node.put("name", "");
            }
            node.put("x", lane.getX());
            node.put("y", lane.getY());
            node.put("width", lane.getWidth());
            node.put("height", lane.getHeight());

            List<String> flowNodeIds = lane.getFlowNodeIds();
            ArrayNode flowNodeIdsArray = new ObjectMapper().createArrayNode();
            for (String flowNodeId : flowNodeIds) {
              flowNodeIdsArray.add(flowNodeId);
            }
            node.put("flowNodeIds", flowNodeIdsArray);

            laneArray.add(node);
          }
        }
        ObjectNode laneSetJson = new ObjectMapper().createObjectNode();
        laneSetJson.put("id", laneSet.getId());
        if (StringUtils.isNotEmpty(laneSet.getName())) {
          laneSetJson.put("name", laneSet.getName());
        } else {
          laneSetJson.put("name", "");
        }
        laneSetJson.put("lanes", laneArray);

        laneSetArray.add(laneSetJson);
      }

      if (laneSetArray.size() > 0) {
        nodes.put("laneSets", laneSetArray);
      }
    }

    ArrayNode sequenceFlowArray = new ObjectMapper().createArrayNode();
    ArrayNode activityArray = new ObjectMapper().createArrayNode();

    // Activities and their sequence-flows

    for (ActivityImpl activity : processDefinition.getActivities()) {
      getActivity(processInstanceId, activity, activityArray, sequenceFlowArray, 
          processInstance, highLightedFlows, subProcessInstanceMap);
    }

    nodes.put("activities", activityArray);
    nodes.put("sequenceFlows", sequenceFlowArray);

    return nodes;
  }

  private List<String> getHighLightedFlows(String processInstanceId, ProcessDefinitionEntity processDefinition) {
    
    List<String> highLightedFlows = new ArrayList<String>();
    List<HistoricActivityInstance> historicActivityInstances = historyService
        .createHistoricActivityInstanceQuery()
        .processInstanceId(processInstanceId)
        .orderByHistoricActivityInstanceStartTime().asc().list();
    
    List<String> historicActivityInstanceList = new ArrayList<String>();
    for (HistoricActivityInstance hai : historicActivityInstances) {
      historicActivityInstanceList.add(hai.getActivityId());
    }

    // add current activities to list
    List<String> highLightedActivities = runtimeService.getActiveActivityIds(processInstanceId);
    historicActivityInstanceList.addAll(highLightedActivities);

    // activities and their sequence-flows
    for (ActivityImpl activity : processDefinition.getActivities()) {
      int index = historicActivityInstanceList.indexOf(activity.getId());

      if (index >= 0 && index + 1 < historicActivityInstanceList.size()) {
        List<PvmTransition> pvmTransitionList = activity
            .getOutgoingTransitions();
        for (PvmTransition pvmTransition : pvmTransitionList) {
          String destinationFlowId = pvmTransition.getDestination().getId();
          if (destinationFlowId.equals(historicActivityInstanceList.get(index + 1))) {
            highLightedFlows.add(pvmTransition.getId());
          }
        }
      }
    }
    return highLightedFlows;
  }

  private void getActivity(String processInstanceId, ActivityImpl activity, ArrayNode activityArray,
                           ArrayNode sequenceFlowArray, ProcessInstance processInstance, List<String> highLightedFlows,
                           Map<String, ObjectNode> subProcessInstanceMap) {

    ObjectNode activityJson = new ObjectMapper().createObjectNode();
    String str="sequential";
    String multiInstance = (String) activity.getProperty("multiInstance");
    if (multiInstance != null) {
      if (!str.equals(multiInstance)) {
        multiInstance = "parallel";
      }
    }

    ActivityBehavior activityBehavior = activity.getActivityBehavior();
    // Gather info on the collapsed marker
    Boolean collapsed = (activityBehavior instanceof CallActivityBehavior);
    Boolean expanded = (Boolean) activity.getProperty(BpmnParse.PROPERTYNAME_ISEXPANDED);
    if (expanded != null) {
      collapsed = !expanded;
    }

    Boolean isInterrupting = null;
    if (activityBehavior instanceof BoundaryEventActivityBehavior) {
      isInterrupting = ((BoundaryEventActivityBehavior) activityBehavior).isInterrupting();
    }

    // Outgoing transitions of activity
    for (PvmTransition sequenceFlow : activity.getOutgoingTransitions()) {
      String flowName = (String) sequenceFlow.getProperty("name");
      boolean isHighLighted = (highLightedFlows.contains(sequenceFlow.getId()));
      boolean isConditional = sequenceFlow.getProperty(BpmnParse.PROPERTYNAME_CONDITION) != null && 
          !((String) activity.getProperty("type")).toLowerCase().contains("gateway");
      boolean isDefault = sequenceFlow.getId().equals(activity.getProperty("default"))
          && ((String) activity.getProperty("type")).toLowerCase().contains("gateway");

      List<Integer> waypoints = ((TransitionImpl) sequenceFlow).getWaypoints();
      ArrayNode xPointArray = new ObjectMapper().createArrayNode();
      ArrayNode yPointArray = new ObjectMapper().createArrayNode();
      Integer integer=2;
      for (int i = 0; i < waypoints.size(); i += integer) {

        xPointArray.add(waypoints.get(i));
        yPointArray.add(waypoints.get(i + 1));
      }

      ObjectNode flowJson = new ObjectMapper().createObjectNode();
      flowJson.put("id", sequenceFlow.getId());
      flowJson.put("name", flowName);
      flowJson.put("flow", "(" + sequenceFlow.getSource().getId() + ")--"
          + sequenceFlow.getId() + "-->("
          + sequenceFlow.getDestination().getId() + ")");

      if (isConditional) {
        flowJson.put("isConditional", isConditional);
      }
      if (isDefault) {
        flowJson.put("isDefault", isDefault);
      }
      if (isHighLighted) {
        flowJson.put("isHighLighted", isHighLighted);
      }

      flowJson.put("xPointArray", xPointArray);
      flowJson.put("yPointArray", yPointArray);

      sequenceFlowArray.add(flowJson);
    }

    // Nested activities (boundary events)
    ArrayNode nestedActivityArray = new ObjectMapper().createArrayNode();
    for (ActivityImpl nestedActivity : activity.getActivities()) {
      nestedActivityArray.add(nestedActivity.getId());
    }

    Map<String, Object> properties = activity.getProperties();
    ObjectNode propertiesJson = new ObjectMapper().createObjectNode();
    for (String key : properties.keySet()) {
      Object prop = properties.get(key);
      if (prop instanceof String) {
        propertiesJson.put(key, (String) properties.get(key));
      } else if (prop instanceof Integer) {
        propertiesJson.put(key, (Integer) properties.get(key));
      } else if (prop instanceof Boolean) {
        propertiesJson.put(key, (Boolean) properties.get(key));
      } else if ("initial".equals(key)) {
        ActivityImpl act = (ActivityImpl) properties.get(key);
        propertiesJson.put(key, act.getId());
      } else if ("timerDeclarations".equals(key)) {
        ArrayList<TimerDeclarationImpl> timerDeclarations = (ArrayList<TimerDeclarationImpl>) properties.get(key);
        ArrayNode timerDeclarationArray = new ObjectMapper().createArrayNode();

        if (timerDeclarations != null) {
          for (TimerDeclarationImpl timerDeclaration : timerDeclarations) {
            ObjectNode timerDeclarationJson = new ObjectMapper().createObjectNode();

            timerDeclarationJson.put("isExclusive", timerDeclaration.isExclusive());
            if (timerDeclaration.getRepeat() != null) {
              timerDeclarationJson.put("repeat", timerDeclaration.getRepeat());
            }

            timerDeclarationJson.put("retries", String.valueOf(timerDeclaration.getRetries()));
            timerDeclarationJson.put("type", timerDeclaration.getJobHandlerType());
            timerDeclarationJson.put("configuration", timerDeclaration.getJobHandlerConfiguration());
            timerDeclarationArray.add(timerDeclarationJson);
          }
        }
        if (timerDeclarationArray.size() > 0) {
          propertiesJson.put(key, timerDeclarationArray);
        }
        // TODO: implement getting description
      } else if ("eventDefinitions".equals(key)) {
        ArrayList<EventSubscriptionDeclaration> eventDefinitions = (ArrayList<EventSubscriptionDeclaration>) properties.get(key);
        ArrayNode eventDefinitionsArray = new ObjectMapper().createArrayNode();

        if (eventDefinitions != null) {
          for (EventSubscriptionDeclaration eventDefinition : eventDefinitions) {
            ObjectNode eventDefinitionJson = new ObjectMapper().createObjectNode();

            if (eventDefinition.getActivityId() != null) {
              eventDefinitionJson.put("activityId",eventDefinition.getActivityId());
            }

            eventDefinitionJson.put("eventName", eventDefinition.getEventName());
            eventDefinitionJson.put("eventType", eventDefinition.getEventType());
            eventDefinitionJson.put("isAsync", eventDefinition.isAsync());
            eventDefinitionJson.put("isStartEvent", eventDefinition.isStartEvent());
            eventDefinitionsArray.add(eventDefinitionJson);
          }
        }

        if (eventDefinitionsArray.size() > 0) {
          propertiesJson.put(key, eventDefinitionsArray);
        }
        
      // TODO: implement it
      } else if ("errorEventDefinitions".equals(key)) {
        ArrayList<ErrorEventDefinition> errorEventDefinitions = (ArrayList<ErrorEventDefinition>) properties.get(key);
        ArrayNode errorEventDefinitionsArray = new ObjectMapper().createArrayNode();

        if (errorEventDefinitions != null) {
          for (ErrorEventDefinition errorEventDefinition : errorEventDefinitions) {
            ObjectNode errorEventDefinitionJson = new ObjectMapper().createObjectNode();

            if (errorEventDefinition.getErrorCode() != null) {
              errorEventDefinitionJson.put("errorCode", errorEventDefinition.getErrorCode());
            } else {
              errorEventDefinitionJson.putNull("errorCode");
            }

            errorEventDefinitionJson.put("handlerActivityId",
            errorEventDefinition.getHandlerActivityId());

            errorEventDefinitionsArray.add(errorEventDefinitionJson);
          }
        }

        if (errorEventDefinitionsArray.size() > 0) {
          propertiesJson.put(key, errorEventDefinitionsArray);
        }
      }

    }
    String string="callActivity";
    String strings="type";
    if (string.equals(properties.get(strings))) {
      CallActivityBehavior callActivityBehavior = null;

      if (activityBehavior instanceof CallActivityBehavior) {
        callActivityBehavior = (CallActivityBehavior) activityBehavior;
      }

      if (callActivityBehavior != null) {
        propertiesJson.put("processDefinitonKey", callActivityBehavior.getProcessDefinitonKey());

        // get processDefinitonId from execution or get last processDefinitonId
        // by key
        ArrayNode processInstanceArray = new ObjectMapper().createArrayNode();
        if (processInstance != null) {
          List<Execution> executionList = runtimeService.createExecutionQuery()
              .processInstanceId(processInstanceId)
              .activityId(activity.getId()).list();
          if (!executionList.isEmpty()) {
            for (Execution execution : executionList) {
              ObjectNode processInstanceJson = subProcessInstanceMap.get(execution.getId());
              processInstanceArray.add(processInstanceJson);
            }
          }
        }

        // If active activities nas no instance of this callActivity then add
        // last definition
        if (processInstanceArray.size() == 0 && StringUtils.isNotEmpty(callActivityBehavior.getProcessDefinitonKey())) {
          // Get last definition by key
          ProcessDefinition lastProcessDefinition = repositoryService
              .createProcessDefinitionQuery()
              .processDefinitionKey(callActivityBehavior.getProcessDefinitonKey())
              .latestVersion().singleResult();

          // TODO: unuseful fields there are processDefinitionName, processDefinitionKey
          if (lastProcessDefinition != null) {
            ObjectNode processInstanceJson = new ObjectMapper().createObjectNode();
            processInstanceJson.put("processDefinitionId", lastProcessDefinition.getId());
            processInstanceJson.put("processDefinitionKey", lastProcessDefinition.getKey());
            processInstanceJson.put("processDefinitionName", lastProcessDefinition.getName());
            processInstanceArray.add(processInstanceJson);
          }
        }

        if (processInstanceArray.size() > 0) {
          propertiesJson.put("processDefinitons", processInstanceArray);
        }
      }
    }

    activityJson.put("activityId", activity.getId());
    activityJson.put("properties", propertiesJson);
    if (multiInstance != null) {
      activityJson.put("multiInstance", multiInstance);
    }
    if (collapsed) {
      activityJson.put("collapsed", collapsed);
    }
    if (nestedActivityArray.size() > 0) {
      activityJson.put("nestedActivities", nestedActivityArray);
    }
    if (isInterrupting != null) {
      activityJson.put("isInterrupting", isInterrupting);
    }

    activityJson.put("x", activity.getX());
    activityJson.put("y", activity.getY());
    activityJson.put("width", activity.getWidth());
    activityJson.put("height", activity.getHeight());

    activityArray.add(activityJson);

    // Nested activities (boundary events)
    for (ActivityImpl nestedActivity : activity.getActivities()) {
      getActivity(processInstanceId, nestedActivity, activityArray, sequenceFlowArray, 
          processInstance, highLightedFlows, subProcessInstanceMap);
    }
  }

  private JsonNode getProcessDefinitionResponse(ProcessDefinitionEntity processDefinition) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode pdrJson = mapper.createObjectNode();
    pdrJson.put("id", processDefinition.getId());
    pdrJson.put("name", processDefinition.getName());
    pdrJson.put("key", processDefinition.getKey());
    pdrJson.put("version", processDefinition.getVersion());
    pdrJson.put("deploymentId", processDefinition.getDeploymentId());
    pdrJson.put("isGraphicNotationDefined", isGraphicNotationDefined(processDefinition));
    return pdrJson;
  }

  private boolean isGraphicNotationDefined(ProcessDefinitionEntity processDefinition) {
    return ((ProcessDefinitionEntity) repositoryService
        .getProcessDefinition(processDefinition.getId()))
        .isGraphicalNotationDefined();
  }
}
