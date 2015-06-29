<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/view/com/config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><spring:message code="label.title" /></title>
	<jsp:include page="/WEB-INF/view/com/meta.jsp" />
	<jsp:include page="/WEB-INF/view/com/favicon.jsp" />
	<jsp:include page="/WEB-INF/view/com/css.jsp" />
	<jsp:include page="/WEB-INF/view/com/script.jsp" />
	<script type="text/javascript">
/*	
	String.prototype.trim = function(){
		return this.replace(/(^\s*)|(\s*$)/gi, "");
	};
	*/
	
	String.prototype.checkLength = function(min, max){
		return this.trim().length < min || this.trim().length > max;
	};

			
	
		$(function(){
			
			$.validator.addMethod(
					"regex",
					function(value, element, regexp){
						var re = new RegExp(regexp);
						return this.optional(element) || re.test(value);
					},
					"Please check your input."
		);
			
			$("#joinForm").validate({
				rules : {
					cid : {
						required :true,
						rangelength : [6, 16] 
					},
					passwd : {
						required :true,
						rangelength : [6, 20]
					},
					repasswd : {
						equalTo : passwd
					},
					email : {
						required : true,
						maxlength : 50,
						email : true
					},
					cellPhone : {
						required : true,
						maxlength : 15,
						regex : /^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/
					},
					name : {
						required : true,
						rangelength : [2, 16]
					},
					/* 주소 조건 필요한거???????? */
					address : {
			//			required : true
					}
					
				},
				messages : {
					cid : {
						<spring:message code="label.id" var="cid" />
					//	required : "<spring:message code="NotEmpty.general" arguments="${cid}"/>",
					//	rangelength : "<spring:message code="Length.general" arguments="${cid},6,16"/>",
					},
					passwd : {
						<spring:message code="label.password" var="passwd" />
					//	required : "<spring:message code="NotEmpty.general" arguments="${passwd}"/>",
					//	rangelength : "<spring:message code="Length.general" arguments="${passwd},6,20"/>"
					},
					repasswd : {
						equalTo : "<spring:message code="EqualTo.password" />"
					},
					email : {
						<spring:message code="label.email" var="email" />
					//	required : "<spring:message code="NotEmpty.general" arguments="${email}"/>",
					//	maxlength : "<spring:message code="Length.max.general" arguments="${email},50"/>",
					//	email : "<spring:message code="Email.email" />"
					},
					cellPhone : {
						<spring:message code="label.cellphone" var="cellPhone" />
					//	required : "<spring:message code="NotEmpty.general" arguments="${cellPhone}"/>",
					//	maxlength : "<spring:message code="Length.max.general" arguments="${cellPhone},15"/>",
						/*regex : "<spring:message code="Invalid.format.general" arguments="${ccellphone},010-1234-5678"/>"*/
					},
					name : {
						<spring:message code="label.name" var="name" />
					//	required : "<spring:message code="NotEmpty.general" arguments="${name}" />",
					//	rangelength : "<spring:message code="Length.general" arguments="${name}, 2, 16" />"
					},
					address : {
						//required : "<spring:message code="NotEmpty.general" arguments="${caddress}" />"
					}
				}
			});
			
			$("input[name=regBtn]").bind('click', function(){
				if($("#joinForm").valid()){
					$.get('<spring:url value="/join/checkValidation" />',
						{"cid" : $("#cid").val()},
						function(result){
						//	if(/*회원가입 버튼 눌렀을 때 아이디 중복체크버튼을 누르지 않았었을때*/){
						//		
						//	}
							if(!result.data.cid){
								alert("<spring:message code="Duplicate.cid" />");
							} else{
							//회원가입 진행
							$.post('<spring:url value="/join/create" />',
									$("#joinForm").serialize(),
									function (result){
										if(result.data != null && result.data.error !=undefined){
											validation.showMessage(result.data.error, "td#label_");
											
										}
										else{
											alert("<spring:message code="msg.alert.complete.join" />");
											window.location.href = "<spring:url value="/login" />";
										}
							});
							}
					});
				}
			});
		
			//취소버튼 눌렀을 때
			$("input[name=cancelBtn]").bind('click', function(){
				alert("<spring:message code="label.cancel" />");//"회원가입이 취소되었습니다."(메세지 코드 수정 필요)
				window.location.href = "<spring:url value="/login" />";//메인페이지로 이동(페이지이름 바꿔야~~)
			});
			
			//중복확인버튼 눌렀을 때
			$("input[name=idCheckBtn]").bind('click', function(){
					$.get('<spring:url value="/join/checkValidation" />',
							{"cid": $("#cid").val()},
							function(result){
								if($("#cid").val().checkLength(6, 16)){
									alert("아이디를 6~16자 이내로 입력하세요.");
								}
								if(!$("#cid").val().match(/[a-zA-Z]/)){
									alert("아이디는 영문자로 시작해야 합니다.");
								}
								if(!result.data.cid){
									alert("<spring:message code="Duplicate.cid" />");
								} else if(!$("#cid").val().checkLength(6, 16) && $("#cid").val().match(/[a-zA-Z]/) && result.data.cid){
									alert("사용가능한 아이디 입니다.");
							}
					});
			});
		});
