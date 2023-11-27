package com.aden.malbas.model.classes;

import com.aden.malbas.dto.ItemDTO;
import com.aden.malbas.model.enums.AdultSize;
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
@DiscriminatorValue("women")
public class WomenItem extends Item{

    @NotNull @NotEmpty
    @ElementCollection
    @CollectionTable(name = "adult_item_available_sizes",
            joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "size")
    @Enumerated(EnumType.STRING)
    private Set<AdultSize> availableSizes = new HashSet<>();

    public WomenItem(ItemDTO item){
        super(item);
    }

    public static Item createWomenItem(ItemDTO item) {

        WomenItem womenItem = new WomenItem(item);
        for(String size: item.getAvailableSizes()){
            // TODO Add custom exception for size wrong data
           womenItem.addSize(size);

        }
        return womenItem;
    }

    @Override
    public void addSize(String sizeName) {

        if(this.availableSizes == null){
            this.availableSizes = new HashSet<>();
        }

        try {
            AdultSize adultSize = AdultSize.valueOf(sizeName.toUpperCase());
            this.availableSizes.add(adultSize);
        }catch (IllegalArgumentException exception){
            throw new IllegalArgumentException();
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
