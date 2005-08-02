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
    <h:inputText id="loginname" value="#{UserBean.user.loginname}"/>
  </h:panelGrid>

  <h:commandButton value="Commit" action="#{UserBean.addUser}" type="submit"/>
  
</h:form>
</f:view>

</body>
</html>

