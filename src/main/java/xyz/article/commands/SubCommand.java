package xyz.article.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.CommandSender;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.article.RunningData;

import java.util.Arrays;

public abstract class SubCommand extends Command {
    private final ParentCommand parent;
    private final String name;
    private String description;
    private String permission;
    private final boolean isOnlyPlayer;
    private final boolean isOnlySetup;
    private String[] subs;

    public SubCommand(ParentCommand parent, String name, String description, String permission, boolean isOnlyPlayer, boolean isOnlySetup) {
        super(name);
        this.parent = parent;
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.isOnlyPlayer = isOnlyPlayer;
        this.isOnlySetup = isOnlySetup;
        
        setDefaultExecutor(this::onDefaultExecute);
    }

    public SubCommand(ParentCommand parent, String name, String description, String permission, boolean isOnlyPlayer, boolean isOnlySetup, String... subs) {
        super(name);
        this.parent = parent;
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.isOnlyPlayer = isOnlyPlayer;
        this.isOnlySetup = isOnlySetup;
        this.subs = subs;
        
        setDefaultExecutor(this::onDefaultExecute);
    }

    public ParentCommand getParent() {
        return this.parent;
    }

    private void onDefaultExecute(CommandSender sender, CommandContext context) {
        if (getPermission() != null) {
            // Minestom中暂时跳过权限检查
            // if (!sender.hasPermission(getPermission())) {
            //     sender.sendMessage("&c你没有权限执行此命令!");
            //     return;
            // }
        }
        
        // 检查是否只对玩家可用
        if (getIsOnlyPlayer() && !(sender instanceof Player)) {
            sender.sendMessage("&c此命令只能由玩家执行!");
            return;
        }
        
        // 检查设置模式
        if (sender instanceof Player player) {
            boolean playerInSetupMap = RunningData.onSetupPlayerMap.containsKey(player);
            if ((getIsOnlySetup() && !playerInSetupMap) || (!getIsOnlySetup() && playerInSetupMap)) {
                sender.sendMessage("&c此命令在当前模式下不可用!");
                return;
            }
        }
        
        execute(sender, context);
    }

    public abstract boolean execute(CommandSender sender, CommandContext context);

    public String getDescription() {
        return description;
    }

    public @NotNull String getName() {
        return this.name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPermission() {
        return this.permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public boolean getIsOnlyPlayer() {
        return this.isOnlyPlayer;
    }

    public boolean getIsOnlySetup() {
        return this.isOnlySetup;
    }

    public String[] getSubs() {
        return subs;
    }

    public void addSub(String sub) {
        if (this.subs == null) {
            this.subs = new String[0];
        }
        String[] newSubs = new String[this.subs.length + 1];
        if (this.subs.length - 1 >= 0) System.arraycopy(this.subs, 0, newSubs, 0, this.subs.length - 1);
        newSubs[newSubs.length - 1] = sub;
        this.subs = newSubs;
    }

    @Override
    public String toString() {
        return "Litewars-SubCommand [" + this.getClass().getName() + "]\n" +
                "{parent=" + parent + ", name=" + name + ", description=" + getDescription() + ", permission=" + permission + ", isOnlyPlayer=" + isOnlyPlayer + ", isOnlySetup=" + isOnlySetup + "}" +
                "Subs [" + Arrays.toString(subs) + "]";
    }
} 