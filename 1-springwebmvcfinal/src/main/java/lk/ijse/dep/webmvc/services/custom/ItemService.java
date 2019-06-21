package lk.ijse.dep.webmvc.services.custom;

import lk.ijse.dep.webmvc.dto.ItemDTO;
import lk.ijse.dep.webmvc.services.SuperService;

import java.util.List;

public interface ItemService extends SuperService {

    public List<ItemDTO> getAllItems();

    public void saveItem(ItemDTO item);

    public void updateItem(ItemDTO item);

    public void deleteItem(String code);

    public ItemDTO getItemByCode(String code);

    public boolean isItemExist(String code);

    public int itemsCount();

}
