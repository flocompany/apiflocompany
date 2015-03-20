<%@ page import="java.util.List" %>
<%@ page import="com.flocompany.rest.model.ParameterDTO" %>
<html>
<body>
	<h1>Ecran d'administration des parametres :</h1>
 
	Ajout d'utilisateur : 
	<form method="post" action="/songsend_parameter_admin" >
		<table>
			<tr>
				<td>
					Name :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                              maxlength="100" name="name" id="name" />
				</td>
			</tr>
			<tr>
				<td>
					Value :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                            maxlength="100" name="value" id="value" />
				</td>
			</tr>	
		</table>
		<input type="hidden" name="action" value="update" />
		<input type="submit" class="save" title="Modifier" value="Modifier" />
	</form>
	<hr />
	<h2>Liste des parametres</h2>
	<table border="1">
		<thead>
			<tr>
				<td>Id</td>
				<td>Name</td>
				<td>Value</td>
				<td>Action</td>
			</tr>
		</thead>
		<%
 
		    List<ParameterDTO> params = (List<ParameterDTO>)request.getAttribute("parameterList");
		    for(ParameterDTO e : params){
 
		%>
			<tr>
			<td><%=e.getId()%></td>
			  <td><%=e.getName() %></td>
			  <td><%=e.getValue() %></td>
			  <td><a href="update/<%=e.getName()%>">...</a></td>
			</tr>
		<%
			}
		%>
	</table>
 
</body>
</html>