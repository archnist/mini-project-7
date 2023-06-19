package id.co.indivara.jdt12.wharehouseApp.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "whouse_src")
    @JsonIgnore
    private Warehouse warehouseSource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "whouse_dest")
    @JsonIgnore
    private Warehouse warehouseDestination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;
    @Column(name = "total")
    private Integer total;
    @Column(name = "date_time")
    private Date dateTime;
}
