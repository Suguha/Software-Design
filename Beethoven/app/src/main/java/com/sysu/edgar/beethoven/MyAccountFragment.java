package com.sysu.edgar.beethoven;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sysu.edgar.beethoven.MyAccountFragmentContents.AboutActivity;
import com.sysu.edgar.beethoven.MyAccountFragmentContents.ProfileActivity;

/**
 * Created by Edgar on 2016/7/8.
 */
public class MyAccountFragment extends Fragment {

    private LinearLayout account_layout = null;
    private boolean logined = false;
    private ImageView avatar = null;
    private TextView name = null;
    private ImageView nav_arrow = null;
    private String curNickName = "在这里显示用户的昵称";
    private int curAvatarId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getActivity().setTheme(R.style.MyThemeStyle);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View account_fragment_view = inflater.inflate(R.layout.account_fragment_layout, container, false);

        Bundle tmp = getArguments();
        logined = tmp.getBoolean("hasLogined");
        avatar = (ImageView)account_fragment_view.findViewById(R.id.account_avatar);
        name = (TextView)account_fragment_view.findViewById(R.id.account_name);
        nav_arrow = (ImageView)account_fragment_view.findViewById(R.id.account_arrow);

        account_layout = (LinearLayout)account_fragment_view.findViewById(R.id.account_layout);
        account_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Hello account!");
                //todo log in here;
            }
        });
        setAccountData(logined);

        Button btn_about = (Button)account_fragment_view.findViewById(R.id.account_about);
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout btn_profile = (LinearLayout) account_fragment_view.findViewById(R.id.account_profile);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (logined) {
                    Bundle bundle = new Bundle();
                    bundle.putString("curNickName", curNickName);
                    bundle.putInt("curAvatarId", curAvatarId);
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ProfileActivity.class);
                    intent.putExtra("PassValue", bundle);
                    startActivityForResult(intent, 0);
                } else {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btn_history_order = (Button)account_fragment_view.findViewById(R.id.account_history_order);
        btn_history_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logined) {
                    System.out.println();
                } else {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return account_fragment_view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String newNickName = data.getStringExtra("NewNickName");
            if (newNickName.length() != 0) {
                name.setText(newNickName);
                curNickName = newNickName;
            }
        }
    }

    public void setAccountData(boolean hasLogin) {

        if (hasLogin) {
            avatar.setImageResource(R.drawable.ic_movie_black_24dp);
            avatar.setScaleType(ImageView.ScaleType.CENTER);
            name.setText(curNickName);
            nav_arrow.setAlpha(0.0f); //隐藏该箭头
            logined = true;
            account_layout.setClickable(false);
            curAvatarId = R.drawable.ic_movie_black_24dp;

        } else {
            avatar.setImageResource(R.drawable.ic_photo_black_100dp);
            avatar.setScaleType(ImageView.ScaleType.CENTER);
            name.setText("请登录");
            nav_arrow.setAlpha(1.0f); //隐藏该箭头
            logined = false;
            account_layout.setClickable(true);
        }
    }

}
