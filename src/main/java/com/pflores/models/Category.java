package com.pflores.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Category {

    private String name;
    private Set<String> keywords;
    private List<Category> subCategories;

    public Category(String name) {
        this.name = name;
        this.keywords = new HashSet<>();
        this.subCategories = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKeyword(String keyword) {
        this.keywords.add(keyword);
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords.addAll(keywords);
    }

    public void setSubCategory(Category subCategory) {
        this.subCategories.add(subCategory);
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories.addAll(subCategories);
    }
}
