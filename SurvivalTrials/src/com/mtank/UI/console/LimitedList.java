package com.mtank.UI.console;

import java.util.LinkedList;

@SuppressWarnings("serial")
public class LimitedList<E> extends LinkedList<E>  {
	private int limit;

    public LimitedList(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean add(E o) {
        super.add(o);
        while (size() > limit) { super.remove(o); }
        return true;
    }
}
