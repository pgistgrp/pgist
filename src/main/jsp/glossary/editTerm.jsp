<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://www.pgist.org/jsf/components" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>Edit Term</title>
<link rel=stylesheet href='<%= request.getContextPath()%>/styles/default.css' type="text/css" media=all>
<script type='text/javascript' src='<%= request.getContextPath()%>/dwr/interface/AjaxGlossary.js'></script>
<script type='text/javascript' src='<%= request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%= request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript'>
  var reply0 = function(data) {
    //if (data != null && typeof data == 'object') alert(DWRUtil.toDescriptiveString(data, 2));
    //else DWRUtil.setValue('d0', DWRUtil.toDescriptiveString(data, 1));
    for (var i=0; i<data.length; i++) {
      var one = data[i];
      alert(one['id']+' --> '+one['name']);
    }//for i
  }
</script>
</head>

<body>

<f:view>
<h:form id="TermEditForm">
  <h:outputText value="Edit Term"/>
  
  <h:inputHidden id="id" value="#{GlossaryBean.term.id}"/>

  <h:panelGrid columns="3">
    <h:outputLabel value="Term Name: " for="name"/>
    <h:inputText id="name" value="#{GlossaryBean.term.name}" required="true"/>
      <h:message for="name"/>
    <h:outputLabel value="Category: " for="category"/>
    <pg:multiSelect id="category" columns="1" styleClass="selectManyCheckbox"
        key="id" label="name" value="#{GlossaryBean.selectedCategories}"
        universalSet="#{GlossaryBean.allCategories}" subSet="#{GlossaryBean.term.categories}"/>
      <h:message for="category"/>
    <h:outputLabel value="Short Definition: " for="shortDefinition"/>
    <h:inputTextarea id="shortDefinition" rows="2" cols="50" value="#{GlossaryBean.term.shortDefinition}" required="true"/>
      <h:message for="shortDefinition"/>
    <h:outputLabel value="Extended Definition: " for="extDefinition"/>
    <h:inputTextarea id="extDefinition" rows="10" cols="50" value="#{GlossaryBean.term.extDefinition}" required="true"/>
      <h:message for="extDefinition"/>
    <h:outputLabel value="Sources of Definition: " />
    <pg:multiInput id="source" value="#{GlossaryBean.sources}" initValue="#{GlossaryBean.term.sources}"/>
      <h:message for="source"/>
    <h:outputLabel value="Realted Terms: " />
    <pg:multiInput id="relatedTerm" value="#{GlossaryBean.relatedTerms}" initValue="#{GlossaryBean.term.relatedTerms}"/>
      <h:message for="relatedTerm"/>
    <h:outputLabel value="Links: " />
    <pg:multiInput id="link" value="#{GlossaryBean.links}" initValue="#{GlossaryBean.term.links}"/>
      <h:message for="link"/>
  </h:panelGrid>

  <input class='ibutton' type='button' onclick='AjaxGlossary.getTermList(reply0, 2);' value='Execute'  title='Calls AjaxGlossary.getTermList(). View source for details.'/>
  <h:commandButton value="Commit" action="#{GlossaryBean.saveTerm}" type="submit"/>
  
</h:form>
</f:view>

</body>
</html>

