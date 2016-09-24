package com.wadeyuan.training.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.wadeyuan.training.common.BookStatusEnum;
import com.wadeyuan.training.common.Constants;
import com.wadeyuan.training.entity.Book;
import com.wadeyuan.training.entity.User;
import com.wadeyuan.training.exception.ParameterException;
import com.wadeyuan.training.exception.ServiceException;
import com.wadeyuan.training.service.BookService;
import com.wadeyuan.training.util.Pagination;
import com.wadeyuan.training.util.SpringUtil;

@Controller
@RequestMapping("/book")
public class BookController {

    private static final String LIST_BOOK_PAGE = "book/list";
    private static final String EDIT_BOOK_PAGE = "book/edit";
    private static final String ADD_BOOK_PAGE = "book/edit";

    private static final Logger logger = Logger.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/list", method=RequestMethod.GET)
    public ModelAndView showMyBook(
            @RequestParam(value = "status", defaultValue = "all") String status,
            @RequestParam(value = "page", defaultValue = "1") int page,
            HttpSession session) {
        User user = (User)session.getAttribute(Constants.USER);
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> objects = new HashMap<String, Object>();

        if(page < 1) {
            page = 1;
        }

        int allCount = bookService.countBooksByUserIdStatus(user.getId(), BookStatusEnum.all);
        int outCount = bookService.countBooksByUserIdStatus(user.getId(), BookStatusEnum.out);
        int inCount = bookService.countBooksByUserIdStatus(user.getId(), BookStatusEnum.in);
        int remainingCount = bookService.countBooksByUserIdStatus(user.getId(), BookStatusEnum.remaining);
        objects.put(Constants.COUNT_ALL, allCount);
        objects.put(Constants.COUNT_OUT, outCount);
        objects.put(Constants.COUNT_IN, inCount);
        objects.put(Constants.COUNT_REMAINING, remainingCount);
        objects.put(Constants.PAGE, page);

        BookStatusEnum statusEnum = null;
        try {
            statusEnum = BookStatusEnum.valueOf(status);
        } catch(Exception e) {
            statusEnum = BookStatusEnum.valueOf("all");
        }
        Pagination pagination = getPagenationObjectByStatus(statusEnum, objects);
        List<Book> books = bookService.getBooksByUserIdStatus(user.getId(), statusEnum, pagination);


        objects.put(Constants.BOOK_LIST, books);
        objects.put(Constants.STATUS, statusEnum.toString());
        objects.put(Constants.PAGINATION, pagination);

        for(String key : objects.keySet()) {
            modelAndView.addObject(key, objects.get(key));
        }
        modelAndView.setViewName(LIST_BOOK_PAGE);
        return modelAndView;
    }

    /**
     * Show add new book page
     * @return
     */
    @RequestMapping(value = "/add", method=RequestMethod.GET)
    public ModelAndView add(HttpSession session) {
        session.removeAttribute(Constants.BOOK);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(EDIT_BOOK_PAGE);
        return modelAndView;
    }

    /**
     * Show edit book page
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method=RequestMethod.GET)
    public ModelAndView edit(@PathVariable int id, HttpSession session) {
        Book book = null;
        ModelAndView modelAndView = new ModelAndView();

        if(id > 0) {
            try {
                book = bookService.getBookById(id);
            } catch (ServiceException se) {
                session.removeAttribute(Constants.BOOK);
                modelAndView.addObject(Constants.ERROR_MESSAGE, se.getMessage());
                modelAndView.setViewName(ADD_BOOK_PAGE);
                return modelAndView;
            }
        }
        session.setAttribute(Constants.BOOK, book);
        modelAndView.setViewName(EDIT_BOOK_PAGE);
        return modelAndView;
    }

    /**
     * Save a new book or save editing an existing book
     * @param bookIdStr
     * @param bookName
     * @param author
     * @param description
     * @param picture
     * @param ownerIdStr
     * @param currentUserIdStr
     * @param session
     * @return
     */
    @RequestMapping(value = "/save", method=RequestMethod.POST)
    public ModelAndView save(
            @RequestParam(value = "bookId", defaultValue = "-1") String bookIdStr, // defaults to "-1" when creating a new book
            @RequestParam(value = "bookName", defaultValue = "") String bookName,
            @RequestParam(value = "author", defaultValue = "") String author,
            @RequestParam(value = "description", defaultValue = "") String description,
            @RequestParam(value = "picture", defaultValue = "") String picture,
            @RequestParam(value = "ownerId", defaultValue = "") String ownerIdStr,
            @RequestParam(value = "currentUserId", defaultValue = "") String currentUserIdStr,
            HttpSession session) {
        Book book = new Book();
        User user = (User) session.getAttribute(Constants.USER);

        ModelAndView modelAndView = new ModelAndView();
        try {
            book.setId(Integer.parseInt(bookIdStr));
            book.setName(bookName);
            book.setAuthor(author);
            book.setDescription(description);
            book.setPicture(picture);
            book.setOwnerId(Integer.parseInt(ownerIdStr) > 0 ? Integer.parseInt(ownerIdStr) : user.getId()); // set as current user if value is invalid
            book.setCurrentUserId(Integer.parseInt(currentUserIdStr) > 0 ? Integer.parseInt(currentUserIdStr) : user.getId()); // set as current user if value is invalid
            if(user.getId() == book.getOwnerId()) {
                bookService.saveBook(book);
            } else {
                modelAndView.addObject(Constants.ERROR_MESSAGE, "No permission to edit this book");
                modelAndView.setViewName(EDIT_BOOK_PAGE);
                return modelAndView;
            }
        } catch (ParameterException pe) {
            session.removeAttribute(Constants.BOOK);
            modelAndView.addObject(Constants.ERROR_MESSAGE, "Book name and author are required");
            modelAndView.setViewName(EDIT_BOOK_PAGE);
            return modelAndView;
        } catch (ServiceException se) {
            session.removeAttribute(Constants.BOOK);
            modelAndView.addObject(Constants.ERROR_MESSAGE, se.getMessage());
            modelAndView.setViewName(EDIT_BOOK_PAGE);
            return modelAndView;
        }

        session.setAttribute(Constants.BOOK, book);
        session.setAttribute(Constants.SUCCESS_MESSAGE, "更新图书成功！");
        modelAndView.setView(new RedirectView(session.getServletContext().getContextPath() + "/" + LIST_BOOK_PAGE));
        return modelAndView;
    }

