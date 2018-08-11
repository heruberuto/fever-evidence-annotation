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
                <h2 class="mb-4">Annotation System</h2>
                <p>
                    We are looking for volunteers to help grow the FEVER dataset by labelling new evidence identified from the shared task.
                    You can help as much or as little as you wish by providing your judgement to the suitability of evidence for a claim.
                    Each evidence labelling task should take approximately one minute and you are free to do as few or as many tasks as you wish.
                </p>
                <p>
                    This project has received ethical approval from The University of Sheffield (reference 21406). You are required to read the following information:
                </p>
            </div>
        </div>


        <div class="row">
            <div class="col-xl-10">
                <h3 class="mb-4">About FEVER</h3>
                <p>
                    With billions of individual pages on the web providing information on almost every conceivable topic,
                    we should have the ability to collect facts that answer almost every conceivable question.
                    However, only a small fraction of this information is contained in structured sources (Wikidata, Freebase, etc.) – we are therefore limited by our ability to
        transform free-form text to structured knowledge. There is, however, another problem that
        has become the focus of a lot of recent research and media coverage: false information
        coming from unreliable sources.
                </p>
                <p>
        In an effort to jointly address both problems, herein we propose a workshop promoting
        research in joint Fact Extraction and VERification (FEVER). We aim for FEVER to be a
        long-term venue for work in verifiable knowledge extraction and to stimulate progress in this
        direction, we will also host the FEVER Challenge, an information verification shared task on
        the dataset that we plan to release as part of the challenge.

                </p>
                <h3 class="mb-4">What are the aims of the study?</h3>
                <p>
        In the shared task, we collected a large number of outputs generated from these computer
        systems and scored the submissions using our dataset. The dataset is not complete and we
        believe that these system submissions have identified new pieces of evidence from
        Wikipedia that could be added to the FEVER dataset to make it larger for future
        competitions.
                </p>
                <p>
        We wish to use members of the wider research community as expert data analysts to label
        whether these computer-generated system outputs are correct and should be added to a
        new dataset or whether the outputs are incorrect and should be discarded.
        You will act as the role of a data analyst and will be given appropriate guidelines for coding
        the data you are given. Once annotation is complete, the newly coded data will be
        processed into a new dataset that will be released publicly under a Creative Commons
        license.
                </p>
                <p>
        Building a more complete dataset will enable the research community to better understand
        how artificial intelligence systems can reason about information and more accurately score
        the submissions to the FEVER shared task.
                </p>
                <h3 class="mb-4">Who can take part?</h3>
                <p>
        This survey is open to any willing volunteer from the research community.
                </p>

                <h3 class="mb-4">What will happen if you agree to take part?</h3>
                <p>
        Each data analysis task will take less than a minute. You are free to complete as many data
        analysis tasks as you wish.
                </p>
                <p>
                    For each data analysis task, we will present you with an encyclopaedic claim which may be
        true or false as well as the evidence which the artificial intelligence system has identified.
        You will be asked a series of questions about the suitability of the evidence which will be
        used to determine whether the evidence will be added to the new dataset.
                </p>
                <p>
        The data annotations you provide will be anonymous and will be shared publicly in
        the form of a new dataset that will be released under a creative commons license.
                </p>
                <p>
        This data analysis task is entirely voluntary and all the information that we collect about you
        during the course of the research will be kept strictly confidential and will only be accessible
        to members of the research team. You will not be able to be identified in any reports or
        publications.
                </p>
                <p>
        You may withdraw from the survey at any point by closing your browser. Withdrawal from the
        study is only possible while completing the survey as once submitted it will be impossible to
        remove the anonymous data. All partial responses will be destroyed.
                </p>
                <p>
        The survey has received full ethical approval from the University of Sheffield Department of
        Computer Science (Principal Investigator: Dr Andreas Vlachos (<a href="mailto:a.vlachos@sheffield.ac.uk">a.vlachos@sheffield.ac.uk</a>))
        Any questions or complaints about this survey should initially be sent to James Thorne (<a href="j.thorne@sheffield.ac.uk">j.thorne@sheffield.ac.uk</a>)
        or the Principle Investigator. If you are unhappy with their
        response, then please contact Prof. Guy J Brown (<a href="mailto:g.j.brown@sheffield.ac.uk">g.j.brown@sheffield.ac.uk</a>) (Head of
        Department).
                </p>
                <p>
        According to data protection legislation, we are required to inform you that the legal basis we
        are applying in order to process your personal data is that ‘processing is necessary for the
        performance of a task carried out in the public interest’ (Article 6(1)(e)). Further information
        can be found in the University’s Privacy Notice:
                    <a href="https://www.sheffield.ac.uk/govern/data-protection/privacy/general">https://www.sheffield.ac.uk/govern/data-protection/privacy/general</a>
                </p>
                <p>
        The University of Sheffield will act as the Data Controller for this study. This means that the
        University is responsible for looking after your information and using it properly. Any
        enquiries related to the use of your data are addressed via the University’s Privacy
        Statement which can be found here:

                    <a href="https://www.sheffield.ac.uk/govern/data-protection/privacy/general">https://www.sheffield.ac.uk/govern/data-protection/privacy/general</a>
                </p>

                <p>
                    Thank you for taking the time to read this.
                    If you are happy to take part in the study please confirm this by entering your email address in the field below.
                </p>

                <h3>Consent</h3>

                <p>By logging into this system, you acknowledge the following: </p>
                <ul>
                    <li>I have read and understood the project information provided on this page</li>
                    <li>I have been given the opportunity to ask questions about the project and
                        understand that questions can be asked at any time by email</li>
                    <li>I understand that taking part in the project will include annotating evidence using
                        this online system and agree to participate</li>
                    <li>I understand that my taking part is voluntary and that I can withdraw from the study
                        by closing my web browser and that I do not have to give any reasons for why I no
                        longer want to take part and there will be no adverse consequences if I choose to
                        withdraw</li>
                    <li>I understand that the information I provide in the form of data annotations will be
                        anonymized and may be published publicly under a Creative Commons 4.0 License and
                        give my permission for this</li>
                </ul>
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