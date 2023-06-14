package id.co.indivara.jdt12.wharehouseApp.repo;
import id.co.indivara.jdt12.wharehouseApp.entity.Goods;
import id.co.indivara.jdt12.wharehouseApp.entity.Warehouse;
import id.co.indivara.jdt12.wharehouseApp.entity.WarehouseInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;

@Transactional
public interface WarehouseInventoryRepository extends JpaRepository <WarehouseInventory,Integer> {
    WarehouseInventory findByGoodsAndWarehouse(Goods goodsId, Warehouse warehouseId);
}
