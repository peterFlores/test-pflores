package com.pflores.services;

import com.pflores.models.Category;
import com.pflores.repositories.ICategoryRepository;

import java.util.Set;

public class CategoryService {

    private ICategoryRepository categoryRepository;

    /**
     * Use dependency injection pattern for access to the repository layer
     * @param categoryRepository
     */
    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Add a category to root category
     * @param category that will be added to root
     * @return added category on root
     */
    public Category addCategory(Category category) {
        if (category == null) throw new IllegalArgumentException("Category must not be null");
        if (category.getName().trim().isEmpty()) throw new IllegalArgumentException("Category name must not be empty");
        return categoryRepository.addCategory(category);
    }

    /**
     * add a child category to parent category
     * @param parentCategory
     * @param childCategory
     * @return added category on parent category
     */
    public Category addCategory(Category parentCategory, Category childCategory) {
        if (parentCategory == null || childCategory == null) throw new IllegalArgumentException("Category must not be null");
        checkValidCategoryName(parentCategory.getName().trim());
        checkValidCategoryName(childCategory.getName().trim());
        Category parentFound = categoryRepository.findCategoryByName(parentCategory.getName());
        if (parentFound != null) return categoryRepository.addCategory(parentCategory, childCategory);
        return null;
    }

    /**
     * finds the level of a given category by its name
     * @param category
     * @return the level of the category node on tree category
     */
    public int getLevelByCategory(Category category) {
        checkValidCategoryName(category.getName().trim());
        return categoryRepository.getLevelByCategoryName(category.getName());
    }

    /**
     * finds the keywords of a given category by its name
     * @param category
     * @return set of string keywords
     */
    public Set<String> getKeywordsByCategory(Category category) {
        checkValidCategoryName(category.getName().trim());
        return categoryRepository.getKeywordsByCategoryName(category.getName());
    }

    /**
     * throw an illegalArgumentException, if category name is empty
     * @param name variable that will be checked fo emptiness
     */
    private void checkValidCategoryName(String name) {
        if (name.isEmpty()) throw new IllegalArgumentException("Category name must not be empty");
    }
}
