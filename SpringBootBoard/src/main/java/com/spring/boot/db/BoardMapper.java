package com.spring.boot.db;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {

	List<BoardEntity> getBoardList(HashMap<String, Object> paramMap);

	int getBoardTotalCount(HashMap<String, Object> paramMap);

	BoardEntity getBoardOne(Long idx);

	int insertBoard(BoardEntity entity);

	int updateBoard(BoardEntity entity);

	int deleteBoard(Long idx);
}
