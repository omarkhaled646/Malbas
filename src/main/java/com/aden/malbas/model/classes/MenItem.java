package com.aden.malbas.model.classes;

import com.aden.malbas.dto.ItemDTO;
import com.aden.malbas.exception.InvalidArgumentException;
import com.aden.malbas.model.enums.AdultSize;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@DiscriminatorValue("men")
public class MenItem extends Item{

    @ElementCollection
    @CollectionTable(name = "adult_item_available_sizes",
            joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "size")
    @Enumerated(EnumType.STRING)
    private Set<AdultSize> availableSizes = new HashSet<>();


    public MenItem(ItemDTO item){
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
            AdultSize adultSize = AdultSize.valueOf(sizeName.toUpperCase());
            this.availableSizes.add(adultSize);
        } catch (InvalidArgumentException exception) {
            throw new InvalidArgumentException("The new size is not a valid size!");
        }
    }

    @Override
    public Boolean isSizeAvailable(String sizeName) {

        AdultSize adultSize = AdultSize.valueOf(sizeName.toUpperCase());

        return this.availableSizes.contains(adultSize);
    }

    @Override
    public List<String> getAvailableSizes() {
        List<String> availableSizes = new ArrayList<>();

        for(AdultSize size: this.availableSizes){
            availableSizes.add(size.name());
        }

        return availableSizes;
    }

}
