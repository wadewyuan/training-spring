package com.wadeyuan.training.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wadeyuan.training.common.BookStatusEnum;
import com.wadeyuan.training.dao.BookDao;
import com.wadeyuan.training.entity.Book;
import com.wadeyuan.training.exception.ParameterException;
import com.wadeyuan.training.exception.ServiceException;
import com.wadeyuan.training.service.BookService;
import com.wadeyuan.training.util.Pagination;

public class BookServiceImpl implements BookService {

    private BookDao bookDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    /**
    *
    * @param book
    * @return the ID of new created book or the updated book
    * @throws ServiceException
    * @throws ParameterException
    */
    @Override
    public int saveBook(Book book) throws ServiceException, ParameterException{
        int id = -1;
        ParameterException parameterException = new ParameterException();
        Map<String, String> errorFields = new HashMap<String, String>();
        parameterException.setErrorFields(errorFields);

        if(book.getName() == null || book.getName().trim().isEmpty()) {
            errorFields.put("name", "Book name is empty");
        }
        if(book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            errorFields.put("author", "Author is empty");
        }

        if(parameterException.hasErrorFields()) {
            throw parameterException;
        }

        if(book.getId() <= 0) {
            // create new book if the ID is not provided
            id = bookDao.createBook(book);
        } else {
            int updatedRows = bookDao.updateBook(book);
            if(updatedRows == 0) {
                throw new ServiceException("Cannot find book with given id: " + book.getId());
            }

            id = book.getId();
        }
        return id;
    }

    @Override
    public Book getBookById(int bookId) throws ServiceException {
        Book book = null;

        book = bookDao.getBookById(bookId);
        if(book == null) {
            throw new ServiceException("Cannot find book with given id: " + bookId);
        }
        return book;
    }

    @Override
    public List<Book> getBooksByUserIdStatus(int userId, BookStatusEnum status){
        List<Book> books = new ArrayList<Book>();
        books = bookDao.getBooksByUserIdStatus(userId, status);
        return books;
    }

    @Override
    public List<Book> getBooksByUserIdStatus(int userId, BookStatusEnum status, Pagination pagination){
        List<Book> books = new ArrayList<Book>();
        books = bookDao.getBooksByUserIdStatus(userId, status, pagination);
        return books;
    }

    @Override
    public int countBooksByUserIdStatus(int userId, BookStatusEnum status){

        return bookDao.countBooksByUserIdStatus(userId, status);
    }

    @Override
    public int deleteBookById(int bookId) throws ServiceException{
        int deletedRows = 0;
        deletedRows = bookDao.deleteBookById(bookId);
        if(deletedRows == 0) {
            throw new ServiceException("Cannot find book with given id: " + bookId);
        }
        return deletedRows;
    }
}
