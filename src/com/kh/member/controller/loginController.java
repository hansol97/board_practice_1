package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class loginController
 */
@WebServlet("/login.me")
public class loginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    /*
	     * <HttpServletRequest, HttpServletResponse 객체>
	     * 
	     * - request : 서버로 요청할 때 정보들이 담겨있음(요청시 전달값, 요청전송방식 등등...)
	     * - response : 요청에 대한 응답하고자 할 때 사용하는 객체
	     */
	    
	    // 1) POST 방식일 경우 인코딩 설정
	    request.setCharacterEncoding("UTF-8");
	    
	    // 2) 요청 시 전달값을 꺼내서 변수에 기록 => request의 Parameter영역
	    //    reqeust.getParameter("키값") : String
	    //    request.getParameterValues("키값") : String[] => checkbox일 경우 사용
	    String userId = request.getParameter("userId");
	    String userPwd = request.getParameter("userPwd");
	    
	    // 3) 해당 요청을 처리해주는 서비스 클래스의 메소드를 호출
	    Member loginUser = new MemberService().loginMember(userId, userPwd);
	    
	    // 일치하는 회원이 있을 경우 : loginUser 객체에 필드값이 회원 정보로 꽉 차있을 것
	    // 일치하는 회원이 없을 경우 : null이 담길 것
	    
	    //System.out.println(loginUser);
	    
	    // 4) 처리된 결과를 가지고 사용자가 보게 될 응답화면을 지정
	    // step 1. request 객체에 응답화면에 보여질 값 담기 => request의 attribute영역에
	    // step 2. RequestDispatcher 객체 생성 => 응답할 뷰 화면을 지정
	    // step 3. RequestDispatcher 객체로부터 forward 메소드 호출
	    
	    // step 1. 어딘가에 응답화면에 보여질 값 담기(request, session, application, page)
	    
	    /*
	     * 응답페이지에 전달할 값이 있을 경우 값을 어딘가에 담아야 함 => 어딘가의 Attribute영역에 담아서 보낸다.
	     *     (담아줄 수 있는 객체가 4종류 => ServletScope 내장객체)
	     * 
	     * 크다
	     * 
	     * 1) application : 웹 어플리케이션 전역에서 언제나 꺼내 쓸 수 있음(자바클래스에서 쓸 수 있음)
	     * 2) session : 모든 jsp와 servlet에서 꺼내 쓸 수 있음
	     *              단, 내가 직접적으로 session 객체에 담은 값을 지우기 전까지만 꺼내쓸 수 있음
	     *              세션이 끊기는 경우 : 브라우저가 종료될 때, 서버가 멈춘 경우
	     * 3) request : 해당 request를 포워딩한 응답 jsp페이지에서만 쓸 수 있음
	     *              요청페이지부터 응답페이지까지에서만 쓸 수 있음
	     * 4) page : 담은 값을 해당 jsp 페이지에서만 쓸 수 있음(화면이 넘어가면 담은 값은 소멸)
	     * 
	     * 작다
	     * 
	     * => session, request가 가장 많이 쓰인다.
	     * => 공통적으로 데이터를 담고자 한다면 : XXX.setAttribute(키, 밸류);
	     *               데이터를 뽑고자 한다면 : XXX.getAttribute(키);
	     *               데이터를 지우고자 한다면 : XXX.removeAttribute(키);
	     *               
	     * 예시)
	     * 로그인 시 : session.setAttribute("userInfo", loginUser);
	     * 로그아웃 시 : session.removeAttribute("userInfo"); 또는 무효화시키는 메소드 사용
	     */
	    
	    // step 2. case1. RequestDispatcher 객체 생성(응답할 뷰 화면 지정) => forward();
	    if(loginUser == null) { // 로그인 실패
	        // 에러메시지 넘기기
	        // step1. request의 attribute영역에 메시지 담기
	        request.setAttribute("errorMsg", "로그인에 실패하였습니다.");
	        // step2. RequestDispatcher 객체 생성
	        RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
	        // step3. forward
	        view.forward(request, response);
	    } else { // 로그인 성공 => index.jsp 페이지 응답
	        // 사용자의 정보 넘기기
	        
	        // 로그인 한 회원의 정보를 로그아웃 하기 전까지 계속 가져다 쓸거기 때문에 session에 담기
	        
	        // step1. session의 attribute 영역에 사용자 정보 담기
	        // => session 객체의 Type : HttpSession
	        // => session 객체 생성 방법 : request.getSesstion();
	        HttpSession session = request.getSession();
	        
	        session.setAttribute("loginUser", loginUser);
	        
	        session.setAttribute("alertMsg", "로그인 성공!");
	        
	        /*
	         * 포워딩 방식
	        // step2. RequestDispatcher 객체 생성
	        RequestDispatcher view = request.getRequestDispatcher("index.jsp");
	        // step3. forward()
	        view.forward(request, response);
	        
	        // http://localhost:8001/jsp/login.me
	        // 내가 요청한 mapping 값이 url에 잘 나온다.
	        */
	        
	        // url 재요청방식 (sendRedirect방식) : 사용자에게 url을 재요청하게 함으로써 응답페이지가 보여지게끔
	        // response 객체를 이용
	        // response.sendRedirect(재요청할경로);
	        
	        response.sendRedirect("/jsp");
	        // => 내가 요청한 경로가 url에 보임
	        // localhost:8001/jsp
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
