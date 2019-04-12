package pp.pokemon.pm.service.book;

import pp.pokemon.pm.dao.entity.Book;
import pp.pokemon.pm.web.vo.book.AddBookReqVo;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getById(Integer id);

    void add(AddBookReqVo reqVo);
}
