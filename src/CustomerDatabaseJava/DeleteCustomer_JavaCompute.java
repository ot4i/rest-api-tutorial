import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.broker.plugin.MbXPathVariables;

public class DeleteCustomer_JavaCompute extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbMessage inMessage = inAssembly.getMessage();
		MbMessage inLocalEnvironment = inAssembly.getLocalEnvironment();
		MbMessageAssembly outAssembly = null;
		try {
			// create new message as a copy of the input
			MbMessage outMessage = new MbMessage(inMessage);
			MbMessage outLocalEnvironment = new MbMessage(inLocalEnvironment);
			outAssembly = new MbMessageAssembly(inAssembly, outLocalEnvironment, inAssembly.getExceptionList(), outMessage);
			// ----------------------------------------------------------
			// Add user code below

			MbXPathVariables vars = new MbXPathVariables();

			// Process the 'customerId' parameter from the inbound request.
			MbElement temp = inLocalEnvironment.getRootElement().getFirstElementByPath("REST/Input/Parameters/customerId");
			int customerId;
			try {
				customerId = Integer.parseInt(temp.getValueAsString());
			} catch (NumberFormatException e) {
				vars.assign("statuscode", 400);
				vars.assign("error", "The value provided for the 'customerId' parameter is not a valid integer.");
				outLocalEnvironment.getRootElement().evaluateXPath("?Destination/?HTTP/?ReplyStatusCode[set-value($statuscode)]", vars);
				outMessage.getRootElement().createElementAsLastChild("JSON");
				outMessage.getRootElement().evaluateXPath("?JSON/?Data/?error[set-value($error)]", vars);
				out.propagate(outAssembly);
				return;
			}

			// Process the 'Authorization' parameter from the inbound request.
			temp = inLocalEnvironment.getRootElement().getFirstElementByPath("REST/Input/Parameters/Authorization");
			if (temp == null || temp.getValueAsString() == null) {
				vars.assign("statuscode", 400);
				vars.assign("error", "No 'Authorization' parameter was provided in the request.");
				outLocalEnvironment.getRootElement().evaluateXPath("?Destination/?HTTP/?ReplyStatusCode[set-value($statuscode)]", vars);
				outMessage.getRootElement().createElementAsLastChild("JSON");
				outMessage.getRootElement().evaluateXPath("?JSON/?Data/?error[set-value($error)]", vars);
				out.propagate(outAssembly);
				return;
			}
			String authorization = temp.getValueAsString();

			// Test the authorization key to validate that this request is
			// authorized.
			if (!authorization.equals("secr3t")) {
				vars.assign("statuscode", 400);
				vars.assign("error", "The value provided for the 'Authorized' parameter was not accepted as a valid authorization key.");
				outLocalEnvironment.getRootElement().evaluateXPath("?Destination/?HTTP/?ReplyStatusCode[set-value($statuscode)]", vars);
				outMessage.getRootElement().createElementAsLastChild("JSON");
				outMessage.getRootElement().evaluateXPath("?JSON/?Data/?error[set-value($error)]", vars);
				out.propagate(outAssembly);
				return;
			}

			// Delete the customer from the database.
			boolean result = CustomerDatabase.deleteCustomer(customerId);

			// If the above code returned false, then the customer did not
			// exist.
			if (!result) {
				vars.assign("statuscode", 404);
				vars.assign("error", "A customer with the specified ID does not exist in the database.");
				outLocalEnvironment.getRootElement().evaluateXPath("?Destination/?HTTP/?ReplyStatusCode[set-value($statuscode)]", vars);
				outMessage.getRootElement().createElementAsLastChild("JSON");
				outMessage.getRootElement().evaluateXPath("?JSON/?Data/?error[set-value($error)]", vars);
			} else {
				vars.assign("message", "The customer with the specified ID was successfully deleted from the database.");
				outMessage.getRootElement().createElementAsLastChild("JSON");
				outMessage.getRootElement().evaluateXPath("?JSON/?Data/?message[set-value($message)]", vars);
			}

			// End of user code
			// ----------------------------------------------------------
		} catch (MbException e) {
			// Re-throw to allow Broker handling of MbException
			throw e;
		} catch (RuntimeException e) {
			// Re-throw to allow Broker handling of RuntimeException
			throw e;
		} catch (Exception e) {
			// Consider replacing Exception with type(s) thrown by user code
			// Example handling ensures all exceptions are re-thrown to be
			// handled in the flow
			throw new MbUserException(this, "evaluate()", "", "", e.toString(), null);
		}
		// The following should only be changed
		// if not propagating message to the 'out' terminal
		out.propagate(outAssembly);

	}

}
