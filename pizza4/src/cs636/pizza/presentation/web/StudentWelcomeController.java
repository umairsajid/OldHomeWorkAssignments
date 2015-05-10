package cs636.pizza.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class StudentWelcomeController implements Controller {

	private String view;

	public StudentWelcomeController(String view) {
		this.view = view;
	}

	public String handleRequest (
			HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		System.out.println("in StudentWelcomeController");
		HttpSession session = request.getSession();
		Integer roomNo = null;
		// take room parameter over session var in StudentBean--
		String paramRoomNoString = (String)request.getParameter("room");
		if (paramRoomNoString != null) {
			try {				
			roomNo = Integer.parseInt(paramRoomNoString);
			System.out.println("Got roomNo from param = "+ roomNo);
			} catch (NumberFormatException e) {
			// if get here, it's a bug: user can't directly enter room no.
			System.out.println("pizza4: studentWelcome: bad number format in room");
			throw new ServletException("Bad roomNo param in StudentWelcomeController");
			}
		} 
		StudentBean student = (StudentBean)session.getAttribute("student");
		if (student == null)
			student = new StudentBean();	
		if (roomNo != null)
			student.setRoomNo(roomNo); // set newly obtained roomNo
		if (student.getRoomNo() > 0)  
			roomNo = student.getRoomNo(); // just set or older setting
		session.setAttribute("student", student);
		String statusNextPageURL = (roomNo != null)? 
				"orderStatus.html":"roomForm.html?wantStatus=true";
		request.setAttribute("statusNextPageURL", statusNextPageURL);
		return view;
	}
}
