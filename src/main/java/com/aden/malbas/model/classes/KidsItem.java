package com.aden.malbas.model.classes;

import com.aden.malbas.dto.ItemDTO;
import com.aden.malbas.model.enums.KidsSize;
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

    public static Item createKidsItem(ItemDTO item, List<String> availableSizes) {

            KidsItem kidsItem = new KidsItem(item);
            for(String size: availableSizes){
                // TODO Add custom exception for size wrong data
               kidsItem.addSize(size);
        }

            return kidsItem;
        }

    @Override
    public void addSize(String sizeName) {

        if(this.availableSizes == null){
            this.availableSizes = new HashSet<>();
        }

        try {
            KidsSize kidsSize = KidsSize.valueOf(sizeName.toUpperCase());
            this.availableSizes.add(kidsSize);
        }catch (IllegalArgumentException exception){
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Boolean isSizeAvailable(String sizeName) {

        KidsSize kidsSize = KidsSize.valueOf(sizeName.toUpperCase());

        return this.availableSizes.contains(kidsSize);
    }

}
