package com.example.notes.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainFragment extends Fragment implements RouterHolder, OnBackPressed {

    private Router router;

    public MainFragment() {
        super(R.layout.main_fragment);
    }

    @Override
    public Router getRouter() {
        return router;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        router = new Router(getChildFragmentManager());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState == null) {
            if (isAuthorized()) {
                router.showNotesList();
            } else {
                router.showAuth();
            }
        }

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_nav_view);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_notes) {
                if (isAuthorized()) {
                    router.showNotesList();
                } else {
                    router.showAuth();
                }
                return true;
            }

            if (item.getItemId() == R.id.action_info) {
                router.showInfo();
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean onBackPressed() {
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            getChildFragmentManager().popBackStack();
            return true;
        }
        return false;
    }

    private boolean isAuthorized() {
        return true;
    }
}
