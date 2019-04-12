package pp.pokemon.pm.service.book.impl;
import org.springframework.beans.BeanUtils;
import pp.pokemon.pm.dao.entity.Book;
import pp.pokemon.pm.dao.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pp.pokemon.pm.service.book.BookService;
import pp.pokemon.pm.web.vo.book.AddBookReqVo;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<Book> getAllBooks() {
        return bookMapper.selectAll();
    }

    @Override
    public Book getById(Integer id) {
        return bookMapper.getById(id);
    }

    @Override
    public void add(AddBookReqVo reqVo) {
        Book book = new Book();
        BeanUtils.copyProperties(reqVo, book);
        book.setPrice(reqVo.getPrice().doubleValue());

        bookMapper.insert(book);
    }
}
