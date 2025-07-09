package xyz.article.api.team.color;

public enum TeamColor {
    RED ("red"),
    BLUE ("blue"),
    GREEN ("green"),
    YELLOW ("yellow"),
    PURPLE ("purple"),
    CYAN ("cyan"),
    ORANGE ("orange"),
    PINK ("pink"),
    LIME ("lime"),
    TEAL ("teal"),
    BROWN ("brown"),
    GRAY ("gray"),
    BLACK ("black"),
    WHITE ("white"),
    EXTENDED1 ("ex1"),
    EXTENDED2 ("ex2"),
    EXTENDED3 ("ex3"),
    EXTENDED4 ("ex4"),
    EXTENDED5 ("ex5"),
    EXTENDED6 ("ex6"),
    EXTENDED7 ("ex7"),
    EXTENDED8 ("ex8"),
    EXTENDED9 ("ex9"),
    EXTENDED10 ("ex10");

    private final String color;

    TeamColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return color;
    }
}
