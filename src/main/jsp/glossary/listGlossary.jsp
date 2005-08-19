<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://www.pgist.org/jsf/components" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>User Management</title>
<link rel=stylesheet href='<%= request.getContextPath() +"/styles/default.css"%>' type="text/css" media=all>
</head>

<body>

<f:view>
<h:form id="glossaryAdminForm">

  <pg:listTable id="glossaryList" width="80%" rowSize="10" pageSize="10" binding="#{GlossaryBean.data}"
                actionBinding="#{GlossaryBean.listGlossary}" pageSetting="#{GlossaryBean.pageSetting}"
                value="#{GlossaryBean.glossaries}" var="glossary">

    <f:facet name="title"><h:outputText value="Glossary List"/></f:facet>
    
    <f:facet name="toolbar">
      <pg:toolbar id="toolbar" styleClass="toolbar">
        <pg:toolButton id="addGlossary" action="#{GlossaryBean.addGlossary}" value="Add" />
        <pg:toolButton id="delGlossaries" actionListener="#{GlossaryBean.delGlossaries}" confirm="Are you sure to delete glossary?" value="Delete" />
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
        <h:outputText value="Glossary Name"/>
      </f:facet>
      <h:inputHidden id="objectId" binding="#{GlossaryBean.objectId}" value="#{glossary.id}"/>
      <h:commandLink id="editGlossary" action="#{GlossaryBean.editGlossary}" value="#{glossary.name}" />
    </h:column>
    
    <h:column>
      <f:facet name="header">
        <h:outputText value="Selection"/>
      </f:facet>
      <h:selectBooleanCheckbox id="checked" rendered="#{!glossary.internal}" binding="#{GlossaryBean.checked}"/>
    </h:column>
    
  </pg:listTable>

</h:form>
</f:view>

</body>
</html>

