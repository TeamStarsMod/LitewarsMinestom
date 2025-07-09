package xyz.article.api.team;

import net.minestom.server.entity.Player;
import xyz.article.api.arena.IArena;
import xyz.article.api.team.color.TeamColor;

import java.util.List;

public interface ITeam {
    IArena getArena();
    TeamColor getColor();
    List<Player> getMembers();
    void addMember(Player player);
    void removeMember(Player player);
}
