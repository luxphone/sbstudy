package com.example.controller;

import com.example.entity.Booktype;
import com.example.entity.Result;
import com.example.service.BooktypeService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;

import static com.example.entity.table.Tables.BOOKTYPE;

@RestController
@RequestMapping("/booktype")
public class BooktypeController {
    @Autowired
    BooktypeService booktypeService;

    @PostMapping("/query")
    public Result<Page<Booktype>> query(Integer pageNumber, Integer pageSize, String keyword) {
        Page<Booktype> page = new Page<>(pageNumber, pageSize);
        QueryWrapper query = new QueryWrapper();
        query.where(BOOKTYPE.NAME.like(keyword == null ? "" : keyword));
        Page<Booktype> booktypePage = booktypeService.page(page, query);
        return Result.success(booktypePage);
    }

    @PostMapping("/add")
    public Result<String> add(Booktype booktype) {
        booktype.setCreatetime(new Timestamp(new Date().getTime()));
        booktype.setUpdatetime(new Timestamp(new Date().getTime()));
        booktypeService.save(booktype);
        return Result.success("added");
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        booktypeService.removeById(id);
        return Result.success("deleted");
    }

    @PostMapping("/update")
    public Result<String> update(Booktype booktype) {
        booktype.setUpdatetime(new Timestamp(new Date().getTime()));
        booktypeService.updateById(booktype);
        return Result.success("updated");
    }

}
