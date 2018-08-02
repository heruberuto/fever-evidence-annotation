<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Fact Extraction and VERification Annotation Interface</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/google-fonts.css">
    <link rel="stylesheet" href="css/style.css">

    <script src="https://www.google.com/recaptcha/api.js?onload=onloadCallback" async defer></script>

    <script type="text/javascript">
        var onloadCallback = function() {
            //grecaptcha.render();
            console.log("grecaptcha is ready!");
        };

        var enableLoginButton = function() {
            document.getElementById("loginbtn").disabled = false;
        }
    </script>

</head>
<body>

<nav class="navbar navbar-dark navbar-expand-lg" style="background-color: #506ea0;">
    <a class="navbar-brand" href="#">
        <img src="img/logo_white_sm.png" width="66" height="30" class="d-inline-block align-top" alt="">
        <!--Fact Extraction and VERification-->
    </a>
</nav>

<section class="mb-5 buffer" id="login">
    <div class="container">
        <div class="row">
            <div class="col-xl-10">
                <h2 class="text-center mb-4">Annotation System</h2>
                <p>We are looking for volunteers to help grow the FEVER dataset by labelling new evidence identified from the shared task. You can help as much or as little as you wish by providing your judgement to the suitability of evidence for a claim. Each evidence labelling task should take less than a minute and you are free to do as few or as many tasks as you wish.
                </p>
                <p>
                    Register or Log in the the Annotation System to get started!
                </p>

            </div>

        </div>

        <form action="" method="POST">

            <div class="row buffer">
                <div class="col-sm-3 col-md-3 col-lg-3 col-xl-3">
                    <label for="email_input">
                        Email address:
                    </label>
                </div>


                <div class="col-sm-7 col-md-7 col-lg-7 col-xl-7">
                    <input class="form-control input-large" id="email_input" type="text" name="email">
                </div>
            </div>

            


            <div class="row buffer">
                <div class="col-sm-3 col-md-3 col-lg-3 col-xl-3">
                    <label for="verification_div">
                        Verification:
                    </label>
                </div>


                <div class="col-sm-7 col-md-7 col-lg-7 col-xl-7">
                    <div id="verification_div" class="g-recaptcha" data-sitekey="6LfpWmYUAAAAAEa_QXv_lCvBOIqcZgMDvSiHGwbh" data-callback="enableLoginButton"></div>
                </div>

            </div>

            <div class="row buffer">
            <div class="col-sm-10 col-md-10 col-lg-10 col-xl-10">
                <input class="btn btn-lg btn-outline-secondary" id="loginbtn" disabled="disabled" type="submit" value="LOG IN / REGISTER">
                </div>
            </div>


        </form>
    </div>
</section>





<footer class="footer bg-light">
    <div class="container">
        <div class="row">
            <a href="https://join.slack.com/t/fever2018/shared_invite/enQtMzgxMDI2ODQyNDQ4LTNlMGQwZDc0NmFkODgwMGMzYzdmMWI0MjNkMmE0Y2NiYzhlZGMyN2MzMjUxZjEwZDZhMmUyZjIwZDhhZmM2Nzg">Contact us on Slack</a>
        </div>
    </div>
</footer>

</body>
</html>