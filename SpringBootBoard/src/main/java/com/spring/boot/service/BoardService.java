package com.spring.boot.service;

import com.spring.boot.db.BoardEntity;
import com.spring.boot.db.BoardMapper;
import com.spring.boot.dto.BoardSaveDto;
import com.spring.boot.util.Header;
import com.spring.boot.util.Pagination;
import com.spring.boot.util.Search;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardMapper boardMapper;

	// 게시글 목록 조회 서비스 메서드
	// 페이지 번호, 페이지 크기, 검색 조건을 받아와서, MyBatis를 사용하여 데이터베이스에서 게시글 목록을 조회하고 페이징 정보를 계산하여
	// 반환

	public Header<List<BoardEntity>> getBoardList(int page, int size, Search search) {

		// 1. 파라미터로 전달받은 페이지 번호(page)와 페이지 크기(size)를 이용하여 검색 파라미터 paramMap을 생성
		HashMap<String, Object> paramMap = new HashMap<>();

		// 2. 페이지가 1 이하로 입력된 경우, 페이지를 0으로 고정
		if (page <= 1) {
			paramMap.put("page", 0);
		} else {
			// 3. 페이지가 2 이상인 경우, (page - 1) * size 값을 페이지로 설정
			paramMap.put("page", (page - 1) * size);
		}
		paramMap.put("size", size); // 4. 페이지 크기(size)를 paramMap에 저장
		paramMap.put("sk", search.getSk()); // 5. 검색 조건(search keyword)을 paramMap에 저장
		paramMap.put("sv", search.getSv()); // 6. 검색 값(search value)을 paramMap에 저장

		// 7. boardMapper를 사용하여 데이터베이스에서 게시글 목록을 조회하고, 그 결과를 boardList에 저장
		List<BoardEntity> boardList = boardMapper.getBoardList(paramMap);

		// 8. Pagination 클래스를 사용하여 페이지네이션 정보를 생성
		Pagination pagination = new Pagination(boardMapper.getBoardTotalCount(paramMap), // 8.1. 전체 게시글 수를 조회하여 전달
				page, // 8.2. 현재 페이지 번호를 전달
				size, // 8.3. 페이지 크기를 전달
				10 // 8.4. 페이지 블록 크기를 설정
		);

		// 9. Header 클래스를 사용하여 조회 결과(boardList)와 페이지네이션 정보(pagination)를 포함한 응답을 생성하여 반환
		return Header.OK(boardList, pagination);
	}

	// 게시글 한 개 조회 서비스 메서드
	// 게시글 고유 식별자인 idx를 받아 해당 게시글을 조회하여 반환
	public Header<BoardEntity> getBoardOne(Long idx) {
		return Header.OK(boardMapper.getBoardOne(idx));
	}

	// 게시글 등록 서비스 메서드
	// BoardSaveDto 객체를 엔티티로 변환하고, 데이터베이스에 등록한 뒤 결과를 반환
	public Header<BoardEntity> insertBoard(BoardSaveDto boardSaveDto) {
		BoardEntity entity = boardSaveDto.toEntity();
		if (boardMapper.insertBoard(entity) > 0) {
			return Header.OK(entity);
		} else {
			return Header.ERROR("ERROR");
		}
	}

	// 게시글 수정 서비스 메서드
	// 업데이트된 게시글 엔티티를 반환
	public Header<BoardEntity> updateBoard(BoardSaveDto boardSaveDto) {
		BoardEntity entity = boardSaveDto.toEntity();
		if (boardMapper.updateBoard(entity) > 0) {
			return Header.OK(entity);
		} else {
			return Header.ERROR("ERROR");
		}
	}

	// 게시글 삭제 서비스 메서드
	// 게시글 고유 식별자 idx를 받아 해당 게시글을 삭제하고 결과를 반환
	public Header<String> deleteBoard(Long idx) {
		if (boardMapper.deleteBoard(idx) > 0) {
			return Header.OK();
		} else {
			return Header.ERROR("ERROR");
		}
	}
}