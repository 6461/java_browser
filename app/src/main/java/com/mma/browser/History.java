package com.mma.browser;

import java.net.URL;
import java.util.ArrayList;

public class History {
    private ArrayList<URL> history;
    private int index;

    public History() {
        index = 0;
        history = new ArrayList();
    }

    public URL getPrevious() {
        if (index > 0) {
            index--;
            return history.get(index);
        }
        else {
            return null;
        }
    }

    public URL getNext() {
        if (history.size() > (index + 1)) {
            index++;
            return history.get(index);
        }
        else {
            return null;
        }
    }

    public void addURL(URL url) {
        if (history.size() > index + 1) {
            history.subList(index + 1, history.size()).clear();
        }

        history.add(url);
        index = history.size() - 1;
    }

    public int getIndex() {
        return index;
    }
}
