package xyz.article;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.minestom.server.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class RunningData {
    private static final Yaml YAML = new Yaml();
    private static final Logger log = LoggerFactory.getLogger(RunningData.class);
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final LegacyComponentSerializer LEGACY_SERIALIZER =
            LegacyComponentSerializer.builder()
                    .character('&')
                    .hexColors()
                    .build();

    public static Component PREFIX;
    private static volatile Map<String, Object> configMap;

    // 设置模式玩家映射
    public static final Map<Player, Boolean> onSetupPlayerMap = new HashMap<>();

    public static void init() {
        String prefixText = (String) getConfig().getOrDefault("prefix", "<gray>[系统]</gray> &7");
        PREFIX = parseMixedFormat(prefixText);
    }

    /**
     * 解析混合格式文本 (同时支持MiniMessage和&颜色符号)
     * @param text 包含MiniMessage和/或传统颜色代码的文本
     * @return 解析后的Component
     */
    private static Component parseMixedFormat(String text) {
        // 先解析MiniMessage格式
        Component parsed = MINI_MESSAGE.deserialize(text);
        // 然后将结果转换为字符串并解析其中的传统颜色代码
        return LEGACY_SERIALIZER.deserialize(LEGACY_SERIALIZER.serialize(parsed));
    }

    /**
     * 获取配置文件 (线程安全)
     * @return 配置文件Map，如果出错返回空Map
     */
    public static Map<String, Object> getConfig() {
        // 第一次检查 (非同步)
        Map<String, Object> result = configMap;
        if (result != null) {
            return result;
        }

        // 同步块
        synchronized (RunningData.class) {
            // 第二次检查 (同步)
            result = configMap;
            if (result != null) {
                return result;
            }

            try {
                // 确保配置文件存在
                ensureConfigFileExists();

                // 加载配置文件
                result = loadConfigFromFile();
                configMap = result;
                return result;
            } catch (Exception e) {
                log.error("加载配置文件时出错", e);
                return new HashMap<>();
            }
        }
    }

    /**
     * 确保配置文件存在，不存在则从资源中复制
     */
    private static void ensureConfigFileExists() throws IOException {
        File configFile = new File("config.yml");
        if (configFile.exists()) {
            return;
        }

        try (InputStream input = RunningData.class.getClassLoader().getResourceAsStream("config.yml")) {
            if (input == null) {
                throw new FileNotFoundException("未找到默认配置文件资源");
            }
            Files.copy(input, configFile.toPath());
        }
    }

    /**
     * 从文件加载配置
     */
    private static Map<String, Object> loadConfigFromFile() throws IOException {
        Path configPath = Paths.get("config.yml");
        try (InputStream input = Files.newInputStream(configPath)) {
            return YAML.load(input);
        }
    }
}