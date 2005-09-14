<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://www.pgist.org/jsf/components" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>Add New Glossary Category</title>
<link rel=stylesheet href='<%= request.getContextPath() +"/styles/default.css"%>' type="text/css" media=all>
</head>

<body>

<f:view>
<h:form id="CategoryAddForm">
  <h:outputText value="Add New Category"/>
  
  <h:panelGrid columns="3">
    <h:outputLabel value="Category Name: " for="name"/>
    <h:inputText id="name" value="#{CategoryBean.category.name}" required="true"/>
      <h:message for="name"/>
    <h:outputLabel value="Description: " for="description"/>
    <h:inputText id="description" value="#{CategoryBean.category.description}" required="false"/>
      <h:message for="description"/>
  </h:panelGrid>

  <h:commandButton value="Commit" action="#{CategoryBean.saveCategory}" type="submit"/>
  
</h:form>
</f:view>

</body>
</html>

