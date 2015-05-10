
package cs636.music.presentation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import cs636.music.config.MusicSystemConfig;
import cs636.music.domain.Cart;
import cs636.music.domain.Download;
import cs636.music.domain.Invoice;
import cs636.music.domain.Product;
import cs636.music.domain.Track;
import cs636.music.domain.User;
import cs636.music.service.AdminServiceAPI;
import cs636.music.service.UserServiceAPI;



/**
 * 
 *         This class tests the system.
 */
public class SystemTest {

	private BufferedReader reader;
	private AdminServiceAPI adminService;
	private UserServiceAPI userService;
	private String inFile;
	private Cart cart;

	public SystemTest(String inFile)
			throws Exception {
		this.inFile = inFile;
		MusicSystemConfig.configureServices();
		adminService = MusicSystemConfig.getAdminService();
		userService = MusicSystemConfig.getUserService();
	}

	public static void main(String[] args) {
		String inFile = null;
		if (args.length == 1) {
			inFile = args[0];
		} else  {
			System.out.println("usage:java <inputFile> ");
			System.exit(1);
		}
		SystemTest test = null;
		try {
				test = new SystemTest(inFile);
				test.run();
				System.out.println("Run complete, exiting");
		} catch (Exception e) {
			System.err.println("Error in run: StackTrace for it: ");
			e.printStackTrace();
			System.err.println("Error in run, shorter report: " + MusicSystemConfig.exceptionReport(e));
		}
	}

	public void run() throws Exception {
		String command = null;
		reader = new BufferedReader(new FileReader(inFile));
		while ((command = getNextCommand()) != null) {
			System.out.println("\n\n*************" + command
					+ "***************\n");
			if (command.equalsIgnoreCase("i")) { // admin init db
				System.out.println("Initializing system");
				this.adminService.initializeDb();
			} else if (command.equalsIgnoreCase("gp")) // get list of CDs
			{
				Set<Product> cdList = userService.getProductList();
				if (cdList != null)
					PresentationUtils.displayCDCatlog(cdList, System.out);

			} else if (command.startsWith("gui")) { // get info on user
				String usr = getTokens(command)[1];
				User user = userService.getUserInfo(usr);
				if (user == null)
					System.out.println("\nNo such user" + usr +"\n");
				else
					PresentationUtils.displayUserInfo(user, System.out);
			} else if (command.startsWith("gpi")) { // get info for product
				String productCode = getTokens(command)[1];
				Product product = userService.getProduct(productCode);
				if (product == null)
					System.out.println("\nNo such product\n");
				else
					PresentationUtils.displayProductInfo(product, System.out);
			} else if (command.startsWith("ureg")) { // ureg fname lname email
				String userInfo[] = getTokens(command); // whitespace delim.
														// tokens
				System.out.println("Registering user: " + 
						userInfo[1] + " " + userInfo[2] + " " + userInfo[3]);
				userService.registerUser(userInfo[1], userInfo[2], userInfo[3]);
			} else if (command.startsWith("gti")) {
				// gti prodcode:  list track info for CD
				String productCode = getTokens(command)[1];
				Product product = userService.getProduct(productCode);
				if (product == null)
					System.out.println("\nNo such product\n");
				else
					PresentationUtils.displayTracks(product, System.out);

			} else if (command.startsWith("dl")) {
				// record download by user x of product y (some track)
				String params[] = getTokens(command);
				String userEmail = params[1];
				String productCode = params[2];
				int tracknum = Integer.parseInt(params[3]);
				Product product = userService.getProduct(productCode);
				if (product == null)
					System.out.println("\nNo such product\n");
				else {
					Set<Track> tracks = product.getTracks();
					//Track track0 = tracks.iterator().next();
					User user = userService.getUserInfo(userEmail);
					if (user == null)
						System.out.println("\nNo such user\n");
					else {
						Track track0 = null;
						for (Track track: tracks ) {
							if (track.getTrackNumber() == tracknum){
								track0 = track;
							}
						}
						if ( track0 != null){
							System.out.println("Recording download for user");
							userService.addDownload(user, track0);
						} else {
							System.out.println("\nNo such track\n");
						}
					}
				}
			} else if (command.startsWith("cc")) { // create cart
				cart = userService.getNewCart();
				System.out.println("\n cart created ");

			} else if (command.startsWith("sc")) { // show cart
				System.out.println("\n Now displaying Cart...");
				PresentationUtils.displayCart(cart, System.out);

			} else if (command.startsWith("co")) { // checkout userid
				String params[] = getTokens(command);
				User user = userService.getUserInfo(params[1]);
				if (user == null)
					System.out.println("\nNo such user\n");
				else {
					userService.checkout(cart, user);
					System.out.println("\n CDs Ordered..");
				}

			} else if (command.startsWith("addli")) { // add to cart

				String params[] = getTokens(command);
				Product product = userService.getProduct(params[1]);
				if (product == null)
					System.out.println("\nNo such product\n");
				else {
					userService.addItemtoCart(product, cart, 1);
					System.out.println("\n Added to Cart..");
				}
			} else if (command.startsWith("setproc")) // process invoice
			{
				int params[] = getIntTokens(command);
				this.adminService.processInvoice(new Long(params[1]));
			} else if (command.equalsIgnoreCase("gi")) // get list of invoices
			{
				Set<Invoice> inv = adminService.getListofInvoices();
				PresentationUtils.displayInvoices(inv, System.out);
			} else if (command.startsWith("gd")) // get list of downloads
			{
				Set<Download> dList = adminService.getListofDownloads();
				PresentationUtils.downloadReport(dList, System.out);
			} else
				System.out.println("Invalid Command: " + command);
			System.out.println("----OK");
		}
		reader.close();
	}

	public String getNextCommand() throws IOException {
		String line = reader.readLine();
		return (line != null) ? line.trim() : line;
	}

	// use powerful but somewhat mysterious split method of String
	private String[] getTokens(String command) {
		return command.split("\\s+"); // white space
	}

	private int[] getIntTokens(String command) {
		String tokens[] = getTokens(command);
		int returnValue[] = new int[tokens.length];
		for (int i = 1; i < tokens.length; i++)
			// skipping 0th, not an int
			returnValue[i] = Integer.parseInt(tokens[i]);
		return returnValue;
	}

}
