package id.co.indivara.jdt12.wharehouseApp.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "goods")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Goods {
    @Id
    @Column(name = "good_id")
    private String goodId;
}
