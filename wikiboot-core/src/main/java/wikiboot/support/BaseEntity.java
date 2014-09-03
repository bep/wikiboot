package wikiboot.support;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * Base class for {@link javax.persistence.Entity entities}.
 *
 * Uses a {@link java.util.UUID} as the key used for {@link #equals(Object)} and {@link #hashCode()},
 * but the primary key is a db-created {@link java.lang.Long}.
 *
 * todo dateCreated, lastUpdated
 *
 * @see org.springframework.data.jpa.domain.AbstractPersistable
 * @author Bjørn Erik Pedersen
 */
@MappedSuperclass
public abstract class BaseEntity extends AbstractPersistable<Long> {

    @Column(nullable = false, unique = true)
    private final String uuid;

    protected BaseEntity() {
        this.uuid = UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity that = (BaseEntity) o;

        return getUuid().equals(that.getUuid());
    }

    @Override
    public int hashCode() {
        return getUuid().hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ShortToStringStyle.SHORT_TO_STRING_STYLE)
                .append("id", getId())
                .append("uuid", uuid)
                .toString();
    }
}
