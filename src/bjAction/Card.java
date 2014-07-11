package bjAction;

import java.util.ArrayList;

import bjAction.Player;

public class Card {

	/**
	 * 全てのトランプ
	 */
	private ArrayList<String> All_tramp = new ArrayList<String>();

	public Card() {

		int key = 0;

		// カードを定義する
		for (int i = 1; i < 14; i++) {
			this.All_tramp.add("H:" + i);
			key++;
			this.All_tramp.add("D:" + i);
			key++;
			this.All_tramp.add("S:" + i);
			key++;
			this.All_tramp.add("C:" + i);
			key++;
		}

	}

	/**
	 * トランプを配る二人用（山のトランプ、一人に対して配る枚数,プレイヤー1、プレイヤー2）
	 */
	public ArrayList<String> deal_tramp(ArrayList<String> now_use_tramp,
			int deal_tramp_count, Player player1, Player player2) {

		// 配る枚数分まわす
		for (int i = 0; i < deal_tramp_count; i++) {
			// それぞれに配る
			now_use_tramp = player1.hit(now_use_tramp);
			now_use_tramp = player2.hit(now_use_tramp);
		}
		return now_use_tramp;
	}

	/**
	 * 表示用の配列に変換
	 * 
	 * @param array_card
	 *            (表示させたいカードの配列)
	 * @return 変換した表示させたいカードの配列
	 */
	public ArrayList<String> display_card(ArrayList<String> array_card) {

		ArrayList<String> disp_card = new ArrayList<String>();

		// 変換する
		for (String val : array_card) {
			String[] pieces;

			// マークと数字にわける
			pieces = val.split(":");

			// マークを変換
			String mark = "";
			switch (pieces[0]) {
			case "H":
				mark = "♥";
				break;
			case "D":
				mark = "♦";
				break;
			case "S":
				mark = "♠";
				break;
			case "C":
				mark = "♣";
				break;
			}

			// 数字部分を変換
			switch (pieces[1]) {
			case "11":
				pieces[1] = "J";
				break;
			case "12":
				pieces[1] = "Q";
				break;
			case "13":
				pieces[1] = "K";
				break;
			case "1":
				pieces[1] = "A";
				break;
			}
			// 元の形に戻して
			disp_card.add(mark + ":" + pieces[1]);
		}

		return disp_card;
	}

	/**
	 * @return all_tramp
	 */
	public ArrayList<String> getAll_tramp() {
		return All_tramp;
	}

	/**
	 * @param all_tramp
	 *            セットする all_tramp
	 */
	public void setAll_tramp(ArrayList<String> all_tramp) {
		All_tramp = all_tramp;
	}

}