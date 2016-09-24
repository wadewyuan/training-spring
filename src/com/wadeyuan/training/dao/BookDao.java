package com.wadeyuan.training.dao;

import java.util.List;

import com.wadeyuan.training.common.BookStatusEnum;
import com.wadeyuan.training.entity.Book;
import com.wadeyuan.training.util.Pagination;

public interface BookDao {


    public int createBook(Book book);

    public Book getBookById(int bookId);

    /**
     *
     * @param book
     * @return the updated rows, if 0 returned, meaning the book with specified ID doesn't exist
     * @throws Exception
     */
    public int updateBook(Book book);

    public int countBooksByUserIdStatus(int userId, BookStatusEnum status);

    public List<Book> getBooksByUserIdStatus(int userId, BookStatusEnum status);

    public List<Book> getBooksByUserIdStatus(int userId, BookStatusEnum status, Pagination pagination);

    public int deleteBookById(int bookId);
}
