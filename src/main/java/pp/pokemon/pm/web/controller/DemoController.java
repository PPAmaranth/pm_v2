package pp.pokemon.pm.web.controller;
import pp.pokemon.pm.service.book.impl.BookServiceImpl;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    private static Gson gson = new Gson();

    @Autowired
    private BookServiceImpl bookService;


    @RequestMapping("/getBook/{id}")
    String bookInfo(@PathVariable("id") Integer id) {
        return gson.toJson(bookService.getById(id));
    }

    @RequestMapping("/getAllBook/")
    String allbookInfo() {
        return gson.toJson(bookService.getAllBooks());
    }
}
