package bjAction;
 
import java.util.*;
 
import javax.servlet.http.HttpServletResponse;
 
import com.opensymphony.xwork2.ActionSupport;
 
import org.apache.struts2.interceptor.ServletResponseAware;
 
import org.apache.struts2.interceptor.SessionAware;
 
public class AbstractAction extends ActionSupport implements ServletResponseAware,SessionAware {
 
    private static final long serialVersionUID = 1L;
 
    public HttpServletResponse response;
    public HttpServletResponse request;
 
    /**
	 * @return response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @param response セットする response
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @return request
	 */
	public HttpServletResponse getRequest() {
		return request;
	}

	/**
	 * @param request セットする request
	 */
	public void setRequest(HttpServletResponse request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }
    
    public void setServletRequest(HttpServletResponse request) {
        this.request = request;
    }
 
    public Map sessionMap;
 
    public void setSession(Map sessionMap) {
        this.sessionMap = sessionMap;
    }
}
