<%@ page import="java.util.List" %>
<%@ page import="com.flocompany.rest.model.PersonDTO" %>
<html>
<body>
	<h1>Ecran d'administration des utilisateurs</h1>
 
	Ajout d'utilisateur : <a href="addCustomerPage"></a>
	<form method="post" action="/songsend_user_admin" >
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
	
	
	
	<hr />
	Retour du webservice : </br>
	<%=request.getAttribute("restResult") %>
	<hr />
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
			  <td><a href="update/<%=e.getPseudo()%>">Update</a> 
                             | <a href="/songsend_user_admin?action=delete&id=<%=e.getId()%>">Delete</a></td>
			</tr>
		<%
			}
		%>
	</table>
 
</body>
</html>