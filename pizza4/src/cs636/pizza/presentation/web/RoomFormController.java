package cs636.pizza.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RoomFormController implements Controller  {

	private int numRooms;
	private String view;

	public RoomFormController(int numRooms, String view) {
		this.numRooms = numRooms;
		this.view = view;
	}

	public String handleRequest (
			HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {
		System.out.println("in RoomFormController");
		boolean wantStatus = (request.getParameter("wantStatus") != null);
		// go on to orderStatus if "wantStatus" was set in previous page
		String nextPageURL = wantStatus ? "orderStatus.html"
					: "studentWelcome.html";
		request.setAttribute("nextPageURL", nextPageURL);
		request.setAttribute("numRooms", numRooms);
		return view;
	}
}
