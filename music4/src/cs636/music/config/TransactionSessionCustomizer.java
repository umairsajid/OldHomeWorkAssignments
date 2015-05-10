package cs636.music.config;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.sessions.JNDIConnector;
import org.eclipse.persistence.sessions.Session;
import org.eclipse.persistence.sessions.DatabaseLogin;

/**
 * This is the awkward way that JPA provides to let us use
 * serializable transactions. At least there is a standard way
 * now, unlike in previous J2EE services.
 * 
 * Also, apply workaround for Eclipselink bug (or extreme non-feature)
 * of not looking up plain string JNDI names.
 *
 */
public class TransactionSessionCustomizer implements SessionCustomizer {

    public void customize(Session session) {
    	System.out.println("Starting customizer...");
        DatabaseLogin login = (DatabaseLogin)session.getDatasourceLogin();
		String dbUrl = login.getDatabaseURL();
		if (dbUrl.length() > 0)
			System.out.println("Database URL from persistence.xml is " + dbUrl);
		else {
			System.out
					.println("No database URL configured in persistence.xml, assuming web app");
			// The database URL (from persistence.xml) is "" in the webapp case,
			// where we use a JNDI lookup of the DataSource created by the app
			// server (tomcat).

			// First, use workaround for non-feature of EclipseLink related to
			// lookup of JNDI objects such as the DataSource: probably not needed
			// in the future. For more information, see
			// http://wiki.eclipse.org/EclipseLink/Examples/JPA/Tomcat_Web_Tutorial
			System.out
					.println("For web app, applying workaround for JNDI lookup problem...");
			JNDIConnector connector = null;
			Context context = null;
			try {
				context = new InitialContext();
				if (null != context) {
					connector = (JNDIConnector) login.getConnector();
					// Change from COMPOSITE_NAME_LOOKUP to STRING_LOOKUP
					connector.setLookupType(JNDIConnector.STRING_LOOKUP);
				} 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// In either case (webapp or clientserver) modify the default database
		// transaction isolation level (almost always Read Committed) 
		// to Serializable to get proper database transactions.
		// For EclipseLink, we also need to specify the "Isolated" connection
		// mode and no shared caching (except for invariant objects)
		// to prevent reads being done in separate connections/transactions
		// for variable entities, which would cause non-serializable executions
		// See persistence.xml for these settings.
		String url;
		if ((url =login.getDatabaseURL()) != null)
			System.out.println("Database URL is " + url);
		System.out
				.println("Now setting future transactions to serializable isolation");
		login.setTransactionIsolation(DatabaseLogin.TRANSACTION_SERIALIZABLE);
		if (login.getTransactionIsolation() != DatabaseLogin.TRANSACTION_SERIALIZABLE)
			System.out.println("--but not actually set to serializable");
        
    }
}