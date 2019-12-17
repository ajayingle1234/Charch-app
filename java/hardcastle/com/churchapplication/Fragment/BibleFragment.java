package hardcastle.com.churchapplication.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.model.BookmarkPojo;
import hardcastle.com.churchapplication.model.HighLightPojo;
import yuku.ambilwarna.AmbilWarnaDialog;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class BibleFragment extends Fragment implements TextToSpeech.OnInitListener, BookmarkBottomSheetDialogFragment.PositionPassListener, BookmarkBottomSheetDialogFragment.DeletePositionPassListener {

    BottomSheetDialogFragment bottomSheetDialogFragment;
    private Unbinder unbinder;

    private List<BookmarkPojo> listBookmarks;
    private ArrayList<HighLightPojo> listHighlights;

    private SharedPreferences prefs;
    Spannable wordtoSpan;
    String title;

    TextToSpeech textToSpeech;


    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.article)
    TextView article;
    @BindView(R.id.previous)
    ImageView imageView_previous;
    @BindView(R.id.next)
    ImageView imageView_next;
    @BindView(R.id.sound)
    ImageView imageView_sound;
    @BindView(R.id.font)
    ImageView imageView_font;

    @BindView(R.id.highlight)
    ImageView highlight;

    @BindView(R.id.img_saved_bookmarks)
    ImageView imageView_savedBookmarks;
    @BindView(R.id.img_search)
    ImageView imageView_search;
    @BindView(R.id.img_bookmark)
    ImageView imageView_bookmark;
    int x, y;

    public BibleFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BibleFragment newInstance() {
        BibleFragment fragment = new BibleFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bible, container, false);
        unbinder = ButterKnife.bind(this, view);
        listBookmarks = new ArrayList<>();
        listHighlights = new ArrayList<>();
        textToSpeech = new TextToSpeech(getActivity(), this);

        /*article.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                int min = 0;
                int max = article.getText().length();
                if (article.isFocused()) {
                    final int selStart = article.getSelectionStart();
                    final int selEnd = article.getSelectionEnd();

                    min = Math.max(0, Math.min(selStart, selEnd));
                    max = Math.max(0, Math.max(selStart, selEnd));
                }

                return false;
            }
        });*/

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        wordtoSpan = new SpannableString(this.getString(R.string.text_with_paragraphs));

        if (getArrayList("listHighlights") != null) {

            listHighlights = getArrayList("listHighlights");
        }
        if (listHighlights.size() != 0) {
            for (int i = 0; i < listHighlights.size(); i++) {
                wordtoSpan.setSpan(new BackgroundColorSpan(listHighlights.get(i).getBackgroundColor()), listHighlights.get(i).getStart(), listHighlights.get(i).getEnd(), listHighlights.get(i).getFlag());
            }
            article.setText(wordtoSpan);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        prefs = getApplicationContext().getSharedPreferences("bible", MODE_PRIVATE);
       /* float fs = prefs.getFloat("fontsize", 12);

        float lineSpacing = prefs.getFloat("linepsacing", 1);

        int paddingLeft = prefs.getInt("paddingleft", 0);
        int paddingRight = prefs.getInt("paddingright", 0);

        // if (fs != 0.0 && lineSpacing != 0.0 && paddingLeft != 0 && paddingRight != 0) {
        article.setTextSize(fs);
        article.setLineSpacing(lineSpacing, lineSpacing);
        article.setPadding(paddingLeft, 0, paddingRight, 0);*/


        article.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    x = (int) event.getX();
                    y = (int) event.getY();
                }

                return false;
            }
        });
        //  }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.highlight)
    public void highlight() {

        final int min, max;
        final int selStart = article.getSelectionStart();
        final int selEnd = article.getSelectionEnd();


        min = Math.max(0, Math.min(selStart, selEnd));
        max = Math.max(0, Math.max(selStart, selEnd));

        final int[] DefaultColor = {getContext().getColor(R.color.tab_background)};
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(getContext(), DefaultColor[0], true, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int color) {

                DefaultColor[0] = color;

                if (article.isFocused()) {


                    if (max != 0) {
                        title = article.getText().subSequence(min, max).toString();
                        listHighlights.add(new HighLightPojo(DefaultColor[0], min, max, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE, x, y, title));

                        for (int i = 0; i < listHighlights.size(); i++) {
                            wordtoSpan.setSpan(new BackgroundColorSpan(listHighlights.get(i).getBackgroundColor()), listHighlights.get(i).getStart(), listHighlights.get(i).getEnd(), listHighlights.get(i).getFlag());
                        }


                        article.setText(wordtoSpan);
                        Log.i("SCrollpos", x + "--" + y);
                        scrollView.smoothScrollTo(x, y - 100);
                        saveArrayList(listHighlights, "listHighlights");


                    }
                }

            }

            @Override
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {

                Toast.makeText(getContext(), "Color Picker Closed", Toast.LENGTH_SHORT).show();
            }
        });
        if (max != 0) {
            ambilWarnaDialog.show();
        }


        //Spannable wordtoSpan = new SpannableString(this.getString(R.string.text_with_paragraphs));
       /* wordtoSpan.setSpan(new BackgroundColorSpan(Color.YELLOW), min, max, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new BackgroundColorSpan(Color.BLUE), 1, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        */
      /* title=article.getText().subSequence(start,end).toString();
       listHighlights.add(new HighLightPojo(new BackgroundColorSpan(Color.YELLOW), min, max, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,title));

        for (int i = 0; i <listHighlights.size() ; i++) {
            wordtoSpan.setSpan(listHighlights.get(i).getBackgroundColorSpan(),listHighlights.get(i).getStart(),listHighlights.get(i).getEnd(),listHighlights.get(i).getFlag());
        }


       article.setText(wordtoSpan);*/
    }


    @OnClick(R.id.sound)
    public void textTospeech() {
        String toSpeak = article.getText().toString();

        if (textToSpeech.isSpeaking()) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        } else {
            textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        }
      /*  if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }*/
    }

    @Override
    public void onInit(int status) {
        if (status != TextToSpeech.ERROR) {
            textToSpeech.setLanguage(Locale.UK);
        }
    }

    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }

    @OnClick(R.id.font)
    public void updateUser() {

        bottomSheetDialogFragment = new CustomBottomSheetDialogFragment(article, scrollView);
        bottomSheetDialogFragment.show(getFragmentManager(), bottomSheetDialogFragment.getTag());
    }

    @OnClick(R.id.img_saved_bookmarks)
    public void savedBookmarks() {

        bottomSheetDialogFragment = new BookmarkBottomSheetDialogFragment(this, this, listBookmarks, listHighlights);
        bottomSheetDialogFragment.show(getFragmentManager(), bottomSheetDialogFragment.getTag());
    }

    @OnClick(R.id.img_search)
    public void searchContents() {

        bottomSheetDialogFragment = new SearchBottomSheetDialogFragment(article, scrollView);
        bottomSheetDialogFragment.show(getFragmentManager(), bottomSheetDialogFragment.getTag());
    }


    @OnClick(R.id.img_bookmark)
    public void book() {
        if (imageView_bookmark.getTag().equals("colorblack")) {
            imageView_bookmark.setTag("colorred");
            imageView_bookmark.setImageResource(R.mipmap.ic_bookmark_filled_red);
            listBookmarks.add(new BookmarkPojo("Bookmark 1", article.getText().toString()));
        } else if (imageView_bookmark.getTag().equals("colorred")) {
            listBookmarks.clear();
            imageView_bookmark.setTag("colorblack");
            imageView_bookmark.setImageResource(R.mipmap.ic_bookmark);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void passPosition(int data) {
        Log.i("SCrollposOnclick", x + "--" + y);
        scrollView.smoothScrollTo(listHighlights.get(data).getX(), listHighlights.get(data).getY() - 100);
    }


    public void saveArrayList(ArrayList<HighLightPojo> list, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public ArrayList<HighLightPojo> getArrayList(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<HighLightPojo>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public void passPositionToDelete(int pos) {
        listHighlights.remove(pos);
        //listHighlights.clear();
        saveArrayList(listHighlights, "listHighlights");
        listHighlights = getArrayList("listHighlights");

        wordtoSpan = new SpannableString(this.getString(R.string.text_with_paragraphs));

        if (getArrayList("listHighlights") != null) {

            listHighlights = getArrayList("listHighlights");
        }
        if (listHighlights.size() != 0) {
            for (int i = 0; i < listHighlights.size(); i++) {
                wordtoSpan.setSpan(new BackgroundColorSpan(listHighlights.get(i).getBackgroundColor()), listHighlights.get(i).getStart(), listHighlights.get(i).getEnd(), listHighlights.get(i).getFlag());
            }
        }

        article.setText(wordtoSpan);
    }
}
