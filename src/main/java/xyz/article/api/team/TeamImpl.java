package xyz.article.api.team;

import net.minestom.server.entity.Player;
import xyz.article.api.arena.IArena;
import xyz.article.api.team.color.TeamColor;

import java.util.List;

public class TeamImpl implements ITeam {
    private List<Player> members;
    private TeamColor color;
    private final IArena arena;
    private int maxMembers;

    public TeamImpl(List<Player> members, TeamColor color, IArena arena, int maxMembers) {
        this.arena = arena;
    }

    @Override
    public void addMember(Player player) {

    }

    @Override
    public void removeMember(Player player) {

    }

    @Override
    public IArena getArena() {
        return arena;
    }

    @Override
    public List<Player> getMembers() {
        return members;
    }

    @Override
    public TeamColor getColor() {
        return color;
    }
}
