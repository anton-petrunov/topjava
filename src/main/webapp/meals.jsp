<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h1>Список еды</h1>

<table border="2">
    <tr>
        <th>Дата, время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>

    <c:forEach var="mealTo" items="${mealsTo}">
        <tr style="color: ${mealTo.isExcess() ? "red" : "green"}">
            <td>${mealTo.dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}</td>
            <td>${mealTo.description}</td>
            <td>${mealTo.calories}</td>
        </tr>
    </c:forEach>

</table>
</body>
</html>
