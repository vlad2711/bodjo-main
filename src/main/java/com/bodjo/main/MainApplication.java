package com.bodjo.main;

import com.bodjo.main.Utils.Utils;
import com.bodjo.main.controllers.ServerController;
import com.bodjo.main.objects.DatabaseConfigModel;
import com.bodjo.main.objects.GamesModel;
import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {

		String config = Utils.getFile("config.txt");
		DatabaseConfigModel configModel = new Gson().fromJson(Utils.decrypt(config), DatabaseConfigModel.class);
		Utils.dbPassword = configModel.getDatabasePassword();
		Utils.dbUser = configModel.getDatabaseUser();

		GamesModel gamesModel = new Gson().fromJson(Utils.getFile("games.json"), GamesModel.class);

		SpringApplication.run(MainApplication.class, args);

		System.out.println(gamesModel.getGames().length);
		for (int i = 0; i < gamesModel.getGames().length; i++) {
			int finalI = i;
			Thread thread = new Thread(() -> {
				System.out.println(gamesModel.getGames()[finalI]);
				try {
					ServerController.runServer(gamesModel.getPath(), gamesModel.getGames()[finalI]);
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			});
			thread.start();

		}
	}
}
