package com.java.webdevelopment.shopping_app.payload.responses;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PageResponse<T> {
    
    private List<T> data;
    private int pageSize;
    private int currentPage;
    private int lastPage;
    private int total;

    public PageResponse() {
        data = new ArrayList<>();
    }
}
