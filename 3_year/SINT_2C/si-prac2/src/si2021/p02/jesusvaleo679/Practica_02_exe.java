package si2021.p02.jesusvaleo679;

import java.util.Random;

import tools.Utils;
import tracks.ArcadeMachine;

public class Practica_02_exe {

    public static void main(String[] args) {

		String p0 = "si2021.p02.jesusvaleo679.AgenteTeleoreactivo";
		
		//Load available games
		String spGamesCollection =  "examples/all_games_sp.csv";
		String[][] games = Utils.readGames(spGamesCollection);

		//Game settings
		boolean visuals = true;
		int seed = new Random().nextInt();
				
		// Game and level to play
		int gameIdx  = 20;
		int levelIdx = 0; // level names from 0 to 4 (game_lvlN.txt).
		
		String gameName = games[gameIdx][1];
		String game = games[gameIdx][0];
		String level1 = game.replace(gameName, gameName + "_lvl" + levelIdx);

		
		// 1. This starts a game, in a level, played by a human.
		//ArcadeMachine.playOneGame(game, level1, null, seed);

		// 2. This plays a game in a level by the controller.
		ArcadeMachine.runOneGame(game, level1, visuals, p0, null, seed, 0);
		
		/*int numPartidas = 100;
		
		String[] files = new String[numPartidas];
		for (int i = 0; i < numPartidas; i++) files[i] = "partidas/acciones"+i+".txt";
		ArcadeMachine.runGames(game, new String[] {level1}, numPartidas, p0, files);
		System.out.println("Media de puntuaciones tras " + numPartidas + " partidas: "
				+ CalcularMedia.calcularMedia("acciones", numPartidas));*/
				
		//System.exit(0);
    }
}
