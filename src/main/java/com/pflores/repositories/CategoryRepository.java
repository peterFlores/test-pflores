package com.pflores.repositories;

import com.pflores.models.Category;
import com.pflores.models.CategoryTree;

import java.util.Set;

public class CategoryRepository implements ICategoryRepository {


    private CategoryTree categoryTree;

    /**
     * Repository initialize with
     * categoryTree data structure to handle tree operations
     * @param categoryTree
     */
    public CategoryRepository(CategoryTree categoryTree) {
        this.categoryTree = categoryTree;
    }

    /**
     * adds a category on root
     * @param category
     * @return category
     */
    @Override
    public Category addCategory(Category category) {
        categoryTree.getRootCategory().setSubCategory(category);
        return category;
    }

    /**
     * adds a child category on parent category
     * @param parentCategory
     * @param childCategory
     * @return category
     */
    @Override
    public Category addCategory(Category parentCategory, Category childCategory) {
        parentCategory.setSubCategory(childCategory);
        return childCategory;
    }


    /**
     * Finds a set of keywords by category name
     * @param categoryName
     * @return root keywords, if given category is missing,
     *  if category has missing keywords returns parent keywords
     */
    @Override
    public Set<String> getKeywordsByCategoryName(String categoryName) {
        Set<String> keywords;
        try {
            Category category = findCategoryByName(categoryName);
            keywords = category.getKeywords().isEmpty()
                    ? findParentCategory(categoryTree.getRootCategory(), category).getKeywords()
                    : category.getKeywords();
        } catch (NullPointerException e) {
            keywords = categoryTree.getRootCategory().getKeywords();
        }
        return keywords;
    }

    @Override
    public int getLevelByCategoryName(String categoryName) {
        return getLevelByCategoryName(categoryTree.getRootCategory(), categoryName);
    }


    /**
     * Finds category level by recursively search
     * @param rootCategory
     * @param categoryName
     * @return 0 if given category is root, and negative one if the category is
     * missing
     */
    private int getLevelByCategoryName(Category rootCategory, String categoryName) {
        if (categoryName.equals(rootCategory.getName())) return 0;

        for (Category child: rootCategory.getSubCategories()) {
            int level = getLevelByCategoryName(child, categoryName);
            if (level >= 0) return ++level;
        }

        return -1;
    }

    @Override
    public Category findCategoryByName(String categoryName) {
        return findCategoryByName(categoryTree.getRootCategory(), categoryName);
    }

    /**
     * finds category on root using recursively search
     * @param rootCategory
     * @param categoryName
     * @return null when category is missing on root,
     * and return given category when exists on parent or root
     */
    private Category findCategoryByName(Category rootCategory, String categoryName) {
        if (categoryName.equals(rootCategory.getName())) return rootCategory;
        for (Category child: rootCategory.getSubCategories()) {
            Category categoryFound = findCategoryByName(child, categoryName);
            if (categoryFound != null) return  categoryFound;
        }
        return null;
    }


    /**
     * Finds a parent category with existing keywords
     * @param rootCategory
     * @param childCategory
     * @return null when category is missing on root,
     * and return parent category when contains the child and keywords exists
     */
    private Category findParentCategory(Category rootCategory, Category childCategory) {
        if (rootCategory.getSubCategories().contains(childCategory)) {
            if (rootCategory.getKeywords().isEmpty()) return findParentCategory(categoryTree.getRootCategory(), rootCategory);
            return rootCategory;
        } else {
            for (Category child: rootCategory.getSubCategories()) {
                Category parent = findParentCategory(child, childCategory);
                if (parent != null) return parent;
            }
            return null;
        }
    }
 }
