package com.aden.malbas.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class MenItem extends Item{
    @Enumerated(EnumType.STRING)
    private MenPieceType pieceType;
    @NotEmpty @Enumerated(EnumType.STRING)
    private List<Size> availableSizes;
}
