package it.city.appedooserver.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResDTO {
    private Integer id;
    private String name;

    private Integer parentCategoryId;

    private Double price;

    private String description;
}
