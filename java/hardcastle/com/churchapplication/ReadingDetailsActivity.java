package hardcastle.com.churchapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReadingDetailsActivity extends AppCompatActivity {

    private TextView txtTotalUnit;
    private TextView txtGST;
    private TextView txtFinalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_details);

        txtTotalUnit = findViewById(R.id.txt_total_unit);
        txtGST = findViewById(R.id.txt_gst);
        txtFinalAmount = findViewById(R.id.txt_final_amount);

        Intent i = getIntent();
        Bundle bundle = i.getBundleExtra("BillDetails");
        String totalUnit = bundle.getString("unit");
        String finalAmount = bundle.getString("finalAmount");

    }
}
