package it.city.appedooserver.service;

import it.city.appedooserver.DTOs.CategoryReqDTO;
import it.city.appedooserver.DTOs.CategoryResDTO;
import it.city.appedooserver.entity.Category;
import it.city.appedooserver.payload.ApiResponse;
import it.city.appedooserver.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public ApiResponse addCategory(CategoryReqDTO categoryReqDTO) {
        if (categoryRepository.existsByNameEqualsIgnoreCase(categoryReqDTO.getName()))
            return new ApiResponse("category name already exist", false);
        if (categoryReqDTO.getPrice() == null) return new ApiResponse("price is not null", false);
        return addOrditCategory(categoryReqDTO, new Category());
    }

    public ApiResponse editCategory(CategoryReqDTO categoryReqDTO, Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceAccessException("Get group"));
        return addOrditCategory(categoryReqDTO, category);
    }

    public List<CategoryResDTO> getCategoryList() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResDTO> categoryResDTOList = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryResDTO categoryResDTO = new CategoryResDTO();
            categoryResDTO.setId(category.getId());
            categoryResDTO.setName(category.getName());
            if (category.getParentCategory() != null)
                categoryResDTO.setParentCategoryId(category.getParentCategory().getId());
            categoryResDTO.setDescription(category.getDescription());
            categoryResDTO.setPrice(category.getPrice());
            categoryResDTOList.add(categoryResDTO);
        }
        return categoryResDTOList;
    }

    public ApiResponse deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
        return new ApiResponse("Successfully deleted!", true);
    }

    public ApiResponse addOrditCategory(CategoryReqDTO categoryReqDTO, Category category) {
        if (categoryReqDTO.getName().isEmpty()) return new ApiResponse("price is not null", false);
        category.setName(categoryReqDTO.getName());
        if (categoryReqDTO.getParentCategoryId() != null)
            category.setParentCategory(categoryRepository.findById(categoryReqDTO.getParentCategoryId()).orElseThrow(() -> new ResourceAccessException("getCategory")));
        category.setPrice(categoryReqDTO.getPrice());
        category.setDescription(categoryReqDTO.getDescription());
        categoryRepository.save(category);
        return new ApiResponse("successfully saved Category", true);
    }
}
