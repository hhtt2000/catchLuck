<%@ include file="/WEB-INF/view/com/config.jsp" %>
<!doctype html>
<html lang="ko">
<head>
	<jsp:include page="/WEB-INF/view/com/meta.jsp" />
	<jsp:include page="/WEB-INF/view/com/favicon.jsp" />
	<title>Login</title>
	<link rel="stylesheet" type="text/css" href="<spring:url value="/css/view/login-box.css" />">
	<script type="text/javascript" src="<spring:url value="/js/com/extends.js"/>" ></script>
	<script type="text/javascript">
		window.onload = function() {
			document.getElementById("login-box").style.margin = ((window.screen.height - 352) / 4) + 'px auto';
			document.getElementById("login-box").style.display = 'block';
			
    		var errorCode = "${sessionScope.error}";
    		<c:remove var="error" scope="session"/>
    		var username = "${sessionScope['j_username']}";
    		<c:remove var="j_username" scope="session"/>
    		
    		if (errorCode.trim().length > 0) {
    			if (errorCode.trim() == "7000") {
    				alert('<spring:message code="msg.alert.fail.login" />');
    			} else {
    				alert('<spring:message code="msg.alert.error.generic" />');
    			}
    		}
			
			//var form = document.getElementById("loginform");
        	var userName = document.getElementById("j_username");
        	var passwd = document.getElementById("j_password");
			
			userName.value = username;
			userName.onkeypress = keyPressHandler;
			passwd.onkeypress = keyPressHandler;
		};
		
		function login() {
        	var form = document.getElementById("loginform");
        	var userName = document.getElementById("j_username");
        	var passwd = document.getElementById("j_password");
        	
        	if (userName.value.trim().length == 0) {
        		<spring:message code="label.login.id" var="label" />
        		alert('<spring:message code="NotEmpty.general" arguments="${label}"/>');
        		userName.focus();
        		return false;
        	} 
        	
        	if (passwd.value.trim().length == 0) {
        		<spring:message code="label.login.password" var="label" />
        		alert('<spring:message code="NotEmpty.general" arguments="${label}" />');
        		passwd.focus();
        		return false;
        	} 
        	
        	form.submit();
        }
		
		function keyPressHandler(e) {
			var event = window.event ? window.event : e;
			if(event.keyCode == 13) {
				login();
			}
		}
	</script>
</head>

<body id="login">
	<form id="loginform" action="j_spring_security_check" method="post">
        <div id="align">
            <div id="login-box">
            	<br />
            	<br />
            	<br />
            	<br />
                <H2>Login</H2>
                <spring:message code="msg.login.welcome"/>&nbsp;
                <br />
                <div id="login-box-name" style="margin-top:20px;"><spring:message code="label.login.id"/>:</div>
    			<div id="login-box-field" style="margin-top:20px;">
    				<input id="j_username" name="j_username" class="form-login" title="Username" value="" size="30" maxlength="30" />
    			</div>
                <div id="login-box-name"><spring:message code="label.login.password"/>:</div>
    			<div id="login-box-field">
    				<input id="j_password" name="j_password" type="password" class="form-login" title="Password" value="" size="30" maxlength="30" />
    			</div>
    			<div class="login-box-options">
    				<input type="checkbox" id="_spring_security_remember_me" name="_spring_security_remember_me" value="on" checked> <spring:message code="label.login.auto"/>
    			</div>
    			<a href="<spring:url value="/qnabbs/list"/>"><img src="<spring:url value="/img/login-btn.png"/>" width="95" height="40" style="margin-left:190px;"/></a>   
    			<br />
    			<br />
                <a href="<spring:url value="/join/index"/>" style="margin-left:90px;"><spring:message code="label.join"/></a>
    			<!--  
    			<a href="<spring:url value="/join/findPwd" />" style="margin-left:30px;"><spring:message code="label.password.lost" /></a>
    			-->			
            </div>
        </div>
	</form>
</body>
</html>
