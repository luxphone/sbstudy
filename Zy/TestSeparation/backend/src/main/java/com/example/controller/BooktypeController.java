package com.example.controller;

import com.example.entity.Booktype;
import com.example.entity.Result;
import com.example.service.BooktypeService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.example.entity.table.Tables.BOOKTYPE;

@RestController
@RequestMapping("/booktype")
public class BooktypeController {
    @Autowired
    BooktypeService booktypeService;
    @Autowired
    RedisTemplate<String, Object> stringObjectRedisTemplate;

    @PostMapping("/query")
    public Result<Page<Booktype>> query(Integer pageNumber, Integer pageSize, String keyword) {
        if (keyword == null) {
            keyword = "";
        }
        ValueOperations<String, Object> opsForValue = stringObjectRedisTemplate.opsForValue();
        String cacheKey = "booktypePage" + ":" + pageNumber + ":" + pageSize + ":" + keyword;
        Object redisValue = opsForValue.get(cacheKey);
        Page<Booktype> booktypePage;
        if (redisValue == null) {
            Page<Booktype> page = new Page<>(pageNumber, pageSize);
            QueryWrapper query = new QueryWrapper();
            query.where(BOOKTYPE.NAME.like(keyword));
            redisValue = booktypePage = booktypeService.page(page, query);
            opsForValue.set(cacheKey, redisValue, 1, TimeUnit.HOURS);
        } else {
            booktypePage = (Page<Booktype>) redisValue;
        }
        return Result.success(booktypePage);
    }

    @PostMapping("/add")
    public Result<String> add(Booktype booktype) {
        removeBooktypePageCache();
        booktype.setCreatetime(new Timestamp(new Date().getTime()));
        booktype.setUpdatetime(new Timestamp(new Date().getTime()));
        booktypeService.save(booktype);
        return Result.success("added");
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        removeBooktypePageCache();
        booktypeService.removeById(id);
        return Result.success("deleted");
    }

    @PostMapping("/update")
    public Result<String> update(Booktype booktype) {
        removeBooktypePageCache();
        booktype.setUpdatetime(new Timestamp(new Date().getTime()));
        booktypeService.updateById(booktype);
        return Result.success("updated");
    }

    private void removeBooktypePageCache() {
        Jedis jedis = new Jedis("localhost", 6379);
        Set<String> keys = jedis.keys("booktypePage*");
        for (String key : keys) {
            jedis.del(key);
        }
        jedis.close();
    }

}
