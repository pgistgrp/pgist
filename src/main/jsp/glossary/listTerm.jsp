<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://www.pgist.org/jsf/components" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>Glossary Management</title>
<link rel=stylesheet href="<%= request.getContextPath() %>/styles/default.css" type="text/css" media=all>
<script src="<%= request.getContextPath() %>/scripts/pgist-util.js" type="text/javascript"></script>
</head>

<body>

<f:view>
<h:form id="glossaryAdminForm">

  <pg:listTable id="glossaryList" width="100%" rowSize="10" pageSize="10" binding="#{GlossaryBean.data}"
                actionBinding="#{GlossaryBean.listTerm}" pageSetting="#{GlossaryBean.pageSetting}"
                value="#{GlossaryBean.terms}" var="term">

    <f:facet name="title"><h:outputText value="Terms List"/></f:facet>
    
    <f:facet name="toolbar">
      <pg:toolbar id="toolbar" styleClass="toolbar">
        <pg:filter id="filter" styleClass="filterPanel" style="width:300; height:200;">
          <h:outputText value="Categories:"/>
          <pg:multiSelect id="categoryFilter" columns="1" styleClass="selectManyCheckbox"
              key="id" label="name" value="#{GlossaryBean.categoryFilter}"
              universalSet="#{GlossaryBean.allCategories}" subSet="#{GlossaryBean.categories}"/>
          <h:commandLink id="filterTerm" action="listTerm" value="Submit"/>
        </pg:filter>
        <pg:toolButton id="addTerm" action="#{GlossaryBean.addTerm}" value="Add" />
        <pg:toolButton id="delTerms" actionListener="#{GlossaryBean.delTerms}" confirm="Are you sure to delete terms?" value="Delete" />
      </pg:toolbar>
    </f:facet>

    <f:facet name="topScroller">
      <pg:scroller id="scroller1" align="left" styleClass="myclass" infoType="row" showPageGo="true" showRowsOfPage="true"/>
    </f:facet>
    
    <f:facet name="bottomScroller">
      <pg:scroller id="scroller2" align="right" styleClass="myclass" infoType="row" showPageGo="true" showRowsOfPage="true"/>
    </f:facet>

    <h:column>
      <f:facet name="header">
        <h:outputText value="Term Name"/>
      </f:facet>
      <h:inputHidden id="objectId" binding="#{GlossaryBean.objectId}" value="#{term.id}"/>
      <h:commandLink id="editTerm" action="#{GlossaryBean.editTerm}" value="#{term.name}" />
    </h:column>
    
    <h:column>
      <f:facet name="header">
        <h:outputText value="Category"/>
      </f:facet>
      <h:outputText value="#{term.categoryList}"/>
    </h:column>
    
    <h:column>
      <f:facet name="header">
        <h:outputText value="Short Definition"/>
      </f:facet>
      <h:outputText value="#{term.shortDefinition}"/>
    </h:column>
    
    <h:column>
      <f:facet name="header">
        <h:outputText value="Selection"/>
      </f:facet>
      <h:selectBooleanCheckbox id="checked" rendered="#{!term.internal}" binding="#{GlossaryBean.checked}"/>
    </h:column>
    
  </pg:listTable>

</h:form>
</f:view>

</body>
</html>

