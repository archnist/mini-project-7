package id.co.indivara.jdt12.wharehouseApp.repo;
import id.co.indivara.jdt12.wharehouseApp.entity.WarehouseToWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;

@Transactional
public interface WarehouseToWarehouseRepository extends JpaRepository<WarehouseToWarehouse, String> {
}
