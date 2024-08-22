package it.city.appedooserver.repository;

import it.city.appedooserver.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group,Integer> {
//    boolean existsById(Integer id);
//    boolean existsByCategoryId(Integer categoryId);
//    boolean existsByNameEqualsIgnoreCaseAndCategoryId(String name, Integer category_id);
}
