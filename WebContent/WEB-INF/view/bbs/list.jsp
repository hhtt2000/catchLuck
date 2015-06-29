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
	$(function() {
		$("input[name=searchText]").attr('disabled', true);
		
		$("input[name=searchButton]").bind('click', function(){
			search();
		});
		$("input[name=searchText]").bind('keypress', function(e){
			if(e.keyCode == 13){
				e.preventDefault();
				search();
			}
		});
		
		$(":checkbox").bind('click', function(e){
			$("input[name=searchText]").attr('disabled', $(":checked").length == 0);
			if($(":checked").length == 0){
				$("input[name=searchText]").val("");
			}
		});
		
		$("#newPost").bind('click', function(){
			window.location.href = "<spring:url value="/bbs/form/new" />";
		});
		
		search();
	});
	
	function search(){
		
		if($("input[name=searchText]").val().length == 0
				
				&& $(":checked").length == 1//checked된 배열의 길이가 1
				&& $($(":checked")[0]).val() == "name"){//그 첫번째 배열의 value가 name이면
				//&& $("input[value=name]:checked").length == 1){
					alert("<spring:message code="label.gab" />");
					//alert("작성자를 입력하세요.");
				return;
		}
		
		$.post("<spring:url value="/bbs/search" />",
				$("#searchForm").serialize(),
				function(result){
					//결과 처리
					//기존 화면 제거
					$("#bbsList").html("");
					//새로운 리스트 추가
					$(result.postList).each(function(index, post){
						var $tr = $("<tr style='cursor:pointer;'>");
						$tr.append($("<td>").append(post.bid));
						$tr.append($("<td>").append(post.title));
						$tr.append($("<td>").append(post.name));
						$tr.append($("<td>").append(post.fileName));
						$tr.append($("<td>").append(post.crtDttm));
						$("#bbsList").append($tr);
						
						$tr.bind('click', function(){
							//alert(post.bid);
							window.location.href = "<spring:url value="/bbs/detail/" />" + post.bid;
						});	
						
						$tr.bind('mouseover', function(){
							$tr.css("background", "#b5e61d");
						});
				
						
						$tr.bind('mouseleave', function(){
							$tr.css("background", "#b5e61d");
						});
					});

					var $p = result.paging;
					var r = $p.currPageNo * $p.pageCnt;
					var pgSet = $p.pageCnt * $p.pageRows;
					var endPageNo = (parseInt(r / pgSet) + (r % pgSet > 0 ? 1: 0)) * $p.pageCnt;

					var startPageNo = endPageNo - $p.pageCnt + 1;
					
					if(endPageNo > $p.lastPageNo){	
						endPageNo = $p.lastPageNo;
						startPageNo = endPageNo - (endPageNo % $p.pageCnt) + 1;
					}
						
					var $pagingArea = $("#pagingArea");
					$pagingArea.html("");
					$pagingArea.append($("<td>")).append("<a href='#' onclick='goPage(" + $p.firstPageNo +")'>first</a>");
					$pagingArea.append($("<td>")).append("<a href='#' onclick='goPage(" + $p.prevPageNo +")'>prev</a>");
					
					for(var i=startPageNo; i<endPageNo; i++){
						if($p.currPageNo == i){
							$pagingArea.append($("<td>")).append(i);
						} else{
							$pagingArea.append($("<td>")).append("<a href='#' onclick='goPage(" + i +")'>" + i + "</a>");
						}
					}
					
					$pagingArea.append($("<td>")).append("<a href='#' onclick='goPage(" + $p.nextPageNo +")'>next</a>");
					$pagingArea.append($("<td>")).append("<a href='#' onclick='goPage(" + $p.lastPageNo +")'>last</a>");
		});
	}
		
		function goPage(currPageNo){
			$("#currPageNo").val(currPageNo);
			search();
		}
		
		function goPost(bid){
			alert(bid);
		}
</script>

<title>"<spring:message code="label.bulletinboard" />"</title>
</head>
<body>
	<table>
		<tr>
			<td align="right">
				<input type="button" name="newPost" id="newPost" value="<spring:message code="label.insert" />" />
			</td>
		</tr>
	</table>
	<table>
		<thead>
			<tr>
				<td><spring:message code="csc.notice.noticeSeq" /></td>
				<td><spring:message code="bbs.notice.title" /></td>
				<td><spring:message code="bbs.notice.writerName" /></td>
				<td><spring:message code="bbs.notice.attach" /></td>
				<td><spring:message code="bbs.fileshare.writeDttm" /></td>
			</tr>
		</thead>
		<tbody id="bbsList">

		</tbody>
		<tfoot>
			<tr>
				<td colspan="5">
				<form id="searchForm">
					<input type="hidden" name="currPageNo" id="currPageNo" value="1" />
					<input type="checkbox" name="searchCondition" value="title"/>제목&nbsp;
					<input type="checkbox" name="searchCondition" value="content"/>내용&nbsp;
					<input type="checkbox" name="searchCondition" value="name"/>작성자&nbsp;
					<input type="text" name="searchText" maxlength="20" />
					<input type="button" name="searchButton" value="검색" />
					</form>
				</td>
			</tr>
		</tfoot>
	</table>
	<table style="width:500px">
		<tr id="pagingArea"></tr>
	</table>
</body>
</html>