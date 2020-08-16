<!DOCTYPE html>
<%@page session="false"%>w
<html lang="en">

<head>
  <title>About</title>

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
    </ul>

  </div>
</nav>

<!---- First Container ---->

<div class="container-fluid text-center fixed2">
  <img src="Images/About-easyshift.png" class="img-responsive" style="width:20%" alt="About-easyshift">
</div>

  <!---- Second Container---->

<div class="container-fluid row bg-3">
  <div class="col-md-6">
    <h1>Our vission</h1>
    <h2>-Our main goal is to make your working day easier.</h2>
    <h2>-We envisioned and desired to make something useful.</h2>
    <h2>-We seek to improve and simplify the way that you clock in and out of work.</h2>
    <h2>-We hope to make every day a little less stressful while also saving you time.</h2>
  </div>
  <div class="col-md-12">
    <img src="Images/Our_vission.jpg" class="img-fluid" alt="Our vission image">
  </div>
</div>


<!---- Third Container ---->
<div class="container">
  <div class="row container-fluid">

    <!-- Lager bilde som relaterer til temaet og printer tekst på motsatt side -->
    <div class="col-md-6">
      <img src="Images/Utdanning-bilde.png" class="img-fluid" alt="Graduation hat">
    </div>

    <div class="col-md-6">
      <h1 style="color: black">Utdanning</h1>
      <li style="color: black">Fullført medier og kommunikasjon med studiekompetanse, ved Tangen VGS.</li>
      <li style="color: black">Studerer medieteknologi og design ved UIA, Grimstad</li>
    </div>

  </div>
</div>

<!---- Fourth Container ---->
<div class="container">
  <div class="row container-fluid">

    <!-- Lager bilde som relaterer til temaet og printer tekst på motsatt side, det blir også printet ut en knapp til Portfolio siden-->
    <div class="col-md-6">
      <h1 style="color: black">Erfaring</h1>
      <li style="color: black">Jeg har over 7 år med erfaring innen grafisk design.</li>
      <li style="color: black">Har laget logo for "les kunst" prosjektet ved Kristiansand kunsthall.</li>
      <li style="color: black">Har laget mangfoldige russelogoer til fornøyde russegrupper.</li>
      <li style="color: black">Jeg har laget ikoner og tegnet grafikk til mangfoldige projekter</li>
      <a href="portefolje.html" role="button" class="btn btn2">Portfolio</a>
    </div>

    <div class="col-md-6">
      <img src="Images/Erfaring-bildet.jpg" class="img-fluid" alt="Logo samling">
    </div>

  </div>
</div>


<!---- Footer ---->
<footer class="container-fluid bg-logo-green text-center">
  <h2>Easyshift.app</h2>
</footer>

</body>
</html>
