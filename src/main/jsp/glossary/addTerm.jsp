<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://www.pgist.org/jsf/components" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>Add New Term</title>
<link rel=stylesheet href='<%= request.getContextPath() +"/styles/default.css"%>' type="text/css" media=all>
</head>

<body>

<f:view>
<h:form id="TermAddForm">
  <h:outputText value="Add New Term"/>
  
  <h:panelGrid columns="1">
    <h:outputLabel value="Term Name: " for="name"/>
    <h:inputText id="name" value="#{GlossaryBean.term.name}" required="true"/>
      <h:message for="name"/>
    <h:outputLabel value="Category: " for="category"/>
    <h:selectOneMenu id="category" value="#{GlossaryBean.term.category}">
      <f:selectItems value="#{GlossaryBean.categories}" />
    </h:selectOneMenu>
      <h:message for="category"/>
    <h:outputLabel value="Short Definition: " for="shortDefinition"/>
    <h:inputTextarea id="shortDefinition" rows="2" cols="50" value="#{GlossaryBean.term.shortDefinition}" required="true"/>
      <h:message for="shortDefinition"/>
    <h:outputLabel value="Extended Definition: " for="extDefinition"/>
    <h:inputTextarea id="extDefinition" rows="10" cols="50" value="#{GlossaryBean.term.extDefinition}" required="true"/>
      <h:message for="extDefinition"/>
    <h:outputLabel value="Sources of Definition: " />
    <h:panelGrid columns="3">
      <h:outputLabel value="1." for="source1"/>
      <h:inputText id="source1" value="#{GlossaryBean.term.source1}"/>
        <h:message for="source1"/>
      <h:outputLabel value="2." for="source2"/>
      <h:inputText id="source2" value="#{GlossaryBean.term.source2}"/>
        <h:message for="source2"/>
      <h:outputLabel value="3." for="source3"/>
      <h:inputText id="source3" value="#{GlossaryBean.term.source3}"/>
        <h:message for="source3"/>
    </h:panelGrid>
    <h:outputLabel value="Related Terms: " />
    <h:panelGrid columns="3">
      <h:outputLabel value="1." for="relatedTerm1"/>
      <h:inputText id="relatedTerm1" value="#{GlossaryBean.term.relatedTerm1}"/>
        <h:message for="relatedTerm1"/>
      <h:outputLabel value="2." for="relatedTerm2"/>
      <h:inputText id="relatedTerm2" value="#{GlossaryBean.term.relatedTerm2}"/>
        <h:message for="relatedTerm2"/>
      <h:outputLabel value="3." for="relatedTerm3"/>
      <h:inputText id="relatedTerm3" value="#{GlossaryBean.term.relatedTerm3}"/>
        <h:message for="relatedTerm3"/>
      <h:outputLabel value="4." for="relatedTerm4"/>
      <h:inputText id="relatedTerm4" value="#{GlossaryBean.term.relatedTerm4}"/>
        <h:message for="relatedTerm4"/>
      <h:outputLabel value="5." for="relatedTerm5"/>
      <h:inputText id="relatedTerm5" value="#{GlossaryBean.term.relatedTerm5}"/>
        <h:message for="relatedTerm5"/>
    </h:panelGrid>
    <h:outputLabel value="Links: " />
    <h:panelGrid columns="3">
      <h:outputLabel value="1." for="link1"/>
      <h:inputText id="link1" value="#{GlossaryBean.term.link1}"/>
        <h:message for="link1"/>
      <h:outputLabel value="2." for="link2"/>
      <h:inputText id="link2" value="#{GlossaryBean.term.link2}"/>
        <h:message for="link2"/>
      <h:outputLabel value="3." for="link3"/>
      <h:inputText id="link3" value="#{GlossaryBean.term.link3}"/>
        <h:message for="link3"/>
    </h:panelGrid>
  </h:panelGrid>

  <h:commandButton value="Commit" action="#{GlossaryBean.saveTerm}" type="submit"/>
  
</h:form>
</f:view>

</body>
</html>

