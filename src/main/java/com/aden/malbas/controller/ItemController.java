package com.aden.malbas.controller;

import com.aden.malbas.dto.ItemDTO;
import com.aden.malbas.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Malbas")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemDTO>> malbasMain() {
        String defaultCategory = "women";
        List<ItemDTO> itemDTOs = itemService.findBy(defaultCategory);
        return new ResponseEntity<>(itemDTOs, HttpStatus.OK);
    }

    @GetMapping("{category}")
    public ResponseEntity<List<ItemDTO>> getCategoryItems(@PathVariable String category) {
        List<ItemDTO> itemDTOs = itemService.findBy(category);
        return new ResponseEntity<>(itemDTOs, HttpStatus.OK);
    }

    @PostMapping("/admin/addItem")
    public ResponseEntity<String> add(@RequestBody ItemDTO item) {
        itemService.add(item);
        return new ResponseEntity<>("Item added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/admin/updateItem/{itemId}/addColor")
    public ResponseEntity<String> addColor(@PathVariable Integer itemId, @RequestParam String colorName) {
        itemService.addColor(itemId, colorName);
        return new ResponseEntity<>("item updated successfully", HttpStatus.OK);
    }

    @PutMapping("/admin/updateItem/{itemId}/addSize")
    public ResponseEntity<String> addSize(@PathVariable Integer itemId, @RequestParam String sizeName) {
        itemService.addSize(itemId, sizeName);
        return new ResponseEntity<>("item updated successfully", HttpStatus.OK);
    }

    @PutMapping("/admin/updateItem/{itemId}/addSale")
    public ResponseEntity<String> addSale(@PathVariable Integer itemId, @RequestParam Double salePrice) {
        itemService.addSale(itemId, salePrice);
        return new ResponseEntity<>("item updated successfully", HttpStatus.OK);
    }

    @PutMapping("/admin/updateItem/{itemId}/removeSale")
    public ResponseEntity<String> removeSale(@PathVariable Integer itemId) {
        itemService.removeSale(itemId);
        return new ResponseEntity<>("item updated successfully", HttpStatus.OK);
    }

    @PutMapping("/admin/updateItem/{itemId}/addPieces")
    public ResponseEntity<String> addPieces(@PathVariable Integer itemId,
                                            @RequestParam Integer piecesCount) {
        itemService.addPieces(itemId, piecesCount);
        return new ResponseEntity<>("item updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/admin/deleteItem/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Integer itemId) {
        itemService.deleteItem(itemId);
        return new ResponseEntity<>("item deleted successfully", HttpStatus.OK);
    }

}
