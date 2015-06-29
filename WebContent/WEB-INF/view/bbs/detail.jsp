<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/view/com/config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><spring:message code="label.title" /></title>
	<link href="<spring:url value="/css/view/comment.css" />" rel="stylesheet" type="text/css">
	<jsp:include page="/WEB-INF/view/com/meta.jsp" />
	<jsp:include page="/WEB-INF/view/com/favicon.jsp" />
	<jsp:include page="/WEB-INF/view/com/css.jsp" />
	<jsp:include page="/WEB-INF/view/com/script.jsp" />
	
<script type="text/javascript">
	$(function(){
		$("input[name=list]").bind('click', function(){
			window.location.href = "<spring:url value="/bbs/list" />";
		});
		//삭제
		$("#remove").bind('click', function(){
			
			if(confirm(validation.getMessage('msg_confirm_remove_general', ['label_post']))){
			$.ajax({
					url: '<spring:url value="/bbs/detail/" />${post.bid}',
					type: 'DELETE',
					success: function(data, textStatus, jqXHR){
						var code = "msg_alert_complete_general";
						//삭제된 결과가 0이면 이미 삭제된 글이므로 메세지를 출력하고 리스트로 보낸다.
						if(data.delCnt == 0){
							code = "msg_alert_error_general";
						}
						//삭제된 결과가 1이면 정상적으로 삭제되었으므로 메세지를 출력하고 리스트로 보낸다.
						alert(validation.getMessage(code, ['label_remove']));
						window.location.href = "<spring:url value="/bbs/list" />";
					},
					error: function(jqXHR, textStatus, errorThrown){
						alert(validation.getMessage('msg_alert_error_general'));
					}
			});
		    } 
	    });
		//수정
		$("#modify").bind('click', function(){
			window.location.href = "<spring:url value="/bbs/form/"/>${post.bid}";
			
		});
		
		$("#commentForm").validate({
			rules : {
				comments : {
					required : true,
					rangelength : [10, 200]
				}
			},
			messages : {
				comments : {
					required : validation.getMessage('NotEmpty.general', ['label.comment']),
					rangelength : validation.getMessage('Length.general', ['label.comment', 10, 200])
				}
			}
		});
		
		$("#regComment").css('cursor', 'pointer');
		
		//코멘트 등록
		$("#regComment").bind('click', function(){
			if($("#commentForm").valid()){
				$.post(
					'<spring:url value="/cmt/comment" />',
					$("#commentForm").serialize(),
					function (result){
						if(result.error != undefined){
							//실패
							validation.showMessage(result.error, validation.getMessages('label.comment'));
						} else {
							//성공
							$("#comment").val("").focus();
							loadComments("${post.bid}");
						}
					}
				);		
			}
		});
		
		loadComments("${post.bid}");
	});
	
	var mBtnSrc = $("<image>").addClass("button-small").attr("src", "<spring:url value="/img/icons/modify.png" />");
	var dBtnSrc = $("<image>").addClass("button-small").attr("src", "<spring:url value="/img/icons/delete.png" />");
	
	
	function loadComments(bid){
				$.get(
					'<spring:url value="/cmt/comment" />/' + bid,
					function (result){
						$("#commentList").html("");
						$(result.data.commentList).each(function(index, comment){
							var ul = $("<ul>").addClass("comment-ul").attr("id", comment.cmtNum);
							var span = $("<span>").html(comment.cid);
							var li = $("<li>").append(span).addClass("comment-li");
							ul.append(li);
							
							span = $("<span>").html(comment.comments);
							li = $("<li>").append(span).addClass("comment-li");
							ul.append(li);
							
							span = $("<span>").html(comment.crtDttm);
							li = $("<li>").append(span).addClass("comment-li");
							ul.append(li);

							//여기서 내가 작성한 코멘트면 버튼을 달아준다.
							if("${userInfo.username}" == comment.cid){
								var modifyBtn = $("<image>").addClass("button-small").attr("src", "<spring:url value="/img/icons/modify.png" />");
								var deleteBtn = $("<image>").addClass("button-small").attr("src", "<spring:url value="/img/icons/delete.png" />");
								
								span = $("<span>")
									.append(modifyBtn)
									.append(deleteBtn);
								li = $("<li>").append(span).addClass("comment-li");
								ul.append(li);
								//수정
								modifyBtn.bind('click', function(){
									modifyComment(comment.cmtNum, span);
									
								});
								//삭제
								deleteBtn.bind('click', function(){
									deleteComment(comment.cmtNum);
								});
							}
							$("#commentList").append(ul);
						});
				});
	}
	
	function modifyComment(cmtNum){
		$("#commentList ul").each(function(idx, rowUI){
			if(rowUI.attr("id") != cmtNum){
				var contentSpan = $("span", ($("li", rowUI)[1]));
				var txtArea = $("textarea", contentSpan);
				if(textArea.length > 0){
					contentSpan.html(txtArea.html());
					
					var mbtn = $("<image>").addClass("button-small").attr("src", mBtnSrc);
					var dbtn = $("<image>").addClass("button-small").attr("src", dBtnSrc);
					
					$("span>img", ($("li", rowUI)[3])).remove();
					$("span", ($("li", rowUI)[3])).append(mbtn).append(dbtn);
					
					mbtn.bind('click', function(){
						modifyComment($(rowUI).attr("id"), $("span", ($("li", rowUI)[3])));
					});
					
					dbtn.bind('click', function(){
						deleteComment($(rowUI).attr("id"), $("span", ($("li", rowUI)[3])));
					});
				}
				
				
			} else{
				var contentSpan = $("span", ($("li", rowUI)[1]));
				contentSpan.html("<textarea>" + contentSpan.html() + "</textarea>");
				
				
				var newBtn = $("<image>").addClass("button-small")
							.attr("src", "<spring:url value="/img/icons/new.png" />");
				$("span>img", ($("li", rowUI)[3])).remove();
				$("span", ($("li", rowUI)[3])).append(newBtn);
				
				newBtn.bind('click', function(){
					//길이 제한을 체크해야 한다.
					alert("길이제한추가");
					$.ajax({
						url : "<spring:url value="/cmt/comment" />",
						type : "PUT",
						data : {"bid" : "${post.bid}", "cmtNum" : cmtNum, "comment" : $("textarea", contentSpan).val()},
						success : function(result){
							
						}
					});
				});
			}
		});
			
	}
	
	function deleteComment(cmtNum){
		$.ajax({
			url : "<spring:url value="/cmt/comment/" />" + cmtNum,
			success : function(result){
				if(result.data.delCnt == 1){
					alert(validation.getMessage("msg.alert.complete.general", ["label.remove"]));
					$("#" + cmtNum).remove();
					//loadComments("${post.bid}");
				}
			}
		});
	}
							
