package com.lele.menuview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lele.menulibrary.AddMenuView;

public class MainActivity extends AppCompatActivity {
    private Context context;
    TextView tx_test;
    LinearLayout ll_test;
    int[] drawbleIds = {R.drawable.bg_intelligent_online_add, R.drawable.bg_manually_add, R.drawable.bg_sweep_one_sweep_add};
    String[] strs = {"大结局", "的代价", "多多开导开导可靠的"};
    AddMenuView addMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        tx_test = (TextView) findViewById(R.id.tx_test);
        ll_test = (LinearLayout) findViewById(R.id.ll_test);
        addMenuView = new AddMenuView(context, drawbleIds, strs);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        addMenuView.setLayoutParams(params);
        addMenuView.setOnItemClickListener(new AddMenuView.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.e("hdltag", "onItemClick(MainActivity.java:34):position="+position);
            }

            @Override
            public void hideAnimationEnd() {
                Log.e("leleTest","hideAnimationEnd()");
            }
        });
        addMenuView.setTopHeight(dip2px(context,45));
        ll_test.addView(addMenuView);
        tx_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addMenuView.isShow()) {
                    addMenuView.hideAnimation();
                } else {
                    addMenuView.showAnimation();
                }
            }
        });
    }

    public static int dip2px(Context context, int dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
