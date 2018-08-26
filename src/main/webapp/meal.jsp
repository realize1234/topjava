<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Meal</title>
    <style>
        body{
            padding-bottom: 30px;
        }
        .lab {
            float:left;
            width: 6%;


        }
        .inp {
            margin-bottom: 5px;
            width: 12%;
        }
        input:hover{
            border-color: black;
        }

    </style>
</head>
<body>
<h2><a href="index.html">Home</a></h2>

    <h2>${param.action == 'create' ? 'Create' : 'Update'} meal</h2>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>

    <form method="post" action="meals">

        <input name="id" type="hidden" value="${meal.id}">
        <label class="lab" for="description">Description: </label>
        <input class="inp" id="description" name="description" type="text" value="${meal.description}"><br>
        <label class="lab" for="calories">Calories: </label>
        <input class="inp" type="number" id="calories" name="calories" value="${meal.calories}"><br>
        <label class="lab" for="dateTime">Date time: </label>
        <input class="inp" id="dateTime" name="dateTime" type="datetime-local" value="${meal.dateTime}"><br>
        <button  type="submit">Save</button>
        <button type="button" onclick="window.history.back()">Back</button>
    </form>
</body>
</html>
