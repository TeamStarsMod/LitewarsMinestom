package xyz.article.utils;

import net.minestom.server.coordinate.Pos;

public class PositionUtils {
    /**
     * 计算两点之间的距离 (性能不好)
     * @param pos1 第一个点
     * @param pos2 第二个点
     * @return 两点之间的距离
     */
    public static double getDistance(Pos pos1, Pos pos2) {
        double dx = pos1.x() - pos2.x();
        double dy = pos1.y() - pos2.y();
        double dz = pos1.z() - pos2.z();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * 判断两点之间的距离是否在指定范围内 (性能好)
     * @param pos1 第一个点
     * @param pos2 第二个点
     * @param range 指定范围
     * @return 是否在范围内
     */
    public static boolean isInRange(Pos pos1, Pos pos2, double range) {
        double dx = pos1.x() - pos2.x();
        double dy = pos1.y() - pos2.y();
        double dz = pos1.z() - pos2.z();
        double squaredDistance = dx * dx + dy * dy + dz * dz;
        return squaredDistance <= range * range;
    }
}