</script>

<title><c:out value="${post.title}" /></title>
</head>
<body>
	<div style="text-align: right; height: 30px;">
	<c:if test="${userInfo.username==post.userId}">
		<td><input type="button" id="modify" name="modify" value="<spring:message code="label.update" />" /></td>
		<td><input type="button" id="remove" name="remove" value="<spring:message code="label.remove" />" /></td>
	</c:if>
	</div>
<table border="1" width=50%>
	<tr>
		<td width="20%"><spring:message code="bbs.notice.title" /></td>
		<td><c:out value="${post.title}" /></td>
		<td width="20%"><spring:message code="bbs.notice.writerName" /></td>
		<td width="20%"><c:out value="${post.name}" /></td>
	</tr>
	<tr height="500px">
		<td valign="top"><spring:message code="csc.qna.content" /></td>
		<td colspan="3" valign="top"><c:out value="${post.content}" /></td>
	</tr>
	<tr>
		<td><spring:message code="bbs.notice.attach" /></td>
		<td colspan="3"><a href="<spring:url value="/file/download/${post.fid}"/>"><c:out value="${post.fileName}" /></a></td>
	</tr>
</table>
<div style="width:50%;margin-top:10px">
	<div id="commentList">

	</div>
	
	<form id="commentForm" name="commentForm">
	<input type="hidden" name="bid" value="${post.bid}"/>
	<div id="inputComment">
		<ul class="comment-ul">
		
			<li><span>${userInfo.name}</span></li>
			<li><span><textarea id="comment" name="comment"></textarea></span></li>
			<li><span><img id="regComment" alt="comment" src="<spring:url value="/img/icons/confirm.png"/>" class="button" /></span></li>
		
		</ul>
	</div>
	</form>
</div>
	<input type="button" name="list" value="<spring:message code="label.index" />" />
</body>
</html>