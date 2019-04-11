package pp.pokemon.pm.service.book.impl;
import pp.pokemon.pm.dao.entity.Book;
import pp.pokemon.pm.dao.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pp.pokemon.pm.service.book.BookService;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    public List<Book> getAllBooks() {
        return bookMapper.selectAll();
    }

    public Book getById(Integer id) {
        return bookMapper.getById(id);
    }
}
