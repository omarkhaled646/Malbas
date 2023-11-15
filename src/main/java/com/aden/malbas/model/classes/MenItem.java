package com.aden.malbas.model.classes;

import com.aden.malbas.dto.ItemDTO;
import com.aden.malbas.model.enums.AdultSize;
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
public class MenItem extends Item{

    @NotNull @NotEmpty
    @ElementCollection
    @CollectionTable(name = "adult_item_available_sizes",
            joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "size")
    @Enumerated(EnumType.STRING)
    private Set<AdultSize> availableSizes = new HashSet<>();


    public MenItem(ItemDTO item){
        super(item);
    }

    public static Item createMenItem(ItemDTO item, List<String> availableSizes) {

        MenItem menItem = new MenItem(item);
        for(String size: availableSizes){
            AdultSize adultSize = AdultSize.valueOf(size.toUpperCase());
            menItem.availableSizes.add(adultSize);
        }

        return menItem;
    }
}
