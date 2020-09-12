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
<body onload="loadToken()">

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
                <a class="nav-link" href="Employees.jsp" style="font-size: 15px">Employees</a>
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
    <div>
        <h2>Welcome to the easyshift app!</h2>
        <h2>We, at the easyshift team, wish to make the times you had to go back to work because you forgot to clock out, a thing of the past!</h2>

        <a href="About.jsp" role="button" class="btn btn1">About</a>
    </div>
</div>


<!---- third Container ---->

<div class="container-fluid text-center bg-1">
    <h2 class="box" style="color: #000000" id="tokenText"></h2>
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

    function loadToken() {
        var httpRequest = new XMLHttpRequest();
        httpRequest.onreadystatechange = function () {
            if (this.status === 200 && this.readyState === 4) {
                console.log(this.responseText);
                document.getElementById("tokenText").innerText = this.responseText;
            }
        };
        httpRequest.open("POST", "getcompanytoken", true);
        httpRequest.send();
    }
</script>