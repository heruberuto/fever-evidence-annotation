<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Fact Extraction and VERification Annotation Interface</title>
    <link rel="stylesheet" href="../../../css/bootstrap.min.css">
    <link rel="stylesheet" href="../../../css/google-fonts.css">
    <link rel="stylesheet" href="../../../css/style.css">

    <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.7.2/angular.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.7.2/angular-route.js"></script>
    <script src="../../../js/app.js"></script>
    
</head>
<body ng-app="annotation">



    <nav class="navbar navbar-dark navbar-expand-lg" style="background-color: #506ea0;">
        <a class="navbar-brand" href="#">
            <img src="../../../img/logo_white_sm.png" width="66" height="30" class="d-inline-block align-top" alt="">
            <!--Fact Extraction and VERification-->
        </a>



        <div class="navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto justify-content-end">
                <li class="nav-item active">
                    <span class="nav-link" href="#">{{count}} annotations done</span>
                </li>

            </ul>
        </div>
    </nav>


    <div ng-view></div>



</body>

</html>