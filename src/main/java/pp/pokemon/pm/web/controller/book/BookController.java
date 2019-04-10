package pp.pokemon.pm.web.controller.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pp.pokemon.pm.service.book.BookService;
import pp.pokemon.pm.web.controller.BaseController;
import pp.pokemon.pm.web.vo.base.DefaultApiResult;
import pp.pokemon.pm.web.vo.book.GetBookReqVo;

@RestController
@RequestMapping("/book")
public class BookController extends BaseController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/getBookById", method = {RequestMethod.POST})
    public DefaultApiResult getBookById(@RequestBody GetBookReqVo reqVo) {
        return success(bookService.getById(reqVo.getId()));
    }
}
