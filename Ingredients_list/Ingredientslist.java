package com.example.akash.cook;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class Ingredientslist extends AppCompatActivity {

    String category;
    FloatingActionButton floatingActionButton;
    SearchView searchView;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.cbSelectAll)
    CheckBox cbSelectAll;

    @BindView(R.id.tvSelect)
    TextView tvSelect;
    StudentAdapter mAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();       // hide the title bar
        setContentView(R.layout.activity_ingredientslist);

        searchView=findViewById(R.id.searchView);
        floatingActionButton=findViewById(R.id.floatingActionButton2);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        Bundle b=getIntent().getExtras();
        category=b.getString("key","Akash");
        toolbar.setTitle(category);
        toolbar.setNavigationIcon(R.drawable.ic_backspace);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // to go back  finish() will do your work.
            }
        });

        ButterKnife.bind(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setNestedScrollingEnabled(false);
        mAdapter = new StudentAdapter(this, prepareData());
        //mAdapter.notifyDataSetChanged();
        recycler_view.setAdapter(mAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder listString=new StringBuilder();
                for (int i=0;i<StudentAdapter.finallist.size();i++){
                    listString.append(StudentAdapter.finallist.get(i));
                    listString.append(" ");
                }
                Toast.makeText(getApplicationContext(),listString,Toast.LENGTH_LONG).show();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {  //search view
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });



    }

    @OnCheckedChanged(R.id.cbSelectAll)
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {  //select all
        if (mAdapter != null) {
            mAdapter.toggleSelection(isChecked);
            tvSelect.setText(isChecked ? "Deselect All" : "Select All");
        }
    }

    public List<StudentModel> prepareData() {
            List<StudentModel> studentList = new ArrayList<>();
            String myJSONStr = loadJSONFromRaw();
            try {
                //Get root JSON object node
                JSONObject rootJSONObject = new JSONObject(myJSONStr);
                //Get ingredient_list array node
                JSONArray iJSONArray = rootJSONObject.getJSONArray(category);
                for (int i = 0; i < iJSONArray.length(); i++) {
                    //Create a temp object of the ingredient model class
                    StudentModel aIngredient = new StudentModel();

                    //Get ingredient JSON object node
                    JSONObject jsonObject = iJSONArray.getJSONObject(i);

                    //Get name of ingredients
                    aIngredient.setName(jsonObject.getString("name"));
                    studentList.add(aIngredient);
                }
                return studentList;
                } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
    }

    public String loadJSONFromRaw()
    {
        String json;
        try
        {
            InputStream is = getResources().openRawResource(R.raw.ingredients_list);
            int size = is.available();
            byte[] buffer = new byte[size];
            //noinspection ResultOfMethodCallIgnored
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}