package pp.pokemon.pm.mapper;

import org.apache.ibatis.annotations.Mapper;
import pp.pokemon.pm.entity.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface BookMapper {

    int insert(Book record);
    List<Book> selectAll();
    Book getById(@Param(value = "id") Integer id);

}
