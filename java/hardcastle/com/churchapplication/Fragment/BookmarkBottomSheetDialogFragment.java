package hardcastle.com.churchapplication.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hardcastle.com.churchapplication.Adapter.BookmarkAdapter;
import hardcastle.com.churchapplication.Adapter.HighLightAdapter;
import hardcastle.com.churchapplication.Adapter.NotificationAdapter;
import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.model.BookmarkPojo;
import hardcastle.com.churchapplication.model.HighLightPojo;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class BookmarkBottomSheetDialogFragment extends BottomSheetDialogFragment implements HighLightAdapter.OnItemClickListener, HighLightAdapter.OnItemLongClickListener {

    private BookmarkAdapter adapter;
    private HighLightAdapter highlightAdapter;


    PositionPassListener positionPassListener;
    DeletePositionPassListener deletePositionPassListener;


    public interface PositionPassListener {
        void passPosition(int pos);
    }

    public interface DeletePositionPassListener {
        void passPositionToDelete(int pos);
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
            bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
    }

    TextView textView;
    ScrollView scrollView;
    private SharedPreferences prefs;
    List<BookmarkPojo> bookmarkPojos;
    List<HighLightPojo> highLightPojos;

    @BindView(R.id.button_highlights)
    Button buttonHighlights;
    @BindView(R.id.button_font_notes)
    Button buttonNotes;
    @BindView(R.id.button_bookmarks)
    Button buttonBookmarks;
    @BindView(R.id.img_close)
    ImageView img_close;
    @BindView(R.id.layout_no_bookmarks)
    LinearLayout layout_no_bookmarks;

    @BindView(R.id.bookmark_recyclerView)
    RecyclerView recyclerView;


    public BookmarkBottomSheetDialogFragment(DeletePositionPassListener deletePositionPassListener,PositionPassListener positionPassListener, List<BookmarkPojo> bookmarkPojos, List<HighLightPojo> highLightPojos) {
        this.deletePositionPassListener=deletePositionPassListener;
        this.positionPassListener = positionPassListener;
        this.bookmarkPojos = bookmarkPojos;
        this.highLightPojos = highLightPojos;
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

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View contentView = View.inflate(getContext(), R.layout.bookmark_bottom_sheet_dialog, null);
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
    }

    @OnClick(R.id.img_close)
    public void updateUser() {
        dismiss();
    }


    @OnClick(R.id.button_bookmarks)
    public void Savedbookmarks() {

        buttonBookmarks.setBackground(getResources().getDrawable(R.drawable.gray_white));
        buttonHighlights.setBackground(getResources().getDrawable(R.drawable.white_button));
        buttonNotes.setBackground(getResources().getDrawable(R.drawable.white_button));

        if (bookmarkPojos.size() == 0) {
            layout_no_bookmarks.setVisibility(View.VISIBLE);
        } else {
            adapter = new BookmarkAdapter(getActivity(), bookmarkPojos);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }

    @OnClick(R.id.button_font_notes)
    public void notes() {

        buttonBookmarks.setBackground(getResources().getDrawable(R.drawable.white_button));
        buttonHighlights.setBackground(getResources().getDrawable(R.drawable.white_button));
        buttonNotes.setBackground(getResources().getDrawable(R.drawable.gray_white));

        if (bookmarkPojos.size() == 0) {
            layout_no_bookmarks.setVisibility(View.VISIBLE);
        } else {
            adapter = new BookmarkAdapter(getActivity(), bookmarkPojos);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }

    @OnClick(R.id.button_highlights)
    public void highlights() {

        buttonBookmarks.setBackground(getResources().getDrawable(R.drawable.white_button));
        buttonHighlights.setBackground(getResources().getDrawable(R.drawable.gray_white));
        buttonNotes.setBackground(getResources().getDrawable(R.drawable.white_button));
        highlightAdapter = new HighLightAdapter(getActivity(), highLightPojos, this,this);

        if (highLightPojos.size() == 0) {
            layout_no_bookmarks.setVisibility(View.VISIBLE);
        } else {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(highlightAdapter);
        }
    }


    @Override
    public void onItemClicked(View view, int pos) {
        dismiss();
        positionPassListener.passPosition(pos);

    }


    @Override
    public void onItemLongClicked(View view, int pos) {
        deletePositionPassListener.passPositionToDelete(pos);
        highlightAdapter.notifyItemRemoved(pos);
        dismiss();   
    }
}
