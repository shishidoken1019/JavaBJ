package bjAction;

import bjAction.Player;

public class Utility {
	/**
	 *  ブラックジャックで結果を出す処理
	 * @param player（プレイヤー）
	 * @param parent（親）
	 * @return 結果コメント
	 */
	public String result(Player player, Player parent) {

		String result = "";
		// 結果
		// 引き分け条件
		if (player.getPlayer_sum_card() == parent.getPlayer_sum_card()
				|| (parent.getPlayer_sum_card() > 21 && player
						.getPlayer_sum_card() > 21)) {
			result = "引き分け";
		}
		// プレイヤー勝利条件
		else if ((parent.getPlayer_sum_card() > 21)
				|| (player.getPlayer_sum_card() > parent.getPlayer_sum_card() && player
						.getPlayer_sum_card() <= 21)) {
			result = "あなたの勝ち";
		}
		// プレイヤー負け条件
		else if ((player.getPlayer_sum_card() > 21)
				|| (player.getPlayer_sum_card() < parent.getPlayer_sum_card() && parent
						.getPlayer_sum_card() <= 21)) {
			result = "あなたの負け";
		}

		return result;
	}
/*	
	//表示側の共通処理(常にテンプレートに渡すもの)
	public void common_display(player1,player2,$smarty){
			
		//	
		$card = new Card();	
		////表示側に渡す
		$smarty->set('game_name', 'BlackJack');
		$smarty->set('player_card', $card->display_card($player1->get_player_card()));
		$smarty->set('parent_card', $card->display_card($player2->get_player_card()));
		//行動を表示するかどうかのフラグを渡す
		$smarty->set('act_flg_ok', @$_SESSION['parent']);
	}
*/
}
