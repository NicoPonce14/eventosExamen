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
<title>Editar Evento</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/main.css">
<!-- change to match your file/naming structure -->
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
<!-- change to match your file/naming structure -->
</head>
<body>

	<div class="container">
		<div class="col-4 mt-3">

			<h2>Edit Event</h2>
			<form:form method="POST" action="/edit/${eventoxid.id }" modelAttribute="event">
				<input type="hidden" name="_method" value="put">
				<form:hidden value="${ usuario.id }" path="organizador" />
				<div>

					<form:label path="nombreEvento">Name:</form:label>
					<form:errors path="nombreEvento" class="text-danger" />
					<form:input value="${eventoxid.nombreEvento }" class="form-control"
						type="text" path="nombreEvento" />
				</div>
				<div>

					<form:label path="fechaEvento">Date:</form:label>
					<form:errors path="fechaEvento" class="text-danger" />
					<fmt:formatDate value="${eventoxid.fechaEvento}"
										pattern="yyyy-MM-dd" var="fechaEvento" /> 
					<form:input value="${fechaEvento }" type="date"
						class="form-control" path="fechaEvento" />
				</div>
				<div class="row">
					<div class="col-6">
						<form:label path="ciudad">Location:</form:label>
						<form:errors path="ciudad" class="text-danger" />
						<form:input value="${eventoxid.ciudad }" class="form-control"
							type="text" path="ciudad" />
					</div>
					<div class="col-6">
						<form:label path="estado">State:</form:label>
						<form:errors path="estado" class="text-danger" />
						<form:select class="form-control" path="estado">
							<option value="${ eventoxid.estado }">${ eventoxid.estado }</option>
							<c:forEach items="${ estados }" var="estado">
								<option value="${ estado }">${ estado }</option>
							</c:forEach>
						</form:select>
					</div>

				</div>
				<button type="submit" class="btn btn-success mt-4">Editar</button>
			</form:form>
		</div>
	</div>

</body>
</html>