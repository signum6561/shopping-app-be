package com.java.webdevelopment.shopping_app.payload;

import java.util.Date;
import java.util.Set;

import com.java.webdevelopment.shopping_app.constants.Contants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    
    private String id;

    @NotBlank
    private String userId;

    @NotBlank
    private String status;

    private Date billedDate;

    @NotEmpty
    @Size(min = 1, message = Contants.INVALID_ORDER_ITEMS)
    private Set<OrderItemDTO> items;
}
