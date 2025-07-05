package xyz.article.server;

import io.github.togar2.fluids.MinestomFluids;
import io.github.togar2.pvp.MinestomPvP;
import io.github.togar2.pvp.feature.CombatFeatureSet;
import io.github.togar2.pvp.feature.CombatFeatures;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.anvil.AnvilLoader;
import xyz.article.commands.CommandManager;
import xyz.article.commands.LitewarsParentCommand;
import xyz.article.server.listeners.PlayerSpawnEventListener;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map;

public class LitewarsServer {
    private final String host;
    private final int port;

    private MinecraftServer server;
    private GlobalEventHandler globalEventHandler;

    public LitewarsServer(String host, int port, Map<String, Object> configMap) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        server = MinecraftServer.init();
        MinestomPvP.init();
        MinestomFluids.init();
        MinecraftServer.setBrandName("Litewars");

        InstanceContainer defaultInstance = MinecraftServer.getInstanceManager().createInstanceContainer();
        defaultInstance.setChunkLoader(new AnvilLoader("world"));
        defaultInstance.setChunkSupplier(LightingChunk::new);
        defaultInstance.setTime(5000);
        defaultInstance.setTimeRate(0);

        CombatFeatureSet legacyVanilla = CombatFeatures.legacyVanilla();
        MinecraftServer.getGlobalEventHandler().addChild(legacyVanilla.createNode());

        MinecraftServer.getGlobalEventHandler().addChild(MinestomFluids.events());

        globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, e -> {
            e.setSpawningInstance(defaultInstance);
        });

        globalEventHandler.addListener(new PlayerSpawnEventListener());

        globalEventHandler.addListener(PlayerDisconnectEvent.class, e -> {
            Player player = e.getPlayer();
            System.out.println(player.getUsername() + " left the server!");
        });

        // 使用命令管理器注册命令
        CommandManager.registerCommands(
            new LitewarsParentCommand()
        );

        server.start(host, port);
        System.out.println("Litewars started on " + host + ":" + port);
    }

    public MinecraftServer getMinestomServer() {
        return server;
    }

    public GlobalEventHandler getGlobalEventHandler() {
        return globalEventHandler;
    }

    public InetAddress getAddress () {
        return new InetSocketAddress(host, port).getAddress();
    }
}
