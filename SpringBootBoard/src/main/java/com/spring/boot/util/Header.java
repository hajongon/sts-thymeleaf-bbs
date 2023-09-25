package com.spring.boot.util;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Header<T> {
	// API 요청을 처리한 시간을 나타냅니다. LocalDateTime 타입으로 현재 시간을 저장합니다.
	private LocalDateTime transactionTime;
	// API 응답의 결과 코드를 나타냅니다. "OK"는 성공을 나타내며, "ERROR"는 실패를 나타냅니다.
	private String resultCode;
	// API 응답의 결과에 대한 설명 또는 메시지를 나타냅니다. 주로 실패 시에 실패 이유를 설명하는 데 사용됩니다.
	private String description;
	// API 응답에 포함되는 데이터를 나타냅니다. 이 부분은 제네릭으로 정의되어 있어, 다양한 데이터 타입을 받을 수 있습니다.
	private T data;
	// 페이지네이션 정보를 포함합니다. 예를 들어, API가 데이터 리스트를 반환할 때 페이지 번호, 페이지 크기, 총 페이지 수 등의 정보를
	// 제공할 수 있습니다.
	private Pagination pagination;

	// OK(): 성공 상태를 의미하며, 데이터 없이 기본적인 성공 응답을 생성합니다.
	public static <T> Header<T> OK() {
		return (Header<T>) Header.<T>builder().transactionTime(LocalDateTime.now()).resultCode("OK").description("OK")
				.build();
	}

	// DATA OK
	// OK(T data): 성공 상태를 의미하며, 데이터를 포함한 성공 응답을 생성합니다.
	public static <T> Header<T> OK(T data) {
		return (Header<T>) Header.<T>builder().transactionTime(LocalDateTime.now()).resultCode("OK").description("OK")
				.data(data).build();
	}

	// OK(T data, Pagination pagination): 성공 상태를 의미하며, 데이터와 페이지네이션 정보를 포함한 성공 응답을
	// 생성합니다.
	public static <T> Header<T> OK(T data, Pagination pagination) {
		return (Header<T>) Header.<T>builder().transactionTime(LocalDateTime.now()).resultCode("OK").description("OK")
				.data(data).pagination(pagination).build();
	}

	// ERROR(String description): 실패 상태를 의미하며, 실패 이유를 설명하는 에러 응답을 생성합니다.
	public static <T> Header<T> ERROR(String description) {
		return (Header<T>) Header.<T>builder().transactionTime(LocalDateTime.now()).resultCode("ERROR")
				.description(description).build();
	}
}