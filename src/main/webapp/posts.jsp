<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@page import="org.example.cruddbfile.BoardDAO, org.example.cruddbfile.BoardVO,java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>free board</title>
	<style>
		#list {
			font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
			border-collapse: collapse;
			width: 100%;
		}
		#list td, #list th {
			border: 1px solid #ddd;
			padding: 8px;
			text-align:center;
		}
		#list tr:nth-child(even){background-color: #f2f2f2;}
		#list tr:hover {background-color: #ddd;}
		#list th {
			padding-top: 12px;
			padding-bottom: 12px;
			text-align: center;
			background-color: #006bb3;
			color: white;
		}
	</style>
	<script>
		function delete_ok(id){
			var a = confirm("정말로 삭제하겠습니까?");
			if(a) location.href='deletepost.jsp?id=' + id;
		}
	</script>
</head>
<body>
<h1>자유게시판</h1>

<form action="posts.jsp" method="get">
	<div>
		<!-- 검색어 입력 -->
		<label for="search">검색어:</label>
		<input type="text" id="search" name="search" value="${param.search}" placeholder="검색어 입력">
	</div>
	<div>
		<!-- 날짜 필터 -->
		<label for="startDate">시작 날짜:</label>
		<input type="date" id="startDate" name="startDate" value="${param.startDate}">
		<label for="endDate">종료 날짜:</label>
		<input type="date" id="endDate" name="endDate" value="${param.endDate}">
	</div>
	<div>
		<!-- 정렬 조건 -->
		<label for="sortOrder">정렬:</label>
		<select id="sortOrder" name="sortOrder">
			<option value="desc" ${param.sortOrder == 'desc' ? 'selected' : ''}>내림차순</option>
			<option value="asc" ${param.sortOrder == 'asc' ? 'selected' : ''}>오름차순</option>
		</select>
	</div>
	<div>
		<button type="submit">검색</button>
	</div>
</form>


<%
	// 검색, 날짜 및 정렬 파라미터 처리
	String search = request.getParameter("search");
	String startDate = request.getParameter("startDate");
	String endDate = request.getParameter("endDate");
	String sortOrder = request.getParameter("sortOrder");

	BoardDAO boardDAO = new BoardDAO();
	List<BoardVO> list;

	if ((search != null && !search.trim().isEmpty()) ||
			(startDate != null && !startDate.isEmpty()) ||
			(endDate != null && !endDate.isEmpty()) ||
			(sortOrder != null && !sortOrder.isEmpty())) {
		list = boardDAO.searchBoardList(search, startDate, endDate, sortOrder);
	} else {
		list = boardDAO.getBoardList();
	}

	request.setAttribute("list", list);
%>

<table id="list" width="90%">
	<tr>
		<th>Id</th>
		<th>Img</th>
		<th>Title</th>
		<th>Writer</th>
		<th>Content</th>
		<th>Regdate</th>
		<th>Edit</th>
		<th>Delete</th>
	</tr>
	<c:forEach items="${list}" var="u">
		<tr>
			<td>${u.getSeq()}</td>
		<td><img src="./upload/${u.getImage_path()}" alt="Image" width="100" height="100" /></td>
		<td>${u.getTitle()}</td>
		<td>${u.getWriter()}</td>
		<td>${u.getContent()}</td>
		<td>${u.getRegdate()}</td>
		<td><a href="editform.jsp?id=${u.getSeq()}">Edit</a></td>
		<td><a href="javascript:delete_ok('${u.getSeq()}')">Delete</a></td>
		</tr>
	</c:forEach>
</table>

<br/><a href="addpostform.jsp">Add New Post</a>
</body>
</html>
