package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class UserHolder {
    private User user;
    private final static UserHolder INSTANCE = new UserHolder();

    public UserHolder() {
    }

    public static UserHolder getInstance() {
        return INSTANCE;
    }





}
