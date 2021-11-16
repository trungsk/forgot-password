<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Person List</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<div>
    <h2>Forgot Password</h2>
    <h2>${err}</h2>
    <h2>${email}</h2>
    <h2>${expired}</h2>

</div>



<form th:action="@{/forgot-password}" method="post" style="max-width: 420px; margin: 0 auto;">
    <div class="border border-secondary rounded p-3">
        <div>
            <p>We will be sending a reset password link to your email.</p>
        </div>
        <div>
            <p>
                <input type="email" name="email" class="form-control" placeholder="Enter your e-mail" required autofocus/>
            </p>
            <p class="text-center">
                <input type="submit" value="Send" class="btn btn-primary" />
            </p>
        </div>
    </div>
</form>
</body>
</html>