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

<body onload="loadEmployees()">
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

<div class="container-fluid text-center bg-1">

</div>

<!---- Second Container ---->
<div class="container-fluid text-center bg-4" style="width: 100%">
    <div class="scroll" style="width: 30%; float: left;">
        <ul class="js-todo-list"></ul>
    </div>
    <a class="box" id="employeeDetails" style="display: none; margin-left: 33%; height: 800px">
        <h3>Email:</h3>
        <input class="input inp-1" type="text" id="empEmail"><br>
        <h3>First name:</h3>
        <input class="input inp-1" type="text" id="empFirstName"><br>
        <h3>Second Name name:</h3>
        <input class="input inp-1" type="text" id="empSecondName"><br>
        <h3>Pay (Hourly):</h3>
        <input class="input inp-1" type="text" id="empPay"><br>
        <h3 role="button" class="btn btn3" onclick="updateDetails()">Update Details</h3>
        <h3 class="box" style="display: none" id="detailsMessage">Details Updated!</h3>
        <h3>First Date</h3>
        <input type="datetime-local" id="firstDate" value="2018-06-12T19:30" min="2018-06-07T00:00">
        <br>
        <h3>Second Date</h3>
        <input type="datetime-local" id="secondDate" value="2018-06-12T19:30" min="2018-06-07T00:00">
        <br>
        <h2 role="button" class="btn btn3" onclick="getDate()">Get Shift</h2>
        <br>
        <h2 style="color: #000000" id="shiftText"></h2>
    </a>
</div>

<!---- Footer ---->
<footer class="container-fluid bg-red text-center">
    <p>Made by Mats Birkeland, Fraser Grandfield</p>
</footer>

</body>
</html>

<script>
    let employeesList = [];
    let selectedEmail = "";

    function LogOut() {
        var httpRequest = new XMLHttpRequest();
        httpRequest.open("POST", "logout", true);
        httpRequest.send();
    }

    function loadEmployees() {
        var httpRequest = new XMLHttpRequest();
        httpRequest.onreadystatechange = function () {
            if (this.status === 200 && this.readyState === 4) {
                employeesList = this.responseText.split("|");
                loopRender();
            }
        };
        httpRequest.open("POST", "getallemployeenames", true);
        httpRequest.send();
    }

    function loopRender() {
        for (var i = 0; i < employeesList.length - 1; i++) {
            renderList(employeesList[i]);
        }
    }

    function renderList(name) {
        var employeeList = name.split("#");
        const list = document.querySelector(".js-todo-list");
        const node = document.createElement("li");
        node.setAttribute("class", "todo-item js-tick");
        node.innerText = employeeList[0];
        node.setAttribute("id", employeeList[1]);
        list.append(node);
    }
    const list = document.querySelector(".js-todo-list");
    list.addEventListener("click", event => {
        if (event.target.classList.contains("js-tick")) {
            document.getElementById("employeeDetails").style.display = "block";
            selectedEmail = event.target.id;
            getEmployeeDetails(event.target.id);

            var today = new Date();
            var month = today.getMonth()+1;
            var date = today.getDate();
            var hours = today.getHours();
            var min = today.getMinutes();
            var sec = today.getSeconds();
            if(month<10){month = '0'+month}
            if(date<10){date = '0'+date}
            if(hours<10){hours = '0'+hours}
            if(min<10){min = '0'+min}
            if(sec<10){sec = '0'+sec}

            var dateTime = today.getFullYear()+'-'+ month +'-'+ date + "T";
            dateTime += hours + ":" + min + ":" + sec;
            document.getElementById("firstDate").value = dateTime;
            document.getElementById("secondDate").value = dateTime;
        }
    });

    function getEmployeeDetails(email) {
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
        httpRequest.send("email=" + email + "&type=company");
    }

    function updateDetails() {
        //TODO check if any of the fields are blank
        var email = document.getElementById("empEmail").value;
        var httpRequest = new XMLHttpRequest();
        httpRequest.onreadystatechange = function () {
            if (this.status === 200 && this.readyState === 4) {
                document.getElementById("detailsMessage").style.display = "block";
                employeesList = [];
                document.querySelectorAll(".todo-item").forEach(el => el.remove());
                loadEmployees();
            }
        };
        var firstName = document.getElementById("empFirstName").value;
        var secondName = document.getElementById("empSecondName").value;
        var pay = document.getElementById("empPay").value;
        httpRequest.open("POST", "updateemployeedetails", true);
        httpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httpRequest.send("oldEmail=" + selectedEmail + "&email=" + email + "&firstName=" + firstName + "&secondName=" +
            secondName + "&pay=" + pay + "&type=company");
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
        console.log(selectedEmail);
        httpRequest.open("POST", "getshifts", true);
        httpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httpRequest.send("firstDate=" + firstDate + "&secondDate=" + secondDate + "&employeeEmail=" + selectedEmail);
    }
</script>