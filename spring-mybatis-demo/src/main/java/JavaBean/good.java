package JavaBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class good {
    private Integer goodsCode;
    private String  goodsName;
    private double  goodsPrice;
}
