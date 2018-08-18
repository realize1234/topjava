<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
    <style>
        .red {
            color: red
        }
        .green {
            color: green
        }
        table, tr, td{
            border: 1px solid black;
            border-collapse: collapse;
            text-align: center;
        }
        td,th{
            padding: 20px;

        }
        th{
            border-right: 1px solid black;

        }

    </style>
</head>
<body>
    <a href="index.html">Home</a>
    <h2>Meal List</h2>
    <table>
    <tr>

        <th>Description</th>
        <th>Time</th>
        <th>Calories</th>

        </tr>



        <c:forEach var="meal" items="${meals}"  >
            <jsp:useBean  id="meal" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>
            <tr class="${meal.exceed ? 'red' : 'green'}">

                <td>${meal.description}</td>
                <td>${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}</td>
                <td>${meal.calories}</td>
        </tr>
    </c:forEach>
    </table>
</body>
</html>
