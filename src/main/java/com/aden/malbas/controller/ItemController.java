package com.aden.malbas.controller;

import com.aden.malbas.dto.ItemDTO;
import com.aden.malbas.exception.InvalidArgumentException;
import com.aden.malbas.exception.NotFoundException;
import com.aden.malbas.exception.ServerException;
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
        try {
            List<ItemDTO> itemDTOs = itemService.findBy(defaultCategory);
            return new ResponseEntity<>(itemDTOs, HttpStatus.OK);
        }catch (Exception e){
            throw new ServerException("An Error Occurred");
        }
    }

    @GetMapping("{category}")
    public ResponseEntity<List<ItemDTO>> getCategoryItems(@PathVariable String category) {
        try {
            List<ItemDTO> itemDTOs = itemService.findBy(category);
            return new ResponseEntity<>(itemDTOs, HttpStatus.OK);
        }catch (NotFoundException notFoundException){
            throw new NotFoundException(notFoundException.getMessage());
        }catch (Exception e){
            throw new ServerException("An Error Occurred");
        }
    }

    @PostMapping("/admin/addItem")
    public ResponseEntity<String> add(@RequestBody ItemDTO item) {
        try {
            itemService.add(item);
            return new ResponseEntity<>("Item added successfully", HttpStatus.CREATED);
        }catch (NotFoundException notFoundException){
            throw new NotFoundException(notFoundException.getMessage());
        }catch (Exception e){
            throw new ServerException("An Error Occurred");
        }
    }

    @PutMapping("/admin/updateItem/{itemId}/addColor")
    public ResponseEntity<String> addColor(@PathVariable Integer itemId, @RequestParam String colorName) {
        try {
            itemService.addColor(itemId, colorName);
            return new ResponseEntity<>("item updated successfully", HttpStatus.OK);
        }catch (NotFoundException notFoundException){
            throw new NotFoundException(notFoundException.getMessage());
        }catch(InvalidArgumentException invalidArgumentException) {
            throw new InvalidArgumentException(invalidArgumentException.getMessage());
        }catch (Exception e){
            throw new ServerException("An Error Occurred");
        }
    }

    @PutMapping("/admin/updateItem/{itemId}/addSize")
    public ResponseEntity<String> addSize(@PathVariable Integer itemId, @RequestParam String sizeName) {
       try {
           itemService.addSize(itemId, sizeName);
           return new ResponseEntity<>("item updated successfully", HttpStatus.OK);
       }catch (NotFoundException notFoundException){
           throw new NotFoundException(notFoundException.getMessage());
       }catch(InvalidArgumentException invalidArgumentException) {
           throw new InvalidArgumentException(invalidArgumentException.getMessage());
       }catch (Exception e){
           throw new ServerException("An Error Occurred");
       }
    }

    @PutMapping("/admin/updateItem/{itemId}/addSale")
    public ResponseEntity<String> addSale(@PathVariable Integer itemId, @RequestParam Double salePrice) {
        try {
            itemService.addSale(itemId, salePrice);
            return new ResponseEntity<>("item updated successfully", HttpStatus.OK);
        }catch (NotFoundException notFoundException){
            throw new NotFoundException(notFoundException.getMessage());
        }catch(InvalidArgumentException invalidArgumentException) {
            throw new InvalidArgumentException(invalidArgumentException.getMessage());
        }catch (Exception e){
            throw new ServerException("An Error Occurred");
        }
    }

    @PutMapping("/admin/updateItem/{itemId}/removeSale")
    public ResponseEntity<String> removeSale(@PathVariable Integer itemId) {
        try {
            itemService.removeSale(itemId);
            return new ResponseEntity<>("item updated successfully", HttpStatus.OK);
        }catch (NotFoundException notFoundException){
            throw new NotFoundException(notFoundException.getMessage());
        }catch (Exception e){
            throw new ServerException("An Error Occurred");
        }
    }

    @PutMapping("/admin/updateItem/{itemId}/addPieces")
    public ResponseEntity<String> addPieces(@PathVariable Integer itemId,
                                            @RequestParam Integer piecesCount) {
        try {
            itemService.addPieces(itemId, piecesCount);
            return new ResponseEntity<>("item updated successfully", HttpStatus.OK);
        }catch (NotFoundException notFoundException){
            throw new NotFoundException(notFoundException.getMessage());
        }catch(InvalidArgumentException invalidArgumentException) {
            throw new InvalidArgumentException(invalidArgumentException.getMessage());
        }catch (Exception e){
            throw new ServerException("An Error Occurred");
        }
    }

    @DeleteMapping("/admin/deleteItem/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Integer itemId) {
        try {
            itemService.deleteItem(itemId);
            return new ResponseEntity<>("item deleted successfully", HttpStatus.OK);
        }catch (NotFoundException notFoundException){
            throw new NotFoundException(notFoundException.getMessage());
        }catch (Exception e){
            throw new ServerException("An Error Occurred");
        }
    }

    @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<String> notFoundHandler(Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<String> invalidArgumentException(InvalidArgumentException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> serverExceptionHandler(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
