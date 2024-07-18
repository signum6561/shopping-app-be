package com.java.webdevelopment.shopping_app.payload.responses;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageResponse<T> {
    
    private List<T> data;
    private Integer pageSize;
    private Integer currentPage;
    private Integer lastPage;
    private Integer total;

    public PageResponse() {
        data = new ArrayList<>();
    }
}
