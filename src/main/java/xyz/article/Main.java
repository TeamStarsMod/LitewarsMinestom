package xyz.article;

import xyz.article.server.LitewarsServer;

public class Main {
    public static LitewarsServer litewarsServer;
    public static void main(String[] args) {
        RunningData.init();

        litewarsServer = new LitewarsServer(
                "0.0.0.0",
                25565,
                RunningData.getConfig()
        );

        litewarsServer.start();
    }
}