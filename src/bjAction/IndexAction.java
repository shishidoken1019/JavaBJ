package bjAction;

import org.apache.struts2.ServletActionContext;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;

import bjAction.AbstractAction;

@Results({
	@Result(name = "loginTop", value = "login.action", type = ServletRedirectResult.class),
	@Result(name = "blackJack", value = "blackJack.action", type = ServletRedirectResult.class),
	@Result(name = "poker", value = "poker.action", type = ServletRedirectResult.class)})


public class IndexAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	public String execute() throws Exception {
		//
		System.out.print("a"+sessionMap.get("Name")+"b");

		//ゲームの開始時にプレイヤーのインスタンスを作る
		Player player = new Player();
		
		//セッションに情報を確認
		sessionMap.put("player", player);
		sessionMap.put("game_name","BlackJack");
		
		// ログイン画面を表示
		return "success";
	}
	
	public String blackJack() throws Exception {
		//
		System.out.print("ブラックジャックメソッド");

		// 
		return "blackJack";
	}
	
	public String poker() throws Exception {
		// ID、パスワード共にセッション値が残っていた場合は初期値に入れる
		System.out.print("ポーカーメソッド");

		// ログイン画面を表示
		return "poker";
	}


	
}
