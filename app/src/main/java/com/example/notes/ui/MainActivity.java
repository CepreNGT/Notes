package com.example.notes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.domain.Notes;
import com.example.notes.ui.notedetails.NoteDetailsFragment;
import com.example.notes.ui.notelist.NotesListFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NotesListFragment.OnNoteClicked {

    private static final String ARG_NOTE = "ARG_NOTE";
    private Notes selectedNote;
    private NotesListFragment listFragment;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem search = menu.findItem(R.id.find_key);

        SearchView searchView = (SearchView) search.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "Submitted", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(MainActivity.this, "Edited", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.info_key) {
            Toast.makeText(MainActivity.this, "Info", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open_drawer,
                R.string.close_drawer);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_one) {
                Toast.makeText(MainActivity.this, "Action One tapped", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }

            if (item.getItemId() == R.id.action_two) {
                Toast.makeText(MainActivity.this, "Action Two tapped", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });

        listFragment = new NotesListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, listFragment, null)
                .commit();
    }

    @Override
    public void onNoteClicked(Notes note) {
        selectedNote = note;
        if (getResources().getBoolean(R.bool.isLandscape)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, listFragment, null)
                    .commit();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.details_container, NoteDetailsFragment.newInstance(note), null)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, NoteDetailsFragment.newInstance(note), null)
                    .addToBackStack("Note")
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (selectedNote != null) {
            outState.putParcelable(ARG_NOTE, selectedNote);
        }
        super.onSaveInstanceState(outState);
    }
}