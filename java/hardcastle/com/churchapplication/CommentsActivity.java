package hardcastle.com.churchapplication;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import hardcastle.com.churchapplication.Adapter.CommentsAdapter;
import hardcastle.com.churchapplication.R;
import hardcastle.com.churchapplication.model.CommentsBean;

public class CommentsActivity extends AppCompatActivity {

    RecyclerView rvComment;
    ArrayList<CommentsBean> commentList;
    EditText edComment;
    CommentsAdapter commentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        rvComment=findViewById(R.id.rvComment);
        edComment=findViewById(R.id.commentsEdText);

        commentList=new ArrayList<>();

        commentList.add(new CommentsBean("user 1","comment 1",""));
        commentList.add(new CommentsBean("user 2","comment 1",""));
        commentList.add(new CommentsBean("user 3","comment 1",""));
        commentList.add(new CommentsBean("user 4","comment 1",""));
        commentList.add(new CommentsBean("user 5","comment 1",""));
        commentList.add(new CommentsBean("user 6","comment 1",""));
        commentList.add(new CommentsBean("user 7","comment 1",""));
        commentList.add(new CommentsBean("user 8","comment 1",""));

        commentsAdapter = new CommentsAdapter(commentList);
        rvComment.setAdapter(commentsAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvComment.setLayoutManager(llm);
    }

    public void onSend(View view) {


        Toast.makeText(this, "Comment Added", Toast.LENGTH_SHORT).show();
        commentList.add(new CommentsBean("You",edComment.getText().toString(),""));
        commentsAdapter.notifyDataSetChanged();
        rvComment.setFocusable(true);
        edComment.setText("");
        hideKeyboardFrom(


        );
    }


    public  void hideKeyboardFrom() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
