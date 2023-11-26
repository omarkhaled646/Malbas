package com.aden.malbas.model.classes;

import com.aden.malbas.dto.ItemDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "category", discriminatorType = DiscriminatorType.STRING)
public abstract class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "item_id")
    private Integer id;
    @NotNull @NotEmpty @NotBlank @Column(unique = true)
    private String name;
    private String collection;
    @NotNull
    private String description;
    @NotNull @Min(1)
    private Double price;
    @NotNull @Min(1)
    private Integer availablePiecesCount;
    @NotNull @NotEmpty
    @ElementCollection
    @CollectionTable(name = "item_available_colors",
            joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "color")
    private List<String> availableColors;
//    private boolean isOnSale = false;
    @Min(1)
    private Double salePrice;
    @OneToMany(mappedBy = "item")
    private List<OrderItem> orders;
    @OneToMany(mappedBy = "item")
    private List<CartItem> carts;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "wishlist_item",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "wishlist_id"))
    private List<Wishlist> wishlists;

    public Item(ItemDTO item) {
        this.name = item.getName();
        this.collection = item.getCollection();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.availablePiecesCount = item.getAvailablePiecesCount();
        this.availableColors = item.getAvailableColors();
        this.salePrice = item.getSalePrice();
    }

    public void addColor(String colorName) {

        if(this.availableColors == null){
            this.availableColors = new ArrayList<>();
        }

        this.availableColors.add(colorName);
    }

    public void addPieces(Item item, Integer piecesCount) {
        item.availablePiecesCount += piecesCount;
    }

    public abstract void addSize(String sizeName);

    public abstract Boolean isSizeAvailable(String sizeName);
}
