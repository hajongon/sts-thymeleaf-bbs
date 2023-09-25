package com.spring.boot.dto;

import com.spring.boot.db.BoardEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardSaveDto {
	private Long idx;
	private String title;
	private String contents;
	private String author;

	public BoardEntity toEntity() {
		return BoardEntity.builder().idx(idx).title(title).contents(contents).author(author).build();
	}
}