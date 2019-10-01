package com.sctt.cinema.api.business.entity.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@Table(name="BuzConfig")
@AllArgsConstructor
@NoArgsConstructor
public class BuzConfig extends BaseJPAEntity {

    @Id
    public String section;

    @Id
    public String buzKey;

    @Column
    public String buzValue;

    @Column
    public String description;

    public String getKey(){
        return String.format("%s_%s", section, buzKey);
    }

    @Override
    public boolean isValid() {
        return !section.isEmpty() && !buzKey.isEmpty() && !buzValue.isEmpty();
    }

    @Data
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class BuzConfigKey implements Serializable{
        public String section;
        public String key;

        public BuzConfigKey(String _key){
            this.section = _key.split("_")[0];
            this.key = _key.split("_")[1];
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BuzConfigKey)) return false;
            BuzConfigKey that = (BuzConfigKey) o;
            return section.equalsIgnoreCase(that.section) &&
                    key.equalsIgnoreCase(that.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(section, key);
        }
    }
}
