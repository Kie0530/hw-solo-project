package com.codestates.soloproject.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PageInfo {
    private int page, size, totalPages;
    private long totalElements;
}
