<!DOCTYPE html>
<%@page session="false"%>
<html lang="en">

<head>
    <title>Calendar</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>

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
                <a class="nav-link" href="Calendar.jsp" style="font-size: 15px">Calendar</a>
            </li>

            <li class="nav-item">
                <a class="nav-link" onclick="LogOut()" href="index.jsp" style="font-size: 15px">Log Out</a>
            </li>
        </ul>

    </div>
</nav>

<!---- First Container ---->

<div class="container-fluid text-center bg-1">

</div>

<!---- Second Container ---->
<div class="container-fluid text-center bg-4">

    <!-- Lager 4 linjer med text hvor 3 av tekstlinjene er rammet inne ved hjelp av class="box" -->
    <h3>First Date</h3>
    <input type="date" id="firstDate">
    <br>
    <h3>Second Date</h3>
    <input type="date" id="secondDate">
    <br>
    <a role="button" class="btn btn1" onclick="getDate()">Get Shift</a>
    <br>
    <h2 class="box" style="color: #000000" id="shiftText"></h2>


    <h1 style="color: #000000">Kontakt meg her</h1>
    <br>
    <h2 class="box" style="color: #000000">Gmail: mats.birkeland@gmail.com</h2>
    <br>
    <h2 class="box" style="color: #000000">Tlf: +47 97069041</h2>
    <br>
    <h2 class="box" style="color: #000000">Adr: Grimstad, Jon Lilletuns Vei</h2>

</div>

<!---- Footer ---->
<footer class="container-fluid bg-red text-center">
    <p>Made by Mats Birkeland, Fraser Grandfield</p>
</footer>

</body>
</html>

<script>
    function LogOut() {
        var httpRequest = new XMLHttpRequest();
        httpRequest.open("POST", "logout", true);
        httpRequest.send();
    }

    function getDate() {
        var httpRequest = new XMLHttpRequest();
        var firstDate = document.getElementById("firstDate").value;
        var secondDate = document.getElementById("secondDate").value;

        httpRequest.onreadystatechange = function () {
            if (this.status === 200 && this.readyState === 4) {
                document.getElementById("shiftText").innerText = this.responseText;
            }
        };

        httpRequest.open("POST", "getshifts", true);
        httpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httpRequest.send("firstDate=" + firstDate + "&secondDate=" + secondDate);
    }

</script>

