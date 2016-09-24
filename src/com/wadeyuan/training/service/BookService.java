package com.wadeyuan.training.service;

import java.util.List;

import com.wadeyuan.training.common.BookStatusEnum;
import com.wadeyuan.training.entity.Book;
import com.wadeyuan.training.exception.ParameterException;
import com.wadeyuan.training.exception.ServiceException;
import com.wadeyuan.training.util.Pagination;

public interface BookService {
    /**
     *
     * @param book
     * @return the ID of new created book or the updated book
     * @throws ServiceException
     * @throws ParameterException
     */
    public int saveBook(Book book) throws ServiceException, ParameterException;

    public Book getBookById(int bookId) throws ServiceException;

    public List<Book> getBooksByUserIdStatus(int userId, BookStatusEnum status);

    public List<Book> getBooksByUserIdStatus(int userId, BookStatusEnum status, Pagination pagination);

    public int countBooksByUserIdStatus(int userId, BookStatusEnum status);

    public int deleteBookById(int bookId) throws ServiceException;
}
