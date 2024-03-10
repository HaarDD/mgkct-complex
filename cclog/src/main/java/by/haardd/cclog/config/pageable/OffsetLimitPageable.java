package by.haardd.cclog.config.pageable;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

public class OffsetLimitPageable implements Pageable, Serializable {

    long offset;
    int limit;
    Sort sort;

    /**
     * Creates a new {@link OffsetLimitPageable} with sort parameters applied.
     *
     * @param offset zero-based offset.
     * @param limit  the size of the elements to be returned.
     * @param sort   must not be {@literal null}, use {@link Sort#unsorted()} instead.
     */
    protected OffsetLimitPageable(long offset, int limit, Sort sort) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }

        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }
        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
    }

    /**
     * Creates a new {@link OffsetLimitPageable} with sort parameters applied.
     *
     * @param offset     zero-based offset.
     * @param limit      the size of the elements to be returned.
     * @param direction  the direction of the {@link Sort} to be specified, can be {@literal null}.
     * @param properties the properties to sort by, must not be {@literal null} or empty.
     */
    protected OffsetLimitPageable(long offset, int limit, Sort.Direction direction, String... properties) {
        this(offset, limit, Sort.by(direction, properties));
    }

    /**
     * Creates a new unsorted {@link OffsetLimitPageable}.
     *
     * @param offset zero-based offset.
     * @param limit  the size of the elements to be returned, must be greater than 0.
     */
    public static OffsetLimitPageable of(long offset, int limit) {
        return of(offset, limit, Sort.unsorted());
    }

    /**
     * Creates a new {@link OffsetLimitPageable} with sort parameters applied.
     *
     * @param offset zero-based offset.
     * @param limit  the size of the elements to be returned, must be greater than 0.
     * @param sort   must not be {@literal null}, use {@link Sort#unsorted()} instead.
     */
    public static OffsetLimitPageable of(long offset, int limit, Sort sort) {
        return new OffsetLimitPageable(offset, limit, sort);
    }

    /**
     * Creates a new {@link OffsetLimitPageable} with sort direction and properties applied.
     *
     * @param offset     zero-based offset.
     * @param limit      the size of the elements to be returned, must be greater than 0.
     * @param direction  must not be {@literal null}.
     * @param properties must not be {@literal null}.
     */
    public static OffsetLimitPageable of(long offset, int limit, Sort.Direction direction, String... properties) {
        return of(offset, limit, Sort.by(direction, properties));
    }

    @Override
    public int getPageNumber() {
        return (int) offset / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new OffsetLimitPageable(getOffset() + getPageSize(), getPageSize(), getSort());
    }

    public OffsetLimitPageable previous() {
        return hasPrevious() ? new OffsetLimitPageable(getOffset() - getPageSize(), getPageSize(), getSort()) : this;
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new OffsetLimitPageable(0, getPageSize(), getSort());
    }

    @Override
    public Pageable withPage(int pageNumber) {
        final int pageSize = getPageSize();
        return new OffsetLimitPageable((long) pageNumber * pageSize, pageSize, getSort());
    }

    @Override
    public boolean hasPrevious() {
        return offset >= limit;
    }
}
