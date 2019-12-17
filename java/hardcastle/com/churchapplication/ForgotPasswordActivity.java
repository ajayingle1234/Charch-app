package hardcastle.com.churchapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hardcastle.com.churchapplication.Utils.RandomString;

public class ForgotPasswordActivity extends AppCompatActivity {

    @BindView(R.id.input_email)
    EditText input_email;
    @BindView(R.id.input_captcha)
    EditText input_captcha;
    @BindView(R.id.txt_captcha)
    TextView txt_captcha;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.btnCancle)
    Button btnCancle;
    private View parentLayout;


    private Random random;

    private char[] symbols;

    private char[] buf;
    private Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_forgot_password);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("Forgot Password");
        setActionBar(toolbar);

        parentLayout = findViewById(R.id.layout_main);
        ButterKnife.bind(this);
        RandomString gen = new RandomString(8, ThreadLocalRandom.current());

        //setTitle("Forgot Password");
        String easy = "WzXK48YPML";
        txt_captcha.setText(easy.toString());


    }

    public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

    @OnClick(R.id.btnSubmit)
    public void forgotSubmit() {
        if (validate())
            showAlert();

    }

    private void showAlert() {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Info..");
        builder.setMessage("Your password reset email has been sent!\n" +
                "\n" +
                "We have sent a password reset email to your email address:\n" +
                "\n" +
                 input_email.getText().toString()+"\n" +
                "\n" +
                "Please check your inbox to continue.");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
       /* builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });*/
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick(R.id.btnCancle)
    public void forgotCancel() {
        finish();

    }

    public boolean validate() {
        try {
            if (input_email.getText().toString().isEmpty()) {

                Snackbar.make(parentLayout, "Please enter email id", Snackbar.LENGTH_SHORT).show();
                return false;
            }else if (!isValidEmail(input_email.getText().toString().trim())) {
                Snackbar.make(parentLayout, "Please enter valid email id", Snackbar.LENGTH_SHORT).show();
                return false;
            }else if (input_captcha.getText().toString().trim().length() == 0) {
                Snackbar.make(parentLayout, "Please enter above text", Snackbar.LENGTH_SHORT).show();
                return false;
            } else if (!input_captcha.getText().toString().equals(txt_captcha.getText().toString())) {
                Snackbar.make(parentLayout, "Please enter correct Captcha", Snackbar.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
