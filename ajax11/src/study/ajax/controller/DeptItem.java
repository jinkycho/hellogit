package study.ajax.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/api/dept_item.do")
public class DeptItem extends HttpServlet {
	private static final long serialVersionUID = -3107452273411406252L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		
		<!-- 탭 버튼 구성 -->
		<ul class="nav nav-tabs" id="mytab">
			<li><a href="#dept" data-toggle="tab" data-deptno="101">컴퓨터공학과</a></li>
			<li><a href="#dept" data-toggle="tab" data-deptno="102">멀티미디어학과</a></li>
			<li><a href="#dept" data-toggle="tab" data-deptno="201">전자공학과</a></li>
			<li><a href="#dept" data-toggle="tab" data-deptno="202">기계공학과</a></li>
		</ul>
		<!-- 탭 페이지 구성(단일페이지) -->
		<div class="tab-content">
			<div role="tabpanel" class="tab-pane fade" id="dept"></div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		/** 탭 페이지가 보여질 경우의 이벤트 */
		// 탭 안의 모든 <a>태그에 대한 이벤트 -->  모든 탭 페이지가 열릴 때 이 이벤트가 호출됨
		$('#mytab a').on('shown.bs.tab', function (e) {
			// data-deptno 속성의 값을 취득한다.
			var current_deptno = $(this).data("deptno");
			
			// Ajax요청을 통한 학과 데이터 조회
			$.get('../api/dept_item.do', {
				deptno: current_deptno
				}, function(json) {
				var h = $("<h3>").html(json.dname);
				var p1 = $("<p>").html("학과번호: " + json.deptno);
				var p2 = $("<p>").html("위치: " + json.loc);

				// #dept 내의 모든 내용을 지우고, 준비한 요소들을 추가한다.
				$("#dept").empty().append(h).append(p1).append(p2);
			});
		});

		/** 첫 번째 링크에 연결된 탭 페이지 표시하기 */
		// 이벤트가 적용된 후의 처리이므로 탭이 표시되면서 미리 정의한 이벤트가 호출된다.
		$("#mytab a:eq(0)").tab('show');
	});
		int deptno = 0;
		String dname = null;
		String loc = null;
		
		try {
			deptno = Integer.parseInt(request.getParameter("deptno"));
		} catch (Exception e) {}
		
		switch (deptno) {
			case 101:  dname = "컴퓨터공학과"; 	 loc = "1호관"; break;
			case 102:  dname = "멀티미디어학과"; loc = "2호관"; break;
			case 201:  dname = "전자공학과"; 	 loc = "3호관"; break;
			case 202:  dname = "기계공학과"; 	 loc = "4호관"; break;
		}
		
		JSONObject json = new JSONObject();
		json.put("deptno", deptno);
		json.put("dname", dname);
		json.put("loc", loc);
		response.getWriter().print(json);
	}
}