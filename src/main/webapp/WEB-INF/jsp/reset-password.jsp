<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head th:replace="fragments :: html_head(removeTag='tag')" >
    <meta charset="UTF-8" />
    <title>Person List</title>

</head>
<body>
<div>
    <h2>Reset Password</h2>
</div>

<form th:action="@{/reset_password}" method="post" style="max-width: 350px; margin: 0 auto;">

    <input type="hidden" name="otp" value="${otp}" />
    <div class="border border-secondary rounded p-3">
        <div>
            <p>
                <input type="password" name="password" id="password" class="form-control"
                       placeholder="Enter your new password" required autofocus />
            </p>
            <p>
                <input type="password" id="confirm_password" name="confirm_password" class="form-control" placeholder="Confirm your new password"
                       oninput="checkPasswordMatch();" />
            </p>
            <p class="text-center">
                <input type="submit" id="submit" value="Change Password" class="btn btn-primary" disabled/>
            </p>
        </div>
    </div>
</form>
<script type="text/javascript">
    function checkPasswordMatch(){

        var ok = true;
        if(document.getElementById('password').value == document.getElementById('confirm_password').value)
        {
            document.getElementById('submit').disabled = false;
        }
        else {
            document.getElementById('submit').disabled = true;
        }
        return ok;
    }
</script>
</body>
</html>