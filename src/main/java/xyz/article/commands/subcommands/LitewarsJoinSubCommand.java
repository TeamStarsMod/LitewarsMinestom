package xyz.article.commands.subcommands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.entity.Player;
import xyz.article.commands.ParentCommand;
import xyz.article.commands.SubCommand;
import xyz.article.utils.MessageUtils;

public class LitewarsJoinSubCommand extends SubCommand {
    
    public LitewarsJoinSubCommand(ParentCommand parent) {
        super(parent, "join", "加入游戏", "litewars.join", true, false);
    }
    
    @Override
    public boolean execute(CommandSender sender, CommandContext context) {
        if (!(sender instanceof Player player)) {
            MessageUtils.sendMessage(sender, "&c此命令只能由玩家执行!");
            return false;
        }
        
        MessageUtils.sendMessage(sender, "&a你已加入游戏!");
        return true;
    }

    @Override
    public String getPermission() {
        return null;
    }
}