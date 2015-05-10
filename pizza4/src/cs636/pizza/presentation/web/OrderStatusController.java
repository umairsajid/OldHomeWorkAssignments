package cs636.pizza.presentation.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs636.pizza.domain.PizzaOrder;
import cs636.pizza.presentation.PresentationUtils;
import cs636.pizza.service.ServiceException;
import cs636.pizza.service.StudentService;

public class OrderStatusController implements Controller {

	private StudentService studentService;
	private String view;

	public OrderStatusController(StudentService studentService, 
			String view) {
		this.studentService = studentService;
		this.view = view;
	}
	public String handleRequest (
			HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		System.out.println("in OrderStatusController");
		HttpSession session = request.getSession();
		StudentBean student = (StudentBean) session.getAttribute("student");
		Integer roomNo = null;
		String roomNoStr;
		if ((roomNoStr = request.getParameter("room")) != null) {
			roomNo = Integer.parseInt(roomNoStr);
		} else if (student != null)
			roomNo = student.getRoomNo();
		System.out.println("in OrderStatusController, roomNo = "+roomNo);
		if (roomNo == null) {
			throw new ServletException("no roomNo in OrderStatusContoller");
		} else {
			student.setRoomNo(roomNo);
			List<PizzaOrder> report = null;
			try {
				report = studentService.getOrderStatus(roomNo);
				PresentationUtils.printOrderStatus(report, System.out);
			} catch (ServiceException e) {
				throw new ServletException("OrderStatus Controller: ", e);
			}
			request.setAttribute("statusReport", report);
		}
		return view;
	}
}
