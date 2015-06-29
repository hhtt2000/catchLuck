<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="/WEB-INF/view/com/config.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><spring:message code="label.title" /></title>
<jsp:include page="/WEB-INF/view/com/meta.jsp" />
<jsp:include page="/WEB-INF/view/com/favicon.jsp" />
<jsp:include page="/WEB-INF/view/com/css.jsp" />
<jsp:include page="/WEB-INF/view/com/script.jsp" />

<jsp:include page="/WEB-INF/view/com/config.jsp" />
<script type="text/javascript">
	$(function() {
		
		<c:choose>
		<c:when test="${crtCnt == 0}">
			//실패
			$("#title").val("${postParam.title})");
			$("#content").html("${postParam.content}");
		</c:when>
		<c:when test="${crtCnt == 1}">
			//성공
			alert(validation.getMessage('msg.alert.complete.general', ['label.insert']));
			window.location.href = vs.config.contextRoot + "/qnabbs/list";
		</c:when>
		<c:otherwise>
			<c:if test="${err != null}">
			//server side validation 실패
			$("#title").val("${postParam.title})");
			$("#content").html("${postParam.content}");
			var err = ${err};
			validation.showMessage(err.data.error, 'td#label_');
			</c:if>
		</c:otherwise>
		</c:choose>
	
		$("input[name=list]").bind('click', function() {
			window.location.href = "<spring:url value="/qnabbs/list" />";
		});
		$("#update").bind('click', function() {
			$("#postForm").validate({
					rules : {
						title : {
							required : true,
							maxlength : 50
						},
						content : {
							required : true,
							maxlength : 1000
						}
					},
					messages : {
						title : {
							required : validation.getMessage("NotEmpty_general", ["label_title"]),
							maxlength : validation.getMessage("Length_max_general", ["label_title", 50])
						},
						content : {
							required : validation.getMessage("NotEmpty_general", ["label_content"]),
							maxlength : validation.getMessage("Length_max_general", ["label_content", 1000])
						}
					}
				});
			if ($("#postForm").valid()){
			//수정 ajax
			$.ajax({
				url : '<spring:url value="/qnabbs/detail/" />',
				data : $("#postForm").serialize(),
				type : 'PUT',
				success : function(data,textStatus, jqXHR) {
							var code = "msg_alert_complete_general";
							
							if (data.updCnt == 0) {
								code = "msg_alert_error_general";
							}
							
							alert(validation.getMessage(code, ['label_update']));
							window.location.href = "<spring:url value="/qnabbs/list" />";
						},
						error : function(jqXHR, textStatus, errorThrown) {
									alert(validation.getMessage('msg_alert_error_general'));
								}
						});
				}

			});
		
		$("#remove").bind('click', function() {
			if (confirm(validation.getMessage('msg_confirm_remove_general', 'label_post'))) {
				$.ajax({
					url : '<spring:url value="/qnabbs/detail" />${post.bid}',
					type : 'DELETE',
					success : function(data, textStatus, jqXHR) {
						var code = "msg_alert_complete_general";
						//삭제된 결과가 0이면 이미 삭제된 글이므로 메세지를 출력하고 리스트로 보낸다.
						if (data.delCnt == 0) {
							code = "msg_alert_error_general";
						}
						//삭제된 결과가 1이면 정상적으로 삭제되었으므로 메세지를 출력하고 리스트로 보낸다.
						alert(validation.getMessage(code,['label_remove']));
						window.location.href = "<spring:url value="/qnabbs/list" />";
					},
					error : function(jqXHR, textStatus, errorThrown) {
								alert(validation.getMessage('msg_alert_error_general'));
							}
					});
			}
		});

		$("#insert").bind('click', function() {
			//if ($("#postForm").valid()) {
				$("#postForm").submit();
			//}
		});
	});
</script>

<title><c:out value="${post.title}" /></title>
</head>
<body>
	<form name="postForm" id="postForm" method="post"
		encType="multipart/form-data"
		action="<spring:url value="/qnabbs/detail" />">
		<input type="hidden" name="bid" id="bid" value="${post.bid}" />
		<table border="1" style="width: 100%">
			<tr>
				<td width="20%" id="label_title"><spring:message code="bbs.notice.title" /></td>
				<td><input type="text" name="title" id="title"
					value="<c:out value="${post.title}" />" /></td>
				<td width="20%"><spring:message code="bbs.notice.writerName" /></td>
				<td width="20%"><c:choose>
						<c:when test="${post == null}">
							<c:out value="${userInfo.name}" />
						</c:when>
						<c:otherwise>
							<c:out value="${post.name}" />
						</c:otherwise>
					</c:choose></td>
			</tr>
			<tr height="300px">
				<td valign="top" id="label_content"><spring:message code="csc.qna.content" /></td>
				<td colspan="3" valign="top"><textarea
						style="width: 100%; height: 100%" id="content" name="content"><c:out
							value="${post.content}" /></textarea></td>
			</tr>
			<tr>
				<td><spring:message code="bbs.notice.attach" /></td>
				<td colspan="3"><c:choose>
						<c:when test="${post != null}">
							<a href="<spring:url value="/file/download/${post.fid}"/>"><c:out
									value="${post.fileName}" /></a>
						</c:when>
						<c:otherwise>
							<input type="file" name="attachment" id="attachment" />
						</c:otherwise>
					</c:choose></td>
			</tr>
		</table>

		<div style="text-align: right; height: 30px;">
			<input type="button" name="list"
				value="<spring:message code="label.index" />" />
		</div>
		<div style="text-align: right; height: 30px;">
			<c:choose>
				<c:when test="${post != null}">
					<input type="button" id="update" name="update"
						value="<spring:message code="label.update" />" />
				</c:when>
				<c:otherwise>
					<input type="button" id="insert" name="insert"
						value="<spring:message code="label.insert" />" />
				</c:otherwise>
			</c:choose>
		</div>
	</form>
</body>
</html>