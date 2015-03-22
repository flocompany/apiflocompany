<%@ page import="java.util.List" %>
<%@ page import="com.flocompany.rest.model.MessageDTO" %>
<html>
   <head>
        <meta charset="utf-8" />
   </head>
<body>
	<h1>Ecran d'administration des Messages</h1>
 
	Ajout d'utilisateur : <a href="addCustomerPage"></a>
	<form method="post" action="/admin/message" >
		<table>
			<tr>
				<td>
					id message :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                              maxlength="30" name="id" id="id" />
				</td>
			</tr>
			<tr>
				<td>
					id friend :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                            maxlength="30" name="idFriend" id="idFriend" />
				</td>
			</tr>
			<tr>
				<td>
					id sender :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                            maxlength="30" name="idSender" id="idSender" />
				</td>
			</tr>			
			<tr>
				<td>
					id song :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                            maxlength="30" name="idSong" id="idSong" />
				</td>
			</tr>
			<tr>
				<td>
					message :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                            maxlength="30" name="message" id="message" />
				</td>
			</tr>
			<tr>
				<td>
					date message :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                            maxlength="30" name="dateMessage" id="dateMessage" />
				</td>
			</tr>
			<tr>
				<td>
					read :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                            maxlength="30" name="read" id="read" />
				</td>
			</tr>																
		</table>
		<select name="action">
			  <option value="add">add (Not WS))</option>
			  <option value="other">other</option>
		</select> 
		<input type="submit" class="save" title="Execute" value="Execute" />
	</form>
	
	
	
	<hr />
	Retour du webservice : </br>
	<%="" %>
	<hr />
	<h2>Liste des messages</h2>
	<table border="1">
		<thead>
			<tr>
				<td>Id</td>
				<td>IdFriend</td>
				<td>idSender</td>
				<td>idSong</td>
				<td>message</td>
				<td>date Message</td>
				<td>Read</td>
			</tr>
		</thead>
		<%
 
		    List<MessageDTO> messages = (List<MessageDTO>)request.getAttribute("messageList");
		    for(MessageDTO m : messages){
 
		%>
			<tr>
			<td><%=m.getId()%></td>
			  <td><%=m.getIdFriend() %></td>
			  <td><%=m.getIdSender()%></td>
			  <td><%=m.getIdSong()%></td>
			  <td><%=m.getMessage()%></td>
			  <td><%=m.getDateMessage()%></td>
			  <td><%=m.getRead()%></td>
			  <td><a href="/admin/message?action=delete&id=<%=m.getId()%>&idFriend=<%=m.getIdFriend()%>">Delete</a></td>
			</tr>
		<%
			}
		%>
	</table>
 
</body>
</html>