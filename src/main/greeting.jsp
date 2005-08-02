<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>PGIST main page</title>
</head>

<body bgcolor="white">

<f:view>
<h:form id="loginForm" >
  <h2>Welcome to PGIST.
  <table>
    <tr>
      <td>User Name:</td>
      <td><h:inputText id="loginname" value="#{LoginBean.loginname}" validator="#{LoginBean.validate}"/></td>
    </tr>
    <tr>
      <td>Password:</td>
      <td><h:inputSecret id="password" value="#{LoginBean.password}" validator="#{LoginBean.validate}"/></td>
    </tr>
    <tr>
      <td colspan="2">
        <h:commandButton id="submit" action="#{LoginBean.login}" value="Login" />
        &nbsp;&nbsp;&nbsp;&nbsp;
        (<h:commandLink id="regist" action="register" value="Register" />)
      </td>
    </tr>
  </table>
</h:form>
</f:view>

</body>
</html>

