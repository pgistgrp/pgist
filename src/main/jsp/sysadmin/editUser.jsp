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
  <h:outputText value="Edit User"/>
  
  <h:inputHidden id="id" value="#{UserBean.user.id}"/>
  <h:panelGrid columns="2">
    <h:outputText value="Login Name: " />
    <h:inputText value="#{UserBean.user.loginname}"/>
    <h:outputText value="Password: " />
    <h:inputSecret value="#{UserBean.user.password}"/>
    <h:outputText value="Email: " />
    <h:inputText value="#{UserBean.user.email}"/>
    <h:outputText value="Roles: " />
    <pg:multiSelect id="roles" columns="4" styleClass="selectManyCheckbox"
        key="id" label="name" value="#{UserBean.selectedRoles}"
        universalSet="#{UserBean.roles}" subSet="#{UserBean.user.roles}"/>
  </h:panelGrid>

  <h:commandButton value="Commit" action="#{UserBean.saveUser}" type="submit"/>
  
</h:form>
</f:view>

</body>
</html>

