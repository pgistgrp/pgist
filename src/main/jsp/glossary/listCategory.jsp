<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://www.pgist.org/jsf/components" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>Glossary Category Management</title>
<link rel=stylesheet href='<%= request.getContextPath() +"/styles/default.css"%>' type="text/css" media=all>
</head>

<body>

<f:view>
<h:form id="glossaryCategoryAdminForm">

  <pg:listTable id="categoryList" width="100%" rowSize="10" pageSize="10" binding="#{CategoryBean.data}"
                actionBinding="#{CategoryBean.listCategory}" pageSetting="#{CategoryBean.pageSetting}"
                value="#{CategoryBean.categories}" var="category">

    <f:facet name="title"><h:outputText value="Category List"/></f:facet>
    
    <f:facet name="toolbar">
      <pg:toolbar id="toolbar" styleClass="toolbar">
        <pg:toolButton id="addCategory" action="#{CategoryBean.addCategory}" value="Add" />
        <pg:toolButton id="delCategories" actionListener="#{CategoryBean.delCategories}" confirm="Are you sure to delete categories?" value="Delete" />
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
        <h:outputText value="Category Name"/>
      </f:facet>
      <h:inputHidden id="objectId" binding="#{CategoryBean.objectId}" value="#{category.id}"/>
      <h:commandLink id="editCategory" action="#{CategoryBean.editCategory}" value="#{category.name}" />
    </h:column>
    
    <h:column>
      <f:facet name="header">
        <h:outputText value="Description"/>
      </f:facet>
      <h:outputText value="#{category.description}"/>
    </h:column>
    
    <h:column>
      <f:facet name="header">
        <h:outputText value="Selection"/>
      </f:facet>
      <h:selectBooleanCheckbox id="checked" rendered="#{!category.internal}" binding="#{CategoryBean.checked}"/>
    </h:column>
    
  </pg:listTable>

</h:form>
</f:view>

</body>
</html>

