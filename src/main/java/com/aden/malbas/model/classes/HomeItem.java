package com.aden.malbas.model.classes;

import com.aden.malbas.dto.ItemDTO;
import com.aden.malbas.model.enums.NonApparelSize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("home")
public class HomeItem extends Item{

    @NotNull @NotEmpty
    @ElementCollection
    @CollectionTable(name = "nonApparel_item_available_sizes",
            joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "size")
    @Enumerated(EnumType.STRING)
    private Set<NonApparelSize> availableSizes = new HashSet<>();

    public HomeItem(ItemDTO item){
        super(item);
    }

    public static Item createHomeItem(ItemDTO item, List<String> availableSizes) {

        HomeItem homeItem = new HomeItem(item);
        for(String size: availableSizes){
            // TODO Add custom exception for size wrong data
            homeItem.addSize(size);
        }

        return homeItem;
    }

    @Override
    public void addSize(String sizeName) {

        if(this.availableSizes == null){
            this.availableSizes = new HashSet<>();
        }

        try {
            NonApparelSize nonApparelSize = NonApparelSize.valueOf(sizeName.toUpperCase());
            this.availableSizes.add(nonApparelSize);
        }catch (IllegalArgumentException exception){
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Boolean isSizeAvailable(String sizeName) {

        NonApparelSize nonApparelSize = NonApparelSize.valueOf(sizeName.toUpperCase());

        return this.availableSizes.contains(nonApparelSize);
    }


}
