<!DOCTYPE html>
<html lang="en">
<%@page session="false"%>
<head>
    <title>Home</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>

    <!----Google fonts---->
    <link href="https://fonts.googleapis.com/css?family=Inter&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Dancing+Script&display=swap" rel="stylesheet">

    <link rel = "stylesheet" type = "text/css" href = "custom.css" />
</head>
<body onload="getEmployeeDetails()">

<!---- The Navigationbar (navbar) ---->
<nav class="navbar navbar-expand-sm navbar-light bg-light sticky-top">
    <a class="navbar-brand" href="home.jsp">
        <img src="Images/Home_btn.png" class="img-responsive" style="width: 30px" alt="Home">
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">

        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav">

            <li class="nav-item">
                <a class="nav-link" href="About.jsp" style="font-size: 15px">About</a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="Features.jsp" style="font-size: 15px">Features</a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="Contact.jsp" style="font-size: 15px">Contact</a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="Calendar.jsp" style="font-size: 15px">Calendar</a>
            </li>

            <li class="nav-item">
                <a class="nav-link" onclick="LogOut()" href="index.jsp" style="font-size: 15px">Log Out</a>
            </li>
        </ul>

    </div>
</nav>

<!---- First Container ---->

<!-- En container med fixed (beveger seg ikke når man skroller) og tekst -->
<div class="container-fluid text-center fixed">
    <img src="Images/Logo_Typography.png" class="img-responsive" style="width:30%" alt="Logo_Typography">
</div>


<!---- Second Container ---->


<div class="container-fluid text-center bg-3">
    <h2>Email:</h2>
    <input class="input" type="text" id="empEmail"><br>
    <h2>First name:</h2>
    <input class="input" type="text" id="empFirstName"><br>
    <h2>Second Name name:</h2>
    <input class="input" type="text" id="empSecondName"><br>
    <h2>Pay (Hourly):</h2>
    <input class="input" type="text" id="empPay"><br>
    <h3 role="button" class="btn btn1" onclick="updateDetails()">Update Details</h3><br>
    <h2 class="box" style="display: none" id="detailsMessage">Details Updated!</h2>
    <h2>Change Password:</h2>
    <input class="input" type="text" id="empPassword1"><br>
    <h2>Confirm Password:</h2>
    <input class="input" type="text" id="empPassword2"><br>
    <h3 role="button" class="btn btn1" onclick="updatePassword()">Update Password</h3><br>
    <h2 class="box" style="display: none" id="passwordMessage">Password Updated!</h2>
</div>

<!---- third Container ---->

<div class="container-fluid text-center bg-1">

</div>
<!-- Footer -->
<footer class="container-fluid bg-logo-green text-center">
    <h2>Easyshift.app</h2>
</footer>

</body>

</html>

<script>

    function LogOut() {
        var httpRequest = new XMLHttpRequest();
        httpRequest.open("POST", "logout", true);
        httpRequest.send();
    }

    function getEmployeeDetails() {
        var httpRequest = new XMLHttpRequest();
        httpRequest.onreadystatechange = function () {
            if (this.status === 200 && this.readyState === 4) {
                var json = JSON.parse(this.responseText);
                document.getElementById("empEmail").value = json.email;
                document.getElementById("empFirstName").value = json.firstName;
                document.getElementById("empSecondName").value = json.secondName;
                document.getElementById("empPay").value = json.pay;
            }
        };
        httpRequest.open("POST", "getemployeedetails", true);
        httpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httpRequest.send("type=employee" );
    }

    function updateDetails() {
        //TODO check if any of the fields are blank
        var httpRequest = new XMLHttpRequest();
        httpRequest.onreadystatechange = function () {
            if (this.status === 200 && this.readyState === 4) {
                document.getElementById("detailsMessage").style.display = "block";
            }
        };
        var email = document.getElementById("empEmail").value;
        var firstName = document.getElementById("empFirstName").value;
        var secondName = document.getElementById("empSecondName").value;
        var pay = document.getElementById("empPay").value;
        httpRequest.open("POST", "updateemployeedetails", true);
        httpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httpRequest.send("email=" + email + "&firstName=" + firstName + "&secondName=" +
            secondName + "&pay=" + pay + "&type=employee");
    }

    function updatePassword() {
        //TODO check if any of the fields are blank
        var httpRequest = new XMLHttpRequest();
        httpRequest.onreadystatechange = function () {
            if (this.status === 200 && this.readyState === 4) {
                document.getElementById("passwordMessage").style.display = "block";
            } else if (this.status === 471 && this.readyState === 4) {
                document.getElementById("passwordMessage").innerText = "Error: Passwords do not match!"
                document.getElementById("passwordMessage").style.display = "block";
            } else if (this.status === 472 && this.readyState === 4) {
                document.getElementById("passwordMessage").innerText = "Error: Password must be longer than 6 characters!"
                document.getElementById("passwordMessage").style.display = "block";
            }
        };
        var password1 = document.getElementById("empPassword1").value;
        var password2 = document.getElementById("empPassword2").value;
        httpRequest.open("POST", "changeemployeepassword", true);
        httpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httpRequest.send("newPassword1=" + password1 + "&newPassword2=" + password2);
    }

</script>