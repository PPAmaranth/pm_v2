package pp.pokemon.pm.service.book;

import pp.pokemon.pm.dao.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getById(Integer id);
}
