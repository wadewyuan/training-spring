package com.wadeyuan.training.dao.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.wadeyuan.training.common.BookStatusEnum;
import com.wadeyuan.training.dao.BookDao;
import com.wadeyuan.training.entity.Book;
import com.wadeyuan.training.util.Pagination;

public class BookDaoImpl extends SqlSessionDaoSupport implements BookDao{

    private static final String BOOK_MAPPER = "com.wadeyuan.training.entity.mappers.BookMapper";

    /** SQL IDs **/
    private static final String INSERT_BOOK = ".insertBook";
    private static final String SELECT_BOOK_BY_ID = ".seletBookById";
    private static final String UPDATE_BOOK = ".updateBook";
    private static final String COUNT_BOOK_BY_USER_ID_STATUS = ".countBookByUserIdStatus";
    private static final String SELECT_BOOK_BY_USER_ID_STATUS = ".selectBookByUserIdStatus";
    private static final String DELETE_BOOK_BY_ID = ".deleteBookById";
    /** End SQL IDs **/

    @Override
    public int createBook(Book book) {

        getSqlSession().insert(BOOK_MAPPER + INSERT_BOOK, book);

        return book.getId();
    }

    @Override
    public Book getBookById(int id) {

        Book book = getSqlSession().selectOne(BOOK_MAPPER + SELECT_BOOK_BY_ID, id);
        return book;
    }

    /**
     *
     * @param book
     * @return the updated rows, if 0 returned, meaning the book with specified ID doesn't exist
     * @throws Exception
     */
    @Override
    public int updateBook(Book book) {

        return getSqlSession().update(BOOK_MAPPER + UPDATE_BOOK, book);
    }

    @Override
    public int countBooksByUserIdStatus(int userId, BookStatusEnum status) {

        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("userId", userId);
        params.put("status", status);
        return getSqlSession().selectOne(BOOK_MAPPER + COUNT_BOOK_BY_USER_ID_STATUS, params);
    }

    @Override
    public List<Book> getBooksByUserIdStatus(int userId, BookStatusEnum status) {
        return getBooksByUserIdStatus(userId, status, null);
    }

    @Override
    public List<Book> getBooksByUserIdStatus(int userId, BookStatusEnum status, Pagination pagination) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("status", status);
        if(pagination != null) {
            params.put("offset", pagination.getOffset());
            params.put("itemPerPage", pagination.getItemPerPage());
        }
        List<Book> books = getSqlSession().selectList(BOOK_MAPPER + SELECT_BOOK_BY_USER_ID_STATUS, params);
        return books;
    }

    @Override
    public int deleteBookById(int id) {
        return getSqlSession().delete(BOOK_MAPPER + DELETE_BOOK_BY_ID, id);
    }
}
