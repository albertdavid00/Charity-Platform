package com.example.softbinatorlabs.services;

import com.example.softbinatorlabs.dtos.CategoryDto;
import com.example.softbinatorlabs.models.Category;
import com.example.softbinatorlabs.models.User;
import com.example.softbinatorlabs.repositories.CategoryRepository;
import com.example.softbinatorlabs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }


    public List<Category> getCategories() {

        return categoryRepository.findAll();
    }


    public void addCategory(CategoryDto categoryDto, long userId) {
        User user = userRepository.findById(userId).get();

        Category category = Category.builder()
                .name(categoryDto.getName())
                .user(user)
                .build();

        categoryRepository.save(category);
    }

    public void updateCategory(CategoryDto categoryDto, Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.updateCategory(categoryDto.getName(), id);
        }
        else{
            throw new BadRequestException("Category with id " + id + "doesn't exist!");
        }
    }

    public void deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new BadRequestException("Category with id " + id + "doesn't exist!");
        }
    }
}
