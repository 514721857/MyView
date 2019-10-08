package com.example.myview.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myview.R;
import com.example.myview.view.CircleBarView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CircleBarActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @BindView(R.id.circle_view)
    CircleBarView circle_view;

//    @BindView(R.id.circle_view1)
//    CircleBarView circle_view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_bar);
        unbinder = ButterKnife.bind(this);
        circle_view.setProgressNum(3000);
//        circle_view1.setProgressNum(3000);
    }
}
