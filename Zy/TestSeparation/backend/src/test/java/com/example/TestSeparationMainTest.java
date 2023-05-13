package com.example;

import com.example.entity.Booktype;
import com.example.service.BooktypeService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.entity.table.Tables.BOOKTYPE;

@SpringBootTest
public class TestSeparationMainTest {
    @Autowired
    BooktypeService booktypeService;
    @Test
    void test() {
        Page<Booktype> page = new Page<>(1, 5);
        QueryWrapper query = new QueryWrapper();
        query.where(BOOKTYPE.NAME.like("语"));
        booktypeService.page(page, query);
    }
}
