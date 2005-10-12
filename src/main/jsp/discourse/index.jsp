<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://www.pgist.org/jsf/components" prefix="pg" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>Discourse List</title>
<link rel=stylesheet href='<%= request.getContextPath()%>/styles/default.css' type="text/css" media=all>
</head>

<body>

<f:view>
<h:form id="discourseListForm">

  <pg:listTable id="discourseList" width="100%" rowSize="10" pageSize="10" binding="#{DiscourseBean.data}"
                actionBinding="#{DiscourseBean.listDiscourse}" pageSetting="#{DiscourseBean.pageSetting}"
                value="#{DiscourseBean.discourses}" var="discourse">

    <f:facet name="title"><h:outputText value="Discourse List"/></f:facet>
    
    <f:facet name="toolbar">
      <pg:toolbar id="toolbar" styleClass="toolbar">
        <pg:toolButton id="newDiscourse" action="#{DiscourseBean.newDiscourse}" value="New Discourse" />
      </pg:toolbar>
    </f:facet>

    <f:facet name="topScroller">
      <pg:scroller id="scroller1" align="left" styleClass="myclass" infoType="row" showPageGo="true" showRowsOfPage="true"/>
    </f:facet>
    
    <f:facet name="bottomScroller">
      <pg:scroller id="scroller2" align="right" styleClass="myclass" infoType="row" showPageGo="true" showRowsOfPage="true"/>
    </f:facet>

    <f:facet name="filter">
      <!--pg:filter id="filter" /-->
    </f:facet>

    <h:column>
      <f:facet name="header">
        <h:outputText value="Discourse"/>
      </f:facet>
      <h:inputHidden id="objectId" binding="#{DiscourseBean.objectId}" value="#{discourse.id}"/>
      <h:commandLink id="readDiscourse" action="#{DiscourseBean.readDiscourse}" value="#{discourse.title}" />
    </h:column>
    
    <h:column>
      <f:facet name="header">
        <h:outputText value="Owner"/>
      </f:facet>
      <h:outputText value="#{discourse.root.owner.loginname}"/>
    </h:column>
    
    <h:column>
      <f:facet name="header">
        <h:outputText value="Post Time"/>
      </f:facet>
      <h:outputText value="#{discourse.root.time}" />
    </h:column>
    
  </pg:listTable>

</h:form>
</f:view>

</body>
</html>

