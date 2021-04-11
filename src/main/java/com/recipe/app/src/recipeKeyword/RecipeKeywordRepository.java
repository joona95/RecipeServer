package com.recipe.app.src.recipeKeyword;

import com.recipe.app.src.recipeKeyword.models.RecipeKeyword;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RecipeKeywordRepository extends CrudRepository<RecipeKeyword, Integer> {
    @Query(value="select keyword,count(keyword) as keywordCount\n" +
            "from RecipeKeyword\n" +
            "where status = 'ACTIVE'\n" +
            "group by keyword\n" +
            "order by keywordCount desc limit 10;",nativeQuery = true)
    List<Object[]> findByBestKeywordTop10();
}
