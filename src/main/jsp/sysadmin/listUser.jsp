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
<h:form id="userAdminForm">

  <pg:listTable id="userList" width="80%" rowSize="10" pageSize="10" binding="#{UserBean.data}"
                actionBinding="#{UserBean.listUser}" pageSetting="#{UserBean.pageSetting}"
                value="#{UserBean.users}" var="user">

    <f:facet name="title"><h:outputText value="User List"/></f:facet>
    
    <f:facet name="toolbar">
      <pg:show forRole="admin">
        <pg:toolbar id="toolbar" styleClass="toolbar">
          <pg:toolButton id="addUser" action="#{UserBean.addUser}" value="Add" />
          <pg:toolButton id="delUsers" actionListener="#{UserBean.delUsers}" confirm="Are you sure to delete user?" value="Delete" />
          <pg:toolButton id="enableUsers" actionListener="#{UserBean.enableUsers}" confirm="Are you sure to enable user?" value="Enable" />
          <pg:toolButton id="disableUsers" actionListener="#{UserBean.disableUsers}" confirm="Are you sure to disable user?" value="Disable" />
        </pg:toolbar>
      </pg:show>
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
        <h:outputText value="Login Name"/>
      </f:facet>
      <pg:show forRole="admin">
        <h:inputHidden id="objectId" binding="#{UserBean.objectId}" value="#{user.id}"/>
        <h:commandLink action="#{UserBean.editUser}" value="#{user.loginname}" />
      </pg:show>
      <pg:hide forRole="admin">
        <h:outputText value="#{user.loginname}"/>
      </pg:hide>
    </h:column>
    
    <h:column>
      <f:facet name="header">
        <h:outputText value="Roles"/>
      </f:facet>
      <h:outputText value="#{user.roleString}"/>
    </h:column>
    
    <h:column>
      <f:facet name="header">
        <h:outputText value="Status"/>
      </f:facet>
      <h:outputText rendered="#{user.enabled}" value="Enabled" />
      <h:outputText rendered="#{!user.enabled}" style="color:red;" value="Disabled" />
    </h:column>
    
    <h:column id="selection">
      <pg:show forRole="admin" for="selection">
        <f:facet name="header">
          <h:outputText value="Selection"/>
        </f:facet>
        <h:selectBooleanCheckbox id="checked" rendered="#{!user.internal}" binding="#{UserBean.checked}"/>
      </pg:show>
    </h:column>
    
  </pg:listTable>

</h:form>
</f:view>

</body>
</html>

