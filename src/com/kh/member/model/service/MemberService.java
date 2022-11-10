package com.kh.member.model.service;

import java.sql.Connection;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.Member;

public class MemberService {

    public Member loginMember(String userId, String userPwd) {
        // Service => Connection 객체 생성
        
        // 1) Connection 객체 생성
        Connection conn = JDBCTemplate.getConnection();
        
        // 2) Controller에서 넘어온 전달값과 Connection 객체를 가지고 DAO 메소드 호출
        Member m = new MemberDao().loginMember(conn, userId, userPwd);
        
        // 3) Connection 객체 반납
        JDBCTemplate.close(conn);
        
        // 4) Controller로 결과 넘기기
        return m;
    }
}
