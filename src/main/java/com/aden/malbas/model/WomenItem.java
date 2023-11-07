package com.aden.malbas.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class WomenItem extends Item{
    @Enumerated(EnumType.STRING)
    private WomenPieceType pieceType;
    @NotEmpty @Enumerated(EnumType.STRING)
    private List<Size> availableSizes;
}
