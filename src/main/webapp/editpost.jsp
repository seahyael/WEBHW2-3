<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.example.cruddbfile.BoardDAO"%>
<%@ page import="org.example.cruddbfile.FileUpload" %>
<%@ page import="org.example.cruddbfile.BoardVO" %>

<% request.setCharacterEncoding("utf-8"); %>

<%
	request.setCharacterEncoding("utf-8");
	BoardVO boardVO = new BoardVO();
	FileUpload fileUpload = new FileUpload();
	boardVO = fileUpload.uploadFile(request);
	BoardDAO boardDAO = new BoardDAO();
	int i=boardDAO.updateBoard(boardVO);
	response.sendRedirect("posts.jsp");
%>