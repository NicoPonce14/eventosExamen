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
<title>Events</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/main.css">
<!-- change to match your file/naming structure -->
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
<!-- change to match your file/naming structure -->
</head>
<body>
	<div class="container mt-4">
		<div class="row">
			<h1>
				Welcome,
				<c:out value="${usuario.firstName }"></c:out>
			</h1>
			<a href="/logout">Logout</a>
		</div>

		<div class="row">
			<div class="col-12">
			<h4>Aqui hay eventos en tu mismo estado:</h4>
				<table class="table table-striped">
					<thead>
						<tr>
							<th scope="col">Name</th>
							<th scope="col">Date</th>
							<th scope="col">Location</th>
							<th scope="col">Host</th>
							<th scope="col">Action/Status</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${eventosEstado}" var="evento">
						<tr>
							<th scope="row"><a href='/events/${evento.id }'>${evento.nombreEvento }</a></th>
							<td><fmt:formatDate value="${evento.fechaEvento}"
										pattern="MMMM dd, yyyy" var="fechaEventoNot" /> <c:out
										value="${fechaEventoNot}"></c:out></td>
							<td>${evento.ciudad }</td>
							<td>${evento.organizador.firstName }</td>
							<td><c:choose>
							<c:when test="${ evento.organizador.id == usuario.id }">
								<a href="/events/${ evento.id }/edit">Edit</a> |
									<form class="delete-form" action="/delete/${ evento.id }"
								method="post">
									<input type="hidden" name="_method" value="delete" />
									<button>Borrar</button>
								</form>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${ evento.asistentes.contains(usuario) }">
										<span>Unido <a href="/events/${ evento.id }/cancel">Cancelar</a></span>
									</c:when>
									<c:otherwise>
										<a href="/events/${ evento.id }/unirse">Unirse</a>
									</c:otherwise>

								</c:choose>
							</c:otherwise>
						</c:choose>
						</td>
						
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="row mt-4">
			<div class="col-12">
			<h4>Aqui hay eventos en otros estado:</h4>
				<table class="table table-striped">
					<thead>
						<tr>
							<th scope="col">Name</th>
							<th scope="col">Date</th>
							<th scope="col">Location</th>
							<th scope="col">State</th>
							<th scope="col">Host</th>
							<th scope="col">Action/Status</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${eventosotroEstado}" var="evento">
						<tr>
							<th scope="row"><a href='/events/${evento.id }'>${evento.nombreEvento }</a></th>
							<td><fmt:formatDate value="${evento.fechaEvento}"
										pattern="MMMM dd, yyyy" var="fechaEventoNot" /> <c:out
										value="${fechaEventoNot}"></c:out></td>
							<td>${evento.ciudad }</td>
							<td>${evento.estado }</td>
							<td>${evento.organizador.firstName }</td>
							<td><c:choose>
							<c:when test="${ evento.organizador.id == usuario.id }">
								<a href="/events/${ evento.id }/edit">Edit</a> |
									<form class="delete-form" action="/delete/${ evento.id }"
								method="post">
									<input type="hidden" name="_method" value="delete" />
									<button>Borrar</button>
								</form>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${ evento.asistentes.contains(usuario) }">
										<span>Unido <a href="/events/${ evento.id }/cancel">Cancelar</a></span>
									</c:when>
									<c:otherwise>
										<a href="/events/${ evento.id }/unirse">Unirse</a>
									</c:otherwise>

								</c:choose>
							</c:otherwise>
						</c:choose>
						</td>
						
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="col-4 mt-3">

			<h2>Create an Event</h2>
			<form:form method="POST" action="/new/event" modelAttribute="event">
				<form:hidden value="${ usuario.id }" path="organizador" />
				<div>

					<form:label path="nombreEvento">Name:</form:label>
					<form:errors path="nombreEvento" class="text-danger" />
					<form:input class="form-control" type="text" path="nombreEvento" />
				</div>
				<div>

					<form:label path="fechaEvento">Date:</form:label>
					<form:errors path="fechaEvento" class="text-danger" />
					<form:input type="date" class="form-control" path="fechaEvento" />
				</div>
				<div class="row">
					<div class="col-6">
						<form:label path="ciudad">Location:</form:label>
						<form:errors path="ciudad" class="text-danger" />
						<form:input class="form-control" type="text" path="ciudad" />
					</div>
					<div class="col-6">
						<form:label path="estado">State:</form:label>
						<form:errors path="estado" class="text-danger" />
						<form:select class="form-control" path="estado">
							<c:forEach items="${estados }" var="estado">

								<form:option value="${estado }"></form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>
				<button type="submit" class="btn btn-success mt-4">Register!!</button>
			</form:form>
			<p class="text-danger mt-2">
				<c:out value="${succesRegister}" />
			</p>
		</div>
	</div>
</body>
</html>