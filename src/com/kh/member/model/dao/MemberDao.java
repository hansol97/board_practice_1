package com.kh.member.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.vo.Member;

public class MemberDao {
    
    private Properties prop = new Properties();
    
    public MemberDao() {
        String file = MemberDao.class.getResource("/sql/member/member-mapper.xml").getPath();
        
        try {
            prop.loadFromXML(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Member loginMember(Connection conn, String userId, String userPwd) {
        // SELECT문 => ResultSet 객체 (unique key 조건에 의해 한 행만 조회됨) - Member
        
        // 필요한 변수 셋팅
        Member m = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        
        String sql = prop.getProperty("loginMember");
        
        try {
            // pstmt 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // 위치홀더(?) 채우기
            pstmt.setString(1, userId);
            pstmt.setString(2, userPwd);
            
            // 쿼리문 실행 후 결과 받기
            // 쿼리문 실행 메소드
            // pstmt.executeQuery(); => SELECT : ResultSet
            // pstmt.executeUpdate(); => INSERT / UPDATE / DELETE : int / 0
            
            rset = pstmt.executeQuery();
            
            // rset으로부터 각각의 컬럼값을 뽑아서 Member 객체에 담는다.
            // 조회 결과가 한 행일 때   => if(rset.next())
            // 조회 결과가 여러 행일 때 => while(rset.next())
            
            if(rset.next()) {
                // 각 컬럼의 값 뽑기
                // rset.getInt/getString/getDate("뽑아올컬럼명 또는 컬럼의순번")
                
                m = new Member(rset.getInt("USER_NO"),
                               rset.getString("USER_ID"),
                               rset.getString("USER_PWD"),
                               rset.getString("USER_NAME"),
                               rset.getString("PHONE"),
                               rset.getString("EMAIL"),
                               rset.getString("ADDRESS"),
                               rset.getString("INTEREST"),
                               rset.getDate("ENROLL_DATE"),
                               rset.getDate("MODIFY_DATE"),
                               rset.getString("STATUS"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 자원반납 -> 생성된 순서의 역순
            JDBCTemplate.close(rset);
            JDBCTemplate.close(pstmt);
        }
        
        // Service에 결과(Member)넘기기
        
        return m;
    }
}
