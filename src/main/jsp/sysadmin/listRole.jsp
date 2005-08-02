<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://www.pgist.org/jsf/components" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>Role Management</title>
<link rel=stylesheet href='<%= request.getContextPath() +"/styles/default.css"%>' type="text/css" media=all>
</head>

<body>

<f:view>
<h:form id="roleAdminForm">

  <pg:listTable id="roleList" width="80%" rowSize="10" pageSize="10" binding="#{RoleBean.data}"
                actionBinding="#{RoleBean.listRole}" pageSetting="#{RoleBean.pageSetting}"
                value="#{RoleBean.roles}" var="role">

    <f:facet name="title"><h:outputText value="Role List"/></f:facet>
    
    <f:facet name="toolbar">
      <pg:toolbar styleClass="toolbar">
        <h:commandLink id="addRole" action="addRole" value="Add Role" />
        <h:commandLink id="delRoles"  actionListener="#{RoleBean.delRoles}" value="Delete Roles" />
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
        <h:outputText value="Role Name"/>
      </f:facet>
      <h:outputText value="#{role.name}"/>
      <!--h:inputHidden id="roleId" binding="#{RoleBean.objectId}" value="#{role.id}"/-->
    </h:column>
    
    <h:column>
      <f:facet name="header">
        <h:outputText value="Description"/>
      </f:facet>
      <h:outputText value="#{role.description}" />
    </h:column>
    
    <h:column>
      <f:facet name="header">
        <h:outputText value="Selection"/>
      </f:facet>
      <h:selectBooleanCheckbox id="checked" rendered="#{!role.internal}" binding="#{RoleBean.checked}"/>
    </h:column>
    
  </pg:listTable>

</h:form>
</f:view>

</body>
</html>

