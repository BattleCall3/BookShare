package com.lq.service;
import java.util.List;
import com.lq.entity.Isbn;
import com.lq.other.PartRentable;
public interface IsbnService {
	public void addIsbnInfor(Isbn isbninfo);
	public Isbn getOneIsbninfor(String isbn);
	public List<PartRentable> getSearchInfo(String keyword);
	public List<PartRentable> getSearchInCore(String keyword);
	public List<PartRentable> getSearchInfoByTwokey(String keyword,String keyword2);
	public List<String> getSearchIsbn(String keyword);
	public List<PartRentable> getSearchOrderByKeys(List<String> isbns);
	public List<PartRentable> getSearchOrderByKey(String isbnstr);
	public boolean alternoBookinfo(String isbn, String title, String publisher, String author);
}
