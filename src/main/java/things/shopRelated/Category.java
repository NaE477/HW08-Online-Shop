package things.shopRelated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class Category {
    private Integer id;
    private String catName;
    private Category superCategory;

    public Category(String catName){
        this.catName = catName;
    }
    public Category(Integer id,String catName){
        this.id = id;
        this.catName = catName;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " , Category: " + catName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(catName, category.catName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}