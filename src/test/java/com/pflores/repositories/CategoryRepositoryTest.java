package com.pflores.repositories;

import com.pflores.models.Category;
import com.pflores.models.CategoryTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CategoryRepositoryTest {

    private CategoryRepository categoryRepository;
    private CategoryTree categoryTree;
    @BeforeEach
    void setUp() {
        categoryTree = new CategoryTree();
        categoryRepository = new CategoryRepository(categoryTree);
    }

    @Test
    public void addCategory() {
        Category expected = new Category("Furnite");
        Category actual = categoryRepository.addCategory(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void addCategoryToParent() {
        Category expected = new Category("Furnite");
        Category actual = categoryRepository.addCategory(expected);
        assertEquals(expected, actual);

        Category expectedChild = new Category("Furnite Appliancces");
        Category actualChild = categoryRepository.addCategory(actual, expectedChild);
        assertEquals(expectedChild, actualChild);
    }

    @Test
    public void findCategoryByName() {
        Category expected = new Category("Furnite");
        Category actual = categoryRepository.addCategory(expected);
        assertEquals(expected, actual);

        Category expectedChild = new Category("Furnite Appliancces");
        Category actualChild = categoryRepository.addCategory(actual, expectedChild);
        assertEquals(expectedChild, actualChild);

        Category actualFound = categoryRepository.findCategoryByName(actualChild.getName());
        assertEquals(expectedChild, actualFound);
    }

    @Test
    public void missingCategoryShouldReturnNull() {
        Category actual = categoryRepository.findCategoryByName("Missing category");
        assertNull(actual);
    }

    @Test
    public void nonExistentCategoryShouldReturnNegativeLevel() {
        int level = categoryRepository.getLevelByCategoryName("Non existent");
        assertEquals(-1 , level);
    }

    @Test
    public void getLevelByCategory() {
        Category actualCategory = categoryRepository.addCategory(new Category("Furniture"));
        int actualLevel = categoryRepository.getLevelByCategoryName(actualCategory.getName());
        assertEquals(1, actualLevel);
    }

    @Test
    public void missingCategoryShouldReturnRootKeywords() {
        Set<String> actual = categoryRepository.getKeywordsByCategoryName("Non existent");
        assertEquals(categoryTree.getRootCategory().getKeywords(), actual);
    }

    @Test
    public void missingChildCategoryKeywordsShouldReturnParentKeywords() {
        Category furniture = new Category("Furniture");
        furniture.setKeyword("Furntirue 80s");
        furniture.setKeyword("Garden Furniture");

        categoryRepository.addCategory(furniture);

        Category gardenAppliances = new Category("Garden Appliances");
        Category garageAppliances = new Category("Garage Appliances");

        categoryRepository.addCategory(furniture, gardenAppliances);
        categoryRepository.addCategory(gardenAppliances, garageAppliances);
        Set<String> actualKeywords = categoryRepository.getKeywordsByCategoryName(garageAppliances.getName());
        assertEquals(furniture.getKeywords(), actualKeywords);
    }

    @Test
    public void getKeywordsByCategoryName() {
        Category furniture = new Category("Furniture");
        furniture.setKeyword("Furntirue 80s");
        furniture.setKeyword("Garden Furniture");

        categoryRepository.addCategory(furniture);

        Category gardenAppliances = new Category("Garden Appliances");
        gardenAppliances.setKeyword("Garden keyword 1");
        gardenAppliances.setKeyword("Garden keyword 2");

        categoryRepository.addCategory(furniture, gardenAppliances);

        Set<String> actualKeywords = categoryRepository.getKeywordsByCategoryName(gardenAppliances.getName());
        assertEquals(gardenAppliances.getKeywords(), actualKeywords);
    }
}