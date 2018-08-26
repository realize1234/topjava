<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
    <style>
        .exceed{
            color: red;
        }

        .normal {
            color: green;
        }
        table {
            border: 1px solid black;
            border-collapse: collapse;

        }
        tr, td, th {
            border: 1px solid grey;
            text-align: center;
            padding: 10px;

        }
    </style>
</head>
<body>
    <h2><a href="index.html">Home</a></h2>
    <h2>Meal list</h2>
  <a href="meals?action=create" style="text-decoration: none; margin-bottom: 15px">Create</a>

    <table style="margin-top: 15px">
        <tr>
            <th>Description</th>
            <th>Calories</th>
            <th>Time</th>
            <th>Delete</th>
            <th>Update</th>
        </tr>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceed' : 'normal'}">
                <td>${meal.description} </td>
                <td>${meal.calories}</td>
                <td><%= TimeUtil.getTime(meal.getDateTime())%></td>
                <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Delete</a></td>
                <td><a href="meals?action=update&id=<c:out value="${meal.id}"/> ">Update</a></td>
            </tr>
        </c:forEach>

    </table>
</body>
</html>
