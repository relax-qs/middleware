package com.lark.middleware.model.query;

import java.io.Serializable;
import java.util.List;

/**
 * Created by houenxun on 16/1/19.
 * 分页查询结果
 */
public class PageResult<X> implements Serializable{
    private static final long serialVersionUID = 1L;

    private List<X> result;

	private int pageNo = BaseQuery.DEFAULT_PAGE;

	private int totalCount; // 总的数目

	private int pageSize = BaseQuery.DEFAULT_PAGE_SIZE; //每一页数目

	public List<X> getResult() {
		return result;
	}

	public void setResult(List<X> result) {
		this.result = result;
	}

    public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotalPage() {
        if(pageNo < 1){
            pageNo = BaseQuery.DEFAULT_PAGE;
        }
		return totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);
	}


	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 克隆新的PageResult 但不克隆result数据
	 * @param clazz
	 * @param <T>
     * @return
     */
	public <T> PageResult<T> clone(Class<T> clazz){
		PageResult<T>  pageResult = new PageResult<>();
		pageResult.setTotalCount(this.totalCount);
		pageResult.setPageSize(this.pageSize);
		pageResult.setPageNo(this.pageNo);
		return pageResult;
	}
}
