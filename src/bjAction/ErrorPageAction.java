package bjAction;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;

public class ErrorPageAction extends AbstractAction {
	
	//シリアライズ
	private static final long serialVersionUID = 1L;
	
	public String execute(){
		return "success";
	}
}
