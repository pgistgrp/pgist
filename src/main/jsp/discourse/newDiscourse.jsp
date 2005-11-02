<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://www.pgist.org/jsf/components" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>Create New Discourse</title>
<link rel=stylesheet href='<%= request.getContextPath()%>/styles/default.css' type="text/css" media=all>
</head>

<body>

<f:view>
<h:form id="NewDiscourseForm">
  <h:outputText value="Create New Discourse"/>
  
  <h:panelGrid columns="3">
    <h:outputLabel value="Title: " for="title"/>
    <h:inputText id="title" value="#{DiscourseBean.discourse.title}" required="true"/>
      <h:message for="title"/>
    <h:outputLabel value="Content: " for="content"/>
    <h:inputTextarea id="content" value="#{DiscourseBean.content}" rows="8" cols="40"/>
      <h:message for="content"/>
    <h:selectBooleanCheckbox title="emailRemind" value="#{DiscourseBean.emailRemind}"/>
    <h:outputText value="Would you like email reminding when someone reply you?"/>
      <h:message for="content"/>
  </h:panelGrid>

  <h:commandButton value="Create" action="#{DiscourseBean.saveNewDiscourse}" type="submit"/>
  
</h:form>
</f:view>

</body>
</html>

