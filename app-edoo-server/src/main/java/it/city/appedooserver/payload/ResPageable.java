package it.city.appedooserver.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResPageable {

    private Integer page;
    private Integer size;

    private Integer totalPage;

    private Long totalElement;

    private Object object;

}
