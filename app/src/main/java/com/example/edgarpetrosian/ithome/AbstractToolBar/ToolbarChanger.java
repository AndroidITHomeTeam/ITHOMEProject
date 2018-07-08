package com.example.edgarpetrosian.ithome.AbstractToolBar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.example.edgarpetrosian.ithome.R;

public abstract class ToolbarChanger extends AppCompatActivity {
    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        toolBar = (Toolbar) this.findViewById(R.id.javaDocToolbar);

        if (toolBar != null) {
            setSupportActionBar(toolBar);
            if (getSupportActionBar() != null) {
                setDisplayHomeEnabled(true);
            }
        }
    }

    protected abstract int getLayoutResource();

    public void setDisplayHomeEnabled(boolean b) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(b);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        toolBar.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        toolBar.setTitle(titleId);
    }

    public Toolbar getToolBar() {
        return toolBar;
    }
}
