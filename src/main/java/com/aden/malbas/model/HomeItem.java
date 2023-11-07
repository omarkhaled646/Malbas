package com.aden.malbas.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class HomeItem extends Item{

    private String pieceType;
    @NotNull @NotEmpty @NotBlank
    private List<String> availableSizes;
}
