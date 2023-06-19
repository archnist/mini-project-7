package id.co.indivara.jdt12.wharehouseApp.entity;
import com.fasterxml.jackson.databind.JsonSerializable;
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
@Table(name = "warehouses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Warehouse {
    @Id
    @Column(name = "warehouse_id")
    private String warehouseId;
    @Column(name = "warehouse_name")
    private String warehouseName;
    @Column(name = "join_date")
    private Date date;
}