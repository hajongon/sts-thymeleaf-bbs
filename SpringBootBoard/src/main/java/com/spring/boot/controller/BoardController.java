package com.spring.boot.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.spring.boot.db.BoardEntity;
import com.spring.boot.dto.BoardSaveDto;
import com.spring.boot.service.BoardService;
import com.spring.boot.util.Header;
import com.spring.boot.util.Search;

import lombok.RequiredArgsConstructor;

// Lombok 어노테이션으로, 생성자 인젝션을 자동으로 생성
@RequiredArgsConstructor
// Spring MVC 컨트롤러임을 나타내는 어노테이션
@RestController
public class BoardController {
	private final BoardService boardService;

	@RequestMapping(value = "/")
	public ModelAndView index() throws Exception {

		ModelAndView mav = new ModelAndView();

		mav.setViewName("index");

		return mav;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() throws Exception {

		ModelAndView mav = new ModelAndView();

		mav.setViewName("bbs/create");

		return mav;
	}

	@GetMapping("/board")
	public ModelAndView list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
			Search search, HttpServletRequest request) throws Exception {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("bbs/board"); // Thymeleaf 템플릿의 경로 및 파일명

		// boardService.getBoardList 메서드를 호출하여 데이터를 가져오고 "response"라는 이름으로
		// ModelAndView에 추가
		mav.addObject("response", boardService.getBoardList(page, size, search));

		String pageParam = request.getParameter("page");
		int currentPage = 1; // Default value if "page" parameter is missing or empty

		if (pageParam != null && !pageParam.isEmpty()) {
			currentPage = Integer.parseInt(pageParam);
		}
		mav.addObject("currentPage", currentPage);

		return mav;
	}

	@GetMapping("/board/{idx}")
	public ModelAndView getBoardOne(@PathVariable Long idx) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("bbs/detail");
		Header<BoardEntity> header = boardService.getBoardOne(idx);
		BoardEntity boardEntity = header.getData();
		mav.addObject("board", boardEntity);
		return mav;
	}

	@GetMapping("/board/{idx}/update")
	public ModelAndView updateBoardPage(@PathVariable Long idx) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("bbs/update");
		Header<BoardEntity> header = boardService.getBoardOne(idx);
		BoardEntity boardEntity = header.getData();
		mav.addObject("board", boardEntity);
		return mav;
	}

	@PostMapping("/board")
	public RedirectView createBoard(BoardSaveDto boardSaveDto) {
		boardService.insertBoard(boardSaveDto);
		return new RedirectView("/board");
	}

	@PostMapping("/boardupdate")
	public RedirectView updateBoard(BoardSaveDto boardSaveDto) {
		boardService.updateBoard(boardSaveDto);
		return new RedirectView("/board");
	}

	@PostMapping("/board/delete/{idx}")
	public RedirectView deleteBoard(@PathVariable Long idx) {
		boardService.deleteBoard(idx);
		return new RedirectView("/board");
	}
}
