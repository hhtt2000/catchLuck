<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="http://code.jquery.com/jquery-2.0.3.js"></script>
<script type="text/javascript" src="<spring:url value="/js/jquery/plugin/validation/jquery.validate.min.js" />" /></script>
<script type="text/javascript" src="<spring:url value="/js/jquery/plugin/storage/jquery.storageapi.min.js" />" /></script>

<script type="text/javascript">

var key = 'messages.properties';
if($.sessionStorage.isEmpty(key)){
	$.get('<spring:url value="/rsc/i18n" />',
			{},
			function(response){
				$.sessionStorage.set(key, response);
			});
}

function getMessage(code){
	var messages = $.sessionStorage.get(key);
	return eval("messages." + code);//eval

}

var validation = {
		getMessage : function(code, args){
			if(args == undefined){
				return eval("$.sessionStorage.get('" + key + "')." + code.replace(/[.]/g, "_"));
			} else{
				var msg = this.getMessage(code);
				for(var i=0; i<args.length; i++){
					msg = msg.replace("{" + i + "}", isNaN(args[i]) ? this.getMessage(args[i]) : args[i]);
				}
				return msg;
			}
			//return $.sessionStorage.get(key).label_name;
		},

	showMessage : function(errors, labelPrefix){
		var parent = this;
		//alert(this.getMessage("label_name"));
		//에러 실행
		$(errors).each(function(index, error){

			var args = error.arguments;
			var code = error.code;
			var field = error.field;
			var label = $(labelPrefix + "name").text().trim();
			label = label.length > 0 ? label : labelPrefix;//.html을 쓰면 밑에 테이블에 선언한 tag값을 불러온다. 예)<spring:message code="label.id" />
			//var msg = validation.getMessage(code + "_general");
			var msg = parent.getMessage(code + "_general");
			//에러 매개변수의 개수에 맞게 {0} 등을 치환하자.
			if(args.length == 3){
				msg = msg.replace("{0}", label).replace("{1}", args[2]).replace("{2}", args[1]);
			} else if(args.length == 2){
				msg = msg.replace("{0}", label).replace("{1}", args[1]);
			} else{
				msg = msg.replace("{0}", label);
			}
			//해당 필드에 포커스를 준다.
			$("#" + field).focus();
			alert(msg);
			
			return false;
		});
	}
};

<c:set var="userInfo"  value="${SPRING_SECURITY_CONTEXT.authentication.principal}" />
var vs = {
		config : {
			contextRoot: "<spring:url value="/" />"
		},
		
		session : {
			user : {
				id: "${userInfo.username}",
				name: "${userInfo.name}",
				cellPhone: "${userInfo.cellPhone}",
				email: "${userInfo.email}",
				role: [<c:forEach var="val" items= "${userInfo.role}" >,'<c:out value="${val}" />'</c:forEach>]
			}
		}
};
	
</script>