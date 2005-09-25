<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://www.pgist.org/jsf/components" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>Add New Term</title>
<link rel=stylesheet href='<%= request.getContextPath()%>/styles/default.css' type="text/css" media=all>
<script type='text/javascript' src='<%= request.getContextPath()%>/dwr/interface/AjaxGlossary.js'></script>
<script type='text/javascript' src='<%= request.getContextPath()%>/scripts/pgist-util.js'></script>
<script type='text/javascript' src='<%= request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%= request.getContextPath()%>/dwr/util.js'></script>
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
    <pg:multiSelect id="category" columns="1" styleClass="selectManyCheckbox"
        key="id" label="name" value="#{GlossaryBean.selectedCategories}"
        universalSet="#{GlossaryBean.allCategories}" subSet="#{GlossaryBean.term.categories}"/>
      <h:message for="category"/>
    <h:outputLabel value="Short Definition: " for="shortDefinition"/>
    <h:inputTextarea id="shortDefinition" rows="2" cols="50" value="#{GlossaryBean.term.shortDefinition}"/>
      <h:message for="shortDefinition"/>
    <h:outputLabel value="Extended Definition: " for="extDefinition"/>
    <h:inputTextarea id="extDefinition" rows="10" cols="50" value="#{GlossaryBean.term.extDefinition}" required="true"/>
      <h:message for="extDefinition"/>
    <h:outputLabel value="Sources of Definition: "/>
    <pg:multiInput id="source" value="#{GlossaryBean.sources}" initValue="#{GlossaryBean.term.sources}"/>
      <h:message for="source"/>
    <h:outputLabel value="Realted Terms: "/>
    <pg:ajaxSelect id="relatedTerms" value="#{GlossaryBean.relatedTerms}" ajax="AjaxGlossary.getTermList"
      initValue="#{GlossaryBean.term.relatedTerms}" key="id" label="name"/>
      <h:message for="relatedTerms"/>
    <h:outputLabel value="Links: "/>
    <pg:multiInput id="link" value="#{GlossaryBean.links}" initValue="#{GlossaryBean.term.links}"/>
      <h:message for="link"/>
  </h:panelGrid>

  <h:commandButton value="Commit" action="#{GlossaryBean.saveTerm}" type="submit"/>
  
</h:form>
</f:view>

</body>
</html>

