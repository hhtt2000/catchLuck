package com.l1j5.web.example.model.dto;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

@Configurable(preConstruction = true, dependencyCheck = true)
public class Paging {

	public Paging(int currentPageNo, int totalRowCnt) {
		this.currentPageNo = currentPageNo == 0 ? 1 : currentPageNo;
		this.totalRowCnt = totalRowCnt;
	}

	// 표시할 페이지의 갯수
	@Value("#{l1j5Prop['paging.page.size']}")
	private int pageCnt;
	// 페이지당 표시할 행의 갯수
	@Value("#{l1j5Prop['paging.item.size']}")
	private int pageRows;

	// 현재 페이지의 번호
	private int currentPageNo;

	// 전체 행의 갯수
	private int totalRowCnt;

	public int getPageCnt() {
		return pageCnt;
	}

	public int getPageRows() {
		return pageRows;
	}

	// 첫 페이지의 번호
	public int getFirstPageNo() {
		return 1;
	}

	// 현재 페이지의 번호
	public int getCurrentPageNo() {
		return currentPageNo;
	}

	// 페이지에서 첫 행의 번호
	public int getFromRowNum() {
		// 현재 페이지가 5페이지라면?
		// 41~50을 얻어야 쿼리를 수행할 수 있다.
		// 그러므로 fromRowNum = 41
		return (currentPageNo - 1) * pageRows + 1;
	}

	// 페이지에서의 끝 행의 번호
	public int getToRowNum() {
		System.out.println("toRowNum : " + (currentPageNo * pageRows));
		return currentPageNo * pageRows;
	}

	// 페이지에서의 끝 행의 번호
	public int getLastPageNo() {
		// 전체 행의 갯수가 733개라면...
		// 733 / 페이지당 행의 수 + (나머지가 있으면 : 1)
		return totalRowCnt / pageRows + (totalRowCnt % pageRows > 0 ? 1 : 0);
	}

	// 현재 페이지의 번호
	public int getPrevPageNo() {
		// 현재 페이지가 23페이지면...
		// 이전 페이지는 11을 가르키고 있으면 된다.
		// 하지만 현재 페이지가 3페이지라면...
		// 이전 페이지는 1페이지를 가르켜야 한다.
		int tmpNo = ((currentPageNo / pageCnt)
				+ (currentPageNo % pageCnt > 0 ? 1 : 0) - 1)
				* pageCnt + 1;
		int tmp = currentPageNo - pageCnt <= 0 ? 1
				: ((currentPageNo - 2 * pageCnt)
						+ (pageCnt - (currentPageNo % pageCnt == 0 ? pageCnt
								: currentPageNo % pageCnt)) + 1);
		return tmp;
	}

	// 다음 페이지의 번호
	public int getNextPageNo() {
		// 현재 페이지가 33페이지이고, 마지막 페이지가 74페이지라면
		// 다음 페이지가 41페이지를 가르키면 된다.
		// 하지만 현재 페이지가 71페이지라면
		// 다음 페이지는 74페이지를 가르켜야한다.
		if ((getLastPageNo() - currentPageNo) / pageCnt > 0) {
			return ((currentPageNo / pageCnt)
					+ (currentPageNo % pageCnt > 0 ? 1 : 0) - 1)
					* pageCnt + 1 + pageCnt;
		} else {
			return getLastPageNo();
		}
	}
}