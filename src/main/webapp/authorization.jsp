<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login page</title>
    <link href="styles/my_style.css" rel="stylesheet">
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-offset-5 col-md-3">
            <div class="form-login">
                <h4>Welcome back.</h4>
                <form accept-charset="UTF-8" role="form" action="test" method="post">
                    <input type="text" name="email" class="form-control input-sm chat-input" placeholder="username" />
                    <br>
                    <input type="password" name="password" class="form-control input-sm chat-input" placeholder="password" />
                    <br>
                    <div class="wrapper">
                    <input type="submit" class="btn btn-primary btn-md" value="Login"/>
                    </div>
                    <Label><br>${applicationScope.invalid_message}</Label>
                </form>
            </div>

        </div>
    </div>
</div>

<br><br>

</body>
</html>
