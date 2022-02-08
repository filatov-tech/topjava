<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Users</title>
    <style>
        .table { background-color:#eee;border-collapse:collapse;font-size: large; }
        .table th { background-color:black;color:white; }
        .table td, .table th, .table tr { padding:5px;border: 2px solid black; }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<table class="table">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <c:forEach var="meal" items="${mealsList}">
        <c:set var="rowColor" value="Green"/>
        <c:if test="${meal.isExcess() eq true}">
            <c:set var="rowColor" value="Red"/>
        </c:if>
        <tr style="color:${rowColor}">
            <td>${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>