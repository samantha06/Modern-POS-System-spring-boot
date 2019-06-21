package lk.ijse.dep.webmvc.controller;


import lk.ijse.dep.webmvc.dto.ItemDTO;
import lk.ijse.dep.webmvc.services.custom.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RequestMapping("/api/v1/items")
@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

//    @GetMapping
//    public List<ItemDTO> getAllItems(){
//        return itemService.getAllItems();
//    }
@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<List<ItemDTO>> getAllCustomers(){
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("X-Count",String.valueOf(itemService.itemsCount()));
    httpHeaders.setAccessControlAllowHeaders(Arrays.asList("X-Count"));
    httpHeaders.setAccessControlExposeHeaders(Arrays.asList("X-Count"));
    return new ResponseEntity<List<ItemDTO>>(itemService.getAllItems(),httpHeaders,HttpStatus.OK);

}

    @GetMapping("{code:I\\d*}")
    public ResponseEntity<ItemDTO> getItem(@PathVariable String code){
        ItemDTO itemDTO = null;
        if (itemService.isItemExist(code)){
            itemDTO = itemService.getItemByCode(code);
        }
        return new ResponseEntity<ItemDTO>(itemDTO,(itemDTO!=null)? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveItem(@RequestBody ItemDTO itemDTO){
        if (itemDTO.getCode().isEmpty() || itemDTO.getDescription().isEmpty() || itemDTO.getQtyOnHand()<=0 || itemDTO.getUnitPrice()<=0){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }else {
            itemService.saveItem(itemDTO);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        }
    }

    @PutMapping("/{code:I\\d*}")
    public ResponseEntity<Void> updateItem(@PathVariable String code ,@RequestBody ItemDTO itemDTO){
        if (!itemService.isItemExist(code)){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        if (itemDTO.getDescription().isEmpty() || itemDTO.getQtyOnHand()<=0 || itemDTO.getUnitPrice()<=0){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }else {
            itemDTO.setCode(code);
            itemService.updateItem(itemDTO);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{code:I\\d*}")
    public ResponseEntity<Void> deleteItem(@PathVariable String code){
        if (!itemService.isItemExist(code)){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }else{
            itemService.deleteItem(code);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

}
