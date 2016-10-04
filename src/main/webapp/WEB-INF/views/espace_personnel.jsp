<!-- A rajouter pour activer formulaire Spring -->
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Taglig pour la jstl -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Espace personnel Administrateur</title>
	</head>
	<body>
		<h3>ESPACE PERSONNEL ADMIN</h3>
		<c:forEach items="${utilisateurs}" var="util">
			<table border="1">
				<tr>
					<td>${util.nom}</td>
					<td> ${util.prenom}</td>
					<td> ${util.email}</td>
					<td><a href="${pageContext.request.contextPath}/modifier?id=${util.id}">Modifier</a></td>
					<td><a href="${pageContext.request.contextPath}/supprimer/${util.id}">Supprimer</a></td>
				</tr>
			</table>
		</c:forEach>

	</body>
</html>
