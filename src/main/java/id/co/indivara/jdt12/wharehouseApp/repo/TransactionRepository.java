package id.co.indivara.jdt12.wharehouseApp.repo;
import id.co.indivara.jdt12.wharehouseApp.entity.SuppToWarehouse;
import id.co.indivara.jdt12.wharehouseApp.entity.Transaction;
import id.co.indivara.jdt12.wharehouseApp.entity.Warehouse;
import id.co.indivara.jdt12.wharehouseApp.entity.WarehouseToWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByType(String type);
}
