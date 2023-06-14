package id.co.indivara.jdt12.wharehouseApp.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "whouse_to_store")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseToStore {
    @Id
    @Column(name = "transaction_id")
    private String transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "whouse_src")
    @JsonIgnore
    private Warehouse warehouseSrc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_dest")
    @JsonIgnore
    private Store storeDest;
    @Column(name = "good_id")
    private String goodId;
    @Column(name = "total")
    private Integer total;
}
