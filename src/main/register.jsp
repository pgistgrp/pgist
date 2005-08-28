<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://www.pgist.org/jsf/components" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>User Register</title>
<link rel=stylesheet href='<%= request.getContextPath() +"/styles/default.css"%>' type="text/css" media=all>
</head>

<body>

<f:view>
<h:form id="userRegisterForm">
  <h:outputText value="Register User"/>
  
  <h:panelGrid columns="3">
    <h:outputLabel value="Login Name: " for="loginname"/>
    <h:inputText id="loginname" value="#{RegisterBean.user.loginname}" required="true"/>
      <h:message for="loginname"/>
    <h:outputLabel value="Password: " for="password"/>
    <h:inputSecret id="password" value="#{RegisterBean.user.password}" required="true"/>
      <h:message for="password"/>
    <h:outputLabel value="Email: " for="email"/>
    <h:inputText id="email" value="#{RegisterBean.user.email}" required="true"/>
      <h:message for="email"/>
  </h:panelGrid>

  <h:commandButton value="Register" action="#{RegisterBean.doRegisterUser}" type="submit"/>
  
</h:form>
</f:view>

</body>
</html>

