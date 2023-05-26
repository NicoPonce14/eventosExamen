<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!-- c:out ; c:forEach etc. -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Formatting (dates) -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mostrar evento</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/main.css">
<!-- change to match your file/naming structure -->
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
<!-- change to match your file/naming structure -->
</head>
<body>
	<div class="container">

		<div class="row">
			<div class="col-6 mt-4">
			<a href="/events">Regresar</a>
				<h2>${ event.nombreEvento }</h2>
				<div class="event-details-side">
					<h4>
						<strong>Host:</strong> ${ event.organizador.firstName }
					</h4>
					<h4><fmt:formatDate value="${event.fechaEvento}"
										pattern="MMMM dd, yyyy" var="fechaEventoNot" /> 
						<strong>Date:</strong> ${ fechaEventoNot }
					</h4>
					<h4>
						<strong>Location:</strong> ${ event.ciudad }, ${ event.estado }
					</h4>
					<h4>
						<strong>People attending:</strong> ${ event.asistentes.size() }
					</h4>
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Name</th>
								<th>Location</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ event.asistentes }" var="user">
								<tr>
									<td>${ user.firstName } ${ user.lastName }</td>
									<td>${ user.location },${ user.state }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div class="col-6">
				<div class="event-details-side">
					<h3>Message Wall</h3>
					<div class="messages">
						<c:forEach items="${ event.mensajes }" var="message">
							<p>${ message.autor.firstName } says: ${ message.comentario }</p>
						</c:forEach>
					</div>
					<form action="/events/${ event.id }/comentario" method="post">
						<div class="form-group">
							<label for="comentario">Add Comment</label> <span>${ error }</span>
							<textarea name="comentario" id="comentario" class="form-control"></textarea>
							<button>Submit</button>
						</div>
					</form>
				</div>
			</div>
		</div>
</body>
</html>