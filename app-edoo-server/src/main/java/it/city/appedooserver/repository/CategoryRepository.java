package it.city.appedooserver.repository;

import it.city.appedooserver.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByNameEqualsIgnoreCase(String name);
}
