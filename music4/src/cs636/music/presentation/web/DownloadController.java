package cs636.music.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs636.music.domain.Product;
import cs636.music.domain.Track;
import cs636.music.domain.User;
import cs636.music.service.ServiceException;
import cs636.music.service.UserServiceAPI;

public class DownloadController implements Controller {
	private UserServiceAPI userService;
	
	public DownloadController(UserServiceAPI userService) {
		this.userService = userService;
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		System.out.println("DownloadController: starting");
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		User user = userBean.getUser();
		Product product = userBean.getProduct();
		Integer trackNumber = Integer.parseInt((String) request.getParameter("trackNum"));
		Track track = null;
		try {
			track = product.findTrackByNumber(trackNumber);
			userService.addDownload(user, track);
		} catch (ServiceException e) {
			System.out.println("DownloadController: " + e);
			throw new ServletException(e);
		}
		String downloadURL = "/sound/" + product.getCode() + "/" + track.getSampleFilename();
	
		return downloadURL;
	}
}
