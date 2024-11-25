//package org.example.cruddbfile;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.example.cruddbfile.JDBCUtil.getConnection;
//
//public class me
//
//public List<BoardVO> getBoardList() {
//    List<BoardVO> list = new ArrayList<>();
//    System.out.println("===> JDBC로 getBoardList() 기능 처리");
//    try (Connection conn = getConnection();
//         PreparedStatement stmt = conn.prepareStatement(BOARD_LIST);
//         ResultSet rs = stmt.executeQuery()) {
//        while (rs.next()) {
//            BoardVO one = new BoardVO();
//            one.setSeq(rs.getInt("seq"));
//            one.setTitle(rs.getString("title"));
//            one.setWriter(rs.getString("writer"));
//            one.setContent(rs.getString("content"));
//            one.setImage_path(rs.getString("image_path")); // 이미지 경로 추가
//            one.setRegdate(rs.getDate("regdate"));
//            one.setCnt(rs.getInt("cnt"));
//            list.add(one);
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return list;
//}
