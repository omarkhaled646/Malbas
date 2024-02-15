package com.aden.malbas.model.classes;

import com.aden.malbas.dto.ItemDTO;
import com.aden.malbas.exception.InvalidArgumentException;
import com.aden.malbas.model.enums.KidsSize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@DiscriminatorValue("kids")
public class KidsItem extends Item{

    @NotNull @NotEmpty
    @ElementCollection
    @CollectionTable(name = "kids_item_available_sizes",
            joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "size")
    @Enumerated(EnumType.STRING)
    private Set<KidsSize> availableSizes = new HashSet<>();


    public KidsItem(ItemDTO item){
        super(item);
    }

    @Override
    public void addAvailableSizes(List<String> sizeNames) {

        for (String sizeName : sizeNames) {
            this.addSize(sizeName);
        }
    }

    @Override
    public void addSize(String sizeName) {

        if (this.availableSizes == null) {
            this.availableSizes = new HashSet<>();
        }

        try {
            KidsSize kidsSize = KidsSize.valueOf(sizeName.toUpperCase());
            this.availableSizes.add(kidsSize);
        } catch (InvalidArgumentException exception) {
            throw new InvalidArgumentException("The new size is not a valid size!");
        }
    }

    @Override
    public Boolean isSizeAvailable(String sizeName) {

        KidsSize kidsSize = KidsSize.valueOf(sizeName.toUpperCase());

        return this.availableSizes.contains(kidsSize);
    }

    @Override
    public List<String> getAvailableSizes() {
        List<String> availableSizes = new ArrayList<>();

        for(KidsSize size: this.availableSizes){
            availableSizes.add(size.name());
        }

        return availableSizes;
    }

}
