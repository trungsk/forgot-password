<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8" />
    <title>Welcome</title>
    <link rel="shortcut icon" href="#" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>
<h1>Welcome</h1>
<div>
    <h2>Chúng tôi đã gửi một mã OTP đến ${vc}</h2>
    <h2>Hãy kiểm tra hộp thư và sử dụng mã OTP để đặt lại mật khẩu</h2>
    <h2>${email}</h2>
    <h2>${expired}</h2>
    <h2>${aa}</h2>

</div>

<form th:action="@{/otp-validation}" method="post" style="max-width: 350px; margin: 0 auto;">

    <div>
        <p>
            <input type="text" name="aa" id="ok" class="form-control"
                   placeholder="Enter your otp" required autofocus />
        </p>

        <p class="text-center">
            <input type="submit" id="submit" value="Confirm" class="btn btn-primary"/>
        </p>
    </div>
</form>

</body>

</html>