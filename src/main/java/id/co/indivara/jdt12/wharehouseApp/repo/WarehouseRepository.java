package id.co.indivara.jdt12.wharehouseApp.repo;
import id.co.indivara.jdt12.wharehouseApp.entity.SuppToWarehouse;
import id.co.indivara.jdt12.wharehouseApp.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface WarehouseRepository extends JpaRepository <Warehouse, String> {
}
