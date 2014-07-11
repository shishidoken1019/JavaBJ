package test;

import java.util.ArrayList;

import user.Player;

public class Test {
	
	public static void main(String[] args){
		
		ArrayList<String> tramp = new ArrayList<String>();
		tramp.add("H:1");
		tramp.add("C:7");
		tramp.add("D:4");
		//System.out.println(tramp);
		Player player = new Player();
		System.out.println(player.getPlayer_card());
		tramp = player.hit(tramp);
		//player.count_card_sum();
		System.out.println(player.getPlayer_card());
		System.out.println(tramp);
		System.out.println(player.getPlayer_sum_card());
		
	}

}
