<!DOCTYPE html>
<%@page session="false"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <form name="CreateEmployeeForm" method="post" action="createemployee">
        First name: <input type="text" name="firstName"/><br/>
        Second name: <input type="text" name="secondName"/><br/>
        Email: <input type="text" name="email"/><br/>
        Password: <input type="text" name="firstPassword"/><br/>
        Confirm Password: <input type="text" name="secondPassword"/><br/>
        Pay: <input type="text" name="pay"/><br/>
        Token: <input type="text" name="token"/><br/>
        <input type="submit" value="create Account" />
    </form><br/>

    <form name="CreateCompanyForm" method="post" action="createcompany">
        Company name: <input type="text" name="companyName"/><br/>
        Password: <input type="text" name="firstPassword"/><br/>
        Confirm password: <input type="text" name="secondPassword"/><br/>
        Email: <input type="text" name="email"/><br/>
        <input type="submit" value="create Account" />
    </form><br/>

    Company Email: <input type="text" id="compEmail">
    Password: <input type="password" id="compPassword">
    <button id="compSubmitButton" onclick="compLogin()">Log In</button><br/>

    <form name="EmployeeLogin" method="post" action="employeecheckpassword">
        Email: <input type="text" name="email"/><br/>
        Password: <input type="text" name="password"/><br/>
        <input type="submit" value="Login" />
    </form><br/>

</body>
</html>

<script>
    //TODO company login
    function compLogin() {
        var httpRequest = new XMLHttpRequest();
        httpRequest.onreadystatechange = function () {
            if (this.status === 200 && this.readyState === 4) {

            }
        };
        httpRequest.open("POST", "companycheckpassword", true);
        httpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httpRequest.send("timestamp=" + dateTime);
    }
</script>