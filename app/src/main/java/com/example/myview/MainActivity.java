package com.example.myview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myview.ui.CircleBarActivity;
import com.example.myview.ui.ExpandMenuActivity;
import com.example.myview.ui.MyView1Activity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MainActivity extends AppCompatActivity {
    private Unbinder unbinder;

    @BindView(R.id.myview1)
    TextView myview1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
    }
    @OnClick({R.id.myview1,R.id.myview3,R.id.myview2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myview1:
            startActivity(MainActivity.this, MyView1Activity.class);
                break;
            case R.id.myview2:
                startActivity(MainActivity.this, CircleBarActivity.class);
                break;
            case R.id.myview3:
                startActivity(MainActivity.this, ExpandMenuActivity.class);
                break;

        }
    }
    private void startActivity(Activity activity, Class aClass){
        Intent intent = new Intent(activity, aClass);
        activity.startActivity(intent);
    }
}
