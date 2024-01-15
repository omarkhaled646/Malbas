package com.aden.malbas.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequest {

    private Integer itemId;
    private Integer numberOfPieces;
    private String size;
}
