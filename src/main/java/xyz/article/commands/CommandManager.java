package xyz.article.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private static final List<Command> registeredCommands = new ArrayList<>();
    
    /**
     * 注册命令
     * @param command 要注册的命令
     */
    public static void registerCommand(Command command) {
        MinecraftServer.getCommandManager().register(command);
        registeredCommands.add(command);
    }
    
    /**
     * 注册多个命令
     * @param commands 要注册的命令数组
     */
    public static void registerCommands(Command... commands) {
        for (Command command : commands) {
            registerCommand(command);
        }
    }
    
    /**
     * 获取所有已注册的命令
     * @return 已注册命令列表
     */
    public static List<Command> getRegisteredCommands() {
        return new ArrayList<>(registeredCommands);
    }
    
    /**
     * 注销所有命令
     */
    public static void unregisterAll() {
        for (Command command : registeredCommands) {
            MinecraftServer.getCommandManager().unregister(command);
        }
        registeredCommands.clear();
    }
} 