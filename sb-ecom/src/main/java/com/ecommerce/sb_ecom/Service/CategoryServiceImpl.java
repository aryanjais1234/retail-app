package com.ecommerce.sb_ecom.Service;

import com.ecommerce.sb_ecom.Model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    public List<Category> categorries = new ArrayList<>();
    private Long nextId = 1l;

    @Override
    public List<Category> getAllCategories() {
        return categorries;
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryId(nextId++);
        categorries.add(category);
    }

    @Override
    public String deleteCategory(Long id) {
        Category category = categorries.stream()
                .filter(c -> c.getCategoryId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found"));
        categorries.remove(category);
        return "Category deleted successfully";
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Optional<Category> optionalCategory = categorries.stream()
                .filter(c -> c.getCategoryId().equals(id))
                .findFirst();

        if(optionalCategory.isPresent()){
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryName(category.getCategoryName());
            return existingCategory;
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found");
        }
    }
}