/*
		function validation(){
			//cid 6~18자 이내
			if($("#id").val().checkLength(6, 12)){
				alert("아이디를 6~12자 이내로 입력하세요.");
				return false;
			}
			if(!$("#id").val().match(/h.^[a-zA-Z]/g)){
				alert("아이디는 영문자로 시작해야 합니다.");
			}

			//password 6~12자
			if($("#passwd").val().checkLength(6, 12)){
				alert("비밀번호를 6~12자 이내로 입력하세요.");
				return false;
			}
			
			//name 2~20자
			if($("#name").val().checkLength(2, 20)){
				alert("이름을 2~20자 이내로 입력하세요.");
				return false;
			}
			
			//cellphone 010-8978-2345
			if(!$("#cellphone").val().match(/^\d{3}-\d{3,4}-\d{4}$/)){
				alert("휴대폰 번호를 12~13자 이내로 형식에 맞게 입력하세요.");
				return false;
			}
			
			//email test@test.com 50자 이내
			if($("#email").val().checkLength(10, 50)){
				alert("이메일을 10~50자 이내로 입력하세요.");
				return false;
			}
			if(!$("#email").val().match(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i)){
				alert("이메일 형식에 따라 입력하세요.");
				return false;
			}

			return true;
		}*/
		
		function checkLength(value, min, max){
			return value.length <min || value.length > max;
		}

	</script>
	<%-- 
	<jsp:include page="/css/view/menu.css" />
	--%>
</head>
<body>
	<form id="joinForm">
	<br />
	<br />
	<br />
	<br />
	<table>
	<caption><h1>MEMBER JOIN</h1></caption>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td bgcolor="#bebbb" id="label_cid"><spring:message code="label.id" /></td>
			<td><input type="text" name="cid" id="cid" size="10" maxlength="16" /> <input type="button" name="idCheckBtn" value="중복확인" /> (6~16 글자 이내로 입력하세요.)</td>
		</tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td id="label_password"><spring:message code="label.password" /></td>
			<td><input type="password" name="passwd" id="passwd" size="10" maxlength="20" /> (영문자, 숫자를 혼합하여 6~20 글자 이내로 입력하세요.)</td>
		</tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td id="label_repassword"><spring:message code="label.repassword" /></td>
			<td><input type="password" name="repasswd" id="repasswd" size="10" maxlength="20" /></td>
		</tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td id="label_name"><spring:message code="label.name" /></td>
			<td><input type="text" name="name" id="name" size="20" maxlength="20"/></td>
		</tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td id="label_cellphone"><spring:message code="label.cellphone" /></td>
			<td>
				<table>
					<tr>
						<td><input type="text" name="cellPhone" id="cellPhone" /> (000-0000-0000형식으로 입력)</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td id="label_email"><spring:message code="label.email" /></td>
			<td><input type="text" name="email" id="email" /> (직접입력)</td>
		</tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td rowspan="2" id="label_address"><spring:message code="label.address" /></td>
			<td>
				<table>
					<tr>
						<td><input type="text" name="address" id="address" size="3" maxlength="3" /></td>
						<td>-</td>
						<td><input type="text" name="address" id="address" size="3" maxlength="3" /></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td><input type="text" name="address" id="address" size="40" maxlength="40" /></td>
		</tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td colspan="2" align="center">
				<table>
					<tr>
						<td><input type="button" name="regBtn" value="<spring:message code="label.title" />" />&nbsp;<input type="button" name="cancelBtn" value="<spring:message code="label.cancel" />" /></td>
				</table>
		</tr>	
	</table>
	</form>
</body>
</html>