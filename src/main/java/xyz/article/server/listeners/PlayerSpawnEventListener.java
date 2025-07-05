package xyz.article.server.listeners;

import ca.atlasengine.projectiles.BowModule;
import ca.atlasengine.projectiles.entities.ArrowProjectile;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerHand;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;

public class PlayerSpawnEventListener implements EventListener<PlayerSpawnEvent> {
    @Override
    public @NotNull Result run(@NotNull PlayerSpawnEvent playerSpawnEvent) {
        if (!playerSpawnEvent.isFirstSpawn()) return Result.SUCCESS;
        Player player = playerSpawnEvent.getPlayer();

        player.setGameMode(GameMode.CREATIVE);
        player.teleport(new Pos(-37, 72, 0));
        System.out.println(player.getUsername() + " joined the server!");
        player.sendMessage("Hello! Welcome to Litewars!");

        // 修改火球事件监听器，使用普通弹射物而非追踪弹射物
        player.eventNode().addListener(PlayerUseItemEvent.class, e -> {
            if (e.getHand() != PlayerHand.MAIN) return;
            if (e.getItemStack().material() == Material.FIRE_CHARGE) {
                // 获取玩家位置和视线方向
                Pos playerPos = e.getPlayer().getPosition();
                Pos spawnPos = playerPos.add(0, e.getPlayer().getEyeHeight(), 0);

                // 创建并发射火球
                ArrowProjectile projectile = new ArrowProjectile(EntityType.FIREBALL, e.getPlayer());
                projectile.setInstance(e.getPlayer().getInstance());
                projectile.shoot(
                        spawnPos,  // 发射起点 (Point from)
                        1.5,       // 发射力度 (double power)
                        0.0        // 扩散角度 (double spread)
                );
            }
        });

        // 保留弓箭功能
        new BowModule(player.eventNode(), (p, i) -> new ArrowProjectile(EntityType.ARROW, p));
        return Result.SUCCESS;
    }

    @Override
    public @NotNull Class<PlayerSpawnEvent> eventType() {
        return PlayerSpawnEvent.class;
    }
}