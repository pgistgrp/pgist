<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://www.pgist.org/jsf/components" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>Discourse Slate</title>
<link rel=stylesheet href='<%= request.getContextPath()%>/styles/default.css' type="text/css" media=all>
<link rel=stylesheet href='<%= request.getContextPath()%>/styles/treemap.css' type="text/css" media=all>
</head>

<body>

<f:view>
<h:form id="discourseForm">

  <pg:doLayout id="doLayout" styleClass="" tree="#{DiscourseBean.discourse}" node="#{DiscourseBean.opinion}"
    key="id" label="name">
  
    <f:facet name="header"></f:facet>
    
    <f:facet name="view"></f:facet>
    
    <f:facet name="uptree">
      <pg:doUpTree id="doUpTree" styleClass="">
      </pg:doUpTree>
    </f:facet>
    
    <f:facet name="treemap">
      <pg:doTreeMap id="doTreeMap" styleClass="" depth="3">
      </pg:doTreeMap>
    </f:facet>
    
    <f:facet name="downtree"></f:facet>
    
    <f:facet name="footer"></f:facet>
    
    <f:facet name="conbar">
      <pg:doConbar id="doConbar" styleClass="">
      </pg:doConbar>
    </f:facet>
    
    <f:facet name="target"></f:facet>
    
    <f:facet name="focus"></f:facet>
  
  </pg:doLayout>

</h:form>
</f:view>

</body>
</html>

