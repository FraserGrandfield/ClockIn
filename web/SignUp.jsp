<!DOCTYPE html>
<html lang="en">
<%@page session="false"%>
<head>
    <title>Home</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>

    <script>

        function empShowLogIn() {
            document.getElementById("compSign").style.display = "none";
            document.getElementById("empSign").style.display = "block";
            document.getElementById("errorText").style.display = "none";
        }

        function compShowLogIn() {
            document.getElementById("empSign").style.display = "none";
            document.getElementById("compSign").style.display = "block";
            document.getElementById("errorText").style.display = "none";
        }

        function empSignUp() {
            var httpRequest = new XMLHttpRequest();
            var firstName = document.getElementById("empFirstName").value;
            var secondName = document.getElementById("empSecondName").value;
            var email = document.getElementById("empEmail").value;
            var password1 = document.getElementById("empPassword1").value;
            var password2 = document.getElementById("empPassword2").value;
            var pay = document.getElementById("pay").value;
            var token = document.getElementById("empToken").value;
            httpRequest.onreadystatechange = function () {
                if (this.status === 200 && this.readyState === 4) {
                    console.log("Signed up");
                    window.location = "http://localhost:8080/LogIn.jsp"
                } else if (this.status === 470 && this.readyState === 4) {
                    console.log("Error")
                    document.getElementById("errorText").style.display = "block";
                    document.getElementById("errorText").innerText = "Error: Invalid email";
                } else if (this.status === 471 && this.readyState === 4) {
                    console.log("Error")
                    document.getElementById("errorText").style.display = "block";
                    document.getElementById("errorText").innerText = "Error: Passwords do not match";
                } else if (this.status === 472 && this.readyState === 4) {
                    console.log("Error")
                    document.getElementById("errorText").style.display = "block";
                    document.getElementById("errorText").innerText = "Error: Password must be more than 6 characters";
                } else if (this.status === 473 && this.readyState === 4) {
                    console.log("Error")
                    document.getElementById("errorText").style.display = "block";
                    document.getElementById("errorText").innerText = "Error: Invalid token";
                } else if (this.status === 474 && this.readyState === 4) {
                    console.log("Error")
                    document.getElementById("errorText").style.display = "block";
                    document.getElementById("errorText").innerText = "Error: Email already exists";
                }else if (this.status === 475 && this.readyState === 4) {
                    console.log("Error")
                    document.getElementById("errorText").style.display = "block";
                    document.getElementById("errorText").innerText = "Error: Please fill in all boxes";
                } else if (this.readyState === 4){
                    document.getElementById("errorText").style.display = "block";
                    document.getElementById("errorText").innerText = "Error: Internal server issue, try again later";
                }
            };
            httpRequest.open("POST", "createemployee", true);
            httpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            httpRequest.send("firstName=" + firstName + "&secondName=" + secondName + "&email=" + email + "&firstPassword="
                + password1 + "&secondPassword=" + password2 + "&pay=" + pay + "&token=" + token);
        }

        function compSignUp() {
            //TODO send error message to be displayed in the backend
            var httpRequest = new XMLHttpRequest();
            var name = document.getElementById("compName").value;
            var email = document.getElementById("compEmail").value;
            var password1 = document.getElementById("compPassword1").value;
            var password2 = document.getElementById("compPassword2").value;
            httpRequest.onreadystatechange = function () {
                if (this.status === 200 && this.readyState === 4) {
                    console.log("Signed up");
                    window.location = "http://localhost:8080/LogIn.jsp"
                } else if (this.status === 470 && this.readyState === 4) {
                    console.log("Error")
                    document.getElementById("errorText").style.display = "block";
                    document.getElementById("errorText").innerText = "Error: Invalid email";
                } else if (this.status === 471 && this.readyState === 4) {
                    console.log("Error")
                    document.getElementById("errorText").style.display = "block";
                    document.getElementById("errorText").innerText = "Error: Passwords do not match";
                } else if (this.status === 472 && this.readyState === 4) {
                    console.log("Error")
                    document.getElementById("errorText").style.display = "block";
                    document.getElementById("errorText").innerText = "Error: Password must be more than 6 characters";
                } else if (this.status === 474 && this.readyState === 4) {
                    console.log("Error")
                    document.getElementById("errorText").style.display = "block";
                    document.getElementById("errorText").innerText = "Error: Email already exists";
                } else if (this.status === 475 && this.readyState === 4) {
                    console.log("Error")
                    document.getElementById("errorText").style.display = "block";
                    document.getElementById("errorText").innerText = "Error: Please fill in all boxes";
                } else if (this.readyState === 4){
                    document.getElementById("errorText").style.display = "block";
                    document.getElementById("errorText").innerText = "Error: Internal server issue, try again later";
                }
            };
            httpRequest.open("POST", "createcompany", true);
            httpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            httpRequest.send("companyName=" + name + "&email=" + email + "&firstPassword=" + password1 + "&secondPassword="
                + password2);
        }
    </script>

    <!----Google fonts---->
    <link href="https://fonts.googleapis.com/css?family=Inter&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Dancing+Script&display=swap" rel="stylesheet">

    <link rel = "stylesheet" type = "text/css" href = "custom.css" />
</head>
<body>

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
                <a class="nav-link" href="LogIn.jsp" style="font-size: 15px">Log In</a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="SignUp.jsp" style="font-size: 15px">Sign up</a>
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
    <div>
        <a role="button" class="btn btn1" onclick="empShowLogIn()">Employee Sign In</a>
        <a role="button" class="btn btn1" onclick="compShowLogIn()">Company Sign In</a>
    </div>
</div>

<div class="container-fluid text-center bg-1">
    <div id="empSign" style="display: none">
        <h2>First name:</h2>
        <input class="input" type="text" id="empFirstName"><br>
        <h2>Second name:</h2>
        <input class="input" type="text" id="empSecondName"><br>
        <h2>Email:</h2>
        <input class="input" type="text" id="empEmail"><br>
        <h2>Password:</h2>
        <input class="input" type="password" id="empPassword1"><br>
        <h2>Confirm password:</h2>
        <input class="input" type="password" id="empPassword2"><br>
        <h2>Hourly pay:</h2>
        <input class="input" type="text" id="pay"><br>
        <h2>Company token:</h2>
        <input class="input" type="text" id="empToken"><br>
        <a role="button" class="btn btn1" onclick="empSignUp()">Sign Up</a>
    </div>

    <div id="compSign" style="display: none">
        <h2>Company Name:</h2>
        <input class="input" type="text" id="compName"><br>
        <h2>Company Email:</h2>
        <input class="input" type="text" id="compEmail"><br>
        <h2>Password:</h2>
        <input class="input" type="password" id="compPassword1"><br>
        <h2>Confirm password:</h2>
        <input class="input" type="password" id="compPassword2"><br>
        <a role="button" class="btn btn1" onclick="compSignUp()">Sign Up</a>
    </div>
    <h2 style="display: none" id="errorText" class="errorBox">Your email or password is incorrect</h2>
</div>


<!-- Footer -->
<footer class="container-fluid bg-logo-green text-center">
    <h2>Easyshift.app</h2>
</footer>

</body>

</html>