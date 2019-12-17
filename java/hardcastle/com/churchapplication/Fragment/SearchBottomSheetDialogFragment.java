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
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hardcastle.com.churchapplication.Adapter.SearchListViewAdapter;
import hardcastle.com.churchapplication.R;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class SearchBottomSheetDialogFragment extends BottomSheetDialogFragment implements SearchView.OnQueryTextListener {
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
            bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
    }


    // Declare Variables

    @BindView(R.id.listview)
    ListView list;
    SearchListViewAdapter adapter;
    String[] searchedList;
    List<String> arraylist = new ArrayList<String>();

    TextView textView;
    ScrollView scrollView;
    private SharedPreferences prefs;

    @BindView(R.id.img_close)
    Button img_close;

    @BindView(R.id.search)
    SearchView editTextSearch;

    @SuppressLint("ValidFragment")
    public SearchBottomSheetDialogFragment(TextView textView, ScrollView scrollView) {
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

//        recyclerView.setLayoutManager(mLinearLayoutManager);
//        recyclerView.setAdapter(mAdapter);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View contentView = View.inflate(getContext(), R.layout.search_bottom_sheet_dialog, null);
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


        searchText();

    }

    private void searchText() {

        // Generate sample data



        searchedList = new String[]{"God", "darkness", "d",
                "water", "t", "earth"};

        // Locate the ListView in listview_main.xml

        for (int i = 0; i < searchedList.length; i++) {
            String searchedText = searchedList[i];
            // Binds all strings into an array
            arraylist.add(searchedText);
        }

        // Pass results to ListViewAdapter Class
        adapter = new SearchListViewAdapter(getActivity(), arraylist);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        editTextSearch.setOnQueryTextListener(this);
    }

    @OnClick(R.id.img_close)
    public void close() {
        dismiss();
    }


    @OnClick(R.id.search)
    public void search() {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }
}
