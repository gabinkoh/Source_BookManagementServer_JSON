package com.cjon.comment.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cjon.book.service.BookService;
import com.cjon.comment.service.CommentService;

/**
 * Servlet implementation class CommentListServlet
 */
@WebServlet("/commentList")
public class CommentListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 입력받고
		String content = request.getParameter("content"); // 책에 대한 keyword를 받는부분
		String keyword = request.getParameter("keyword"); // 책에 대한 keyword를 받는부분
		String callback = request.getParameter("callback"); // JSONP처리를 위해서 사용
		
		System.out.println("servlet// keyword : " + keyword + ", content: " + content);
		// 2. 로직처리하고(DB처리포함)
		// Servlet은 입력을 받고 출력에대한 지정을 담당. 로직처리는 하지 않아요!!
		// 로직처리하는 객체를 우리가 일반적으로 Service객체라고 불러요! 이놈을 만들어서 일을 시켜서
		// 결과를 받아오는 구조로 만들어 보아요!
		// 로직처리를 하기 위해서 일단 Service객체를 하나 생성합니다.
		CommentService service = new CommentService();
		String result = null;
		if (content.equals("")) {
			result = service.cAllList(content);
		} else {
				result = service.cListForABook(keyword, content);
			
		}
		// 결과로 가져올건..DB 처리한 후 나온 책에 대한 JSON data		
		// 3. 출력처리
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out = response.getWriter();
		out.println(callback + "(" + result + ")");
		out.flush();
		out.close();	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
