<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://www.pgist.org/jsf/components" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>Edit Template</title>
<link rel=stylesheet href='<%= request.getContextPath() +"/styles/default.css"%>' type="text/css" media=all>
</head>

<body>

<f:view>
<h:form id="TemplateEditForm">
  <h:outputText value="Edit Template"/>
  
  <h:inputHidden id="id" value="#{EmailTemplateBean.template.id}"/>

  <h:panelGrid columns="3">
    <h:outputLabel value="Template Name: " for="name"/>
    <h:outputText id="name" value="#{EmailTemplateBean.template.name}" style="width:100%"/>
      <h:message for="name"/>
    <h:outputLabel value="Description: " for="description"/>
    <h:outputText id="description" value="#{EmailTemplateBean.template.description}"/>
      <h:message for="description"/>
    <h:outputLabel value="Notes: " for="notes"/>
    <h:outputText id="notes" value="#{EmailTemplateBean.template.notes}" style="color:blue;"/>
      <h:message for="notes"/>
    <h:outputLabel value="Template Content: " for="content"/>
    <h:inputTextarea id="content" rows="20" cols="80" value="#{EmailTemplateBean.template.content}" required="true"/>
      <h:message for="content"/>
  </h:panelGrid>

  <h:commandButton value="Save" action="#{EmailTemplateBean.saveTemplate}" type="submit"/>
  
</h:form>
</f:view>

</body>
</html>

