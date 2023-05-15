package com.pflores.services;

import com.pflores.models.Category;
import com.pflores.models.CategoryTree;
import com.pflores.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceTest {

    private CategoryTree tree;
    private CategoryService categoryService;
    @BeforeEach
    void setUp() {
        tree = new CategoryTree();
        categoryService = new CategoryService(new CategoryRepository(tree));
    }

    @Test
    public void addNullCategoryShouldReturnException() {
        assertThrows(IllegalArgumentException.class, () -> {
            categoryService.addCategory(null);
        });
    }

    @Test
    public void addNullCategoriesShouldReturnException() {

        assertThrows(IllegalArgumentException.class, () -> {
            categoryService.addCategory(null, new Category("Test"));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            categoryService.addCategory(new Category("Test"), null);
        });
    }

    @Test
    public void addCategoryWithEmptyNameShouldReturnException() {
        assertThrows(IllegalArgumentException.class, () -> {
            categoryService.addCategory(new Category(" "));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            categoryService.addCategory(new Category(" "), new Category("Test"));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            categoryService.addCategory(new Category(" "), new Category("Test"));
        });
    }

    @Test
    public void nonExistentCategoryShouldReturnNegativeLevel() {
        int level = categoryService.getLevelByCategory(new Category("Non existent"));
        assertEquals(-1 , level);
    }

    @Test
    public void getLevelByCategory() {
        Category actualCategory = categoryService.addCategory(new Category("Furniture"));
        int actualLevel = categoryService.getLevelByCategory(actualCategory);
        assertEquals(1, actualLevel);
    }

    @Test
    public void missingCategoryKeywordsShouldReturnParentKeywords() {
        Category furniture = new Category("Furniture");
        furniture.setKeyword("Furntirue 80s");
        furniture.setKeyword("Garden Furniture");

        categoryService.addCategory(furniture);

        Category gardenAppliances = new Category("Garden Appliances");
        Category garageAppliances = new Category("Garage Appliances");

        categoryService.addCategory(furniture, gardenAppliances);
        categoryService.addCategory(gardenAppliances, garageAppliances);
        Set<String> actualKeywords = categoryService.getKeywordsByCategory(garageAppliances);
        assertEquals(furniture.getKeywords(), actualKeywords);
    }


    @Test
    public void missingParentCategoryKeywordsShouldReturnRootKeywords() {
        Category furniture = new Category("Furniture");

        categoryService.addCategory(furniture);

        Category gardenAppliances = new Category("Garden Appliances");
        Category garageAppliances = new Category("Garage Appliances");

        categoryService.addCategory(furniture, gardenAppliances);
        categoryService.addCategory(gardenAppliances, garageAppliances);
        Set<String> actualKeywords = categoryService.getKeywordsByCategory(garageAppliances);
        assertEquals(tree.getRootCategory().getKeywords(), actualKeywords);
    }

    @Test void addCategory() {
        Category expected = new Category("Furniture");
        Category actual = categoryService.addCategory(expected);
        assertEquals(expected, actual);
    }

    @Test void addCategories() {
        Category expectedParent = new Category("Furniture");
        Category actualParent = categoryService.addCategory(expectedParent);
        assertEquals(expectedParent, actualParent);
        Category childExpected = new Category("Garage Appliances");
        Category childActual = categoryService.addCategory(actualParent, childExpected);
        assertEquals(childExpected, childActual);
    }

    @Test void addCategoryOnMissingParentShouldReturnNull() {
        Category missingParent = new Category("Furniture");
        Category child = new Category("Garage Appliances");
        Category childActual = categoryService.addCategory(missingParent, child);
        assertNull(childActual);
    }


}