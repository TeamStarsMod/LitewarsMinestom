package xyz.article.commands.subcommands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.entity.Player;
import xyz.article.commands.ParentCommand;
import xyz.article.commands.SubCommand;
import xyz.article.utils.MessageUtils;

public class LitewarsLeaveSubCommand extends SubCommand {
    
    public LitewarsLeaveSubCommand(ParentCommand parent) {
        super(parent, "leave", "离开游戏", "litewars.leave", true, false);
    }
    
    @Override
    public boolean execute(CommandSender sender, CommandContext context) {
        if (!(sender instanceof Player player)) {
            MessageUtils.sendMessage(sender, "&c此命令只能由玩家执行!");
            return false;
        }
        
        MessageUtils.sendMessage(sender, "&a你已离开游戏!");
        return true;
    }

    @Override
    public String getPermission() {
        return null;
    }
} 