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
            document.getElementById("logInContainer").style.display = "block";
            document.getElementById("compLog").style.display = "none";
            document.getElementById("empLog").style.display = "block";
        }
0
        function compShowLogIn() {
            document.getElementById("logInContainer").style.display = "block";
            document.getElementById("empLog").style.display = "none";
            document.getElementById("compLog").style.display = "block";
        }

        function empLogin() {
            //TODO Add check if fields are blank
            var httpRequest = new XMLHttpRequest();
            var email = document.getElementById("empEmail").value;
            var password = document.getElementById("empPass").value;
            httpRequest.onreadystatechange = function () {
                if (this.status === 200 && this.readyState === 4) {
                    console.log("Logged in");
                    window.location = "http://localhost:8080/home.jsp"
                } else if (this.status === 401 && this.readyState === 4) {
                    console.log("Error")
                    document.getElementById("errorText").style.display = "block";
                }
            };
            httpRequest.open("POST", "employeecheckpassword", true);
            httpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            httpRequest.send("email=" + email + "&password=" + password);
        }

        function compLogin() {
            //TODO Add check if fields are blank
            var httpRequest = new XMLHttpRequest();
            var email = document.getElementById("compEmail").value;
            var password = document.getElementById("compPass").value;
            httpRequest.onreadystatechange = function () {
                if (this.status === 200 && this.readyState === 4) {
                    console.log("Logged in");
                    window.location = "http://localhost:8080/DashBoard.jsp"
                } else if (this.status === 401 && this.readyState === 4) {
                    document.getElementById("errorText").style.display = "block";
                }
            };
            httpRequest.open("POST", "companycheckpassword", true);
            httpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            httpRequest.send("email=" + email + "&password=" + password);
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
        <a role="button" class="btn btn1" onclick="empShowLogIn()">Employee Log In</a>
        <a role="button" class="btn btn1" onclick="compShowLogIn()">Company Log In</a>
    </div>
</div>


<!---- third Container ---->
<div class="container-fluid text-center bg-1" style="display: none" id="logInContainer">
    <div id="empLog" style="display: none">
        <h2>Email:</h2>
        <input class="input" type="text" id="empEmail"><br>
        <h2>Password:</h2>
        <input class="input" type="password" id="empPass"><br>
        <a role="button" class="btn btn1" onclick="empLogin()">Log In</a>
    </div>
    <div id="compLog" style="display: none">
        <h2>Company Email:</h2>
        <input class="input" type="text" id="compEmail"><br>
        <h2>Password:</h2>
        <input class="input" type="password" id="compPass"><br>
        <a role="button" class="btn btn1" onclick="compLogin()">Log In</a>
    </div>
    <h2 style="display: none" id="errorText" class="errorBox">Your email or password is incorrect</h2>
</div>
<!-- Footer -->
<footer class="container-fluid bg-logo-green text-center">
    <h2>Easyshift.app</h2>
</footer>

</body>

</html>

