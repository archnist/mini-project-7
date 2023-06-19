package id.co.indivara.jdt12.wharehouseApp.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "stores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {
    @Id
    @Column(name = "store_id")
    private String storeId;
    @Column(name = "store_name")
    private String storeName;
    @Column(name = "join_date")
    private Date date;
}
