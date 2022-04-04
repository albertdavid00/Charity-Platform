package com.example.softbinatorlabs.services;

import com.example.softbinatorlabs.models.Category;
import com.example.softbinatorlabs.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService categoryService;

    @Test
    void getCategories(){
        Category category1 = Category.builder().name("firstCategory").build();
        Category category2 = Category.builder().name("secondCategory").build();
        List<Category> categoryList = List.of(category1, category2);

        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<Category> categories = categoryService.getCategories();
        Assertions.assertEquals(categoryList, categories);
        verify(categoryRepository).findAll();
    }

}
