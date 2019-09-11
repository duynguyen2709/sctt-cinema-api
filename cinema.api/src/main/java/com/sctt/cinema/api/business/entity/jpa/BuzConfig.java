package com.sctt.cinema.api.business.entity.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@Table(name="BuzConfig")
@IdClass(BuzConfig.BuzConfigKey.class)
public class BuzConfig implements Serializable {

    @Id
    public String section;

    @Id
    public String key;

    @Column
    public String value;

    @Column
    public String description;

    @Data
    @AllArgsConstructor
    public static class BuzConfigKey implements Serializable {
        public String section;
        public String key;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BuzConfigKey)) return false;
            BuzConfigKey that = (BuzConfigKey) o;
            return section.equals(that.section) && key.equals(that.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(getSection(), getKey());
        }
    }
}
