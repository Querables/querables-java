package com.github.querables;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidKey {
    private String first;
    private String second;
    private String third;

    public static final ValidKey FIRST = new ValidKey("first1", "second1", "third1");
    public static final ValidKey SECOND = new ValidKey("first2", "second2", "third2");
    public static final ValidKey EMPTY = new ValidKey(null, null, null);
}
