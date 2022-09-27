package com.nikameru.skinjsoneditor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.amrdeveloper.codeview.CodeView;
import com.nikameru.skinjsoneditor.ui.project.ProjectFragment;
import com.nikameru.skinjsoneditor.ui.project.ProjectPreviewFragment;

import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;
import java.util.regex.Pattern;

public class CreateProjectActivity extends AppCompatActivity implements
        PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    final private static JSONAssembler assembler = new JSONAssembler();
    final private static Pattern[] regexHighlighting = {
            Pattern.compile("\"[0-9a-zA-Z#]+\""),   // strings
            Pattern.compile("[^\"#:A-Z]+\\d[^\",}A-Z]+"),   // numbers
            Pattern.compile("true|false")   // booleans
    };
    final private static int[] highlightingColors = {
            Color.GREEN,
            Color.CYAN,
            Color.YELLOW
    };
    protected String previewJSON;

    public void showPreview(View view) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        previewJSON = assembler.getSkinPropertiesJSON(sharedPreferences);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.createProject, ProjectPreviewFragment.newInstance())
                .commitNow();

        setContentView(R.layout.fragment_project_preview);

        CodeView preview = findViewById(R.id.previewTextView);

        assert preview != null;
        preview.setText(previewJSON);

        for (int i = 0; i < regexHighlighting.length; i++) {
            preview.addSyntaxPattern(regexHighlighting[i], highlightingColors[i]);
        }

        preview.reHighlightSyntax();
    }

    private String getFilepath(String uri) throws UnsupportedEncodingException {
        final String filepath = URLDecoder.decode(uri, "utf-8");

        if (!filepath.contains("primary:")) {
            return "";   // storageId
        } else {
            return "/storage/emulated/0/" + filepath.substring(filepath.indexOf("primary:") + 8) + "/skin.json";
        }
    }

    private void saveFile(String json, Uri filepath) {
        try {
            final String stringFilepath = getFilepath(String.valueOf(filepath));

            FileWriter fileWriter = new FileWriter(stringFilepath);

            fileWriter.write(json);
            fileWriter.close();

            Toast.makeText(this, "Saved to " + stringFilepath + "!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, "Error! Please grant me an access to the storage!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_project);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.createProject, ProjectFragment.newInstance())
                    .commitNow();
        }

        Toolbar projectToolbar = findViewById(R.id.projectToolbar);
        setSupportActionBar(projectToolbar);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.project_toolbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem menuItem) {
        int menuItemId = menuItem.getItemId();

        if (menuItemId == R.id.action_save_project) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivityForResult(Intent.createChooser(intent, "Choose save destination"), 1);

            return true;
        } else if (menuItemId == R.id.action_project_save_as) {
            return true;
        } else {
            return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            assert data != null;

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

            try {
                final String configuredSkinProperties = assembler.getSkinPropertiesJSON(sharedPreferences);

                saveFile(configuredSkinProperties, data.getData());
            } catch (Exception e) {
                e.printStackTrace();

                Toast.makeText(
                        this, "Error! You've inserted parameters with wrong data types!", Toast.LENGTH_LONG
                ).show();
            }
        }
    }

    @Override
    public boolean onPreferenceStartFragment(@NonNull PreferenceFragmentCompat caller, @NonNull Preference pref) {
        final Bundle args = pref.getExtras();
        final Fragment fragment = getSupportFragmentManager().getFragmentFactory().instantiate(
                getClassLoader(),
                Objects.requireNonNull(pref.getFragment())
        );

        fragment.setArguments(args);
        fragment.setTargetFragment(caller, 0);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.createProject, fragment)
                .addToBackStack(null)
                .commit();

        return true;
    }
}