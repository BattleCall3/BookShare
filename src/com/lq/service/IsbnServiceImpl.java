package com.lq.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lq.entity.Isbn;
import com.lq.other.PartRentable;
import com.lq.dao.IsbnDao;
@Service
public class IsbnServiceImpl implements IsbnService{
	@Autowired
	private IsbnDao isbndao;
	@Override
	public void addIsbnInfor(Isbn isbninfo) {
		isbndao.addIsbn(isbninfo);
	}
	@Override
	public Isbn getOneIsbninfor(String isbn) {
		return isbndao.getOneIsbninfor(isbn);
	}
	@Override
	public List<PartRentable> getSearchInfo(String keyword) {
		// TODO Auto-generated method stub
		return isbndao.getSearchInfo(keyword);
	}
	@Override
	public List<PartRentable> getSearchInfoByTwokey(String keyword,String keyword2) {
		// TODO Auto-generated method stub
		return isbndao.getSearchInfoByTwokey(keyword,keyword2);
	}
	@Override
	public List<PartRentable> getSearchInCore(String keyword) {
		// TODO Auto-generated method stub
		return isbndao.getSearchInCore(keyword);
	}
	@Override
	public List<String> getSearchIsbn(String keyword) {
		// TODO Auto-generated method stub
		return isbndao.getSearchIsbn(keyword);
	}
	@Override
	public List<PartRentable> getSearchOrderByKeys(List<String> isbns) {
		// TODO Auto-generated method stub
		return isbndao.getSearchOrderByKeys(isbns);
	}
	@Override
	public List<PartRentable> getSearchOrderByKey(String isbnstr) {
		// TODO Auto-generated method stub
		return isbndao.getSearchOrderByKey(isbnstr);
	}
	@Override
	public boolean alternoBookinfo(String isbn, String title, String publisher, String author) {
		return isbndao.alternoBookinfo(isbn, title, publisher, author);
	}
}
