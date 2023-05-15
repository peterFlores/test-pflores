package com.pflores.repositories;

import com.pflores.models.Category;

import java.util.Set;

public interface ICategoryRepository {
    public Category addCategory(Category category);
    public Category addCategory(Category parentCategory, Category childCategory);
    public Set<String> getKeywordsByCategoryName(String categoryName);
    public int getLevelByCategoryName(String categoryName);
    public Category findCategoryByName(String categoryName);
}
