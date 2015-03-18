<%@ page import="java.util.List" %>
<%@ page import="com.flocompany.rest.model.SongDTO" %>
<%@ page import="com.google.appengine.api.blobstore.*" %>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>
<html>
<body>
	<h1>Ecran d'administration des parametres :</h1>
 
	Ajout d'utilisateur : 
	<form method="post" action="<%= blobstoreService.createUploadUrl("/songsend_song_admin") %>" enctype="multipart/form-data">
		<table>
			<tr>
				<td>
					Film :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                              maxlength="100" name="group" id="group" />
				</td>
			</tr>
			<tr>
				<td>
					Extrait :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                            maxlength="100" name="title" id="title" />
				</td>
			</tr>	
			<tr>
				<td>
					Description  :
				</td>
				<td>
					<input type="text" name="description" id="title" />
				</td>
			</tr>	
			<tr>
				<td>
					Status :
				</td>
				<td>
					<input type="text" name="status" id="status" />
				</td>
			</tr>	
			<tr>
				<td>
					Fichier à envoyer :
				</td>
				<td>
					<input type="file" name="uploadedFile" />
				</td>
			</tr>				
		</table>
		<select name="action">
			  <option value="upload">upload</option>
			  <option value="download">download</option>
			  <option value="other">other</option>
		</select> 
		<input type="hidden" name="action" value="update" />
		<input type="submit" class="save" title="Save" value="Save" />
	</form>
		<a href="http://localhost:81/songsend_song_admin?action=download&blob-key=VBlBPtiofrngm7qmCvTXbA"> blob</a>
	<hr />
	<hr />
	Retour de la servlet : </br>
	<%=request.getAttribute("result") %>
	<hr />
	
	<h2>Liste des extraits sonores</h2>
	<table border="1">
		<thead>
			<tr>
				<td>Id</td>
				<td>KeyBlob</td>
				<td>film</td>
				<td>extrait</td>
				<td>description</td>
				<td>status</td>
				<td>Action</td>
			</tr>
		</thead>
		<%
 
		    List<SongDTO> songs = (List<SongDTO>)request.getAttribute("songList");
		    for(SongDTO e : songs){
 
		%>
			<tr>
			<td><%=e.getId()%></td>
			  <td><%=e.getKeyBlob() %></td>
			  <td><%=e.getGroup() %></td>
			  <td><%=e.getTitle() %></td>
			  <td><%=e.getDescription() %></td>
			  <td><%=e.getStatus() %></td>
			  <td><a href="update/<%//=e.getName()%>">...</a></td>
			</tr>
		<%
		}
		%>
	</table>
 
</body>
</html>