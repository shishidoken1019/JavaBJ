package user;

import java.util.ArrayList;
import java.util.Collections;

public class Player {

	private ArrayList<String> player_card = new ArrayList<String>();
	private int player_money = 1000;
	private int player_sum_card = 0;
	private String player_state = "hit";
	
	//カードを手札に一枚追加
	public ArrayList<String> hit(ArrayList<String> now_use_tramp){
		//手札に追加
		this.player_card.add(now_use_tramp.get(0));
		//ヤマにある札から配ったものを減らす
		now_use_tramp.remove(0);
		//現在のカードの合計を再計算し、セット
		this.count_card_sum();
		//
		return now_use_tramp;
	}
	
	//プレイヤーの現在のカードの合計を計算
	public void count_card_sum(){
	
		//
		ArrayList<String> only_card_number_arr = new ArrayList<String>();
		String[] card_element;
		//数字だけの配列に変換
		for(String val:this.player_card){
			card_element = (val.split(":"));
			only_card_number_arr.add(card_element[1]);
		}
		
		////合計値を出す
		int sum = 0;
		//まずは降順に並び替え
		Collections.sort(only_card_number_arr);
		Collections.reverse(only_card_number_arr);
		
		//まわす
		for(int i =0;i < only_card_number_arr.size();i++){
			
			//1(エース)が出るまではカードのスコアにより加点、1が出た場合は$sumの値により1か11でカウントするかを決定
			if(Integer.parseInt(only_card_number_arr.get(i)) >= 10){
				 sum += 10;
			
			//エースの場合
			}else if(Integer.parseInt(only_card_number_arr.get(i)) == 1){
				if(i != (only_card_number_arr.size()-1)){
					sum += 1;
				}else{
					if(sum <= 10){
						sum += 11;
					}else{
						sum += 1;
					}
				}
			}else{
				sum +=Integer.parseInt(only_card_number_arr.get(i)); 
			}
		}
		
		//合計値を返す
		this.player_sum_card = sum;
	}
	
	//親の行動
	public ArrayList<String> parent_act(ArrayList<String> now_use_tramp){
	
		//親のカードが15以下だった場合、親はヒットする(条件は(仮))
		if(this.player_sum_card > 15){
			this.player_state = "stand";
		}else{
			now_use_tramp = this.hit(now_use_tramp);
			//ヒットしたので、もう一度状態を確認
			if(this.player_sum_card > 15){
				this.player_state = "stand";
			}
		}
		
		return now_use_tramp;
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
	 * @return player_money
	 */
	public int getPlayer_money() {
		return player_money;
	}

	/**
	 * @param player_money セットする player_money
	 */
	public void setPlayer_money(int player_money) {
		this.player_money = player_money;
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

	/**
	 * @return player_state
	 */
	public String getPlayer_state() {
		return player_state;
	}

	/**
	 * @param player_state セットする player_state
	 */
	public void setPlayer_state(String player_state) {
		this.player_state = player_state;
	}
	
	
}
