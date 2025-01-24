<!DOCTYPE html>
<html>
<head>
    <title>Iniciar Sesión</title>
</head>
<body>
<h1>Iniciar Sesión</h1>
<form action="/process-login" method="POST">
    <label for="username">Usuario:</label>
    <input type="text" name="username" id="username" required>
    <br>
    <label for="password">Contraseña:</label>
    <input type="password" name="password" id="password" required>
    <br>
    <button type="submit">Entrar</button>
</form>
<p><a href="/register">Registrarse</a></p>
</body>
</html>
