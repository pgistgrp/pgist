<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://www.pgist.org/jsf/components" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>User Edit</title>
<link rel=stylesheet href='<%= request.getContextPath() +"/styles/default.css"%>' type="text/css" media=all>
</head>

<body>

<f:view>
<h:form id="userEditForm">

  <h:inputHidden id="id" value="#{UserBean.user.id}"/>
  <h:panelGrid columns="2">
    <h:outputText value="Login Name: " />
    <h:inputText value="#{UserBean.user.loginname}"/>
    <h:outputText value="Password: " />
    <h:inputSecret value="#{UserBean.user.password}"/>
    <h:outputText value="Email: " />
    <h:inputText value="#{UserBean.user.email}"/>
    <h:outputText value="Roles: " />
    <h:dataTable id="userRoles" binding="#{UserBean.data}" value="#{UserBean.roles}" var="role">
      <h:column>
        <h:selectBooleanCheckbox id="checked" rendered="true" binding="#{UserBean.checked}"/>
        <h:outputText id="name" value="#{role.name}"/>
      </h:column>
    </h:dataTable>
  </h:panelGrid>

  <h:commandButton value="Commit" action="#{UserBean.addUser}" type="submit"/>
  
</h:form>
</f:view>

</body>
</html>

