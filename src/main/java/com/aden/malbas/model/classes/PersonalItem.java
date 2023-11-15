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
public class PersonalItem extends Item{

    @NotNull @NotEmpty
    @ElementCollection
    @CollectionTable(name = "nonApparel_item_available_sizes",
            joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "size")
    @Enumerated(EnumType.STRING)
    private Set<NonApparelSize> availableSizes = new HashSet<>();

    public PersonalItem(ItemDTO item){
        super(item);
    }

    public static Item createPersonalItem(ItemDTO item, List<String> availableSizes) {

        PersonalItem personalItem = new PersonalItem(item);
        for(String size: availableSizes){
            NonApparelSize nonApparelSize = NonApparelSize.valueOf(size.toUpperCase());
            personalItem.availableSizes.add(nonApparelSize);
        }

        return personalItem;
    }
}
