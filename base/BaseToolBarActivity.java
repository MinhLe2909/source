package tamhoang.ldpro4.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import tamhoang.ldpro4.R;


public abstract class BaseToolBarActivity extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();
    protected Toolbar toolbar;

    public abstract int getLayoutId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setTitle(this.TAG);
        Toolbar toolbar = findViewById(R.id.toolbar);
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);
    }
}
