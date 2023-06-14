package id.co.indivara.jdt12.wharehouseApp.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "whouse_to_whouse")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseToWarehouse {
    @Id
    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "whouse_src")
    private String warehouseSource;
    @Column(name = "whouse_dest")
    private String warehouseDestination;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "good_id")
    @JsonIgnore
    private Goods goods;
    @Column(name = "total")
    private Integer total;
}
