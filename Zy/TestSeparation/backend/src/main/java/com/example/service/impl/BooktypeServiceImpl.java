package com.example.service.impl;

import com.example.entity.Booktype;
import com.example.mapper.BooktypeMapper;
import com.example.service.BooktypeService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BooktypeServiceImpl extends ServiceImpl<BooktypeMapper, Booktype> implements BooktypeService {
}
