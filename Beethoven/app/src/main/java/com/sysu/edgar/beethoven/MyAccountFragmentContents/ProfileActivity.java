package com.sysu.edgar.beethoven.MyAccountFragmentContents;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sysu.edgar.beethoven.R;

/**
 * Created by Edgar on 2016/7/10.
 */
public class ProfileActivity extends AppCompatActivity {

    private String currentNickName, newNickName;
    private int currentAvatarId;
    private TextView curnickname = null;
    private ImageView curavatar = null;
    private EditText newname = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.MyThemeStyle);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Bundle bundle = this.getIntent().getBundleExtra("PassValue");
        currentNickName = bundle.getString("curNickName");
        currentAvatarId = bundle.getInt("curAvatarId");
        newNickName = currentNickName;

        curnickname = (TextView)this.findViewById(R.id.current_nickname);
        curavatar = (ImageView)this.findViewById(R.id.current_avatar);
        newname = (EditText)this.findViewById(R.id.new_nickname);

        curnickname.setText(currentNickName);
        curavatar.setImageResource(currentAvatarId);
        curavatar.setScaleType(ImageView.ScaleType.CENTER);

        ImageButton btn_back = (ImageButton)this.findViewById(R.id.about_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("NewNickName", newNickName);
                setResult(0, intent);
                finish();
            }
        });

        Button btn_confirm = (Button)this.findViewById(R.id.profile_confirm_modify);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newname.length() == 0) {
                    Intent intent = new Intent();
                    intent.putExtra("NewNickName", newNickName);
                    setResult(0, intent);
                    finish();
                } else {
                    newNickName = newname.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra("NewNickName", newNickName);
                    setResult(0, intent);
                    finish();
                }
            }
        });

    }
}
