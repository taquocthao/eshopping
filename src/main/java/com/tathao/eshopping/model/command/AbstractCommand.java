package com.tathao.eshopping.model.command;

import java.io.Serializable;
import java.util.List;

public class AbstractCommand<E> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int firstItem = 0;
    private int totalItems = 0;
    private String sortExpression;
    private String sortDirection;
    private String[] checkList;
    private List<E> listResult;
    private String crudaction;
    private String searchTerm;
    private String sessionId;
    private String tableId = "tableList";
    private int page = 1;
    protected E pojo;
    
	public int getFirstItem() {
		return firstItem;
	}
	
	public void setFirstItem(int firstItem) {
		this.firstItem = firstItem;
	}
	
	public int getTotalItems() {
		return totalItems;
	}
	
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}
	
	public String getSortExpression() {
		return sortExpression;
	}
	
	public void setSortExpression(String sortExpression) {
		this.sortExpression = sortExpression;
	}
	
	public String getSortDirection() {
		return sortDirection;
	}
	
	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}
	
	public String[] getCheckList() {
		return checkList;
	}
	
	public void setCheckList(String[] checkList) {
		this.checkList = checkList;
	}
	
	public List<E> getListResult() {
		return listResult;
	}
	
	public void setListResult(List<E> listResult) {
		this.listResult = listResult;
	}
	
	public String getCrudaction() {
		return crudaction;
	}
	
	public void setCrudaction(String crudaction) {
		this.crudaction = crudaction;
	}
	
	public String getSearchTerm() {
		return searchTerm;
	}
	
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getTableId() {
		return tableId;
	}
	
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public E getPojo() {
		return pojo;
	}
	
	public void setPojo(E pojo) {
		this.pojo = pojo;
	}
	
}
