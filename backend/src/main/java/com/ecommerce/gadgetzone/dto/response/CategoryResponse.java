package com.ecommerce.gadgetzone.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class CategoryResponse {
    
    private int categoryId;
    private String categoryName;

}
