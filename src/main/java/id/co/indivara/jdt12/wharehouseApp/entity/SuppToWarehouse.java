package id.co.indivara.jdt12.wharehouseApp.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "supp_to_whouse")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuppToWarehouse {
    @Id
    @Column(name = "transaction_id")
    private String transactionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "whouse_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private Warehouse warehouse;
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "goods_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private Goods goods;
    @Column(name = "total")
    private Integer total;
    @Column(name = "date_time")
    private Date dateTime;
}
