package com.searchbox.framework.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageRequest implements Pageable {

  private int pageNumber = 0;
  private int pageSize = 0;
  private Sort sort;

  public PageRequest(){
    this(0, 10, null);
  }

  public PageRequest(int page, int size, Sort sort) {

    if (page < 0) {
      throw new IllegalArgumentException(
          "Page index must not be less than zero!");
    }

    if (size < 1) {
      throw new IllegalArgumentException("Page size must not be less than one!");
    }

    this.pageNumber = page;
    this.pageSize = size;
    this.sort = sort;
  }

  @Override
  public int getPageNumber() {
    return this.pageNumber;
  }

  @Override
  public int getPageSize() {
    return this.pageSize;
  }

  @Override
  public int getOffset() {
    return pageSize * pageNumber;
  }

  @Override
  public Sort getSort() {
    return this.sort;
  }

  @Override
  public Pageable next() {
    return new PageRequest(pageNumber + 1, pageSize, sort);
  }

  @Override
  public Pageable previousOrFirst() {
    return hasPrevious() ? new PageRequest(pageNumber - 1, pageSize, sort) : this;
  }

  @Override
  public Pageable first() {
    return new PageRequest(0, pageSize, sort);
  }

  @Override
  public boolean hasPrevious() {
    return this.getPageNumber() > 0;
  }

}
