package it.city.appedooserver.entity;


import it.city.appedooserver.entity.template.AbsNameEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//bu kurslarning classi hisoblanadi(BackEnd, FrontEnd, Foundation)
public class Category extends AbsNameEntity {//Alibek & Aslbek

    @ManyToOne
    private Category parentCategory;

    @Column(nullable = false)
    private Double price;//kursning oylik tulovi


    @Column(columnDefinition = "text")
    private String description;//kurs haqida malumot
    private boolean active;//ishlayapdi yoki ishlamayapdi

}
