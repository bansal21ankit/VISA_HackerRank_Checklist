package com.droidrank.checklist;

class Item {
    private long id;
    private String name;
    private boolean checked;

    Item(long id, String name, int checked) {
        this.id = id;
        this.name = name;
        this.checked = (checked != 0);
    }

    long getId() {
        return id;
    }

    String getName() {
        return name;
    }

    boolean isChecked() {
        return checked;
    }
}
