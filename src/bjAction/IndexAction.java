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

		//ゲームの開始時にプレイヤーのインスタンスを作る
		Player player = new Player();
		
		//セッションに情報を確認
		sessionMap.put("player", player);
		sessionMap.put("game_name","BlackJack");
		
		// ログイン画面を表示
		return "success";
	}
	
	//ブラックジャックを押されたら
	public String blackJack() throws Exception {

		return "blackJack";
	}
	
	//ポーカーを押されたら
	public String poker() throws Exception {

		return "poker";
	}


	
}
