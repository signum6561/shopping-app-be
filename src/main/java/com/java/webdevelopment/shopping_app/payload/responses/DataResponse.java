package com.java.webdevelopment.shopping_app.payload.responses;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DataResponse<T> {
    
    private List<T> data;
    
    public DataResponse(List<T> data) {
        this.data = data;
    }

    public DataResponse() {
        data = new ArrayList<>();
    }

    public void addItem(T item) {
        data.add(item);
    }
}
