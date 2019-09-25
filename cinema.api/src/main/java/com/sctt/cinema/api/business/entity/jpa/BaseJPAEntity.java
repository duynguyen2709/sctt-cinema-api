package com.sctt.cinema.api.business.entity.jpa;

import java.io.Serializable;

public abstract class BaseJPAEntity implements Serializable {
    public abstract boolean isValid();
}
