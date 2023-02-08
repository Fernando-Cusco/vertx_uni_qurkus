package cusco.mejia.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataDto implements Serializable {
    private String can;
    private String sub;
    private String trn;
    private String code;
    private String message;
    private List<Map<String, String>> cuentas = new ArrayList<>();
    private List<Map<String, String>> tarjetas = new ArrayList<>();
    private Map<String, Object> ctl = new HashMap<>();
    private static final long serialVersionUID = 1L;
}
