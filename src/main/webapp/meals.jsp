<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h1>Спосок еды</h1>

<table border="2">
    <tr>
        <th>Дата, время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>

    <c:forEach var="mealTo" items="${mealsTo}">
        <tr style="color: ${mealTo.isExcess() ? "red" : "green"}">
            <td>${mealTo.getDateTimeFormatted()}</td>
            <td>${mealTo.getDescription()}</td>
            <td>${mealTo.getCalories()}</td>
        </tr>
    </c:forEach>

</table>
</body>
</html>
