package id.co.indivara.jdt12.wharehouseApp.repo;
import id.co.indivara.jdt12.wharehouseApp.entity.Goods;
import id.co.indivara.jdt12.wharehouseApp.entity.Store;
import id.co.indivara.jdt12.wharehouseApp.entity.StoreInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;

@Transactional
public interface StoreInventoryRepository extends JpaRepository<StoreInventory, Integer> {
    StoreInventory findByGoodsAndStore(Goods goodsId, Store storeId);
}
