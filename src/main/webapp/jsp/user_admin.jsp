<%@ page import="java.util.List" %>
<%@ page import="com.flocompany.rest.model.PersonDTO" %>
<html>
   <head>
   		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   </head>
<body>
	<h1>Ecran d'administration des utilisateurs</h1>
 
	Ajout d'utilisateur : <a href="addCustomerPage"></a>
	<form method="post" action="/admin/user" >
		<table>
			<tr>
				<td>
					Pseudo :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                              maxlength="30" name="pseudo" id="pseudo" />
				</td>
			</tr>
			<tr>
				<td>
					Mail :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                            maxlength="30" name="mail" id="mail" />
				</td>
			</tr>
			<tr>
				<td>
					password :
				</td>
				<td>
					<input type="password" style="width: 185px;" 
                                            maxlength="30" name="pwd" id="pwd" />
				</td>
			</tr>	
			<tr>
				<td>
					device :
				</td>
				<td>
					<input type="deviceId" style="width: 185px;" 
                                            maxlength="30" name="deviceId" id="deviceId" />
				</td>
			</tr>						
		</table>
		<select name="action">
			  <option value="signup">Signup (WS, POST)</option>
			  <option value="login">login (WS, POST)</option>
			  <option value="demo">sampledemo (WS, GET)</option>
			  <option value="search">search (WS, GET)</option>
			  <option value="add">add (Not WS))</option>
			  <option value="other">other</option>
		</select> 
		<!-- input type="hidden" name="action" value="add" / -->
		<input type="submit" class="save" title="Execute" value="Execute" />
	</form>
	
	<hr/>
	Retour du webservice : </br>
	<%=request.getAttribute("restResult") %>
	<hr/>
	<h2>Liste des utilisateurs</h2>
	<table border="1">
		<thead>
			<tr>
				<td>Id</td>
				<td>Pseudo</td>
				<td>Email</td>
				<td>Action</td>
			</tr>
		</thead>
		<%
 
		    List<PersonDTO> users = (List<PersonDTO>)request.getAttribute("userList");
		    for(PersonDTO e : users){
 
		%>
			<tr>
			<td><%=e.getId()%></td>
			  <td><%=e.getPseudo() %></td>
			  <td><%=e.getEmail() %></td>
			  <td><a href="/admin/user?action=notify&id=<%=e.getId()%>">notifier</a> 
                             | <a href="/admin/user?action=delete&id=<%=e.getId()%>">Delete</a></td>
			</tr>
		<%
			}
		%>
	</table>
 
</body>
</html>