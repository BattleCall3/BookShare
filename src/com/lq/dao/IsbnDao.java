package com.lq.dao;
import java.util.List;

import com.lq.entity.BookSort;
import com.lq.entity.Isbn;
import com.lq.other.PartRentable;
public interface IsbnDao {
	public void addIsbn(Isbn isbninfo);
	public Isbn getOneIsbninfor(String isbn);
	public List<PartRentable> getSearchInfo(String keyword);
	public List<PartRentable> getSearchInfoByTwokey(String keyword,String keyword2);
	public List<PartRentable> getSearchInCore(String keyword);
	public List<String> getSearchIsbn(String keyword);
	public List<PartRentable> getSearchOrderByKeys(List<String> isbns);
	public List<PartRentable> getSearchOrderByKey(String isbnstr);
	public boolean alternoBookinfo(String isbn, String title, String publisher, String author, String price);
	public void addBookSort(BookSort booksort);
	public List<String> searchIsbn0(String keyword);
	public List<String> searchInIsbn(String keyword, List<String> isbns);
	public List<PartRentable> searchBookbyIsbn(int startlocation, int size, List<String> isbns);
	public List<String> getIsbnbySort(int label1, int label2, int label3);
	public long bookNumberbyIsbn(List<String> isbns);
}