    /**
     * Delete book by given id
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method=RequestMethod.GET)
    public ModelAndView delete(@PathVariable int id, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        try {
            Book book = bookService.getBookById(id);
            if(book != null) {
                if(book.getOwnerId() != book.getCurrentUserId()) {
                    session.setAttribute(Constants.ERROR_MESSAGE, "没有权限删除此图书");
                    modelAndView.setView(new RedirectView(session.getServletContext().getContextPath() + "/" + LIST_BOOK_PAGE));
                    return modelAndView;
                } else {
                    bookService.deleteBookById(id);
                }
            }
        } catch(ServiceException se) {
            session.setAttribute(Constants.ERROR_MESSAGE, "没有权限删除此图书");
            modelAndView.setView(new RedirectView(session.getServletContext().getContextPath() + "/" + LIST_BOOK_PAGE));
            return modelAndView;
        }
        session.setAttribute(Constants.SUCCESS_MESSAGE, "删除图书成功");
        modelAndView.setView(new RedirectView(session.getServletContext().getContextPath() + "/" + LIST_BOOK_PAGE));
        return modelAndView;
    }

    private Pagination getPagenationObjectByStatus(BookStatusEnum statusEnum, Map<String, Object> objects) {
        int currentPage = (int) objects.get(Constants.PAGE);
        Pagination pagination = (Pagination) SpringUtil.getBean("pagination");
        switch(statusEnum) {
        case all:
            pagination.setTotalCount((int) objects.get(Constants.COUNT_ALL));
            break;
        case out:
            pagination.setTotalCount((int) objects.get(Constants.COUNT_OUT));
            break;
        case in:
            pagination.setTotalCount((int) objects.get(Constants.COUNT_IN));
            break;
        case remaining:
            pagination.setTotalCount((int) objects.get(Constants.COUNT_REMAINING));
            break;
        default: break;
        }
        // TODO: Refine this. Now setCurrentPage in the end, as it depends on Pagination.getPageCount() method, which is calculated by page total count
        pagination.setCurrentPage(currentPage);
        return pagination;
    }

    /** RESTFull APIs ---- START **/

    @RequestMapping(value = "/rest/get/{id}", method=RequestMethod.GET)
    @ResponseBody
    public Book get(@PathVariable int id) {
        Book book = null;
        try {
            book = bookService.getBookById(id);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
        return book;
    }

    @RequestMapping(value = "/rest/add", method=RequestMethod.PUT)
    @ResponseBody
    public Book add(@RequestBody Book book) {
        int newBookId = -1;
        try {
            newBookId = bookService.saveBook(book);
            book.setId(newBookId);
        } catch (ServiceException | ParameterException e) {
            logger.error(e.getMessage());
        }
        return book;
    }

    @RequestMapping(value = "/rest/update", method=RequestMethod.POST)
    @ResponseBody
    public Book update(@RequestBody Book book) {
        int newBookId = -1;
        try {
            newBookId = bookService.saveBook(book);
            book.setId(newBookId);
        } catch (ServiceException | ParameterException e) {
            logger.error(e.getMessage());
        }
        return book;
    }

    @RequestMapping(value = "/rest/delete/{id}", method=RequestMethod.DELETE)
    @ResponseBody
    public int update(@PathVariable int id) {
        int deletedRows = 0;
        try {
            deletedRows = bookService.deleteBookById(id);

        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
        return deletedRows;
    }

    /** RESTFull APIs ---- END **/
}
