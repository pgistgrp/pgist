<jsp:useBean id="thread" scope="request" class="org.pgist.nlp.ConversationThread" />
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<title>Conversation Thread</title>
</head>

<frameset cols="80%,20%">
  <frame name="slate" src="slate.jsp?treeId=<%= thread.getId() %>">
  <frameset rows="60%,40%">
    <frame name="conbar" src="conbar.jsp?treeId=<%= thread.getId() %>">
    <frame name="focus" src="focus.jsp?treeId=<%= thread.getId() %>">
  </frameset>
</frameset>
<noframes>
  <h2>Frame Alert</h2>
  <p>
    This document is designed to be viewed using the frames feature.  
    If you see this message, you are using a non-frame-capable web 
    client.
    <br>
    Link to <a href="overview-summary.html">Non-frame version.</a>
</noframes>

</html>

