package com.ecommerce.gadgetzone.dto.request;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    private String categoryName;

}
