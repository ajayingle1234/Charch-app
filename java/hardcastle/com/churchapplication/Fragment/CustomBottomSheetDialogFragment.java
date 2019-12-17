package hardcastle.com.churchapplication.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hardcastle.com.churchapplication.R;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

@SuppressLint("ValidFragment")
public class CustomBottomSheetDialogFragment extends BottomSheetDialogFragment {

    Button btn_continue;
    TextView textView;
    ScrollView scrollView;
    private SharedPreferences prefs;

    @BindView(R.id.button_font_type)
    Button btn_font;
    @BindView(R.id.button_font_type1)
    Button btn_font1;
    @BindView(R.id.button_theme_day)
    Button btn_theme_day;
    @BindView(R.id.button_theme_night)
    Button btn_theme_night;

    @BindView(R.id.img_close)
    ImageView img_close;
    @BindView(R.id.seekbar_allignment)
    SeekBar seekbar_text_allignement;
    @BindView(R.id.seekbar_text_fontsize)
    SeekBar seekbar_text_size;
    @BindView(R.id.seekbar_linespacing)
    SeekBar seekbar_text_spacing;

    @SuppressLint("ValidFragment")
    public CustomBottomSheetDialogFragment(TextView textView, ScrollView scrollView) {
        this.textView = textView;
        this.scrollView = scrollView;
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            // setStateText(newState);
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            //  setOffsetText(slideOffset);
        }
    };
    private LinearLayoutManager mLinearLayoutManager;
    private Unbinder unbinder;
    //  private ApplicationAdapter mAdapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onViewCreated(View contentView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(contentView, savedInstanceState);


//
//        recyclerView.setLayoutManager(mLinearLayoutManager);
//        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
       /* float fs = prefs.getFloat("fontsize", 12);
        float fprogress = prefs.getFloat("fontprogress", 30);
        seekbar_text_size.setProgress((int) fprogress);
        textView.setTextSize(fs);

        seekbar_text_spacing.setProgress((int) prefs.getFloat("linespacingprogerss", 30));
        textView.setLineSpacing(prefs.getFloat("linepsacing", 1), prefs.getFloat("linepsacing", 1));


        seekbar_text_allignement.setProgress((int) prefs.getInt("seekprogresspadding", 30));
        seekbar_text_size.setPadding(prefs.getInt("paddingleft", 1), 0, prefs.getInt("paddingright", 1), 0);*/
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View contentView = View.inflate(getContext(), R.layout.custombottomsheet, null);
        unbinder = ButterKnife.bind(this, contentView);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
        // btn_continue = (Button) contentView.findViewById(R.id.button_continue);
        prefs = getApplicationContext().getSharedPreferences("bible", MODE_PRIVATE);


        seekbar_text_size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SharedPreferences.Editor ed = prefs.edit();
                ed.putFloat("fontsize", textView.getTextSize());
                ed.putFloat("fontprogress", seekBar.getProgress());
                ed.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                if (progress <= 20) {
                    textView.setTextSize(16);
                }

                if (progress >= 20 && progress <= 40) {
                    textView.setTextSize(25);
                }

                if (progress >= 40 && progress <= 60) {
                    textView.setTextSize(30);
                }

                if (progress >= 60 && progress <= 100) {
                    textView.setTextSize(35);
                }

            }
        });

        seekbar_text_spacing.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SharedPreferences.Editor ed = prefs.edit();
                ed.putFloat("linepsacing", textView.getLineSpacingExtra());
                ed.putFloat("linespacingprogerss", seekBar.getProgress());
                ed.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (progress <= 20) {
                    textView.setLineSpacing(1f, 1f);
                } else {
                    textView.setLineSpacing(progress / 20, progress / 20);
                }
            }
        });

        seekbar_text_allignement.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SharedPreferences.Editor ed = prefs.edit();
                ed.putInt("paddingleft", textView.getPaddingLeft());
                ed.putInt("paddingright", textView.getPaddingRight());
                ed.putInt("seekprogresspadding", seekBar.getProgress());
                ed.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (progress <= 20) {
                    textView.setPadding(0, 0, 0, 0);
                }

                if (progress >= 20 && progress <= 40) {
                    textView.setPadding(10, 0, 10, 0);
                }

                if (progress >= 40 && progress <= 60) {
                    textView.setPadding(20, 0, 20, 0);
                }

                if (progress >= 60 && progress <= 80) {
                    textView.setPadding(30, 0, 30, 0);
                }

                if (progress >= 80 && progress <= 100) {
                    textView.setPadding(40, 0, 40, 0);
                }
            }
        });

    }

    @OnClick(R.id.img_close)
    public void updateUser() {
        dismiss();
    }

    @OnClick(R.id.button_theme_day)
    public void changeThemeDay() {
        btn_theme_day.setBackground(getResources().getDrawable(R.drawable.button_dark));
        btn_theme_night.setBackground(getResources().getDrawable(R.drawable.white_button));
        scrollView.setBackgroundColor(getResources().getColor(R.color.white));
        textView.setTextColor(getResources().getColor(R.color.black));
    }

    @OnClick(R.id.button_theme_night)
    public void changeThemeNight() {

        btn_theme_day.setBackground(getResources().getDrawable(R.drawable.white_button));
        btn_theme_night.setBackground(getResources().getDrawable(R.drawable.button_dark));
        scrollView.setBackgroundColor(getResources().getColor(R.color.black));
        textView.setTextColor(getResources().getColor(R.color.white));
    }

    @OnClick(R.id.button_font_type)
    public void setFont1() {

        Typeface face = Typeface.createFromAsset(getActivity().getAssets(),
                "font/serifitalic.otf");
        textView.setTypeface(face);
    }

    @OnClick(R.id.button_font_type1)
    public void setFont2() {
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(),
                "font/anonymous.ttf");
        textView.setTypeface(face);

    }
}
