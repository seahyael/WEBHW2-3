package org.example.cruddbfile;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.cruddbfile.JDBCUtil.getConnection;

public class BoardDAO {

	private final String BOARD_INSERT = "insert into BOARD (title, writer, content, image_path) values (?,?,?,?)";
	private final String BOARD_UPDATE = "update BOARD set title=?, writer=?, content=?, image_path=? where seq=?";
	private final String BOARD_DELETE = "delete from BOARD where seq=?";
	private final String BOARD_GET = "select * from BOARD where seq=?";
	private final String BOARD_LIST = "select * from BOARD order by seq desc";

	// Insert a new board entry
	public int insertBoard(BoardVO vo) {
		System.out.println("===> JDBC로 insertBoard() 기능 처리");
		int result = 0;
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(BOARD_INSERT)) {
			stmt.setString(1, vo.getTitle());
			stmt.setString(2, vo.getWriter());
			stmt.setString(3, vo.getContent());
			stmt.setString(4, vo.getImage_path()); // 이미지 경로 추가
			result = stmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// Update an existing board entry
	public int updateBoard(BoardVO vo) {
		System.out.println("===> JDBC로 updateBoard() 기능 처리");
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(BOARD_UPDATE)) {
			stmt.setString(1, vo.getTitle());
			stmt.setString(2, vo.getWriter());
			stmt.setString(3, vo.getContent());
			stmt.setString(4, vo.getImage_path()); // 이미지 경로 추가
			stmt.setInt(5, vo.getSeq());
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void deleteBoard(BoardVO vo) {
		System.out.println("===> JDBC로 deleteBoard() 기능 처리");
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(BOARD_DELETE)) {
			stmt.setInt(1, vo.getSeq());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Retrieve a specific board entry by its seq
	public BoardVO getBoard(int seq) {
		BoardVO one = new BoardVO();
		System.out.println("===> JDBC로 getBoard() 기능 처리");
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(BOARD_GET)) {
			stmt.setInt(1, seq);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					one.setSeq(rs.getInt("seq"));
					one.setTitle(rs.getString("title"));
					one.setWriter(rs.getString("writer"));
					one.setContent(rs.getString("content"));
					one.setImage_path(rs.getString("image_path")); // 이미지 경로 추가
					one.setCnt(rs.getInt("cnt"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return one;
	}

	public List<BoardVO> searchBoardList(String search, String startDate, String endDate, String sortOrder) {
		List<BoardVO> list = new ArrayList<>();
		System.out.println("===> JDBC로 searchBoardList() 기능 처리");

		// 기본 SQL 쿼리
		String sql = "select * from BOARD where title like ? AND regdate >= ? AND regdate <= ? order by seq ";
		sql += ("asc".equalsIgnoreCase(sortOrder)) ? "asc" : "desc"; // 정렬 조건 추가

		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			int paramIndex = 1;

			// 검색 파라미터
			if (search != null && !search.trim().isEmpty()) {
				stmt.setString(paramIndex++, "%" + search + "%");
			} else {
				stmt.setString(paramIndex++, "%"); // 검색 조건이 없을 때 모든 제목 포함
			}

			// 날짜 파라미터
			if (startDate != null && !startDate.isEmpty()) {
				stmt.setString(paramIndex++, startDate);
			} else {
				stmt.setString(paramIndex++, "1900-01-01"); // 기본 시작 날짜
			}

			if (endDate != null && !endDate.isEmpty()) {
				stmt.setString(paramIndex++, endDate);
			} else {
				stmt.setString(paramIndex++, "9999-12-31"); // 기본 종료 날짜
			}

			// 결과 처리
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					BoardVO one = new BoardVO();
					one.setSeq(rs.getInt("seq"));
					one.setTitle(rs.getString("title"));
					one.setWriter(rs.getString("writer"));
					one.setContent(rs.getString("content"));
					one.setImage_path(rs.getString("image_path"));
					one.setRegdate(rs.getDate("regdate"));
					one.setCnt(rs.getInt("cnt"));
					list.add(one);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}




	// Retrieve a list of all board entries
	public List<BoardVO> getBoardList() {
		List<BoardVO> list = new ArrayList<>();
		System.out.println("===> JDBC로 getBoardList() 기능 처리");
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(BOARD_LIST);
			 ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				BoardVO one = new BoardVO();
				one.setSeq(rs.getInt("seq"));
				one.setTitle(rs.getString("title"));
				one.setWriter(rs.getString("writer"));
				one.setContent(rs.getString("content"));
				one.setImage_path(rs.getString("image_path")); // 이미지 경로 추가
				one.setRegdate(rs.getDate("regdate"));
				one.setCnt(rs.getInt("cnt"));
				list.add(one);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}