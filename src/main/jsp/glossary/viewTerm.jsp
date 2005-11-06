<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://www.pgist.org/jsf/components" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>View Term</title>
<link rel=stylesheet href='<%= request.getContextPath()%>/styles/default.css' type="text/css" media=all>
</head>

<body>

<f:view>
<h:form id="TermViewForm">
  <h:outputText value="View Term"/>
  
  <h:inputHidden id="id" value="#{GlossaryBean.term.id}"/>

  <h:panelGrid columns="2">
    
    <h:outputLabel value="Term Name: "/>
    <h:outputText id="name" value="#{GlossaryBean.term.name}"/>
    
    <h:outputLabel value="Category: "/>
    <pg:showList id="category" value="#{GlossaryBean.term.categories}" field="name"/>
    
    <h:outputLabel value="Short Definition: "/>
    <h:outputText id="shortDefinition" value="#{GlossaryBean.term.shortDefinition}"/>
    
    <h:outputLabel value="Extended Definition: "/>
    <h:inputTextarea id="extDefinition" rows="10" cols="50" readonly="true" value="#{GlossaryBean.term.extDefinition}"/>
    
    <h:outputLabel value="Sources of Definition: "/>
    <pg:showList id="source" value="#{GlossaryBean.term.sources}" field="source"/>
    
    <h:outputLabel value="Realted Terms: "/>
    <pg:showList id="relterm" value="#{GlossaryBean.term.relatedTerms}" field="name"/>
    
    <h:outputLabel value="Links: "/>
    <pg:showList id="link" value="#{GlossaryBean.term.links}" field="link"/>
    
  </h:panelGrid>

  <input type="button" value="Back" onClick="history.go(-1); return null;"/>
  
</h:form>
</f:view>

</body>
</html>

