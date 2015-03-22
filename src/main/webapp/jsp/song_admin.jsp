<%@ page import="java.util.List" %>
<%@ page import="com.flocompany.rest.model.SongDTO" %>
<%@ page import="com.flocompany.util.EnumCategorySong" %>
<%@ page import="com.google.appengine.api.blobstore.*" %>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>
<html>
   <head>
        <meta charset="ISO-8859-1" />
   </head>
<body>
	<h1>Ecran d'administration des parametres :</h1>
 
	Ajout d'utilisateur : 
	<form method="post" action="<%= blobstoreService.createUploadUrl("/admin/song") %>" enctype="multipart/form-data">
		<table>
			<tr>
				<td>
					ID (just for the update) :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                              maxlength="100" name="id_song" id="id_song" />
				</td>
			</tr><tr>
				<td>
					Category :
				</td>
				<td>
					<select name="category">
						        <option value="<%=EnumCategorySong.FRENCH_MOVIE.getCode() %>"><%=EnumCategorySong.FRENCH_MOVIE.getLibelle()%></option>
						        <option value="<%=EnumCategorySong.US_MOVIE.getCode() %>"><%=EnumCategorySong.US_MOVIE.getLibelle()%></option>
						        <option value="<%=EnumCategorySong.MUSIC.getCode() %>"><%=EnumCategorySong.MUSIC.getLibelle()%></option>
					</select> 
				</td>
			</tr>
			<tr>
				<td>
					Titre :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                              maxlength="100" name="title" id="title" />
				</td>
			</tr>
			<tr>
				<td>
					Extrait :
				</td>
				<td>
					<input type="text" style="width: 185px;" 
                                            maxlength="100" name="extract" id="extract" />
				</td>
			</tr>	
			<tr>
				<td>
					Description  :
				</td>
				<td>
					<input type="text" name="description" id="description" />
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
					Fichier .MP3 :
				</td>
				<td>
					<input type="file" name="mp3File" />
				</td>
			</tr>				
			<tr>
				<td>
					Fichier .OGG :
				</td>
				<td>
					<input type="file" name="oggFile" />
				</td>
			</tr>				
		</table>
		<select name="action">
			  <option value="upload">Créer</option>
			  <option value="update">update</option>
			  <option value="other">other</option>
		</select> 
		<input type="submit" class="save" title="Executer" value="Executer" />
	</form>
	<hr />
	Message de retour : </br>
	<%=request.getAttribute("result") %>
	<hr />
	
	<h2>Liste des extraits sonores</h2>
	<table border="1">
		<thead>
			<tr>
				<td>Id</td>
				<td>Category</td>
				<td>Titre</td>
				<td>Extrait</td>
				<td>Description</td>
				<td>Status</td>
				<td>mp3Key</td>
				<td>oggKey</td>
				<td>Action</td>
			</tr>
		</thead>
		<%
 
		    List<SongDTO> songs = (List<SongDTO>)request.getAttribute("songList");
		    for(SongDTO s : songs){
 
		%>
			<tr>
			<td><%=s.getId()%></td>
			  <td><%=s.getCategory() %></td>
			  <td><%=s.getTitle() %></td>
			  <td><%=s.getExtract() %></td>
			  <td><%=s.getDescription() %></td>
			  <td><%=s.getStatus() %></td>
			  <td><%=s.getMp3Key() %></td>
			  <td><%=s.getOggKey() %></td>
			  <td><a href="/admin/song?action=delete&id=<%=s.getId()%>">Delete</a></td>
			</tr>
		<%
		}
		%>
	</table>
 
</body>
</html>