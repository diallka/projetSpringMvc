<!-- A rajouter pour activer formulaire Spring -->
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE HTML>
<html><head><meta charset="UTF-8"><title>Spring MVC - Tutorial 1: Data List</title></head>
<body>
	<h3>Authentification</h3>
	
	<form:form method="post" modelAttribute="util">
		<table>
				<td><form:label path="login">Identifiant</form:label></td>
				<td><form:input path="login"
						placeholder="saisir identifiant..." /></td>
			</tr>
			<tr>
				<td><form:label path="mdp">Mot de passe</form:label></td>
				<td><form:password path="mdp" placeholder="saisir mot de passe..." /></td>
			</tr>
			<tr>
			<td><input type="submit" value="${lienSeconnecter}"/></td>
			</tr>

		</table>
	</form:form>
</body>
</html>