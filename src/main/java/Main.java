import com.pflores.models.Category;
import com.pflores.models.CategoryTree;
import com.pflores.repositories.CategoryRepository;
import com.pflores.services.CategoryService;

public class Main {
    public static void main(String[] args) {
        CategoryTree categoryTree = new CategoryTree();
        CategoryRepository categoryRepository = new CategoryRepository(categoryTree);
        CategoryService categoryService = new CategoryService(categoryRepository);

        Category furniture = new Category("Furniture");
        furniture.setKeyword("Furntirue 80s");
        furniture.setKeyword("Garden Furniture");

        Category gardenAppliances = new Category("Garden Appliances");

        Category garageAppliances = new Category("Garage Appliances");

        Category home = new Category("Home");
        home.setKeyword("Home 80s");

        Category homeGothic = new Category("Home Gothic");
        homeGothic.setKeyword("Home Gothic style");

        categoryService.addCategory(furniture);
        categoryService.addCategory(home);

        categoryService.addCategory(furniture, garageAppliances);
        categoryService.addCategory(garageAppliances, gardenAppliances);

        categoryService.addCategory(home, homeGothic);

        System.out.println(categoryService.getKeywordsByCategory(garageAppliances));
        System.out.println(categoryService.getLevelByCategory(gardenAppliances));
    }
}
