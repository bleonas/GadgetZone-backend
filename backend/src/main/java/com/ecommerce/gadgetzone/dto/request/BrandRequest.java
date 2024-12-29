package com.ecommerce.gadgetzone.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class BrandRequest {
    private String brandName;

}
