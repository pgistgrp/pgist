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
  
  <h:inputHidden id="id" value="#{UserBean.userId}"/>
  
  <h:panelGrid columns="3">
    <h:outputLabel value="Login Name: " for="loginname"/>
    <h:inputText id="loginname" value="#{UserBean.user.loginname}" required="true"/>
      <h:message for="loginname"/>
    <h:outputLabel value="Password: " for="password"/>
    <pg:optionalInput id="password" secret="true" value="#{UserBean.user.password}" required="true"/>
      <h:message for="password"/>
    <h:outputLabel value="First Name: " for="firstname"/>
    <h:inputText id="firstname" value="#{UserBean.user.firstname}" required="true"/>
      <h:message for="firstname"/>
    <h:outputLabel value="Last Name: " for="lastname"/>
    <h:inputText id="lastname" value="#{UserBean.user.lastname}" required="true"/>
      <h:message for="lastname"/>
    <h:outputLabel value="Email: " for="email"/>
    <h:inputText id="email" value="#{UserBean.user.email}" required="true"/>
      <h:message for="email"/>
    <h:outputLabel value="Roles: " />
    <pg:multiSelect id="roles" columns="4" styleClass="selectManyCheckbox"
        key="id" label="name" value="#{UserBean.selectedRoles}"
        universalSet="#{UserBean.roles}" subSet="#{UserBean.user.roles}"/>
  </h:panelGrid>

  <h:commandButton value="Commit" action="#{UserBean.updateUser}" type="submit"/>
  
</h:form>
</f:view>

</body>
</html>

