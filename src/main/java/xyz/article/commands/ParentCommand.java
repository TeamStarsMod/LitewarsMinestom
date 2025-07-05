package xyz.article.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.CommandSender;
import net.minestom.server.entity.Player;
import xyz.article.RunningData;
import xyz.article.utils.MessageUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ParentCommand extends Command {
    private final List<SubCommand> subCommands = new ArrayList<>();
    private final String[] name;
    private String description = "";

    public ParentCommand(String... name) {
        super(name[0]);
        this.name = name;
        
        // 设置默认执行器
        setDefaultExecutor(this::onDefaultExecute);
    }

    public List<SubCommand> getSubCommands() {
        return this.subCommands;
    }

    public void addSubCommand(SubCommand subCommand) {
        this.subCommands.add(subCommand);
        addSubcommand(subCommand);
    }

    private void onDefaultExecute(CommandSender sender, CommandContext context) {
        StringBuilder sb = new StringBuilder();
        sb.append("&bLitewars &7命令系统\n");
        sb.append("&6可用命令:\n");
        int count = 0;
        for (SubCommand sub : subCommands) {
            if (sub.getIsOnlyPlayer() && !(sender instanceof Player)) continue;
            if (sender instanceof Player player) {
                boolean inSetup = RunningData.onSetupPlayerMap.containsKey(player);
                if ((inSetup && !sub.getIsOnlySetup()) || (!inSetup && sub.getIsOnlySetup())) continue;
            }
            if (sub.getPermission() != null) continue;
            sb.append("  + ")
              .append(sub.getName())
              .append(" | ")
              .append(sub.getDescription() == null ? "" : sub.getDescription())
              .append("\n");
            count++;
        }
        if (count == 0) {
            sb.append("  (无可用子命令)");
        }
        if (!sb.isEmpty() && sb.charAt(sb.length() - 1) == '\n') {
            sb.setLength(sb.length() - 1);
        }
        sender.sendMessage(MessageUtils.reColor(sb.toString()));
    }

    public abstract boolean execute(CommandSender sender, CommandContext context);

    public String[] getNames() {
        return name;
    }

    @Override
    public String toString() {
        return "Litewars-ParentCommand [" + this.getClass().getName() + "]\n" +
                "{name=" + Arrays.toString(name) + "}" +
                "Subs [" + subCommands + "]";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
} 