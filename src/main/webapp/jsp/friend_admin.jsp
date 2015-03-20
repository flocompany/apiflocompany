<%@ page import="java.util.List" %>
<%@ page import="com.flocompany.rest.model.FriendDTO" %>
<html>
   <head>
        <meta charset="utf-8" />
   </head>
<body>
	<h1>Ecran d'administration des amis</h1>
 
	Ajout d'une amitié : <a href="addCustomerPage"></a>
	<form method="post" action="/admin/friend" >
		<table>
			<tr>
				<td>
					id :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                              maxlength="30" name="id" id="id" />
				</td>
			</tr>
			<tr>
				<td>
					idPersonApplicant :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                            maxlength="30" name="idPersonApplicant" id="idPersonApplicant" />
				</td>
			</tr>
			<tr>
				<td>
					idPerson :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                            maxlength="30" name="idPerson" id="idPerson" />
				</td>
			</tr>	
			<tr>
				<td>
					status :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                            maxlength="30" name="status" id="status" />
				</td>
			</tr>			
		</table>
		<select name="action">
			  <option value="mylist">myList (WS, GET)</option>
			  <option value="add">add (Not WS))</option>
			  <option value="addWs">add (WS))</option>
			  <option value="update">update (Not WS))</option>
			  <option value="other">other</option>
		</select> 
		<!-- input type="hidden" name="action" value="add" / -->
		<input type="submit" class="save" title="Execute" value="Execute" />
	</form>
	
	
	
	<hr />
	Retour du webservice : </br>
	<%=request.getAttribute("restResult") %>
	<hr />
	<h2>Liste des amis</h2>
	<table border="1">
		<thead>
			<tr>
				<td>Id</td>
				<td>Id du demandeur</td>
				<td>Id de receveur</td>
				<td>Status de l'amitié</td>
				<td>Action</td>
			</tr>
		</thead>
		<%
 
		    List<FriendDTO> friends = (List<FriendDTO>)request.getAttribute("friendList");
		    for(FriendDTO e : friends){
 
		%>
			<tr>
			<td><%=e.getId()%></td>
			  <td><%=e.getIdPersonApplicant()%></td>
			  <td><%=e.getIdPerson() %></td>
			  <td><%=e.getStatus() %></td>
			  <td><a href="/admin/friend?action=delete&id=<%=e.getId()%>">Delete</a></td>
			</tr>
		<%
			}
		%>
	</table>
 
</body>
</html>