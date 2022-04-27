package diplom2;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Orders {
    private List<String> ingredients;

    public Orders(List<String> ingredients){
        this.ingredients =ingredients;
    }

}
