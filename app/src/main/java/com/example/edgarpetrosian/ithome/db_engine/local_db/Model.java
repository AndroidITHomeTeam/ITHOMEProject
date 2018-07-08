package com.example.edgarpetrosian.ithome.db_engine.local_db;

public class Model {
    //SQLite model
    private long id;
    private long recyclerViewPosition;

    public Model(long recyclerViewPosition) {
        this.recyclerViewPosition = recyclerViewPosition;
    }

    public Model() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRecyclerViewPosition() {
        return recyclerViewPosition;
    }

    public void setRecyclerViewPosition(long recyclerViewPosition) {
        this.recyclerViewPosition = recyclerViewPosition;
    }
}
