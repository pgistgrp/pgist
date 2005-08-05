<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://www.pgist.org/jsf/components" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>Conversation List</title>
<link rel=stylesheet href='<%= request.getContextPath() +"/styles/default.css"%>' type="text/css" media=all>
</head>

<body>

<f:view>
<h:form id="conversationListForm">

  <pg:listTable id="conversationList" width="80%" rowSize="10" pageSize="10" binding="#{ConversationBean.data}"
                actionBinding="#{ConversationBean.listConversation}" pageSetting="#{ConversationBean.pageSetting}"
                value="#{ConversationBean.conversations}" var="conversation">

    <f:facet name="title"><h:outputText value="Conversation List"/></f:facet>
    
    <f:facet name="toolbar">
      <pg:toolbar id="toolbar" styleClass="toolbar">
        <pg:toolButton id="newConversation" action="#{ConversationBean.newConversation}" value="New Thread" />
      </pg:toolbar>
    </f:facet>

    <f:facet name="topScroller">
      <pg:scroller id="scroller1" align="left" styleClass="myclass" infoType="row" showPageGo="true" showRowsOfPage="true"/>
    </f:facet>
    
    <f:facet name="bottomScroller">
      <pg:scroller id="scroller2" align="right" styleClass="myclass" infoType="row" showPageGo="true" showRowsOfPage="true"/>
    </f:facet>

    <f:facet name="filter">
      <!--pg:filter id="filter" /-->
    </f:facet>

    <h:column>
      <f:facet name="header">
        <h:outputText value="Thread"/>
      </f:facet>
      <h:inputHidden id="objectId" binding="#{ConversationBean.objectId}" value="#{user.id}"/>
      <h:commandLink id="readConversation" action="#{ConversationBean.readConversation}" value="#{conversation.root.title}" />
    </h:column>
    
    <h:column>
      <f:facet name="header">
        <h:outputText value="Owner"/>
      </f:facet>
      <h:outputText value="#{conversation.root.owner.loginname}"/>
    </h:column>
    
    <h:column>
      <f:facet name="header">
        <h:outputText value="Post Time"/>
      </f:facet>
      <h:outputText value="#{conversation.root.time}" />
    </h:column>
    
  </pg:listTable>

</h:form>
</f:view>

</body>
</html>

