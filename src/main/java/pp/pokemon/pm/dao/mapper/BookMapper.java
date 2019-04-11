package pp.pokemon.pm.dao.mapper;

import pp.pokemon.pm.dao.entity.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface BookMapper {

    int insert(Book record);
    List<Book> selectAll();
    Book getById(@Param(value = "id") Integer id);

}
