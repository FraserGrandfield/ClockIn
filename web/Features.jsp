<!DOCTYPE html>
<%@page session="false"%>
<html lang="en">

<head>
  <title>Portfolio</title>

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
        <a class="nav-link" onclick="LogOut()" href="index.jsp" style="font-size: 15px">Log Out</a>
      </li>
    </ul>

  </div>
</nav>

<!---- First Container ---->
<div class="container-fluid text-center bg-1">
  <h1>Portefølje</h1>
  <a href="home.jsp" role="button" class="btn btn1">Back</a>
</div>

<!---- second Container (carousel) ---->

<div class="container-fluid fixed img-center text-center">
  <div id="demo" class="carousel slide container" data-ride="carousel">

<ul class="carousel-indicators">
  <li data-target="#demo" data-slide-to="0" class=""></li>
  <li data-target="#demo" data-slide-to="1" class=""></li>
  <li data-target="#demo" data-slide-to="2" class=""></li>
  <li data-target="#demo" data-slide-to="3" class=""></li>
  <li data-target="#demo" data-slide-to="4" class=""></li>
  <li data-target="#demo" data-slide-to="5" class=""></li>
  <li data-target="#demo" data-slide-to="6" class=""></li>
  <li data-target="#demo" data-slide-to="7" class=""></li>
  <li data-target="#demo" data-slide-to="8" class=""></li>
  <li data-target="#demo" data-slide-to="9" class="active"></li>
</ul>

    <div class="carousel-inner text-center">

      <!-- Finner "carousel items" eller bildene, og gir dem en alt. spesifiserer også høyden på bildene i carouselen -->
      <div class="carousel-item">
        <img src="Images/PixelShot.jpg" alt="PixelShot logo design" height="500">
      </div>

      <div class="carousel-item">
        <img src="Images/Illuminati.jpg" alt="Outcome from doodeling for inspiration" height="500">
      </div>

      <div class="carousel-item">
        <img src="Images/Melonglasses.jpg" alt="Melonglases sticker design" height="500">
      </div>

      <div class="carousel-item">
        <img src="Images/James_From_Ludkins.jpg" alt="Profile picture for a person working at a card grading company" height="500">
      </div>

      <div class="carousel-item">
        <img src="Images/Napkin-Avatar.jpg" alt="Channel Avatar" height="500">
      </div>

      <div class="carousel-item">
        <img src="Images/NebulaLogo.jpg" alt="Nebula Logo" height="500">
      </div>

      <div class="carousel-item">
        <img src="Images/T-rexSticker.jpg" alt="T-rexSticker" height="500">
      </div>

      <div class="carousel-item">
        <img src="Images/SaleNade-Logo.jpg" alt="SaleNade-Logo" height="500">
      </div>

      <div class="carousel-item">
        <img src="Images/Sailboat.jpg" alt="Sailboat" height="500">
      </div>

      <div class="carousel-item active">
        <img src="Images/ImSoda.jpg" alt="ImSoda" height="500">
      </div>
    </div>

  <a class="carousel-control-prev" href="#demo" data-slide="prev">
    <span class="carousel-control-prev-icon"></span>
  </a>
  <a class="carousel-control-next" href="#demo" data-slide="next">
    <span class="carousel-control-next-icon"></span>
  </a>
  </div>
</div>

<!---- second Container  ---->

<div class="container-fluid bg-3 text-center">
  <div class="gallery">
    <img src="Images/PixelShot.jpg" alt="PixelShot logo">
    <img src="Images/Melonglasses.jpg" alt="Melonglasses sticker">
    <img src="Images/James_From_Ludkins.jpg" alt="avatar for James, a worker at Ludkins">
    <img src="Images/T-rexSticker.jpg" alt="T-rex sticker design">
    <img src="Images/Banner-logo.jpg" alt="banner type logo">
    <img src="Images/Napkin-Avatar.jpg" alt="Napkin avatar/logo">
    <img src="Images/Sailboat.jpg" alt="Seilbåt logo">
    <img src="Images/NebulaLogo.jpg" alt="Nebula brand logo">
    <img src="Images/SaleNade-Logo.jpg" alt="salenade logo">
    <img src="Images/Lowpoly-Album.jpg" alt="Lowpoly album cover logo">
    <img src="Images/ImSoda.jpg" alt="PixelShot logo">
    <img src="Images/Hodelosehons.jpg" alt="Hodelosehons logo">
    <img src="Images/TheChobbit.jpg" alt="TheChobbit avatar/logo">
    <img src="Images/MrMozy.jpg" alt="MrMozy avatar/logo">
    <img src="Images/ProfitLogo.jpg" alt="Profit logo">
    <img src="Images/Illuminati.jpg" alt="Illuminati logo">
    <img src="Images/GameOver.jpg" alt="GameOver russelogo">
    <img src="Images/Zeus.jpg" alt="Zeus russelogo">
    <img src="Images/Uprising.jpg" alt="Uprising russelogo">
    <img src="Images/Redemption.jpg" alt="Redemption russelogo">
  </div>
</div>




<!---- Footer ---->
<footer class="container-fluid bg-red text-center">
  <p>Made by Mats Birkeland</p>
</footer>

</body>
</html>

<script>
  function LogOut() {
    var httpRequest = new XMLHttpRequest();
    httpRequest.open("POST", "logout", true);
    httpRequest.send();
  }
</script>