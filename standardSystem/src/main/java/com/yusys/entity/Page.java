package com.yusys.entity;

public class Page {
	private final Integer pageSize=5;
	private Integer currentPage=1;
	private Integer totalNum;
	private Integer totalPage;
	private Integer startRow;
	private Integer endRow;
	public Integer getStartRow() {
		this.startRow= (currentPage-1)*pageSize;
		return startRow;
	}
	public Integer getEndRow() {
		if(currentPage==totalPage&&currentPage*pageSize<totalNum){
			this.endRow = totalNum;
		}else{
			this.endRow=currentPage*pageSize;
		}
		return endRow;
	}

	public Integer getTotalPage() {
		totalPage=totalNum%pageSize==0?(totalNum/pageSize):((totalNum/pageSize)+1);
		return totalPage;
	}
	
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	
	
}
