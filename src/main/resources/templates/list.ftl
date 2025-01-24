<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" th:replace="layout :: content">
<body>
<h2>Lista de Reservas</h2>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Usuario</th>
        <th>Vuelo</th>
        <th>Asientos</th>
        <th>Estado</th>
        <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
    <tr th:each="booking : ${bookings}">
        <td th:text="${booking.bookingId}"></td>
        <td th:text="${booking.user.username}"></td>
        <td th:text="${booking.flight.flightNumber}"></td>
        <td th:text="${booking.numberOfSeats}"></td>
        <td th:text="${booking.status}"></td>
        <td>
            <a th:href="@{/bookings/{id}(id=${booking.bookingId})}">Detalles</a>
            <form th:action="@{/bookings/cancel/{id}(id=${booking.bookingId})}" method="post" style="display: inline;">
                <button type="submit">Cancelar</button>
            </form>
        </td>
    </tr>
    </tbody>
</table>
</body>
</html>
