<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>New Conversation</title>
<link rel=stylesheet href='<%= request.getContextPath() +"/styles/default.css"%>' type="text/css" media=all>
</head>

<body>

<f:view>
<h:form id="newConversation">

<h3>New Conversation</h3>

  <h:panelGrid columns="2">
    <h:outputText value="Title: " />
    <h:inputText value="#{ConversationBean.thread.title}"/>
    <h:outputText value="Content: " />
    <h:inputTextarea value="#{ConversationBean.thread.root.content}"/>
  </h:panelGrid>

  <h:commandButton value="Commit" action="#{ConversationBean.saveNewConversation}" type="submit"/>

</h:form>
</f:view>

</body>
</html>

