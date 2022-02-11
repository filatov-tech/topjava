<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Users</title>
    <style>
        .table {
            background-color:#eee;
            border-collapse:collapse;
            font-size: large;
        }
        .table th {
            background-color:dimgray;
            color:white;
        }
        .table td, .table th, .table tr {
            padding:5px;
            border: 2px solid dimgray;
        }
        .button {
            display: inline-block;
            background: dimgray;
            color: white;
            padding: 5px 7px;
            text-decoration: none;
        }
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
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="meal" items="${mealsList}">
        <tr style="color:${meal.excess ? "red" : "green"}">
            <td>${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>
                <a href="meals?action=update&id=${meal.id}" class="button">Update</a>
            </td>
            <td>
                <a href="meals?action=delete&id=${meal.id}" class="button">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<br>
<a href="meals?action=add" class="button">Add</a>
</body>
</html>