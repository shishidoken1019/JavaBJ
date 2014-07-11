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
	@Result(name = "errorPage", value = "errorPage.action", type = ServletRedirectResult.class)})

public class BlackJackAction extends AbstractAction {
	
	/**
	 * 親のカード情報
	 */
	private ArrayList<String> parent_card;
	/**
	 * プレイヤーのカード情報
	 */
	private ArrayList<String> player_card;
	/**
	 * ゲーム名
	 */
	private String game_name = "";
	/**
	 * 結果コメント
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

	private static final long serialVersionUID = 1L;

	//初期アクセス時の処理
	public String execute() throws Exception {
		
		//ゲームをリロードしようとしたらエラー
		if(sessionMap.get("parent") != null){
			//セッションを消して
			sessionMap.put("parent", null);
			//エラーページに飛ぶ
			return "errorPage";
		}
				
		//対戦相手を作る
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
		this.session_common(now_use_tramp, parent, player);
		
		//もしプレイヤーのカードが21を超えていたら勝負
		if(player.getPlayer_sum_card() > 21){
			//
			this.fight(player, parent);
		}
		
		//共通部分を表示側に渡す
		this.common_disp(card, parent, player);
		
		return "success";
		
	}
	
	//スタンドを押されたとき
	public String stand(){
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
		this.session_common(now_use_tramp, parent, player);		
		
		//もしプレイヤーのカードが21を超えていたら勝負
		if(parent.getPlayer_state() == "stand"){
			//
			this.fight(player, parent);
		}
		
		//共通部分を表示側に渡す
		this.common_disp(card, parent, player);
		
		return "success";		
	}

	/**
	 * 共通でセッションに情報を格納するもの
	 */
	public void session_common(ArrayList<String> now_use_tramp,Player parent,Player player){
		sessionMap.put("now_use_tramp",now_use_tramp);
		sessionMap.put("parent",parent);
		sessionMap.put("player",player);
	}
	/**
	 * 共通で表示側に渡すものを渡す
	 * @param card
	 * @param parent
	 * @param player
	 */
	public void common_disp(Card card,Player parent,Player player){
		//表示側に渡す
		this.setGame_name((String)sessionMap.get("game_name"));
		this.setParent_card(card.display_card(parent.getPlayer_card()));
		this.setPlayer_card(card.display_card(player.getPlayer_card()));
		
	}
	
	/**
	 *  勝負した場合の処理
	 * @param player
	 * @param parent
	 */
	public void fight(Player player,Player parent){
		
		Utility utility = new Utility();
		String re = utility.result(player, parent);
		this.setResult(re);
		this.setPlayer_sum_card(player.getPlayer_sum_card());
		this.setParent_sum_card(parent.getPlayer_sum_card());
		
		sessionMap.put("parent", null);
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
