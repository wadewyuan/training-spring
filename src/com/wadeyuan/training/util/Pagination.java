package com.wadeyuan.training.util;


public class Pagination {
    private int totalCount;
    private int pageCount;
    private int itemPerPage;
    private int currentPage;
    private int offset;


    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getItemPerPage() {
        return itemPerPage;
    }

    public void setItemPerPage(int itemPerPage) {
        this.itemPerPage = itemPerPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if(currentPage < 0) {
            currentPage = 1;
        } else if (currentPage > getPageCount()) {
            currentPage = this.getPageCount();
        }
        this.currentPage = currentPage;
    }

    public int getPageCount() {
        pageCount = (int) Math.ceil((double)totalCount / itemPerPage);
        return pageCount;
    }

    public int getOffset() {
        offset = (currentPage -1) * itemPerPage;
        return offset;
    }

    public boolean isFirstPage() {
        return currentPage <= 1;
    }

    public boolean isLastPage() {
        return currentPage >= this.getPageCount();
    }
}
