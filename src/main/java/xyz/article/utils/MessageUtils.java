package xyz.article.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.minestom.server.command.CommandSender;
import xyz.article.RunningData;

public class MessageUtils {
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final LegacyComponentSerializer LEGACY_SERIALIZER =
            LegacyComponentSerializer.builder()
                    .character('&')
                    .hexColors()
                    .build();

    /**
     * 发送带有前缀的消息 (同时支持MiniMessage和旧版颜色符号)
     * @param sender 发送者
     * @param message 消息内容，可以包含MiniMessage格式或传统&颜色代码
     */
    public static void sendMessageWithPrefix(CommandSender sender, String message) {
        Component parsed = MINI_MESSAGE.deserialize(message);
        parsed = LEGACY_SERIALIZER.deserialize(LEGACY_SERIALIZER.serialize(parsed));

        sender.sendMessage(RunningData.PREFIX.append(parsed));
    }

    /**
     * 发送带有前缀的Component消息
     * @param sender 发送者
     * @param message 已经解析的Component消息
     */
    public static void sendMessageWithPrefix(CommandSender sender, Component message) {
        sender.sendMessage(RunningData.PREFIX.append(message));
    }

    /**
     * 转换颜色符号(&)为旧版颜色符号
     * @param text 待转换文本
     * @return 转换后的文本
     */
    public static String getColorText(String text) {
        return getColorText('&', text);
    }

    /**
     * 转换指定符号为旧版颜色符号
     * @param colorChar 转换符号
     * @param text 待转换文本
     * @return 转换后的文本
     */
    public static String getColorText(char colorChar, String text) {
        char[] b = text.toCharArray();

        for(int i = 0; i < b.length - 1; ++i) {
            if (b[i] == colorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                b[i] = '§';
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }

        return new String(b);
    }

    /**
     * 将带颜色代码的字符串转换为Component
     * @param message 带颜色代码的消息
     * @return Component对象
     */
    public static Component reColor(String message) {
        if (message == null || message.isEmpty()) {
            return Component.empty();
        }
        // 将Bukkit颜色代码转换为MiniMessage格式
        String miniMessage = message
                .replace("&0", "<black>")
                .replace("&1", "<dark_blue>")
                .replace("&2", "<dark_green>")
                .replace("&3", "<dark_aqua>")
                .replace("&4", "<dark_red>")
                .replace("&5", "<dark_purple>")
                .replace("&6", "<gold>")
                .replace("&7", "<gray>")
                .replace("&8", "<dark_gray>")
                .replace("&9", "<blue>")
                .replace("&a", "<green>")
                .replace("&b", "<aqua>")
                .replace("&c", "<red>")
                .replace("&d", "<light_purple>")
                .replace("&e", "<yellow>")
                .replace("&f", "<white>")
                .replace("&l", "<bold>")
                .replace("&n", "<underline>")
                .replace("&o", "<italic>")
                .replace("&k", "<obfuscated>")
                .replace("&m", "<strikethrough>")
                .replace("&r", "<reset>");
        return MiniMessage.miniMessage().deserialize(miniMessage);
    }

    /**
     * 发送带颜色的消息给命令发送者，自动加上前缀
     * @param sender 命令发送者
     * @param message 消息内容
     */
    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(RunningData.PREFIX.append(reColor(message)));
    }

    public static void sendMessage(CommandSender sender, Component message) {
        sender.sendMessage(RunningData.PREFIX.append(message));
    }
}