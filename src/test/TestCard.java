package test;

import java.util.ArrayList;

import java.util.Collections;

import user.Player;
import card.Card;
public class TestCard {
	public static void main(String[] args){
		
		Card card = new Card();
		Player p1 = new Player();
		Player p2 = new Player();
		ArrayList<String> now_use_tramp = card.getAll_tramp();
		Collections.shuffle(now_use_tramp);
		card.deal_tramp(now_use_tramp, 2, p1, p2);
		ArrayList<String> p1_card = card.display_card(p1.getPlayer_card());
		System.out.println(p1.getPlayer_card());
		System.out.println(p1_card);
		System.out.println(p2.getPlayer_card());
		System.out.print(card.getAll_tramp().size());
		
	}
}
