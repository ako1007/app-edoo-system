package it.city.appedooserver.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryReqDTO {

    private Integer parentCategoryId;

    private  String name;

    private Double price;

    private String description;
}
