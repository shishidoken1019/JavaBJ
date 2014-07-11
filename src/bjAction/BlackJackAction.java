package bjAction;

import java.util.ArrayList;

import java.util.Collections;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;

import bjAction.Card;
import bjAction.Player;
import bjAction.AbstractAction;

@Results({
	@Result(name = "errorPage", value = "errorPage.action", type = ServletRedirectResult.class),
	@Result(name = "blackJack", value = "blackJack.action", type = ServletRedirectResult.class),
	@Result(name = "poker", value = "poker.action", type = ServletRedirectResult.class)})


public class BlackJackAction extends AbstractAction {
	
	/**
	 * 親の情報が入ったbean
	 */
	private ArrayList<String> parent_card;
	/**
	 * プレイヤーの情報が入ったbean
	 */
	private ArrayList<String> player_card;
	/**
	 * ゲーム名
	 */
	private String game_name = "";
	/**
	 * 結果
	 */
	String result ="";
	/**
	 * 親のカードの合計
	 */
	int parent_sum_card = 0;
	/**
	 * プレイヤーのカードの合計
	 */
	int player_sum_card = 0;

	
	//シリアライズ
	private static final long serialVersionUID = 1L;
	
	public String execute() throws Exception {
		// ID、パスワード共にセッション値が残っていた場合は初期値に入れる
		System.out.print("BlackJackAction");
		
		
		//ゲームをリロードしようとしたらエラー
		if(sessionMap.get("parent") != null){
			//セッションを消して
			sessionMap.put("parent", null);
			//エラーページに飛ぶ
			return "errorPage";
		}
		
		
		//親のインスタンスを作る
		Player parent = new Player();
		//プレイヤーのインスタンスをセッション情報から持ってくる
		Player player = (Player)sessionMap.get("player");
		//継続してゲームの場合、プレイヤーのカードを初期化
		if(player.getPlayer_card() != null){
			player.setPlayer_card(new ArrayList<String>());
		}
		
		//ゲームを始めるにあたってトランプを作る
		Card card = new Card();
		ArrayList<String> now_use_tramp = card.getAll_tramp();
		
		//トランプを混ぜる
		Collections.shuffle(now_use_tramp);
		
		//カードを親とプレイヤーに配る
		now_use_tramp = card.deal_tramp(now_use_tramp,2,parent,player);
		
		//セッションに情報を格納
		sessionMap.put("game_name",(String)sessionMap.get("game_name"));
		sessionMap.put("now_use_tramp",now_use_tramp);
		sessionMap.put("parent",parent);
		sessionMap.put("player",player);
		
		//表示側に渡す情報をセット
		this.setParent_card(card.display_card(parent.getPlayer_card()));
		this.setPlayer_card(card.display_card(player.getPlayer_card()));
		this.setGame_name((String)sessionMap.get("game_name"));
		
		// ブラックジャックの初期画面を表示
		return "success";
	}
	
	//ヒットを押されたとき
	public String hit(){
		System.out.println("hit内");
		
		//
		Card card = new Card();
		
		//ゲームをリロードしようとしたらエラー
		if(sessionMap.get("parent") == null){
			//エラーページに飛ぶ
			return "errorPage";
		}
		
		//セッション情報からインスタンスを取得
		Player player = (Player)sessionMap.get("player"); 
		Player parent = (Player)sessionMap.get("parent");
		ArrayList<String> now_use_tramp = (ArrayList<String>)sessionMap.get("now_use_tramp");
		String game_name = (String)sessionMap.get("game_name"); 
		
		//親が行動
		now_use_tramp = parent.parent_act(now_use_tramp);
		//ヒットしたのでプレイヤーは手札にカードを追加
		now_use_tramp = player.hit(now_use_tramp);

		//セッションに情報を格納
		sessionMap.put("now_use_tramp",now_use_tramp);
		sessionMap.put("parent",parent);
		sessionMap.put("player",player);
		
		Utility utility = new Utility();
		
		//もしプレイヤーのカードが21を超えていたら勝負
		if(player.getPlayer_sum_card() > 21){
			//
			String re = utility.result(player, parent);
			this.setResult(re);
			this.setPlayer_sum_card(player.getPlayer_sum_card());
			this.setParent_sum_card(parent.getPlayer_sum_card());		
			
			sessionMap.put("parent", null);
		}
		
		//表示側に渡す
		this.setGame_name((String)sessionMap.get("game_name"));
		this.setParent_card(card.display_card(parent.getPlayer_card()));
		this.setPlayer_card(card.display_card(player.getPlayer_card()));
		
		return "success";
		
	}
	
