<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://www.pgist.org/jsf/components" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>Email Template Management</title>
<link rel=stylesheet href='<%= request.getContextPath() +"/styles/default.css"%>' type="text/css" media=all>
</head>

<body>

<f:view>
<h:form id="emailTemplateAdminForm">

<pg:show forRole="admin">

  <pg:listTable id="emailTemplateList" width="80%" rowSize="10" pageSize="10" binding="#{EmailTemplateBean.data}"
                actionBinding="#{EmailTemplateBean.listTemplate}" pageSetting="#{EmailTemplateBean.pageSetting}"
                value="#{EmailTemplateBean.templates}" var="template">

    <f:facet name="title"><h:outputText value="Email Template List"/></f:facet>
    
    <f:facet name="topScroller">
      <pg:scroller id="scroller1" align="left" styleClass="myclass" infoType="row" showPageGo="true" showRowsOfPage="true"/>
    </f:facet>
    
    <f:facet name="bottomScroller">
      <pg:scroller id="scroller2" align="right" styleClass="myclass" infoType="row" showPageGo="true" showRowsOfPage="true"/>
    </f:facet>

    <h:column>
      <f:facet name="header">
        <h:outputText value="Template Name"/>
      </f:facet>
      <h:inputHidden id="objectId" binding="#{EmailTemplateBean.objectId}" value="#{template.id}"/>
      <h:commandLink action="#{EmailTemplateBean.editTemplate}" value="#{template.name}" />
    </h:column>
    
    <h:column>
      <f:facet name="header">
        <h:outputText value="Description"/>
      </f:facet>
      <h:outputText value="#{template.description}"/>
    </h:column>
    
  </pg:listTable>

</pg:show>

</h:form>
</f:view>

</body>
</html>

