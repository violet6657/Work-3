package JavaBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class order {
    private  Integer orderCode;
    private LocalDateTime orderTime;
    private Integer orderPrice;

}
