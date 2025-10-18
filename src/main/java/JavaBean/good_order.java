package JavaBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class good_order {
    private Integer ogCodes;
    private Integer order_id;
    private Integer goods_id;
    private  Integer quantity;
}
