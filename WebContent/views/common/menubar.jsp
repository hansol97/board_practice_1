<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.kh.member.model.vo.Member" %>
<%
    Member loginUser = (Member)session.getAttribute("loginUser");
    // 로그인 전 : menubar.jsp가 로딩될 때 null
    // 로그인 후 : menubar.jsp가 로딩될 때 로그인한 회원의 정보가 담겨있음
    
    // 성공 / 경고메시지 뽑기
    String alertMsg = (String)session.getAttribute("alertMsg");
    // 서비스 요청 전 : alertMsg = null
    // 서비스 요청 후 성공 시 : alertMsg = 메시지 문구
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상단 메뉴바</title>
<style>

    .login-area, #user-info {
        float: right;
    }
    #user-info a {
        text-decoration : none;
        color : lightgray;
        font-size : 12px;
    }
    .nav-area{background-color: forestgreen;}
    .menu{
        display: table-cell;
        height: 50px;
        width: 150px;
    }
    .menu a{
        text-decoration: none;
        color: white;
        font-size: 22px;
        font-weight: bold;
        display: block;
        height: 100%;
        line-height:  50px;
    }
    .menu a:hover {background-color: rgb(27, 109,27);}
</style>
</head>
<body>
    <script>
        // script태그 안에서도 스크립틀릿 같은 jsp 요소 사용 가능
        var msg = '<%= alertMsg %>'; // 메시지문구 / null 
        
        if(msg != 'null'){
        	alert(msg);
        	
        	// session에 들어있는 alertMsg 키값에 대한 밸류를 지워줘야 함!
        	// 왜냐? menubar.jsp가 로딩될 때마다 alert이 계속 뜰것임
            // => XX.removeAttribute("키값");
            <% session.removeAttribute("alertMsg"); %>
        }
    </script>

    <h1 align="center">실리콘밸리</h1>

    <div class="login-area">

        <!-- 
            form 태그 안에 있는 제출버튼(submit) 클릭 시 form 태그가 가지고 있는 속성 중
            action에 작성된 url로 요청됨(제출)
            즉, Controller (Servlet)을 호출한다고 생각하면 됨

            Servlet 요청 같은 경우, 반드시 그 요청값이 현재 웹 어플리케이션의
            context path == /context Root/뒤에붙는경로
            형식으로 작성해야함
            => http://localhost:8001/jsp/test1.do (서블릿 매핑값)

            * 경로 지정 방식
            절대경로방식(/) : /Context Root/요청할 url
                              / 로 시작하는 경우
                              localhost:8001 뒤에 action에 작성한 값이 붙어지면서 요청

            상대경로방식(test1.do) : 요청할 url 문구로 시작하는 경우
                                     현재 이 페이지가 보여질 때의 url 경로 중에서
                                     마지막 /로부터 뒤에 action에 작성한 값이 붙어지면서 요청
        -->

        <% if(loginUser == null){ %>
        <!-- 사용자가 로그인 전 보게 될 화면 -->
        <form action="/jsp/login.me" method="post">
            <table>
                <tr>
                    <th>아이디</th>
                    <td><input type="text" required name="userId"></td>
                </tr>
                <tr>
                    <th>비밀번호</th>
                    <td><input type="password" required name="userPwd"></td>
                </tr>
                <tr>
                    <th colspan="2">
                        <button type="submit">로그인</button>
                        <button type="button">회원가입</button>
                    </th>
                </tr>
            </table>
        </form>
        <% } else { %>
        <!-- 로그인 성공 시 보게될 화면 -->
        <div id="user-info">
            <b><%= loginUser.getUserName() %></b>님 환영합니다. <br><br>
            <div align="center">
                <a href="#">마이페이지</a>
                <a href="#">로그아웃</a>
            </div>
        </div>
        <% } %>
    </div>

    <br clear="both">

    <div class="nav-area" align="center">
        <div class="menu"><a href="#">HOME</a></div>
        <div class="menu"><a href="#">공지사항</a></div>
        <div class="menu"><a href="#">일반게시판</a></div>
        <div class="menu"><a href="#">사진게시판</a></div>
    </div>
</body>
</html>