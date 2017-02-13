package com.nixsolutions.project3;

/**
 * Created by annnikon on 30.01.17.
 */
public class InputException extends IllegalArgumentException {
    public InputException(String input) {
        super("Not allowed combination in part: "+ input);
    }
}
