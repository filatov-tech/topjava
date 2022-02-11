<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Edit Meal</title>
    <style>
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
<h2>Edit Meal</h2>
<br>
<form method="post" action="meals">
    <input type="hidden" name="id" value="${meal.id}">
    DateTime <input type="datetime-local" name="date" value="${meal.dateTime}"><br><br>
    Description <input type="text" name="description" value="${meal.description}"><br><br>
    Calories <input type="text" name="calories" value="${meal.calories}"><br><br>
    <input type="submit" value="Save" class="button"> <a href="meals" class="button">Cancel</a>
</form>
</body>
</html>