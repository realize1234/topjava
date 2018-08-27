<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
        .row {
            width:80%;
            height:80px;
        }
        label {
            float: left;
            padding-left: 15px;
            width: 10%;
        }
        input {
            float: left;
            width: 15%;

        }

    </style>

</head>
<body>
<section>
    <h2><a href="index.html">Home</a></h2>
    <h2>Meal list</h2>
    <a href="meals?action=create">Add Meal</a>
    <hr>
    <form  method="post" action="meals?action=filter">
        <div class="row">
            <label for="fromDate">From Date:</label>
            <input id="fromDate" name="fromDate" value="${param.fromDate}" type="date">
            <label for="toDate">To Date:</label>
            <input id="toDate" name="toDate" value="${param.toDate}" type="date">
        </div>
        <div class="row">
            <label for="fromTime">From Time:</label>
            <input id="fromTime" name="fromTime" value="${param.fromTime}" type="time">
            <label for="toTime">To Time:</label>
            <input id="toTime" name="toTime" value="${param.toTime}" type="time">
        </div>
            <button type="submit">Filter</button>
    </form>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>