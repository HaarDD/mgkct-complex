package by.haardd.cclog.config.pageable;

public class PageableUtils {

    public static Long calcOffset(Long offset) {
        return offset < 0 ? 0L : offset;
    }

    public static Integer calcLimit(Integer limit, Integer maxLimit) {
        return limit > maxLimit || limit <= 0 ? maxLimit : limit;
    }

    public static Integer calcLimit(Integer limit) {
        return limit > 10 || limit <= 0 ? 10 : limit;
    }
}
