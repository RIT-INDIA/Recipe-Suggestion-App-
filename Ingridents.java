package com.example.letscook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class Ingridents extends AppCompatActivity implements View.OnClickListener {
//Dialog mydialog;
ImageView vegetable,dairy,fruits,meats;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    String item = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingridents);
        vegetable = findViewById(R.id.imageView10);
        dairy = findViewById(R.id.imageView11);
        fruits=findViewById(R.id.imageView12);
        meats=findViewById(R.id.imageView13);
        vegetable.setOnClickListener(this);
        dairy.setOnClickListener(this);
        fruits.setOnClickListener(this);
        dairy.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
    int id=v.getId();
    switch (id)
    {
        case R.id.imageView10:

                listitems(R.array.Vegetable,R.string.dialog_title1);
                break;
        case R.id.imageView11:
                   listitems(R.array.Dairy,R.string.dialog_title2);
                   break;
        case R.id.imageView12:
            listitems(R.array.Fruits,R.string.dialog_title3);
            break;
        case R.id.imageView13:
            listitems(R.array.Meats,R.string.dialog_title4);
            break;

         }
    }


public void listitems(int ingrediants, int name)
{
    listItems = getResources().getStringArray(ingrediants);
    checkedItems = new boolean[listItems.length];
    AlertDialog.Builder mBuilder = new AlertDialog.Builder(Ingridents.this);
    mBuilder.setTitle(name);
    mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int position, boolean isChecked) {
            if (isChecked) {
                mUserItems.add(position);
            } else {
                mUserItems.remove((Integer.valueOf(position)));
            }
        }
    });
    mBuilder.setCancelable(false);
    mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int which) {

            for (int i = 0; i < mUserItems.size(); i++) {
                item = item + listItems[mUserItems.get(i)];
                if (i != mUserItems.size() - 1) {
                    item = item + ", ";

                }

            }
            for (int i = 0; i < checkedItems.length; i++) {
                checkedItems[i] = false;
                mUserItems.clear();

            }

            Toast.makeText(getApplicationContext(), item, Toast.LENGTH_LONG).show();
        }
    });
    mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    });

    mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            for (int i = 0; i < checkedItems.length; i++) {
                checkedItems[i] = false;
                mUserItems.clear();

            }
        }
    });

    AlertDialog mDialog = mBuilder.create();
    mDialog.show();
}





}




