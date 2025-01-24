<!DOCTYPE html>
<html>
<head>
    <title>Reservas</title>
</head>
<body>
<h1>Tus Reservas</h1>
<ul>
    <#list bookings as booking>
        <li>${booking.id}: ${booking.description}</li>
    </#list>
</ul>
</body>
</html>
