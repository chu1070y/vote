package org.zerock.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {

    public List<MenuVO> getMenus(int sno){

        String sql = "select * from tbl_menu where sno=? order by mno";
        List<MenuVO> list = new ArrayList<>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            //sql 연결시키기
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@10.10.10.95:1521:XE","zz","12345678");
            System.out.println(con);
            stmt = con.prepareStatement(sql);

            //mid,mno,score //여기서는 0이 아니라 1부터 시작한다.
            stmt.setInt(1,sno);

            rs = stmt.executeQuery();

            //데이터 행을 한줄씩 빨아들이고 다음 행으로 넘어간다.
            //데이터 받아오는 코드
            while(rs.next()){
                MenuVO vo = new MenuVO(); //한 라인당 menuvo로 저장해준다.
                vo.setMno(rs.getInt("mno")); //mno를 받아와 숫자로 바꿔준다.
                vo.setSno(rs.getInt("sno"));
                vo.setMname(rs.getString("mname"));
                vo.setPrice(rs.getInt("price"));
                list.add(vo);
            }

            //업데이트 실행
            int count = stmt.executeUpdate();
            System.out.println(count);

        }catch(Exception e){
            System.out.println(e.getMessage());

        }finally {
            if(rs != null){     try{rs.close();}    catch (Exception e){} }
            if(stmt != null){   try{stmt.close();}  catch (Exception e){} }
            if(con != null){    try{con.close();}   catch (Exception e){} }
        }//end finally

        return list;

    }
}
