package com.github.ipan97.kpexpress.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.ipan97.kpexpress.R;
import com.github.ipan97.kpexpress.ui.fragment.AboutFragment;
import com.github.ipan97.kpexpress.ui.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.nav_home:
                return replaceFragment(new HomeFragment());
            case R.id.nav_about:
                return replaceFragment(new AboutFragment());
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mBottomNavigationView.setItemIconTintList(null);
        mBottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    private boolean replaceFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameMainLayout, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
