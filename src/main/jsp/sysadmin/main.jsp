<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>PGIST System Adminitration Page</title>
</head>
<body>

<f:view>
<h:form id="sysAdminForm" >

  <h2>System Adminitration</h2>
  <br>
  <h:commandLink action="listUser" value="User Management" />
  <br>
  <h:commandLink action="listRole" value="Role Management" />
  <br>
  <h:commandLink action="listEmailTemplate" value="Email Template Management" />
  <br>
  <h:commandLink action="#{UserBean.editProfile}" value="Edit Profile" />
  <br>
  <h:commandLink action="listCategory" value="Glossary Category Management" />
  <br>
  <h:commandLink action="listTerm" value="Glossary Term List" />
  <br>
  <h:commandLink action="listDiscourse" value="Discourse Object" />

</h:form>
</f:view>
</body>
</html>

