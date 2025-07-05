package xyz.article.commands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import xyz.article.utils.MessageUtils;

public class LitewarsParentCommand extends ParentCommand {
    
    public LitewarsParentCommand() {
        super("litewars", "lw");
        
        setDescription("Litewars 主命令");
        
        // 添加子命令
        addSubCommand(new xyz.article.commands.subcommands.LitewarsJoinSubCommand(this));
        addSubCommand(new xyz.article.commands.subcommands.LitewarsLeaveSubCommand(this));
        addSubCommand(new xyz.article.commands.subcommands.LitewarsListSubCommand(this));
    }
    
    @Override
    public boolean execute(CommandSender sender, CommandContext context) {
        // 当没有子命令参数时，显示帮助信息
        MessageUtils.sendMessage(sender, "&9Lite&bwars &7命令系统");
        MessageUtils.sendMessage(sender, "&6使用 &c/litewars help &6查看所有可用命令");
        return true;
    }
} 