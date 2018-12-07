package com.yh.hr.dto;

import lombok.Data;

@Data
public class CPage {

    private int totalPages;
    private long totalElements;
    private long currPage;
    private boolean hasNext;
}