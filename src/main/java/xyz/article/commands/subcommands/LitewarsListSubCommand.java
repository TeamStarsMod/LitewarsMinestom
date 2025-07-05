package xyz.article.commands.subcommands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import xyz.article.commands.ParentCommand;
import xyz.article.commands.SubCommand;
import xyz.article.utils.MessageUtils;

public class LitewarsListSubCommand extends SubCommand {
    
    public LitewarsListSubCommand(ParentCommand parent) {
        super(parent, "list", "查看游戏列表", "litewars.list", false, false);
    }
    
    @Override
    public boolean execute(CommandSender sender, CommandContext context) {
        MessageUtils.sendMessage(sender, "&6当前游戏列表:");
        MessageUtils.sendMessage(sender, "&a- 竞技场1 (0/10)");
        MessageUtils.sendMessage(sender, "&a- 竞技场2 (5/10)");
        MessageUtils.sendMessage(sender, "&a- 竞技场3 (10/10) &c已满");
        return true;
    }

    @Override
    public String getPermission() {
        return null;
    }
} 