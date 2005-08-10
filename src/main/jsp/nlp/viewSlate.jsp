<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://www.pgist.org/jsf/components" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>Conversation Slate</title>
<link rel=stylesheet href='<%= request.getContextPath() +"/styles/default.css"%>' type="text/css" media=all>
</head>

<body>

<f:view>
<h:form id="slateForm">

  <pg:treemap id="treemap" depth="3" actionBinding="#{ConversationBean.getThread}"
      title="title" content="content" username="owner.loginname" conbar="" focus=""/>

</h:form>
</f:view>

</body>
</html>

