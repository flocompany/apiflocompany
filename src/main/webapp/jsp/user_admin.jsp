<%@ page import="java.util.List" %>
<%@ page import="com.flocompany.rest.model.PersonWrappedDTO" %>
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
				<td>devices</td>
				<td>Action</td>
			</tr>
		</thead>
		<%
 
		    List<PersonWrappedDTO> users = (List<PersonWrappedDTO>)request.getAttribute("userList");
		    for(PersonWrappedDTO u : users){
 
		%>
			<tr>
			<td><%=u.getId()%></td>
			  <td><%=u.getPseudo() %></td>
			  <td><%=u.getEmail() %></td>
			  <td><%=u.getDevices() %></td>
			  <td><a href="/admin/user?action=notify&id=<%=u.getId()%>">notifier</a> 
                             | <a href="/admin/user?action=delete&id=<%=u.getId()%>">Delete</a></td>
			</tr>
		<%
			}
		%>
	</table>
 
</body>
</html>