	//スタンドを押されたとき
	public String stand(){
		System.out.println("stand内");
		
		//
		Card card = new Card();
		
		//ゲームをリロードしようとしたらエラー
		if(sessionMap.get("parent") == null){
			//エラーページに飛ぶ
			return "errorPage";
		}
		
		//セッション情報からインスタンスを取得
		Player player = (Player)sessionMap.get("player"); 
		Player parent = (Player)sessionMap.get("parent");
		ArrayList<String> now_use_tramp = (ArrayList<String>)sessionMap.get("now_use_tramp");
		
		//親が行動
		now_use_tramp = parent.parent_act(now_use_tramp);
		//ヒットしたのでプレイヤーは手札にカードを追加
		player.setPlayer_state("stand");;

		//セッションに情報を格納
		sessionMap.put("now_use_tramp",now_use_tramp);
		sessionMap.put("parent",parent);
		sessionMap.put("player",player);
		
		Utility utility = new Utility();
		
		//もしプレイヤーのカードが21を超えていたら勝負
		if(parent.getPlayer_state() == "stand"){
			//
			String re = utility.result(player, parent);
			this.setResult(re);
			this.setPlayer_sum_card(player.getPlayer_sum_card());
			this.setParent_sum_card(parent.getPlayer_sum_card());
			
			sessionMap.put("parent", null);
		}
		
		//表示側に渡す
		this.setGame_name((String)sessionMap.get("gameName"));
		this.setParent_card(card.display_card(parent.getPlayer_card()));
		this.setPlayer_card(card.display_card(player.getPlayer_card()));
		
		return "success";		
	}


	
	/**
	 * @return parent_card
	 */
	public ArrayList<String> getParent_card() {
		return parent_card;
	}


	/**
	 * @param parent_card セットする parent_card
	 */
	public void setParent_card(ArrayList<String> parent_card) {
		this.parent_card = parent_card;
	}


	/**
	 * @return player_card
	 */
	public ArrayList<String> getPlayer_card() {
		return player_card;
	}


	/**
	 * @param player_card セットする player_card
	 */
	public void setPlayer_card(ArrayList<String> player_card) {
		this.player_card = player_card;
	}


	/**
	 * @return gameName
	 */
	public String getGame_name() {
		return game_name;
	}

	/**
	 * @param gameName セットする gameName
	 */
	public void setGame_name(String game_name) {
		this.game_name = game_name;
	}


	/**
	 * @return result
	 */
	public String getResult() {
		return result;
	}


	/**
	 * @param result セットする result
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return parent_sum_card
	 */
	public int getParent_sum_card() {
		return parent_sum_card;
	}

	/**
	 * @param parent_sum_card セットする parent_sum_card
	 */
	public void setParent_sum_card(int parent_sum_card) {
		this.parent_sum_card = parent_sum_card;
	}

	/**
	 * @return player_sum_card
	 */
	public int getPlayer_sum_card() {
		return player_sum_card;
	}

	/**
	 * @param player_sum_card セットする player_sum_card
	 */
	public void setPlayer_sum_card(int player_sum_card) {
		this.player_sum_card = player_sum_card;
	}
	
}
