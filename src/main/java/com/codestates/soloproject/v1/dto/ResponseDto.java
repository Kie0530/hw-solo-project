package com.codestates.soloproject.v1.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseDto<T> {
    public List<T> data;

    public ResponseDto(List<T> data) {
        this.data = data;
    }
}
