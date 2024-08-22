package it.city.appedooserver.controller;

import it.city.appedooserver.DTOs.CategoryReqDTO;
import it.city.appedooserver.DTOs.CategoryResDTO;
import it.city.appedooserver.payload.ApiResponse;
import it.city.appedooserver.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/category")
public class CategoryController {
    final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseBody
    public List<CategoryResDTO> getCategory() {
        return categoryService.getCategoryList();
    }

    @PostMapping("/add")
    public HttpEntity<?> saveCategory(@RequestBody CategoryReqDTO categoryReqDTO) {
        ApiResponse apiResponse = categoryService.addCategory(categoryReqDTO);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteCategory(@PathVariable Integer id) {
        ApiResponse apiResponse = categoryService.deleteCategory(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editCategory(@PathVariable Integer id, @RequestBody CategoryReqDTO reqCategory) {
        ApiResponse apiResponse = categoryService.editCategory(reqCategory, id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

}
