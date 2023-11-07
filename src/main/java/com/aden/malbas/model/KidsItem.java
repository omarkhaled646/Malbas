package com.aden.malbas.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class KidsItem extends Item{
    @Enumerated(EnumType.STRING)
    private KidsPieceType pieceType;
    @NotNull @NotEmpty @NotBlank
    private List<String> availableSizes;
}
