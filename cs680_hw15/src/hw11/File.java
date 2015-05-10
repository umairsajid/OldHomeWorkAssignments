package hw11;

import java.util.Date;

public class File extends FSElement {

	public File(Directory par, Date cr, String nm) {
		super(par, cr, "File");
		// TODO Auto-generated constructor stub
		rename(nm);
	}

}
