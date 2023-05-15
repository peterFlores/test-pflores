package com.pflores.models;

import java.util.HashSet;
import java.util.Set;

public class CategoryTree {

    private Category root;

    public CategoryTree() {
        this.root = new Category("Root");
        Set<String> defaultKeywords = new HashSet<>();
        defaultKeywords.add("Root");
        defaultKeywords.add("Index");
        defaultKeywords.add("Default");
        this.root.setKeywords(defaultKeywords);
    }

    public Category getRootCategory() {
        return this.root;
    }
}
