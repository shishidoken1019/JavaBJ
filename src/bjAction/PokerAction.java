package bjAction;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;

import bjAction.AbstractAction;
import bjAction.Player;

@Results({
	@Result(name = "loginTop", value = "login.action", type = ServletRedirectResult.class),
	@Result(name = "blackJack", value = "blackJack.action", type = ServletRedirectResult.class),
	@Result(name = "poker", value = "poker.action", type = ServletRedirectResult.class)})


public class PokerAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	public String execute() throws Exception {
		// ID、パスワード共にセッション値が残っていた場合は初期値に入れる
		System.out.print("PokerAction");

		Player parent = new Player();
		System.out.print(parent);
		
		
		// ログイン画面を表示
		return "success";
	}
	
	public String blackJack() throws Exception {
		// ID、パスワード共にセッション値が残っていた場合は初期値に入れる
		System.out.print("a"+sessionMap.get("Name")+"b");

		// ログイン画面を表示
		return "blackJack";
	}
	
	public String poker() throws Exception {
		// ID、パスワード共にセッション値が残っていた場合は初期値に入れる
		System.out.print("poker");

		// ログイン画面を表示
		return "poker";
	}


	
}
