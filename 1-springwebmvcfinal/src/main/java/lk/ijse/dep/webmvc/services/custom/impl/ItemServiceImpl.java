package lk.ijse.dep.webmvc.services.custom.impl;

import lk.ijse.dep.webmvc.dto.ItemDTO;
import lk.ijse.dep.webmvc.entity.Item;
import lk.ijse.dep.webmvc.repository.ItemRepository;
import lk.ijse.dep.webmvc.services.custom.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<ItemDTO> getAllItems(){

        List<ItemDTO> items = itemRepository.findAll().stream().map(item -> new ItemDTO(item.getCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand())).collect(Collectors.toList());

        return items;


    }

    public void saveItem(ItemDTO item){
        if (isItemExist(item.getCode())){
            throw new RuntimeException("Item is already exist");
        }
        itemRepository.save(new Item(item.getCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));

    }

    public void updateItem(ItemDTO item){

        itemRepository.save(new Item(item.getCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));

    }

    public void deleteItem(String code) {

        itemRepository.deleteById(code);

    }

    @Override
    public ItemDTO getItemByCode(String code) {
        Item item = itemRepository.getOne(code);
        ItemDTO itemDTO = new ItemDTO(item.getCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand());
        return itemDTO;
    }

    @Override
    public boolean isItemExist(String code) {
        return itemRepository.existsById(code);
    }

    public int itemsCount(){
        return (int) itemRepository.count();
    }

}
