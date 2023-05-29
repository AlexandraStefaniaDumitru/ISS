package domain;

import java.io.Serializable;

public interface Identifiable<T> extends Serializable {

    void setID(T id);
    T getID();
}
