package id.co.indivara.jdt12.wharehouseApp.repo;
import id.co.indivara.jdt12.wharehouseApp.entity.Goods;
import id.co.indivara.jdt12.wharehouseApp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;

@Transactional
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    Transaction findByGoods(Goods goods);
}
