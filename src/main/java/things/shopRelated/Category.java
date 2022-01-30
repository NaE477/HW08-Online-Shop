package things.shopRelated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
}